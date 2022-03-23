package nmeagps.parser;

import java.util.function.Function;
import java.util.function.Supplier;

public class SentenceTokenizer {
  // assume int is usually unsigned and we can use -1 to mean no value,
  // unless explicitly specified
  public static final int INVALID_INT = -1;

  public static final double INVALID_FLOAT = Double.NaN;

  private String[] fields;
  private int ptr;

  public void tokenize(String sentence) {
    if (!sentence.startsWith("$")) {
      throw new GPSParserException("The sentence does not start with $");
    }

    int starIndex = sentence.indexOf("*", 1);
    if (starIndex < 0) {
      throw new GPSParserException("The sentence does not have *");
    }

    String contents = sentence.substring(1, starIndex);
    int checksum = Integer.parseInt(sentence.substring(starIndex + 1).trim(), 16);
    int sum = contents.codePoints().reduce(0, (a, b) -> a ^ b) & 0xff;
    if (checksum != sum) {
      throw new GPSParserException(
          String.format("The checksum %02X does not match the actual sum %02X", checksum, sum));
    }

    fields = contents.split(",", -1);
    ptr = 0;
  }

  public String[] getFields() {
    return fields;
  }

  public String peekString() {
    return peekWithDefault(s -> s, "");
  }

  public String nextString() {
    return next(this::peekString);
  }

  public int peekInt() {
    return peekIntWithDefault(INVALID_INT);
  }

  public int nextInt() {
    return nextIntWithDefault(INVALID_INT);
  }

  public int peekIntWithDefault(int defaultValue) {
    return peekWithDefault(Integer::parseInt, defaultValue);
  }

  public int nextIntWithDefault(int defaultValue) {
    return next(() -> peekIntWithDefault(defaultValue));
  }

  public double peekFloat() {
    return peekWithDefault(Double::parseDouble, INVALID_FLOAT);
  }

  public double nextFloat() {
    return next(this::peekFloat);
  }

  public boolean isEol() {
    return ptr == fields.length;
  }

  private <T> T peekWithDefault(Function<String, T> f, T defaultValue) {
    if (isEol()) {
      return defaultValue;
    }

    try {
      return f.apply(fields[ptr]);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  private <T> T next(Supplier<T> f) {
    T value = f.get();
    ptr++;
    return value;
  }
}
