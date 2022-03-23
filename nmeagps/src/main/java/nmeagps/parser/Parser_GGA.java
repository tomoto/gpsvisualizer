package nmeagps.parser;

import java.util.function.Consumer;

import nmeagps.data.Direction;
import nmeagps.data.Record_GGA;

public class Parser_GGA extends AbstractSentenceParser<Record_GGA> {
  public Parser_GGA() {
    super("GGA");
  }

  public void parse(SentenceTokenizer t, Consumer<Record_GGA> handler) {
    Record_GGA data = new Record_GGA();
    data.talker = checkTypeAndGetTalker(t);
    data.timeString = t.nextString();
    String latString = t.nextString();
    String latNS = t.nextString();
    data.lat = DegreesParser.parse(latString, Direction.parse(latNS));
    String lngString = t.nextString();
    String latEW = t.nextString();
    data.lng = DegreesParser.parse(lngString, Direction.parse(latEW));
    data.fixQuality = t.nextInt();
    data.numSatellites = t.nextInt();
    data.hdop = t.nextFloat();
    data.altitude = t.nextFloat();
    t.nextString(); // unit of altitude, always M, ignore
    data.geoidHeight = t.nextFloat();
    t.nextString(); // unit of geoid height, always M, ignore
    data.correctionAgeInSeconds = t.nextInt();
    data.correctionStationId = t.nextString();

    handler.accept(data);
  }
}
