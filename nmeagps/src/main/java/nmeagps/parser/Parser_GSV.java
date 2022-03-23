package nmeagps.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import nmeagps.data.Record_GSV;
import nmeagps.data.SatelliteView;

public class Parser_GSV extends AbstractSentenceParser<Record_GSV> {
  private class Context {
    int expectedNumMessages;
    int expectedNumSatellites;
    int expectedNextMessageNumber = 1;
    List<SatelliteView> satelliteViews = new ArrayList<SatelliteView>();;
  }

  private Map<String, Context> contextMap = new HashMap<>();

  public Parser_GSV() {
    super("GSV");
  }

  public void parse(SentenceTokenizer t, Consumer<Record_GSV> handler) {
    String talker = checkTypeAndGetTalker(t);

    int numMessages = t.nextInt();
    int messageNumber = t.nextInt();
    int numSatellites = t.nextInt();

    Context context;
    if (messageNumber == 1) {
      // handle the first message as the reset request
      context = new Context();
      context.expectedNumMessages = numMessages;
      context.expectedNumSatellites = numSatellites;
      contextMap.put(talker, context);
    } else {
      context = contextMap.get(talker);
    }

    if (context == null ||
        context.expectedNextMessageNumber != messageNumber ||
        context.expectedNumMessages != numMessages ||
        context.expectedNumSatellites != numSatellites) {
      contextMap.remove(talker);
      throw new GPSParserException("Inconsistent GSV message sequence is found and discarded", t);
    }

    while (t.peekInt() != SentenceTokenizer.INVALID_INT) {
      int id = t.nextInt();
      int elevation = t.nextInt();
      int azimuth = t.nextInt();
      int snr = t.nextInt();
      context.satelliteViews.add(new SatelliteView(id, elevation, azimuth, snr));
    }

    if (context.expectedNumMessages == messageNumber) {
      Record_GSV data = new Record_GSV();
      data.talker = talker;
      data.satelliteViews = context.satelliteViews;
      contextMap.remove(talker);
      handler.accept(data);
    } else {
      context.expectedNextMessageNumber++;
    }
  }
}
