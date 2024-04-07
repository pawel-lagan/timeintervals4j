package net.oliste.timeintervals4j.calendar;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public class Month<T> extends SingleTimeInterval<T> implements SequencedInterval<Month<T>> {

  Month(ZonedDateTime dateTime, T properties) {
    super(
        getBeginningOfTheMonth(dateTime),
        getBeginningOfTheMonth(dateTime).plusMonths(1),
        properties);
  }

  public static <S> Month<S> of(ZonedDateTime dateTime) {
    return new Month<>(dateTime, null);
  }

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

  public List<Day<T>> getDays() {
    var days = getFrom().getMonth().length(Year.isLeap(getFrom().getYear()));
    var list = new ArrayList<Day<T>>(days);
    for (var i = 1; i <= days; i++) {
      list.add(new Day<>(getFrom().plusDays(i - 1), getProperties()));
    }
    return list;
  }

  private static ZonedDateTime getBeginningOfTheMonth(ZonedDateTime dateTime) {
    var zoneId = dateTime.getZone();
    var localDate = LocalDate.of(dateTime.getYear(), dateTime.getMonthValue(), 1);
    return ZonedDateTime.of(localDate.atTime(0, 0), zoneId);
  }
}
