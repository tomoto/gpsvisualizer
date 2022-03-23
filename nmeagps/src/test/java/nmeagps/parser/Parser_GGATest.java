package nmeagps.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nmeagps.data.Trans;

public class Parser_GGATest {
  @Test
  void testHappySentence() {
    // https://www.hiramine.com/physicalcomputing/general/gps_nmeaformat.html
    final String DATA = "$GPGGA,085120.307,3541.1493,N,13945.3994,E,1,08,1.0,6.9,M,35.9,M,,0000*5E";

    SentenceTokenizer t = new SentenceTokenizer();
    t.tokenize(DATA);
    new Parser_GGA().parse(t, d -> {
      assertEquals("085120.307", d.timeString);
      assertEquals("35\u00b0 41.1493' N", d.lat.toString());
      assertEquals("139\u00b0 45.3994' E", d.lng.toString());
      assertEquals("Standard position service", Trans.GGA.fixQuality.get(d.fixQuality));
      assertEquals(8, d.numSatellites);
      assertEquals(1.0, d.hdop);
      assertEquals(6.9, d.altitude);
      assertEquals(35.9, d.geoidHeight);
      assertEquals(-1, d.correctionAgeInSeconds);
      assertEquals("0000", d.correctionStationId);
    });
  }
}
