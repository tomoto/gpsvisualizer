package nmeagps.parser;

import java.util.function.Consumer;

import nmeagps.data.Record_ZDA;

public class Parser_ZDA extends AbstractSentenceParser<Record_ZDA> {
  public Parser_ZDA() {
    super("ZDA");
  }

  public void parse(SentenceTokenizer t, Consumer<Record_ZDA> handler) {
    Record_ZDA data = new Record_ZDA();
    data.talker = checkTypeAndGetTalker(t);
    String timeString = t.nextString();
    int dd = t.nextInt();
    int mm = t.nextInt();
    int yyyy = t.nextInt();
    String dateString = String.format("%02d%02d%02d", dd, mm, yyyy);
    // accurate only when the time zone is Z
    data.dateTime = DateTimeParser.parseUTC(dateString, timeString);
    data.localZoneHours = t.nextIntWithDefault(0);
    data.localZoneMinutes = t.nextInt();

    handler.accept(data);
  }
}
