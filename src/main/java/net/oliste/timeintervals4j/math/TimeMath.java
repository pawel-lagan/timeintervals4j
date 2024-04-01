package net.oliste.timeintervals4j.math;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeMath {

  public static final ZonedDateTime PAST_INFINITY =
      ZonedDateTime.of(LocalDateTime.MIN, ZoneId.of("UTC"));
  public static final ZonedDateTime FUTURE_INFINITY =
      ZonedDateTime.of(LocalDateTime.MAX, ZoneId.of("UTC"));

  private TimeMath() {}

  public static ZonedDateTime min(ZonedDateTime date1, ZonedDateTime date2) {
    if (date1.isBefore(date2)) {
      return date1;
    }
    return date2;
  }

  public static ZonedDateTime max(ZonedDateTime date1, ZonedDateTime date2) {
    if (date1.isBefore(date2)) {
      return date2;
    }
    return date1;
  }

  public static boolean isBefore(ZonedDateTime dateTimeA, ZonedDateTime dateTimeB) {
    return dateTimeA.isBefore(dateTimeB);
  }

  public static boolean isAfter(ZonedDateTime dateTimeA, ZonedDateTime dateTimeB) {
    return dateTimeA.isAfter(dateTimeB);
  }

  public static boolean isBeforeOrEquals(ZonedDateTime dateTimeA, ZonedDateTime dateTimeB) {
    return !dateTimeA.isAfter(dateTimeB);
  }

  public static boolean isAfterOrEquals(ZonedDateTime dateTimeA, ZonedDateTime dateTimeB) {
    return !dateTimeA.isBefore(dateTimeB);
  }
}
