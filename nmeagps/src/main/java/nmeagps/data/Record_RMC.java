package nmeagps.data;

import java.util.Date;

public class Record_RMC extends AbstractRecord {
  public String timeString;
  public Degrees lat;
  public Degrees lng;
  public String status;
  public double speedInKnots;
  public double trackMadeGood;
  public String dateString;
  public double magneticVariation;
  public String mode;
  public String navStatus;
  public Date dateTime;
}