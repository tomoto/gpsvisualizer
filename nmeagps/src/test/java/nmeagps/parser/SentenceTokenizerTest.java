package nmeagps.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SentenceTokenizerTest {
  @Test
  void testTokenizeHappySentence() {
    final String DATA = "$GNRMC,203844.000,V,,200322,N*77\r\n";

    SentenceTokenizer t = new SentenceTokenizer();
    t.tokenize(DATA);
    assertEquals(6, t.getFields().length);
    assertEquals("GNRMC", t.nextString());
    assertEquals(-1, t.peekInt());
    assertEquals(203844, t.nextFloat());
    assertEquals("V", t.nextString());
    assertEquals(-1, t.nextInt());
    assertEquals(200322, t.nextInt());
    assertEquals("N", t.nextString());
  }

  @Test
  void testIsEol() {
    final String DATA = "$A,B,C*40\r\n";

    SentenceTokenizer t = new SentenceTokenizer();
    t.tokenize(DATA);
    t.nextString();
    t.nextString();
    assertEquals(false, t.isEol());
    t.nextString();
    assertEquals(true, t.isEol());
    assertEquals("", t.nextString()); // default value
  }

  @Test
  void testSyntaxErrorDollarSign() {
    final String DATA = "hello, world";

    SentenceTokenizer t = new SentenceTokenizer();
    Exception e = assertThrows(GPSParserException.class, () -> {
      t.tokenize(DATA);
    });
    assertTrue(e.getMessage().contains("does not start with $"));
  }

  @Test
  void testSyntaxErrorStarSign() {
    final String DATA = "$hello, world";

    SentenceTokenizer t = new SentenceTokenizer();
    Exception e = assertThrows(GPSParserException.class, () -> {
      t.tokenize(DATA);
    });
    assertTrue(e.getMessage().contains("does not contain *"));
  }

  @Test
  void testChecksumError() {
    final String DATA = "$hello, world*FE";

    SentenceTokenizer t = new SentenceTokenizer();
    Exception e = assertThrows(GPSParserException.class, () -> {
      t.tokenize(DATA);
    });
    assertTrue(e.getMessage().contains("*FE does not match the actual sum"));
  }
}
