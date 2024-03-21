package net.oliste;

import java.time.ZonedDateTime;

public class TimeMath {

  private TimeMath() {}

  public static ZonedDateTime min(ZonedDateTime date1, ZonedDateTime date2) {
    if (date1 == null || date2 == null) {
      return null;
    }
    if (date1.isBefore(date2)) {
      return date1;
    }
    return date2;
  }

  public static ZonedDateTime max(ZonedDateTime date1, ZonedDateTime date2) {
    if (date1 == null || date2 == null) {
      return null;
    }
    if (date1.isBefore(date2)) {
      return date2;
    }
    return date1;
  }
}
