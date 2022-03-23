class Processor {
  private SimpleGPSAggregator aggeragor = new SimpleGPSAggregator();
  
  Map<String, List<SatelliteView>> satelliteViews = new HashMap<String, List<SatelliteView>>();
  Map<String, List<Integer>> satellitesUsed = new HashMap<String, List<Integer>>();
  Degrees lat;
  Degrees lng;
  double altitude;
  Date dateTime;

  void processMessage(String s) {
    try {
      //System.out.println(s.trim());
      aggeragor.parse(s);
      
      Set<String> talkers = aggeragor.getTalkers();
      
      for (String talker : talkers) {
        List<SatelliteView> views = aggeragor.getResult(talker).satelliteViews; 
        if (views != null) {
          satelliteViews.put(talker, views);
        }
        List<Integer> used = aggeragor.getResult(talker).satellitesUsed;
        if (used != null) {
          satellitesUsed.put(talker, used);
        }
      }
      
      // Perhaps "GN" should be the most reliable
      SimpleGPSAggregator.Result result = aggeragor.getResult("GN");
      if (result != null) {
        lat = result.lat;
        lng = result.lng;
        altitude = result.altitude;
        dateTime = result.dateTime;
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

Processor processor = new Processor();
