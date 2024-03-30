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
}