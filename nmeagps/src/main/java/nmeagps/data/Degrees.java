package nmeagps.data;

public class Degrees {
  public final double value;
  public final Direction direction;

  public Degrees(double value, Direction direction) {
    if (value < 0) {
      throw new IllegalArgumentException("Degrees must not be negative");
    }
    this.value = value * direction.sign;
    this.direction = direction;
  }

  public static Degrees fromDegAndMin(int deg, double min, Direction direction) {
    return new Degrees(deg + min / 60, direction);
  }

  public int getDegInt() {
    return (int) Math.abs(value);
  }

  public double getMinFloat() {
    double a = Math.abs(value);
    return (a - Math.floor(a)) * 60;
  }

  public String toString() {
    return String.format("%d\u00b0 %.4f\' %s", getDegInt(), getMinFloat(), direction);
  }
}
