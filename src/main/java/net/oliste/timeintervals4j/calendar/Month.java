package net.oliste.timeintervals4j.calendar;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

/**
 * Represents a month.
 *
 * @author Paweł Łagan
 * @param <T> type of the properties
 */
public class Month<T> extends SingleTimeInterval<T> implements SequencedInterval<Month<T>> {

  /**
   * Constructor of a new Month with the given date and time and properties.
   *
   * @param dateTime ZonedDateTime object
   * @param properties properties object
   */
  Month(ZonedDateTime dateTime, T properties) {
    super(
        getBeginningOfTheMonth(dateTime),
        getBeginningOfTheMonth(dateTime).plusMonths(1),
        properties);
  }

  /**
   * Creates a new Month with the given date and time.
   *
   * @param <S> type of properties object
   * @param dateTime ZonedDateTime object
   * @return new Month object
   */
  public static <S> Month<S> of(ZonedDateTime dateTime) {
    return new Month<>(dateTime, null);
  }

  /**
   * Creates a new Month with the given date and time and properties.
   *
   * @param <S> type of properties object
   * @param dateTime ZonedDateTime object
   * @param properties properties object
   * @return new Month object
   */
  public static <S> Month<S> of(ZonedDateTime dateTime, S properties) {
    return new Month<>(dateTime, properties);
  }

  @Override
  public Month<T> getNext() {
    return Month.of(getFrom().plusMonths(1), getProperties());
  }

  @Override
  public Month<T> getPrev() {
    return Month.of(getFrom().minusMonths(1), getProperties());
  }

  /**
   * Gets the list of the days in the month.
   *
   * @return list of {@link Day} object
   */
  public List<Day<T>> getDays() {
    var days = getFrom().getMonth().length(Year.isLeap(getFrom().getYear()));
    var list = new ArrayList<Day<T>>(days);
    for (var i = 1; i <= days; i++) {
      list.add(new Day<>(getFrom().plusDays(i - 1), getProperties()));
    }
    return list;
  }

  /**
   * Gets the beginning of the month (2024-05-01 00:00:00).
   *
   * @return date time
   */
  private static ZonedDateTime getBeginningOfTheMonth(ZonedDateTime dateTime) {
    var zoneId = dateTime.getZone();
    var localDate = LocalDate.of(dateTime.getYear(), dateTime.getMonthValue(), 1);
    return ZonedDateTime.of(localDate.atTime(0, 0), zoneId);
  }
}
