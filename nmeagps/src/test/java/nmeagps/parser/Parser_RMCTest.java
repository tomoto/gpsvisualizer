package nmeagps.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import nmeagps.data.Trans;

public class Parser_RMCTest {
  @Test
  void testHappySentence() {
    // https://orolia.com/manuals/VSP/Content/NC_and_SS/Com/Topics/APPENDIX/NMEA_RMCmess.htm
    final String DATA = "$GPRMC,123519,A,4807.038,N,01131.000,E,0.022,269.131,230394,,,A,C*11";

    SentenceTokenizer t = new SentenceTokenizer();
    t.tokenize(DATA);
    new Parser_RMC().parse(t, d -> {
      assertEquals("48\u00b0 7.0380' N", d.lat.toString());
      assertEquals("11\u00b0 31.0000' E", d.lng.toString());
      assertEquals("1994-03-23T12:35:19Z[UTC]",
          d.dateTime.toInstant().atZone(ZoneId.of("UTC")).toString());
      assertEquals("Active", Trans.RMC.status.get(d.status));
      assertEquals(0.022, d.speedInKnots);
      assertEquals(269.131, d.trackMadeGood);
      assertEquals(Double.NaN, d.magneticVariation);
      assertEquals("Autonomous", Trans.RMC.mode.get(d.mode));
      assertEquals("Caution", Trans.RMC.navStatus.get(d.navStatus));
    });
  }

  @Test
  void testTypeMismatch() {
    final String DATA = "$GPGSV,1,2,3*49";

    SentenceTokenizer t = new SentenceTokenizer();
    t.tokenize(DATA);
    Exception e = assertThrows(GPSParserException.class, () -> {
      new Parser_RMC().parse(t, d -> {
        // dummy
      });
    });
    System.out.println(e.getMessage());
    assertTrue(e.getMessage().contains("Invalid sentence header"));
  }
}
