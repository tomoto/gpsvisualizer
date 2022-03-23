package nmeagps.parser;

import java.util.function.Consumer;

import nmeagps.data.Record_VTG;

public class Parser_VTG extends AbstractSentenceParser<Record_VTG> {
  public Parser_VTG() {
    super("VTG");
  }

  public void parse(SentenceTokenizer t, Consumer<Record_VTG> handler) {
    Record_VTG data = new Record_VTG();
    data.talker = checkTypeAndGetTalker(t);
    data.trueCourse = t.nextFloat();
    t.nextString(); // always T (True)
    data.magneticCourse = t.nextFloat();
    t.nextString(); // always M (Magnetic)
    data.speedInKnots = t.nextFloat();
    t.nextString(); // always N (Knot)
    data.speedInKmphs = t.nextFloat();
    t.nextString(); // always K (Km/h)
    data.mode = t.nextString();

    handler.accept(data);
  }
}
