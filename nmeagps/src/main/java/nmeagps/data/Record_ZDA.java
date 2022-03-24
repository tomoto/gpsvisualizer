package nmeagps.data;

import java.util.Date;

public class Record_ZDA extends AbstractRecord {
  public Date dateTime; // accurate only when the time zone is Z
  public int localZoneHours;
  public int localZoneMinutes;
}