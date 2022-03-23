class Renderer {
  private static final float GLOBE_SIZE = 200;
  private static final float ROTATE_SPEED = 0.005;
  
  // https://www.heavy.ai/blog/12-color-palettes-for-telling-better-stories-with-your-data
  private color[] palette = new color[] {
    #e60049,
    #0bb4ff,
    #50e991,
    #e6d800,
    #9b19f5,
    #ffa300,
    #dc0ab4,
    #b3d4ff,
    #00bfa0,
  };
  
  private class ColorMap<T> {
    Map<T, Integer> colorMap = new HashMap<T, Integer>();
    
    public int get(T key) {
      if (colorMap.containsKey(key)) {
        return colorMap.get(key);
      } else {
        color c = palette[colorMap.size() % palette.length];
        colorMap.put(key, c);
        return c;
      }
    }
    
    public Set<T> keySet() {
      return colorMap.keySet();
    }
  }
  
  private PGraphics gMain;
  private PGraphics gOverlay;
  private ColorMap<String> colorMap = new ColorMap<String>();
  
  void setup() {
    gMain = createGraphics(width, height, P3D);
    gOverlay = createGraphics(width, height);
  }
  
  private void drawBackground(PGraphics g) {
    g.stroke(0);
    g.fill(50);
    g.circle(0, 0, GLOBE_SIZE*2);
    
    g.pushMatrix();
    g.textAlign(CENTER, BOTTOM);
    g.textSize(50);
    g.fill(100);
    g.rotateX(-PI/2);
    for (int i = 0; i < 4; i++) {
      g.text("SENW".charAt(i), 0, 0, -GLOBE_SIZE);
      g.rotateY(PI/2);
    }
    
    g.stroke(100, 100, 100, 100);
    g.noFill();
    g.sphereDetail(24);
    g.sphere(GLOBE_SIZE);
    
    g.popMatrix();
  }
  
  private void drawSatellites(PGraphics g) {
    for (String talker : processor.satelliteViews.keySet()) {
      int c = colorMap.get(talker);
      List<Integer> used = processor.satellitesUsed.getOrDefault(talker, Collections.EMPTY_LIST);
      for (SatelliteView v : processor.satelliteViews.get(talker)) {
        if (!v.hasValidView()) {
          continue;
        }
        
        g.pushMatrix();
        
        g.rotateZ(radians(v.azimuth));
        g.rotateX(radians(v.elevation));
        g.translate(0, GLOBE_SIZE, 0);
        
        g.stroke(c);
        g.fill(c);
        g.box(10);
        
        g.fill(used.contains(v.id) ? #cccc00 : #808080);
        g.textSize(16);
        g.textAlign(CENTER, CENTER);
        g.rotateX(PI/2);
        g.rotateZ(PI);
        g.text(String.valueOf(v.id), 0, 0, 10);
        
        g.popMatrix();
      }
    }
  }
  
  private String formatDegrees(Degrees d) {
    if (d != null) {
      return String.format("%s (%.6f\u00b0)", d.toString(), d.value);       
    } else {
      return "null";
    }
  }
  
  private void drawParameters(PGraphics g, int size) {
    g.fill(200, 200, 200);
    g.textAlign(LEFT, TOP);
    g.textSize(size*0.9);
    g.text("Lat = " + formatDegrees(processor.lat), 0, 0);
    g.text("Lng = " + formatDegrees(processor.lng), 0, size);
    g.text("Alt = " + processor.altitude, 0, size*2);
    g.text("Time = " + processor.dateTime, 0, size*3);
    
    int i = 0;
    for (String key : colorMap.keySet()) {
      g.fill(colorMap.get(key));
      //g.rect(i*size*2, size*5, size*2, size);
      g.rect(0, size*(5+i), size*4, size);
      g.fill(0);
      g.textAlign(LEFT, TOP);
      //g.text(key, (i*2+1)*size, size*5);
      g.text(String.format("%s %d/%d",
        key,
        processor.satellitesUsed.getOrDefault(key, Collections.EMPTY_LIST).size(),
        processor.satelliteViews.getOrDefault(key, Collections.EMPTY_LIST).size()),
        size*0.1,
        size*(5+i));
      i++;
    }
  }
  
  void draw() {
    gMain.beginDraw();
    float ca = frameCount * ROTATE_SPEED;
    gMain.camera(cos(ca)*GLOBE_SIZE*1, sin(ca)*GLOBE_SIZE*1, GLOBE_SIZE*0.2, 0, 0, GLOBE_SIZE*0.6, 0, 0, -1);
    gMain.background(0);
    gMain.pointLight(255, 255, 255, 0, 0, 10);
    gMain.ambientLight(200, 200, 200);
    renderer.drawBackground(gMain);
    renderer.drawSatellites(gMain);
    gMain.endDraw();
    
    gOverlay.beginDraw();
    gOverlay.background(0, 0, 0, 0);
    renderer.drawParameters(gOverlay, 20);
    gOverlay.endDraw();
    
    image(gMain, 0, 0);
    image(gOverlay, 10, 10);
  }
}

Renderer renderer = new Renderer();
