package net.oliste.timeintervals4j.timeline;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import net.oliste.timeintervals4j.interval.TimeIntervalFixture;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalOffset;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalSize;
import org.junit.jupiter.api.Test;

class ComplexTimelineTest {

  private final TimeIntervalFixture fixture = new TimeIntervalFixture(IntervalSize.L);
  private final String props = "props";

  @Test
  void headShouldBeEmptyWhenNoIntervals() {
    var timeline = new ComplexTimeline<String>();
    assertThat(timeline.getHead()).isEmpty();
  }

  @Test
  void headShouldReturnMostRecentInterval() {
    var timeline = new ComplexTimeline<String>();
    var intervalA = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    var intervalB = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    timeline.getIntervals().add(intervalA);
    timeline.getIntervals().add(intervalB);
    assertThat(timeline.getHead()).isNotEmpty().contains(intervalB);
  }

  @Test
  void tailShouldBeEmptyWhenNoIntervals() {
    var timeline = new ComplexTimeline<String>();
    assertThat(timeline.getTail()).isEmpty();
  }

  @Test
  void tailShouldReturnLatestInterval() {
    var timeline = new ComplexTimeline<String>();
    var intervalA = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    var intervalB = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    timeline.getIntervals().add(intervalA);
    timeline.getIntervals().add(intervalB);
    assertThat(timeline.getTail()).isNotEmpty().contains(intervalA);
  }

  @Test
  void timelineShouldBeEmptyAfterInitialization() {
    var timeline = new ComplexTimeline<String>();
    assertThat(timeline.getIntervals()).isEmpty();
  }

  @Test
  void timelineIntervalsShouldReturnAllIntervalsInTheTimeline() {
    var timeline = new ComplexTimeline<String>();
    var intervalA = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    var intervalB = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    timeline.getIntervals().add(intervalA);
    timeline.getIntervals().add(intervalB);
    assertThat(timeline.getIntervals()).containsExactly(intervalA, intervalB);
  }

  @Test
  void findShouldReturnTimelineSerchObject() {
    var timeline = new ComplexTimeline<String>();

    assertThat(timeline.find()).isInstanceOf(TimelineSearch.class);
  }

  @Test
  void modifyShouldReturnTimelineOperationObject() {
    var timeline = new ComplexTimeline<String>();

    assertThat(timeline.modify()).isInstanceOf(TimelineOperation.class);
  }

  @Test
  void joinShouldReturnTimelineJoinOperationObject() {
    var timeline = new ComplexTimeline<String>();

    assertThat(timeline.join()).isInstanceOf(TimelineJoinOperation.class);
  }

  @Test
  void addInOrderShouldAddElementAfterEachOther() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, "");
    var intervalB = fixture.createInterval(IntervalOffset._5, IntervalOffset._7, "");

    timeline.addInOrder(intervalA);
    timeline.addInOrder(intervalB);

    assertThat(timeline.getIntervals()).containsExactly(intervalA, intervalB);

    var intervalC = fixture.createInterval(IntervalOffset._4, IntervalOffset._5, "");
    timeline.addInOrder(intervalC);

    assertThat(timeline.getIntervals()).containsExactly(intervalA, intervalC, intervalB);

    var intervalD = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, "");
    timeline.addInOrder(intervalD);

    assertThat(timeline.getIntervals()).containsExactly(intervalD, intervalA, intervalC, intervalB);
  }

  @Test
  void addInOrderShouldAddElementAfterEachOther2() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._6, IntervalOffset._7, "A");
    var intervalB = fixture.createInterval(IntervalOffset._5, IntervalOffset._6, "B");

    timeline.addInOrder(intervalA);
    timeline.addInOrder(intervalB);

    assertThat(timeline.getIntervals()).containsExactly(intervalB, intervalA);

    var intervalC = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, "C");
    timeline.addInOrder(intervalC);

    assertThat(timeline.getIntervals()).containsExactly(intervalC, intervalB, intervalA);

    var intervalD = fixture.createInterval(IntervalOffset._3, IntervalOffset._4, "D");
    timeline.addInOrder(intervalD);

    assertThat(timeline.getIntervals()).containsExactly(intervalC, intervalD, intervalB, intervalA);
  }

  @Test
  void addInOrderShouldAddElementAfterEachOther3() {
    var timeline = new ComplexTimeline<String>();

    var expectedA = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, "A");
    var expectedB = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, "B");
    var expectedC = fixture.createInterval(IntervalOffset._3, IntervalOffset._4, "C");
    var expectedD = fixture.createInterval(IntervalOffset._4, IntervalOffset._5, "D");
    var expectedE = fixture.createInterval(IntervalOffset._5, IntervalOffset._7, "E");
    var expectedF = fixture.createRightOpenedInterval(IntervalOffset._7, "F");

    timeline.addInOrder(expectedD);
    timeline.addInOrder(expectedF);
    timeline.addInOrder(expectedA);
    timeline.addInOrder(expectedB);
    timeline.addInOrder(expectedE);
    timeline.addInOrder(expectedC);

    assertThat(timeline.getIntervals()).containsExactly(expectedA, expectedB, expectedC, expectedD, expectedE, expectedF);
  }



}