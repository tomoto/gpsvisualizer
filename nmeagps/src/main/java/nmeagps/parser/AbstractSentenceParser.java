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
      // possibly the parent parser's bug
      throw new GPSParserException(
          String.format("Invalid sentence header: xx%s is expected but got %s", type, header),
          t);
    }
    return header.substring(0, 2);
  }

  abstract public void parse(SentenceTokenizer t, Consumer<D> consumer);
}
