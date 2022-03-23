import processing.serial.*;
import nmeagps.SimpleGPSAggregator;
import java.util.*;

final String PORT = "COM6";

Serial myPort;

void setup() {
  size(960, 720, P3D);
  myPort = new Serial(this, PORT, 115200);
  renderer.setup();
}

void draw() {
  if (myPort.available() > 0) {
    String s = myPort.readStringUntil('\n');
    if (s != null && s.startsWith("$")) {
      processor.processMessage(s);
    }
  }
  
  renderer.draw();
}
