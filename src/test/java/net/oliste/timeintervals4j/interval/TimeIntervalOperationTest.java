package net.oliste.timeintervals4j.interval;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalOffset;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalSize;
import org.junit.jupiter.api.Test;

class TimeIntervalOperationTest {

  private TimeIntervalFixture fixture = new TimeIntervalFixture(IntervalSize.L);
  private String propsA = "IntervalA";
  private String propsB = "IntervalB";

  @Test
  void withMergeStrategy() {
  }

  @Test
  void withSplitStrategy() {
  }

  @Test
  void intersection() {
    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._3, IntervalOffset._5, propsB);
    var expected = fixture.createInterval(IntervalOffset._3, IntervalOffset._5, propsA);

    var result = intervalA.combine().intersection(intervalB);
    assertThat(result).isEqualTo(expected);

    intervalB = fixture.createInterval(IntervalOffset._1, IntervalOffset._3, propsB);
    expected = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, propsA);

    result = intervalA.combine().intersection(intervalB);
    assertThat(result).isEqualTo(expected);

    intervalB = fixture.createInterval(IntervalOffset._5, IntervalOffset._7, propsB);
    expected = fixture.createInterval(IntervalOffset._5, IntervalOffset._6, propsA);

    result = intervalA.combine().intersection(intervalB);
    assertThat(result).isEqualTo(expected);

    intervalB = fixture.createInterval(IntervalOffset._1, IntervalOffset._7, propsB);
    expected = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA);

    result = intervalA.combine().intersection(intervalB);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void lackOfIntersection() {
    var intervalA = fixture.createInterval(IntervalOffset._3, IntervalOffset._5, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._1, IntervalOffset._3, propsB);

    assertThatThrownBy(() ->  intervalA.combine().intersection(intervalB))
        .isInstanceOf(TimeIntervalException.class);

    var intervalC = fixture.createInterval(IntervalOffset._5, IntervalOffset._6, propsB);

    assertThatThrownBy(() ->  intervalA.combine().intersection(intervalC))
        .isInstanceOf(TimeIntervalException.class);
  }

  @Test
  void union() {
    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._3, IntervalOffset._5, propsB);
    var expected = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA);

    var result = intervalA.combine().union(intervalB);
    assertThat(result).isEqualTo(expected);

    intervalB = fixture.createInterval(IntervalOffset._1, IntervalOffset._3, propsB);
    expected = fixture.createInterval(IntervalOffset._1, IntervalOffset._6, propsA);

    result = intervalA.combine().union(intervalB);
    assertThat(result).isEqualTo(expected);

    intervalB = fixture.createInterval(IntervalOffset._5, IntervalOffset._7, propsB);
    expected = fixture.createInterval(IntervalOffset._2, IntervalOffset._7, propsA);

    result = intervalA.combine().union(intervalB);
    assertThat(result).isEqualTo(expected);

    intervalB = fixture.createInterval(IntervalOffset._1, IntervalOffset._7, propsB);
    expected = fixture.createInterval(IntervalOffset._1, IntervalOffset._7, propsA);

    result = intervalA.combine().union(intervalB);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void unionImposibleDueToNotOverlappingIntervals() {
    var intervalA = fixture.createInterval(IntervalOffset._3, IntervalOffset._5, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._1, IntervalOffset._3, propsB);

    assertThatThrownBy(() ->  intervalA.combine().union(intervalB))
        .isInstanceOf(TimeIntervalException.class);

    var intervalC = fixture.createInterval(IntervalOffset._5, IntervalOffset._6, propsB);

    assertThatThrownBy(() ->  intervalA.combine().union(intervalC))
        .isInstanceOf(TimeIntervalException.class);
  }

  @Test
  void diff() {
    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._3, IntervalOffset._5, propsB);
    var expectedL = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, propsA);
    var expectedR = fixture.createInterval(IntervalOffset._5, IntervalOffset._6, propsA);

    var result = intervalA.combine().diff(intervalB);
    assertThat(result).containsOnly(expectedL, expectedR);

    intervalB = fixture.createInterval(IntervalOffset._3, IntervalOffset._6, propsB);
    expectedR = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, propsA);

    result = intervalA.combine().diff(intervalB);
    assertThat(result).containsOnly(expectedR);

    intervalB = fixture.createInterval(IntervalOffset._1, IntervalOffset._4, propsB);
    expectedR = fixture.createInterval(IntervalOffset._4, IntervalOffset._6, propsA);

    result = intervalA.combine().diff(intervalB);
    assertThat(result).containsOnly(expectedR);

    intervalB = fixture.createLeftOpenedInterval(IntervalOffset._3, propsB);
    expectedR = fixture.createInterval(IntervalOffset._3, IntervalOffset._6, propsA);

    result = intervalA.combine().diff(intervalB);
    assertThat(result).containsOnly(expectedR);

    intervalB = fixture.createInterval(IntervalOffset._4, IntervalOffset._6, propsB);
    expectedL = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, propsA);

    result = intervalA.combine().diff(intervalB);
    assertThat(result).containsOnly(expectedL);

    intervalB = fixture.createRightOpenedInterval(IntervalOffset._3, propsB);
    expectedL = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, propsA);

    result = intervalA.combine().diff(intervalB);
    assertThat(result).containsOnly(expectedL);
  }

  @Test
  void diffNotPossible() {
    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, propsB);

    assertThatThrownBy(() -> intervalA.combine().diff(intervalB))
        .isInstanceOf(TimeIntervalException.class);

    var intervalC = fixture.createInterval(IntervalOffset._6, IntervalOffset._7, propsB);

    assertThatThrownBy(() -> intervalA.combine().diff(intervalC))
        .isInstanceOf(TimeIntervalException.class);
  }

  @Test
  void split() {
    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA);
    var timestamp = fixture.createTimePoint(IntervalOffset._3);
    var expectedL = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, propsA);
    var expectedR = fixture.createInterval(IntervalOffset._3, IntervalOffset._6, propsA);

    var result = intervalA.combine().split(timestamp);
    assertThat(result).containsOnly(expectedL, expectedR);
  }

  @Test
  void splitNotPossible() {
    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA);
    var timestamp = fixture.createTimePoint(IntervalOffset._1);

    assertThatThrownBy(() -> intervalA.combine().split(timestamp))
        .isInstanceOf(TimeIntervalException.class);
  }
}