package nmeagps.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZoneId;

import org.junit.jupiter.api.Test;

public class Parser_ZDATest {
  @Test
  void testHappySentence() {
    final String DATA = "$GNZDA,234212.000,20,03,2022,-08,00*6A";

    SentenceTokenizer t = new SentenceTokenizer();
    t.tokenize(DATA);
    new Parser_ZDA().parse(t, d -> {
      assertEquals("2022-03-20T23:42:12Z[UTC]",
          d.dateTime.toInstant().atZone(ZoneId.of("UTC")).toString());
      assertEquals(-8, d.localZoneHours);
      assertEquals(0, d.localZoneMinutes);
    });
  }
}
