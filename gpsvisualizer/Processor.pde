import nmeagps.SimpleGPSAggregator;
import nmeagps.parser.GPSParserException;

class Processor {
  private SimpleGPSAggregator aggeragor = new SimpleGPSAggregator();
  
  Map<String, List<SatelliteView>> satelliteViews = new HashMap<String, List<SatelliteView>>();
  Map<String, List<Integer>> satellitesUsed = new HashMap<String, List<Integer>>();
  Degrees lat;
  Degrees lng;
  double altitude;
  Date dateTime;
  
  void process(String s) {
    try {
      if (Config.LOG_SENTENCES) {
        System.out.println(s.trim());
      }
      
      aggeragor.parse(s);
      
      Set<String> talkers = aggeragor.getTalkers();
      
      for (String talker : talkers) {
        List<SatelliteView> views = aggeragor.getResult(talker).satelliteViews; 
        List<Integer> used = aggeragor.getResult(talker).satellitesUsed;
        if (views != null || used != null) {
          satelliteViews.put(talker, views != null ? views : Collections.EMPTY_LIST);
          satellitesUsed.put(talker, used != null ? used : Collections.EMPTY_LIST);
        }
      }
      
      SimpleGPSAggregator.Result result = aggeragor.getResult(Config.PRIMARY_TAKER);
      if (result != null) {
        lat = result.lat;
        lng = result.lng;
        altitude = result.altitude;
        dateTime = result.dateTime;
      }
      
    } catch(GPSParserException e) {
      // parse error
      // log the message only because it may be temporary
      System.err.println(e);
    } catch(Exception e) {
      // other errors
      // log the full stack trace because it is unexpected
      e.printStackTrace();
    }
  }
}

Processor processor = new Processor();
