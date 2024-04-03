package net.oliste.timeintervals4j.calendar;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public class Day<T> extends SingleTimeInterval<T> implements SequencedInterval<Day<T>> {

  Day(ZonedDateTime dateTime, T properties) {
    super(getBeginningOfTheDay(dateTime), getBeginningOfTheDay(dateTime).plusDays(1), properties);
  }

  public static <S> Day<S> of(ZonedDateTime dateTime) {
    return new Day<>(dateTime, null);
  }

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

  private static ZonedDateTime getBeginningOfTheDay(ZonedDateTime dateTime) {
    var zoneId = dateTime.getZone();
    var localDate = LocalDate.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());
    return ZonedDateTime.of(localDate.atTime(0, 0), zoneId);
  }
}
