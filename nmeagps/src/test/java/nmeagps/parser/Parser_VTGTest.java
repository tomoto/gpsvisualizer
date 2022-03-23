package nmeagps.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nmeagps.data.Trans;

public class Parser_VTGTest {
  @Test
  void testHappySentence() {
    final String DATA = "$GPVTG,309.62,T, ,M,0.13,N,0.2,K,A*23";

    SentenceTokenizer t = new SentenceTokenizer();
    t.tokenize(DATA);
    new Parser_VTG().parse(t, d -> {
      assertEquals(309.62, d.trueCourse);
      assertEquals(Double.NaN, d.magneticCourse);
      assertEquals(0.13, d.speedInKnots);
      assertEquals(0.2, d.speedInKmphs);
      assertEquals("Autonomous", Trans.VTG.mode.get(d.mode));
    });
  }
}
