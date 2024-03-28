package net.oliste.timeintervals4j.math;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import net.oliste.timeintervals4j.interval.TimeIntervalFixture;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalOffset;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalSize;
import org.junit.jupiter.api.Test;

class TimeMathTest {

  private final TimeIntervalFixture fixture = new TimeIntervalFixture(IntervalSize.L);

  @Test
  void min() {
    var pointA = fixture.createTimePoint(IntervalOffset._1);
    var pointB = fixture.createTimePoint(IntervalOffset._2);

    var result = TimeMath.min(pointA, pointB);
    assertThat(result).isEqualTo(pointA);

    result = TimeMath.min(pointB, pointA);
    assertThat(result).isEqualTo(pointA);
  }

  @Test
  void max() {
    var pointA = fixture.createTimePoint(IntervalOffset._1);
    var pointB = fixture.createTimePoint(IntervalOffset._2);

    var result = TimeMath.max(pointA, pointB);
    assertThat(result).isEqualTo(pointB);

    result = TimeMath.min(pointB, pointA);
    assertThat(result).isEqualTo(pointB);
  }

  @Test
  void isBefore() {
  }

  @Test
  void isAfter() {
  }

  @Test
  void isBeforeOrEquals() {
  }

  @Test
  void isAfterOrEquals() {
  }
}