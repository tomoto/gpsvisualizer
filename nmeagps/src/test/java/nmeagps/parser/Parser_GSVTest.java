package nmeagps.parser;

import org.junit.jupiter.api.Test;

import nmeagps.data.Record_GSV;
import nmeagps.data.SatelliteView;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Consumer;

public class Parser_GSVTest {
  @Test
  void testHappySentences() {
    final String[] DATA = {
        "$GPGSV,3,1,10,01,15,041,,06,28,154,,12,06,277,,13,35,223,*76",
        "$GPGSV,3,2,10,14,41,069,,15,25,260,19,17,69,044,,19,85,203,*74",
        "$GPGSV,3,3,10,24,28,313,,30,19,139,*75",
    };

    SentenceTokenizer t = new SentenceTokenizer();
    Parser_GSV p = new Parser_GSV();
    Consumer<Record_GSV> h = d -> {
      assertEquals(10, d.satelliteViews.size());
      SatelliteView v = d.satelliteViews.get(1);
      assertEquals(6, v.id);
      assertEquals(28, v.elevation);
      assertEquals(154, v.azimuth);
      assertEquals(-1, v.snr);
    };

    for (String s : DATA) {
      t.tokenize(s);
      p.parse(t, h);
    }
  }
}
