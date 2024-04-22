package net.oliste.timeintervals4j.calendar;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

/**
 * Represents a day.
 *
 * @author Paweł Łagan
 * @param <T> type of the properties
 */
public class Day<T> extends SingleTimeInterval<T> implements SequencedInterval<Day<T>> {

  /**
   * Constructs a new Day with the given date and time and properties.
   *
   * @param dateTime date time representing a day, time will be aligned to 00:00
   * @param properties properties object
   */
  Day(ZonedDateTime dateTime, T properties) {
    super(getBeginningOfTheDay(dateTime), getBeginningOfTheDay(dateTime).plusDays(1), properties);
  }

  /**
   * Creates a new day from a ZonedDateTime object.
   *
   * @param <S> type of properties object
   * @param dateTime ZonedDateTime object
   * @return new Day object
   */
  public static <S> Day<S> of(ZonedDateTime dateTime) {
    return new Day<>(dateTime, null);
  }

  /**
   * Creates a new Day with the given date and time and properties.
   *
   * @param <S> type of properties object
   * @param dateTime ZonedDateTime object
   * @param properties properties object
   * @return new Day object
   */
  public static <S> Day<S> of(ZonedDateTime dateTime, S properties) {
    return new Day<>(dateTime, properties);
  }

  @Override
  public Day<T> getNext() {
    return Day.of(getFrom().plusDays(1), getProperties());
  }

  @Override
  public Day<T> getPrev() {
    return Day.of(getFrom().minusDays(1), getProperties());
  }

  /**
   * Gets the beginning of the day (00:00:00).
   *
   * @return date time
   */
  private static ZonedDateTime getBeginningOfTheDay(ZonedDateTime dateTime) {
    var zoneId = dateTime.getZone();
    var localDate =
        LocalDate.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());
    return ZonedDateTime.of(localDate.atTime(0, 0), zoneId);
  }
}
