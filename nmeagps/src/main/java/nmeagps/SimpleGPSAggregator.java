package nmeagps;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import nmeagps.data.Degrees;
import nmeagps.data.SatelliteView;
import nmeagps.data.Trans;

/**
 * Simple convenient tool to track the basic information from NMEA GPS messages.
 * The application should create this object and call `parse` with the incoming
 * messages, then the basic information (such as location, speed, and satellites
 * in the view) will be extracted and maintained in `Result`. Because the
 * messages may come from different systems ("talkers"), `Result` will be kept
 * for each of those talkers separately.
 */
public class SimpleGPSAggregator {
  public static class Result {
    // from RMC
    public Degrees lat;
    public Degrees lng;
    public String status;
    public String mode;
    public Date dateTime;
    public double speedInKnots;
    public double trackMadeGood;

    // from GGA
    public double altitude;

    // from GSV
    public List<SatelliteView> satelliteViews;

    // from GSA
    public List<Integer> satellitesUsed;
  }

  private HashMap<String, Result> resultMap = new HashMap<>();

  private GPSParser parser = new GPSParser();

  public SimpleGPSAggregator() {
    parser.setHandler_RMC(d -> {
      Result result = getOrCreateResult(d.talker);
      result.lat = d.lat;
      result.lng = d.lng;
      result.status = Trans.RMC.status.get(d.status);
      result.mode = Trans.RMC.mode.get(d.mode);
      result.dateTime = d.dateTime;
      result.speedInKnots = d.speedInKnots;
      result.trackMadeGood = d.trackMadeGood;
    });
    parser.setHandler_GSV(d -> {
      Result result = getOrCreateResult(d.talker);
      result.satelliteViews = d.satelliteViews;
    });
    parser.setHandler_GSA(d -> {
      Result result = getOrCreateResult(d.talker);
      result.satellitesUsed = d.satellitesUsed;
    });
    parser.setHandler_GGA(d -> {
      Result result = getOrCreateResult(d.talker);
      result.altitude = d.altitude;
    });
  }

  public void parse(String s) {
    parser.parse(s);
  }

  public Set<String> getTalkers() {
    return resultMap.keySet();
  }

  public Result getResult(String talker) {
    return resultMap.get(talker);
  }

  private Result getOrCreateResult(String talker) {
    Result result = resultMap.get(talker);
    if (result == null) {
      result = new Result();
      resultMap.put(talker, result);
    }
    return result;
  }
}
