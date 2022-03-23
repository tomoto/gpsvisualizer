package nmeagps.data;

import java.util.List;

public class Record_GSA extends AbstractRecord {
  public String mode1;
  public int mode2;
  public List<Integer> satellitesUsed;
  public double pdop; // position dilution of precision
  public double hdop; // horizontal dilution of precision
  public double vdop; // vertical dilution of precision
}