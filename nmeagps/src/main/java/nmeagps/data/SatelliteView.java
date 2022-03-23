package nmeagps.data;

public class SatelliteView {
  public final int id;
  public final int elevation;
  public final int azimuth;
  public final int snr;

  public SatelliteView(int id, int elevation, int azimuth, int snr) {
    this.id = id;
    this.elevation = elevation;
    this.azimuth = azimuth;
    this.snr = snr;
  }

  public boolean hasValidView() {
    return elevation >= 0 && azimuth >= 0;
  }

  public String toString() {
    return String.format("{id:%d,elevation:%d,azimuth:%d,snr:%d}", id, elevation, azimuth, snr);
  }
}
