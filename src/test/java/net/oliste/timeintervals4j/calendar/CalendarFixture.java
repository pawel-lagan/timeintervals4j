package net.oliste.timeintervals4j.calendar;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CalendarFixture {

  private final ZonedDateTime NOW =
      LocalDateTime.parse("2024-03-25T12:00:00").atZone(ZoneId.of("UTC"));

  private final ZonedDateTime NOW_BEGINNING_OF_THE_DAY =
      LocalDateTime.parse("2024-03-25T00:00:00").atZone(ZoneId.of("UTC"));

  private final ZonedDateTime NOW_BEGINNING_OF_THE_MONTH =
      LocalDateTime.parse("2024-03-01T00:00:00").atZone(ZoneId.of("UTC"));

  private final ZonedDateTime NOW_BEGINNING_OF_THE_YEAR =
      LocalDateTime.parse("2024-01-01T00:00:00").atZone(ZoneId.of("UTC"));

  public CalendarFixture() {
  }

  public ZonedDateTime getNowUtc() {
    return NOW;
  }

  public ZonedDateTime getBeginningOfTheCurrentDay() {
    return NOW_BEGINNING_OF_THE_DAY;
  }

  public ZonedDateTime getBeginningOfTheCurrentMonth() {
    return NOW_BEGINNING_OF_THE_MONTH;
  }

  public ZonedDateTime getBeginningOfTheCurrentYear() {
    return NOW_BEGINNING_OF_THE_YEAR;
  }

  public <T> List<Day<T>> getAllDaysOfCurrentMonth(T props) {
    return IntStream.rangeClosed(1,31).mapToObj(i -> new Day<>(getBeginningOfTheCurrentMonth().plusDays(i-1), props)).collect(
        Collectors.toList());
  }

  public <T> List<Month<T>> getAllMonthsOfCurrentYear(T props) {
    return IntStream.rangeClosed(1,12).mapToObj(i -> new Month<>(getBeginningOfTheCurrentYear().plusMonths(i-1), props)).collect(
        Collectors.toList());
  }

}
