package nmeagps.parser;

import java.util.function.Consumer;

import nmeagps.data.AbstractRecord;

public abstract class AbstractSentenceParser<D extends AbstractRecord> {
  public final String type;

  protected AbstractSentenceParser(String type) {
    this.type = type;
  }

  protected String checkTypeAndGetTalker(SentenceTokenizer t) {
    String header = t.nextString();
    if (!header.endsWith(type)) {
      throw new GPSParserException("Invalid sentence type");
    }
    return header.substring(0, 2);
  }

  abstract public void parse(SentenceTokenizer t, Consumer<D> consumer);
}
