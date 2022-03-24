package nmeagps.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nmeagps.data.Trans;

public class Parser_GLLTest {
  @Test
  void testHappySentence() {
    // https://www.hiramine.com/physicalcomputing/general/gps_nmeaformat.html
    final String DATA = "$GPGLL,3723.2475,N,12158.3416,W,161229.487,A,A*41";

    SentenceTokenizer t = new SentenceTokenizer();
    t.tokenize(DATA);
    new Parser_GLL().parse(t, d -> {
      assertEquals("37\u00b0 23.2475' N", d.lat.toString());
      assertEquals("121\u00b0 58.3416' W", d.lng.toString());
      assertEquals("161229.487", d.timeString);
      assertEquals("Active", Trans.GLL.status.get(d.status));
      assertEquals("Autonomous", Trans.GLL.mode.get(d.mode));
    });
  }
}
