package nmeagps.data;

public enum Direction {
  UNKNOWN(0),
  N(1),
  S(-1),
  E(1),
  W(-1);

  public final int sign;

  private Direction(int sign) {
    this.sign = sign;
  }

  public static Direction parse(String s) {
    try {
      return Direction.valueOf(s);
    } catch (IllegalArgumentException e) {
      return Direction.UNKNOWN;
    }
  }
}
