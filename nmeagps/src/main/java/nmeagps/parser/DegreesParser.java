package nmeagps.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nmeagps.data.Degrees;
import nmeagps.data.Direction;

public class DegreesParser {
  static final Pattern degreesRegex = Pattern.compile("(\\d{2,3})(\\d{2}\\.\\d{2,})");

  static Degrees parse(String s, Direction direction) {
    Matcher m = degreesRegex.matcher(s);
    if (m.matches()) {
      return Degrees.fromDegAndMin(
          Integer.parseInt(m.group(1)),
          Float.parseFloat(m.group(2)),
          direction);
    } else {
      return null;
    }
  }
}
