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

    result = TimeMath.max(pointB, pointA);
    assertThat(result).isEqualTo(pointB);
  }

  @Test
  void isBefore() {
    var pointA = fixture.createTimePoint(IntervalOffset._1);
    var pointB = fixture.createTimePoint(IntervalOffset._2);

    var result = TimeMath.isBefore(pointA, pointB);
    assertThat(result).isTrue();

    result = TimeMath.isBefore(pointA, pointA);
    assertThat(result).isFalse();

    result = TimeMath.isBefore(pointB, pointA);
    assertThat(result).isFalse();
  }

  @Test
  void isAfter() {
    var pointA = fixture.createTimePoint(IntervalOffset._2);
    var pointB = fixture.createTimePoint(IntervalOffset._1);

    var result = TimeMath.isAfter(pointA, pointB);
    assertThat(result).isTrue();

    result = TimeMath.isAfter(pointA, pointA);
    assertThat(result).isFalse();

    result = TimeMath.isAfter(pointB, pointA);
    assertThat(result).isFalse();
  }

  @Test
  void isBeforeOrEquals() {
    var pointA = fixture.createTimePoint(IntervalOffset._1);
    var pointB = fixture.createTimePoint(IntervalOffset._2);

    var result = TimeMath.isBeforeOrEquals(pointA, pointB);
    assertThat(result).isTrue();

    result = TimeMath.isBeforeOrEquals(pointA, pointA);
    assertThat(result).isTrue();

    result = TimeMath.isBeforeOrEquals(pointB, pointA);
    assertThat(result).isFalse();
  }

  @Test
  void isAfterOrEquals() {
    var pointA = fixture.createTimePoint(IntervalOffset._2);
    var pointB = fixture.createTimePoint(IntervalOffset._1);

    var result = TimeMath.isAfterOrEquals(pointA, pointB);
    assertThat(result).isTrue();

    result = TimeMath.isAfterOrEquals(pointA, pointA);
    assertThat(result).isTrue();

    result = TimeMath.isAfterOrEquals(pointB, pointA);
    assertThat(result).isFalse();
  }
}