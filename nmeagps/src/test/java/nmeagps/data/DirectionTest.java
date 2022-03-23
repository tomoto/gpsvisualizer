package nmeagps.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DirectionTest {
  @Test
  void testParse() {
    assertEquals(Direction.N, Direction.parse("N"));
    assertEquals(Direction.UNKNOWN, Direction.parse(""));
  }
}
