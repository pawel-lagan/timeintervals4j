package net.oliste.timeintervals4j.calendar;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class MonthTest {

  private final CalendarFixture fixture = new CalendarFixture();

  private final String props = "props";
  @Test
  void of() {
    var result = Month.of(fixture.getNowUtc(), props);
    assertThat(result.getFrom()).isEqualTo(fixture.getBeginningOfTheCurrentMonth());
    assertThat(result.getTo()).isEqualTo(fixture.getBeginningOfTheCurrentMonth().plusMonths(1));
    assertThat(result.getProperties()).isEqualTo(props);
  }

  @Test
  void getNext() {
    var day = Month.of(fixture.getNowUtc(), props);
    var result = day.getNext();
    assertThat(result.getFrom()).isEqualTo(fixture.getBeginningOfTheCurrentMonth().plusMonths(1));
    assertThat(result.getTo()).isEqualTo(fixture.getBeginningOfTheCurrentMonth().plusMonths(2));
    assertThat(result.getProperties()).isEqualTo(props);
  }

  @Test
  void getPrev() {
    var day = Month.of(fixture.getNowUtc(), props);
    var result = day.getPrev();
    assertThat(result.getFrom()).isEqualTo(fixture.getBeginningOfTheCurrentMonth().minusMonths(1));
    assertThat(result.getTo()).isEqualTo(fixture.getBeginningOfTheCurrentMonth());
    assertThat(result.getProperties()).isEqualTo(props);
  }

  @Test
  void getDays() {
    var day = Month.of(fixture.getNowUtc(), props);
    var result = day.getDays();
    assertThat(result).hasSize(31).containsSequence(fixture.getAllDaysOfCurrentMonth(props));
  }
}