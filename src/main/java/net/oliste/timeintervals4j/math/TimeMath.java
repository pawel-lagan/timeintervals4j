package net.oliste.timeintervals4j.math;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Utility class for operations on time like comparison and min, max.
 *
 * @author Paweł Łagan
 */
public class TimeMath {

  /** Represents a point in time that is before all other points in time. */
  public static final ZonedDateTime PAST_INFINITY =
      ZonedDateTime.of(LocalDateTime.MIN, ZoneId.of("UTC"));

  /** Represents a point in time that is after all other points in time. */
  public static final ZonedDateTime FUTURE_INFINITY =
      ZonedDateTime.of(LocalDateTime.MAX, ZoneId.of("UTC"));

  private TimeMath() {}

  /**
   * Returns the minimum of two date-time objects.
   *
   * @param date1 the first date-time object
   * @param date2 the second date-time object
   * @return the minimum of the two date-time objects
   */
  public static ZonedDateTime min(ZonedDateTime date1, ZonedDateTime date2) {
    if (date1.isBefore(date2)) {
      return date1;
    }
    return date2;
  }

  /**
   * Returns the maximum of two date-time objects.
   *
   * @param date1 the first date-time object
   * @param date2 the second date-time object
   * @return the maximum of the two date-time objects
   */
  public static ZonedDateTime max(ZonedDateTime date1, ZonedDateTime date2) {
    if (date1.isBefore(date2)) {
      return date2;
    }
    return date1;
  }

  /**
   * Returns true if the first date-time object is before the second date-time object.
   *
   * @param dateTimeA the first date-time object
   * @param dateTimeB the second date-time object
   * @return true if the first date-time object is before the second date-time object
   */
  public static boolean isBefore(ZonedDateTime dateTimeA, ZonedDateTime dateTimeB) {
    return dateTimeA.isBefore(dateTimeB);
  }

  /**
   * Returns true if the first date-time object is after the second date-time object.
   *
   * @param dateTimeA the first date-time object
   * @param dateTimeB the second date-time object
   * @return true if the first date-time object is after the second date-time object
   */
  public static boolean isAfter(ZonedDateTime dateTimeA, ZonedDateTime dateTimeB) {
    return dateTimeA.isAfter(dateTimeB);
  }

  /**
   * Returns true if the first date-time object is before or equal to the second date-time object.
   *
   * @param dateTimeA the first date-time object
   * @param dateTimeB the second date-time object
   * @return true if the first date-time object is before or equal to the second date-time object
   */
  public static boolean isBeforeOrEquals(ZonedDateTime dateTimeA, ZonedDateTime dateTimeB) {
    return !dateTimeA.isAfter(dateTimeB);
  }

  /**
   * Returns true if the first date-time object is after or equal to the second date-time object.
   *
   * @param dateTimeA the first date-time object
   * @param dateTimeB the second date-time object
   * @return true if the first date-time object is after or equal to the second date-time object
   */
  public static boolean isAfterOrEquals(ZonedDateTime dateTimeA, ZonedDateTime dateTimeB) {
    return !dateTimeA.isBefore(dateTimeB);
  }
}
