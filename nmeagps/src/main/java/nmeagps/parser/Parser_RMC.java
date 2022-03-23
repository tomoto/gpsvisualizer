package nmeagps.parser;

import java.util.function.Consumer;

import nmeagps.data.Direction;
import nmeagps.data.Record_RMC;

public class Parser_RMC extends AbstractSentenceParser<Record_RMC> {
  public Parser_RMC() {
    super("RMC");
  }

  public void parse(SentenceTokenizer t, Consumer<Record_RMC> handler) {
    Record_RMC data = new Record_RMC();
    data.talker = checkTypeAndGetTalker(t);
    data.timeString = t.nextString();
    data.status = t.nextString();
    String latString = t.nextString();
    String latNS = t.nextString();
    data.lat = DegreesParser.parse(latString, Direction.parse(latNS));
    String lngString = t.nextString();
    String latEW = t.nextString();
    data.lng = DegreesParser.parse(lngString, Direction.parse(latEW));
    data.speedInKnots = t.nextFloat();
    data.trackMadeGood = t.nextFloat();
    data.dateString = t.nextString();

    data.dateTime = DateTimeParser.parseUTC(data.dateString, data.timeString);

    double mv = t.nextFloat();
    String mvEW = t.nextString();
    data.magneticVariation = mv * Direction.parse(mvEW).sign;
    data.mode = t.nextString();
    data.navStatus = t.nextString();

    handler.accept(data);
  }
}
