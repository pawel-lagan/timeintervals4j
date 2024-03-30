package net.oliste.timeintervals4j.timeline;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import net.oliste.timeintervals4j.interval.TimeIntervalException;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalOffset;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalSize;
import org.junit.jupiter.api.Test;

class ComplexTimelineOperationTest {

  private final TimeIntervalFixture fixture = new TimeIntervalFixture(IntervalSize.L);
  private final String propsA = "IntervalA";

  @Test
  void insertShouldAddElementAfterEachOther() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._4, IntervalOffset._7, propsA);

    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);

    assertThat(timeline.getIntervals()).containsExactly(intervalA, intervalB);
  }

  @Test
  void insertShouldAddElementAfterEachOtherByDates() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._4, IntervalOffset._7, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, propsA);

    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);

    assertThat(timeline.getIntervals()).containsExactly(intervalB, intervalA);

    var intervalC = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, propsA);

    timeline.modify().insert(intervalC);

    assertThat(timeline.getIntervals()).containsExactly(intervalC, intervalB, intervalA);
  }

  @Test
  void insertWhenOverlappingIntervalExistsShouldThrowAnError() {
    var timeline = new ComplexTimeline<String>();

    timeline.modify().insert(fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA));
    assertThatThrownBy(() -> timeline.modify().insert(fixture.createInterval(IntervalOffset._1, IntervalOffset._7, propsA)))
        .isInstanceOf(TimeIntervalException.class);
  }

  @Test
  void overwriteShouldInsertIntervalsWhenNoOverlapping() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._4, IntervalOffset._7, propsA);

    timeline.modify().overwrite(intervalA);
    timeline.modify().overwrite(intervalB);

    assertThat(timeline.getIntervals()).containsExactly(intervalA, intervalB);
  }

  @Test
  void overwriteShouldModifyExistingAndInsertNew() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._4, IntervalOffset._7, propsA);

    timeline.modify().overwrite(intervalA);
    timeline.modify().overwrite(intervalB);

    var expectedA = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, propsA);

    assertThat(timeline.getIntervals()).containsExactly(expectedA, intervalB);
  }

  @Test
  void removeShouldRemoveOnlyIntervalsContainedInRemovedInterval() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._3, IntervalOffset._4, propsA);
    var intervalC = fixture.createInterval(IntervalOffset._4, IntervalOffset._6, propsA);

    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);
    timeline.modify().insert(intervalC);


    var toRemove = fixture.createInterval(IntervalOffset._3, IntervalOffset._5, propsA);

    timeline.modify().remove(toRemove);

    assertThat(timeline.getIntervals()).containsExactly(intervalA, intervalC);
  }

  @Test
  void removeInShouldRemoveIntervalsContainedAndTrimOverlappedInRemovedInterval() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._3, IntervalOffset._4, propsA);
    var intervalC = fixture.createInterval(IntervalOffset._4, IntervalOffset._6, propsA);

    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);
    timeline.modify().insert(intervalC);


    var toRemove = fixture.createInterval(IntervalOffset._3, IntervalOffset._5, propsA);

    timeline.modify().removeIn(toRemove);

    var expectedC = fixture.createInterval(IntervalOffset._5, IntervalOffset._6, propsA);
    assertThat(timeline.getIntervals()).containsExactly(intervalA, expectedC);
  }

  @Test
  void divide() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._6, propsA);
    var timestamp = fixture.createTimePoint(IntervalOffset._4);

    timeline.modify().insert(intervalA);

    timeline.modify().divide(timestamp);

    var expectedA = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, propsA);
    var expectedB = fixture.createInterval(IntervalOffset._4, IntervalOffset._6, propsA);
    assertThat(timeline.getIntervals()).containsExactly(expectedA, expectedB);
  }
}