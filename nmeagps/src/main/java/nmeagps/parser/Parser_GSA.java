package nmeagps.parser;

import java.util.ArrayList;
import java.util.function.Consumer;

import nmeagps.data.Record_GSA;

public class Parser_GSA extends AbstractSentenceParser<Record_GSA> {
  public Parser_GSA() {
    super("GSA");
  }

  public void parse(SentenceTokenizer t, Consumer<Record_GSA> handler) {
    Record_GSA data = new Record_GSA();
    data.talker = checkTypeAndGetTalker(t);
    data.mode1 = t.nextString();
    data.mode2 = t.nextInt();

    data.satellitesUsed = new ArrayList<Integer>();
    for (int i = 0; i < 12; i++) {
      int id = t.nextInt();
      if (id != SentenceTokenizer.INVALID_INT) {
        data.satellitesUsed.add(id);
      }
    }

    data.pdop = t.nextFloat();
    data.hdop = t.nextFloat();
    data.vdop = t.nextFloat();

    handler.accept(data);
  }
}
