package nmeagps;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import nmeagps.data.AbstractRecord;
import nmeagps.data.Record_GGA;
import nmeagps.data.Record_GLL;
import nmeagps.data.Record_GSA;
import nmeagps.data.Record_GSV;
import nmeagps.data.Record_RMC;
import nmeagps.data.Record_VTG;
import nmeagps.data.Record_ZDA;
import nmeagps.parser.AbstractSentenceParser;
import nmeagps.parser.Parser_GGA;
import nmeagps.parser.Parser_GLL;
import nmeagps.parser.Parser_GSA;
import nmeagps.parser.Parser_GSV;
import nmeagps.parser.Parser_RMC;
import nmeagps.parser.Parser_VTG;
import nmeagps.parser.Parser_ZDA;
import nmeagps.parser.SentenceTokenizer;

/**
 * Generic NMEA GPS message parser. The application should set up the handlers
 * upfront and call `parse` with the incoming messages, then the handlers will
 * be called with the parsed data.
 */
public class GPSParser {
  public final SentenceTokenizer tokenizer = new SentenceTokenizer();
  private final ParserHandlerMap parserHandlerMap = new ParserHandlerMap();

  public void setHandler_GGA(Consumer<Record_GGA> handler) {
    parserHandlerMap.put(new Parser_GGA(), handler);
  }

  public void setHandler_GLL(Consumer<Record_GLL> handler) {
    parserHandlerMap.put(new Parser_GLL(), handler);
  }

  public void setHandler_GSA(Consumer<Record_GSA> handler) {
    parserHandlerMap.put(new Parser_GSA(), handler);
  }

  public void setHandler_GSV(Consumer<Record_GSV> handler) {
    parserHandlerMap.put(new Parser_GSV(), handler);
  }

  public void setHandler_RMC(Consumer<Record_RMC> handler) {
    parserHandlerMap.put(new Parser_RMC(), handler);
  }

  public void setHandler_VTG(Consumer<Record_VTG> handler) {
    parserHandlerMap.put(new Parser_VTG(), handler);
  }

  public void setHandler_ZDA(Consumer<Record_ZDA> handler) {
    parserHandlerMap.put(new Parser_ZDA(), handler);
  }

  public void parse(String sentence) {
    tokenizer.tokenize(sentence);
    String header = tokenizer.peekString();
    String type = header.substring(2);

    ParserHandlerMap.Entry<?> entry = parserHandlerMap.get(type);
    if (entry != null && entry.handler != null) {
      entry.parse(tokenizer);
    }
  }
}

class ParserHandlerMap {
  public static class Entry<D extends AbstractRecord> {
    public final AbstractSentenceParser<D> parser;
    public final Consumer<D> handler;

    public Entry(AbstractSentenceParser<D> parser, Consumer<D> handler) {
      this.parser = parser;
      this.handler = handler;
    }

    public void parse(SentenceTokenizer tokenizer) {
      parser.parse(tokenizer, handler);
    }
  }

  private Map<String, Entry<?>> map = new HashMap<>();

  public <D extends AbstractRecord> void put(
      AbstractSentenceParser<D> parser,
      Consumer<D> handler) {
    map.put(parser.type, new Entry<D>(parser, handler));
  }

  public Entry<?> get(String key) {
    return map.get(key);
  }
}
