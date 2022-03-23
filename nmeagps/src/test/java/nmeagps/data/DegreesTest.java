package nmeagps.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DegreesTest {
  Degrees d1 = Degrees.fromDegAndMin(12, 34.56789, Direction.N);
  Degrees d2 = Degrees.fromDegAndMin(0, 34.56789, Direction.S);
  Degrees d3 = Degrees.fromDegAndMin(0, 0, Direction.E);
  Degrees d4 = new Degrees(12.3456, Direction.W);

  @Test
  void testToString() {
    assertEquals("12\u00b0 34.5679' N", d1.toString());
    assertEquals("0\u00b0 34.5679' S", d2.toString());
    assertEquals("0\u00b0 0.0000' E", d3.toString());
    assertEquals("12\u00b0 20.7360' W", d4.toString());
  }

  @Test
  void testToFloat() {
    assertEquals(12.5761315, d1.value);
    assertEquals(-0.5761315, d2.value);
    assertEquals(0, d3.value);
    assertEquals(-12.3456, d4.value);
  }
}
