package nmeagps.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nmeagps.data.Trans;

public class Parser_GSATest {
  @Test
  void testHappySentence() {
    // https://www.hiramine.com/physicalcomputing/general/gps_nmeaformat.html
    final String DATA = "$GPGSA,A,3,29,26,05,10,02,27,08,15,,,,,1.8,1.0,1.5*3E";

    SentenceTokenizer t = new SentenceTokenizer();
    t.tokenize(DATA);
    new Parser_GSA().parse(t, d -> {
      assertEquals("Automatic", Trans.GSA.mode1.get(d.mode1));
      assertEquals("3D", Trans.GSA.mode2.get(d.mode2));
      assertEquals(8, d.satellitesUsed.size());
      assertEquals(26, d.satellitesUsed.get(1));
      assertEquals(1.8, d.pdop);
      assertEquals(1.0, d.hdop);
      assertEquals(1.5, d.vdop);
    });
  }
}
