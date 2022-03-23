package nmeagps.parser;

public class GPSParserException extends RuntimeException {
  static String buildMessage(String message, SentenceTokenizer t) {
    if (t != null) {
      return String.format("%s %s", message, t);
    } else {
      return message;
    }
  }

  public GPSParserException(String message) {
    super(message);
  }

  public GPSParserException(String message, SentenceTokenizer t) {
    super(buildMessage(message, t));
  }
}
