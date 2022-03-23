package nmeagps.parser;

import java.util.function.Consumer;

import nmeagps.data.Direction;
import nmeagps.data.Record_GLL;

public class Parser_GLL extends AbstractSentenceParser<Record_GLL> {
  public Parser_GLL() {
    super("GLL");
  }

  public void parse(SentenceTokenizer t, Consumer<Record_GLL> handler) {
    Record_GLL data = new Record_GLL();
    data.talker = checkTypeAndGetTalker(t);
    String latString = t.nextString();
    String latNS = t.nextString();
    data.lat = DegreesParser.parse(latString, Direction.parse(latNS));
    String lngString = t.nextString();
    String latEW = t.nextString();
    data.lng = DegreesParser.parse(lngString, Direction.parse(latEW));
    data.timeString = t.nextString();
    data.status = t.nextString();
    data.mode = t.nextString();

    handler.accept(data);
  }
}
