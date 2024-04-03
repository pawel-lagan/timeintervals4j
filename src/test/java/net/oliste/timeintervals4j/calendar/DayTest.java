package net.oliste.timeintervals4j.calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

class DayTest {

  private final CalendarFixture fixture = new CalendarFixture();

  private final String props = "props";
  @Test
  void of() {
    var result = Day.of(fixture.getNowUtc(), props);
    assertThat(result.getFrom()).isEqualTo(fixture.getBeginningOfTheCurrentDay());
    assertThat(result.getTo()).isEqualTo(fixture.getBeginningOfTheCurrentDay().plusDays(1));
    assertThat(result.getProperties()).isEqualTo(props);
  }

  @Test
  void getNext() {
    var day = Day.of(fixture.getNowUtc(), props);
    var result = day.getNext();
    assertThat(result.getFrom()).isEqualTo(fixture.getBeginningOfTheCurrentDay().plusDays(1));
    assertThat(result.getTo()).isEqualTo(fixture.getBeginningOfTheCurrentDay().plusDays(2));
    assertThat(result.getProperties()).isEqualTo(props);
  }

  @Test
  void getPrev() {
    var day = Day.of(fixture.getNowUtc(), props);
    var result = day.getPrev();
    assertThat(result.getFrom()).isEqualTo(fixture.getBeginningOfTheCurrentDay().minusDays(1));
    assertThat(result.getTo()).isEqualTo(fixture.getBeginningOfTheCurrentDay());
    assertThat(result.getProperties()).isEqualTo(props);
  }
}