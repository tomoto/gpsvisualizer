import processing.serial.*;
import java.util.*;

// These configurations are defined as per my hardware configuraiton and location.
// Please change before using the application.
static class Config {
  // Serial port to read the data from
  final static String PORT = "COM6";
  
  // Talker to determine the location information
  // Choose the one most available and reliable in your location
  final static String PRIMARY_TAKER = "GN";
  
  // Set to true to output the received raw sentences on the console 
  final static boolean LOG_SENTENCES = false;
}  

Serial myPort;

void setup() {
  size(960, 720, P3D);
  myPort = new Serial(this, Config.PORT, 115200);
  renderer.setup();
}

void draw() {
  if (myPort.available() > 0) {
    String s = myPort.readStringUntil('\n');
    if (s != null && s.startsWith("$")) {
      processor.process(s);
    }
  }
  
  renderer.draw();
}
