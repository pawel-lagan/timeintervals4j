package net.oliste.timeintervals4j.calendar;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

/**
 * Represents a year.
 *
 * @author Paweł Łagan
 * @param <T> type of the properties
 */
public class Year<T> extends SingleTimeInterval<T> implements SequencedInterval<Year<T>> {

  /**
   * Constructor of a new Year with the given date and time and properties.
   *
   * @param dateTime ZonedDateTime object
   * @param properties properties object
   */
  Year(ZonedDateTime dateTime, T properties) {
    super(
        getBeginningOfTheYear(dateTime), getBeginningOfTheYear(dateTime).plusYears(1), properties);
  }

  /**
   * Creates a new Month with the given date and time and properties.
   *
   * @param <S> type of properties object
   * @param dateTime ZonedDateTime object
   * @return new Month object
   */
  public static <S> Year<S> of(ZonedDateTime dateTime) {
    return new Year<>(dateTime, null);
  }

  /**
   * Creates a new Year with the given date and time and properties.
   *
   * @param <S> type of properties object
   * @param dateTime ZonedDateTime object
   * @param properties properties object
   * @return new Month object
   */
  public static <S> Year<S> of(ZonedDateTime dateTime, S properties) {
    return new Year<>(dateTime, properties);
  }

  @Override
  public Year<T> getNext() {
    return Year.of(getFrom().plusYears(1), getProperties());
  }

  @Override
  public Year<T> getPrev() {
    return Year.of(getFrom().minusYears(1), getProperties());
  }

  /**
   * Gets the list of the months in the year.
   *
   * @return list of {@link Month} object
   */
  public List<Month<T>> getMonths() {
    var months = 12;
    var list = new ArrayList<Month<T>>(months);
    for (var i = 1; i <= months; i++) {
      list.add(new Month<>(getFrom().plusMonths(i - 1), getProperties()));
    }
    return list;
  }

  /**
   * Gets the beginning of the year (2024-01-01 00:00:00).
   *
   * @return date time
   */
  private static ZonedDateTime getBeginningOfTheYear(ZonedDateTime dateTime) {
    var zoneId = dateTime.getZone();
    var localDate = LocalDate.of(dateTime.getYear(), 1, 1);
    return ZonedDateTime.of(localDate.atTime(0, 0), zoneId);
  }
}
