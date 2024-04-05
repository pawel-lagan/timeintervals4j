package net.oliste.timeintervals4j.calendar;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class YearTest {

  private final CalendarFixture fixture = new CalendarFixture();

  private final String props = "props";

  @Test
  void of() {
    var result = Year.of(fixture.getNowUtc(), props);
    assertThat(result.getFrom()).isEqualTo(fixture.getBeginningOfTheCurrentYear());
    assertThat(result.getTo()).isEqualTo(fixture.getBeginningOfTheCurrentYear().plusYears(1));
    assertThat(result.getProperties()).isEqualTo(props);
  }

  @Test
  void getNext() {
    var day = Year.of(fixture.getNowUtc(), props);
    var result = day.getNext();
    assertThat(result.getFrom()).isEqualTo(fixture.getBeginningOfTheCurrentYear().plusYears(1));
    assertThat(result.getTo()).isEqualTo(fixture.getBeginningOfTheCurrentYear().plusYears(2));
    assertThat(result.getProperties()).isEqualTo(props);
  }

  @Test
  void getPrev() {
    var day = Year.of(fixture.getNowUtc(), props);
    var result = day.getPrev();
    assertThat(result.getFrom()).isEqualTo(fixture.getBeginningOfTheCurrentYear().minusYears(1));
    assertThat(result.getTo()).isEqualTo(fixture.getBeginningOfTheCurrentYear());
    assertThat(result.getProperties()).isEqualTo(props);
  }

  @Test
  void getMonths() {
    var day = Year.of(fixture.getNowUtc(), props);
    var result = day.getMonths();
    assertThat(result).hasSize(12).containsSequence(fixture.getAllMonthsOfCurrentYear(props));
  }
}
