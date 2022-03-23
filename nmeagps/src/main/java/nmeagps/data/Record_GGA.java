package nmeagps.data;

public class Record_GGA extends AbstractRecord {
  public String timeString;
  public Degrees lat;
  public Degrees lng;
  public int fixQuality;
  public int numSatellites;
  public double hdop; // horizontal dilution of precision
  public double altitude;
  public double geoidHeight;
  public int correctionAgeInSeconds;
  public String correctionStationId;
}