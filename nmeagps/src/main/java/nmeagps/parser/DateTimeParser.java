package nmeagps.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeParser {
  public static Date parseUTC(String dateString, String timeString) {
    // subseconds are optional
    DateFormat df = new SimpleDateFormat(
        "ddMMyy HHmmss" + (timeString.contains(".") ? ".SSS" : ""));
    df.setTimeZone(TimeZone.getTimeZone("UTC"));
    try {
      return df.parse(dateString + " " + timeString);
    } catch (ParseException e) {
      return null;
    }
  }
}
