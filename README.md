This is my personal experimental GSP satellite visualization project. This project includes the NMEA GPS message parser (Java) and the visualizer application (Processing). It worked with M5Stack's GPS Unit (AT6558) in my location. See the video at https://youtu.be/IK79sHW0ylA .

# GPS Visualizer

- Connect your GPS unit to the serial port, modify the configuration in gpsvisualizer/GPSVisualizer.pde, and run it on Processing 3.

- Screenshot:
  ![Screenshot](https://github.com/tomoto/gpsvisualizer/blob/main/screenshot.png?raw=true)

# NMEA GPS Parser

- You may use the parser module by itself if it satisfies your application's requirement. `SimpleGPSAggregator` will be the best place to start with. If you need more control, look at `GPSParser`.

- If you changed the parser code for your needs and want to use it in the GPS visualizer application, run `build-nmeagps.bat` to rebuild the jar and deploy it to the application folder.