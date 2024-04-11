package net.oliste.timeintervals4j.calendar;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Fixture for the tests. It contains methods that creates some frequently used test data.
 *
 * @author Paweł Łagan
 */
public class CalendarFixture {

  private static final ZonedDateTime NOW =
      LocalDateTime.parse("2024-03-25T12:00:00").atZone(ZoneId.of("UTC"));

  private static final ZonedDateTime NOW_BEGINNING_OF_THE_DAY =
      LocalDateTime.parse("2024-03-25T00:00:00").atZone(ZoneId.of("UTC"));

  private static final ZonedDateTime NOW_BEGINNING_OF_THE_MONTH =
      LocalDateTime.parse("2024-03-01T00:00:00").atZone(ZoneId.of("UTC"));

  private static final ZonedDateTime NOW_BEGINNING_OF_THE_YEAR =
      LocalDateTime.parse("2024-01-01T00:00:00").atZone(ZoneId.of("UTC"));

  /** Constructor. */
  public CalendarFixture() {}

  /**
   * Get date time representing current time in the unit tests.
   *
   * @return {@link ZonedDateTime} date time with timezone
   */
  public ZonedDateTime getNowUtc() {
    return NOW;
  }

  /**
   * Get date time representing current day at midnight.
   *
   * @return {@link ZonedDateTime} date time with timezone
   */
  public ZonedDateTime getBeginningOfTheCurrentDay() {
    return NOW_BEGINNING_OF_THE_DAY;
  }

  /**
   * Get date time representing current month first day at midnight.
   *
   * @return {@link ZonedDateTime} date time with timezone
   */
  public ZonedDateTime getBeginningOfTheCurrentMonth() {
    return NOW_BEGINNING_OF_THE_MONTH;
  }

  /**
   * Get date time representing current year 1st Jan at midnight.
   *
   * @return {@link ZonedDateTime} date time with timezone
   */
  public ZonedDateTime getBeginningOfTheCurrentYear() {
    return NOW_BEGINNING_OF_THE_YEAR;
  }

  /**
   * Get all days in current month.
   *
   * @param <T> time interval properties type
   * @param props properties of the interval
   * @return {@link List} of {@link Day} objects
   */
  public <T> List<Day<T>> getAllDaysOfCurrentMonth(T props) {
    return IntStream.rangeClosed(1, 31)
        .mapToObj(i -> new Day<>(getBeginningOfTheCurrentMonth().plusDays(i - 1), props))
        .collect(Collectors.toList());
  }

  /**
   * Get all months list.
   *
   * @param <T> time interval properties type
   * @param props properties of the interval
   * @return {@link List} of {@link Month} objects
   */
  public <T> List<Month<T>> getAllMonths(T props) {
    return IntStream.rangeClosed(1, 12)
        .mapToObj(i -> new Month<>(getBeginningOfTheCurrentYear().plusMonths(i - 1), props))
        .collect(Collectors.toList());
  }
}
