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

    var result = intervalA.combine().diff(intervalB);
  }

  @Test
  void split() {
  }
}