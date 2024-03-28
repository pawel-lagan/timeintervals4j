package net.oliste.timeintervals4j.interval;

import static org.assertj.core.api.Assertions.assertThat;

import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalOffset;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalSize;
import org.junit.jupiter.api.Test;

class TimeIntervalTest {

  private TimeIntervalFixture fixture = new TimeIntervalFixture(IntervalSize.L);
  private String props = "Interval props";

  @Test
  void leftOpenedIntervalContainsTimePoint() {
    var interval = fixture.createLeftOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createTimePoint(IntervalOffset._2);
    assertThat(interval.contains(case1)).isFalse();

    var case2 = fixture.createTimePoint(IntervalOffset._1);
    assertThat(interval.contains(case2)).isTrue();

    var case3 = fixture.createTimePoint(IntervalOffset._3);
    assertThat(interval.contains(case3)).isFalse();
  }

  @Test
  void rightOpenedIntervalContainsTimePoint() {
    var interval = fixture.createRightOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createTimePoint(IntervalOffset._2);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = fixture.createTimePoint(IntervalOffset._1);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = fixture.createTimePoint(IntervalOffset._3);
    assertThat(interval.contains(case3)).isTrue();
  }

  @Test
  void closedIntervalContainsTimePoint() {
    var interval = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, props);

    var case1 = fixture.createTimePoint(IntervalOffset._2);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = fixture.createTimePoint(IntervalOffset._1);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = fixture.createTimePoint(IntervalOffset._3);
    assertThat(interval.contains(case3)).isTrue();

    var case4 = fixture.createTimePoint(IntervalOffset._6);
    assertThat(interval.contains(case4)).isFalse();

    var case5 = fixture.createTimePoint(IntervalOffset._7);
    assertThat(interval.contains(case5)).isFalse();
  }


  @Test
  void leftOpenedIntervalContainsLeftOpenedInterval() {
    var interval = fixture.createLeftOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createLeftOpenedInterval(IntervalOffset._2, props);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = fixture.createLeftOpenedInterval(IntervalOffset._1, props);
    assertThat(interval.contains(case2)).isTrue();

    var case3 = fixture.createLeftOpenedInterval(IntervalOffset._3, props);
    assertThat(interval.contains(case3)).isFalse();

    var case4 = fixture.createInfinityInterval(props);
    assertThat(interval.contains(case4)).isFalse();

    var case5 = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    assertThat(interval.contains(case5)).isTrue();
  }

  @Test
  void leftOpenedIntervalContainsRightOpenedInterval() {
    var interval = fixture.createLeftOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createRightOpenedInterval(IntervalOffset._2, props);
    assertThat(interval.contains(case1)).isFalse();

    var case2 = fixture.createRightOpenedInterval(IntervalOffset._1, props);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = fixture.createRightOpenedInterval(IntervalOffset._3, props);
    assertThat(interval.contains(case3)).isFalse();

    var case4 = fixture.createInfinityInterval(props);
    assertThat(interval.contains(case4)).isFalse();
  }

  @Test
  void leftOpenedIntervalContainsClosedInterval() {
    var interval = fixture.createLeftOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, props);
    assertThat(interval.contains(case2)).isFalse();
  }

  @Test
  void rightOpenedIntervalContainsLeftOpenedInterval() {
    var interval = fixture.createRightOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createLeftOpenedInterval(IntervalOffset._2, props);
    assertThat(interval.contains(case1)).isFalse();

    var case2 = fixture.createLeftOpenedInterval(IntervalOffset._1, props);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = fixture.createLeftOpenedInterval(IntervalOffset._3, props);
    assertThat(interval.contains(case3)).isFalse();

    var case4 = fixture.createInfinityInterval(props);
    assertThat(interval.contains(case4)).isFalse();
  }

  @Test
  void rightOpenedIntervalContainsRightOpenedInterval() {
    var interval = fixture.createRightOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createRightOpenedInterval(IntervalOffset._2, props);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = fixture.createRightOpenedInterval(IntervalOffset._1, props);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = fixture.createRightOpenedInterval(IntervalOffset._3, props);
    assertThat(interval.contains(case3)).isTrue();

    var case4 = fixture.createInfinityInterval(props);
    assertThat(interval.contains(case4)).isFalse();
  }

  @Test
  void rightOpenedIntervalContainsClosedInterval() {
    var interval = fixture.createRightOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, props);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    assertThat(interval.contains(case2)).isFalse();
  }

  @Test
  void closedIntervalContainsLeftOpenedInterval() {
    var interval = fixture.createInterval(IntervalOffset._2, IntervalOffset._5, props);

    var case1 = fixture.createLeftOpenedInterval(IntervalOffset._2, props);
    assertThat(interval.contains(case1)).isFalse();

    var case2 = fixture.createLeftOpenedInterval(IntervalOffset._1, props);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = fixture.createLeftOpenedInterval(IntervalOffset._3, props);
    assertThat(interval.contains(case3)).isFalse();

    var case4 = fixture.createInfinityInterval(props);
    assertThat(interval.contains(case4)).isFalse();
  }

  @Test
  void closedIntervalContainsRightOpenedInterval() {
    var interval = fixture.createInterval(IntervalOffset._2, IntervalOffset._5, props);

    var case1 = fixture.createRightOpenedInterval(IntervalOffset._2, props);
    assertThat(interval.contains(case1)).isFalse();

    var case2 = fixture.createRightOpenedInterval(IntervalOffset._1, props);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = fixture.createRightOpenedInterval(IntervalOffset._3, props);
    assertThat(interval.contains(case3)).isFalse();

    var case4 = fixture.createInfinityInterval(props);
    assertThat(interval.contains(case4)).isFalse();
  }

  @Test
  void closedIntervalContainsClosedInterval() {
    var interval = fixture.createInterval(IntervalOffset._2, IntervalOffset._5, props);

    var case1 = fixture.createInterval(IntervalOffset._2, IntervalOffset._5, props);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, props);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = fixture.createInterval(IntervalOffset._3, IntervalOffset._5, props);
    assertThat(interval.contains(case3)).isTrue();

    var case4 = fixture.createInterval(IntervalOffset._1, IntervalOffset._5, props);
    assertThat(interval.contains(case4)).isFalse();

    var case5 = fixture.createInterval(IntervalOffset._1, IntervalOffset._7, props);
    assertThat(interval.contains(case5)).isFalse();

    var case6 = fixture.createInfinityInterval(props);
    assertThat(interval.contains(case6)).isFalse();
  }

  @Test
  void leftOpenedIntervalOverlapsLeftOpenedInterval() {
    var interval = fixture.createLeftOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createLeftOpenedInterval(IntervalOffset._2, props);
    assertThat(interval.overlaps(case1)).isTrue();

    var case2 = fixture.createLeftOpenedInterval(IntervalOffset._1, props);
    assertThat(interval.overlaps(case2)).isTrue();

    var case3 = fixture.createLeftOpenedInterval(IntervalOffset._3, props);
    assertThat(interval.overlaps(case3)).isTrue();

    var case4 = fixture.createInfinityInterval(props);
    assertThat(interval.overlaps(case4)).isTrue();

    var case5 = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    assertThat(interval.overlaps(case5)).isTrue();

    var case6 = fixture.createInterval(IntervalOffset._3, IntervalOffset._4, props);
    assertThat(interval.overlaps(case6)).isFalse();

    var case7 = fixture.createRightOpenedInterval(IntervalOffset._3, props);
    assertThat(interval.overlaps(case7)).isFalse();
  }

  @Test
  void leftOpenedIntervalOverlapsRightOpenedInterval() {
    var interval = fixture.createLeftOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createRightOpenedInterval(IntervalOffset._2, props);
    assertThat(interval.overlaps(case1)).isFalse();

    var case2 = fixture.createRightOpenedInterval(IntervalOffset._1, props);
    assertThat(interval.overlaps(case2)).isTrue();

    var case3 = fixture.createRightOpenedInterval(IntervalOffset._3, props);
    assertThat(interval.overlaps(case3)).isFalse();

    var case4 = fixture.createInfinityInterval(props);
    assertThat(interval.overlaps(case4)).isTrue();
  }

  @Test
  void leftOpenedIntervalOverlapsClosedInterval() {
    var interval = fixture.createLeftOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    assertThat(interval.overlaps(case1)).isTrue();

    var case2 = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, props);
    assertThat(interval.overlaps(case2)).isFalse();

    var case3 =  fixture.createRightOpenedInterval(IntervalOffset._2, props);
    assertThat(interval.overlaps(case3)).isFalse();

    var case4 =  fixture.createRightOpenedInterval(IntervalOffset._1, props);
    assertThat(interval.overlaps(case4)).isTrue();
  }

  @Test
  void rightOpenedIntervalOverlapsLeftOpenedInterval() {
    var interval = fixture.createRightOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createLeftOpenedInterval(IntervalOffset._2, props);
    assertThat(interval.overlaps(case1)).isFalse();

    var case2 = fixture.createLeftOpenedInterval(IntervalOffset._1, props);
    assertThat(interval.overlaps(case2)).isFalse();

    var case3 = fixture.createLeftOpenedInterval(IntervalOffset._3, props);
    assertThat(interval.overlaps(case3)).isTrue();

    var case4 = fixture.createInfinityInterval(props);
    assertThat(interval.overlaps(case4)).isTrue();
  }

  @Test
  void rightOpenedIntervalOverlapsRightOpenedInterval() {
    var interval = fixture.createRightOpenedInterval(IntervalOffset._2, props);

    var case1 = fixture.createRightOpenedInterval(IntervalOffset._2, props);
    assertThat(interval.overlaps(case1)).isTrue();

    var case2 = fixture.createRightOpenedInterval(IntervalOffset._1, props);
    assertThat(interval.overlaps(case2)).isTrue();

    var case3 = fixture.createRightOpenedInterval(IntervalOffset._3, props);
    assertThat(interval.overlaps(case3)).isTrue();

    var case4 = fixture.createInfinityInterval(props);
    assertThat(interval.overlaps(case4)).isTrue();
  }

  @Test
  void rightOpenedIntervalOverlapsClosedInterval() {
    var interval = fixture.createRightOpenedInterval(IntervalOffset._3, props);

    var case1 = fixture.createInterval(IntervalOffset._4, IntervalOffset._5, props);
    assertThat(interval.overlaps(case1)).isTrue();

    var case1b = fixture.createInterval(IntervalOffset._3, IntervalOffset._5, props);
    assertThat(interval.overlaps(case1b)).isTrue();

    var case2 = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, props);
    assertThat(interval.overlaps(case2)).isFalse();

    var case3 = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    assertThat(interval.overlaps(case3)).isFalse();
  }

  @Test
  void closedIntervalOverlapsClosedInterval() {
    var interval = fixture.createInterval(IntervalOffset._2,IntervalOffset._6, props);

    var case1 = fixture.createInterval(IntervalOffset._3, IntervalOffset._5, props);
    assertThat(interval.overlaps(case1)).isTrue();

    var case2 = fixture.createInterval(IntervalOffset._1, IntervalOffset._3, props);
    assertThat(interval.overlaps(case2)).isTrue();

    var case3 = fixture.createInterval(IntervalOffset._4, IntervalOffset._7, props);
    assertThat(interval.overlaps(case3)).isTrue();

    var case4 = fixture.createInterval(IntervalOffset._1, IntervalOffset._7, props);
    assertThat(interval.overlaps(case4)).isTrue();

    interval = fixture.createInterval(IntervalOffset._3,IntervalOffset._5, props);

    var case5 = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    assertThat(interval.overlaps(case5)).isFalse();

    var case6 = fixture.createInterval(IntervalOffset._6, IntervalOffset._7, props);
    assertThat(interval.overlaps(case6)).isFalse();

  }


  @Test
  void testContains() {
  }

  @Test
  void overlaps() {
  }

  @Test
  void combine() {
  }

}