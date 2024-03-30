package net.oliste.timeintervals4j.timeline;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import net.oliste.timeintervals4j.interval.TimeIntervalException;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalOffset;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalSize;
import org.junit.jupiter.api.Test;

class ComplexTimelineOperationTest {

  private TimeIntervalFixture fixture = new TimeIntervalFixture(IntervalSize.L);
  private String propsA = "IntervalA";

  @Test
  void insert() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._4, IntervalOffset._7, propsA);

    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);

    assertThat(timeline.getIntervals()).containsExactly(intervalA, intervalB);
  }

  @Test
  void insertWhenOverlappingIntervalExsistsShouldThrowAnError() {
    var timeline = new ComplexTimeline<String>();

    timeline.modify().insert(fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA));
    assertThatThrownBy(() -> timeline.modify().insert(fixture.createInterval(IntervalOffset._1, IntervalOffset._7, propsA)))
        .isInstanceOf(TimeIntervalException.class);
  }

  @Test
  void overwrite() {
  }

  @Test
  void remove() {
  }

  @Test
  void removeIn() {
  }

  @Test
  void divide() {
  }
}