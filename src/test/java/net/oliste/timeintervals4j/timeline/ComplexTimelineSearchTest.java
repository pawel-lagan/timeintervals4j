package net.oliste.timeintervals4j.timeline;

import static org.assertj.core.api.Assertions.assertThat;

import net.oliste.timeintervals4j.interval.TimeIntervalFixture;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalOffset;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalSize;
import org.junit.jupiter.api.Test;

class ComplexTimelineSearchTest {
  private final TimeIntervalFixture fixture = new TimeIntervalFixture(IntervalSize.L);
  private final String props = "props";

  @Test
  void findOverlapping() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, props);
    var intervalB = fixture.createInterval(IntervalOffset._4, IntervalOffset._7, props);

    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);

    var searchInterval = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, props);

    var result = timeline.find().findOverlapping(searchInterval);
    assertThat(result).containsExactly(intervalA);

    searchInterval = fixture.createInterval(IntervalOffset._2, IntervalOffset._5, props);

    result = timeline.find().findOverlapping(searchInterval);
    assertThat(result).containsExactly(intervalA, intervalB);
  }

  @Test
  void findContaining() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, props);
    var intervalB = fixture.createInterval(IntervalOffset._4, IntervalOffset._7, props);

    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);

    var searchInterval = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, props);

    var result = timeline.find().findContaining(searchInterval);
    assertThat(result).containsExactly(intervalA);

    searchInterval = fixture.createInterval(IntervalOffset._2, IntervalOffset._5, props);

    result = timeline.find().findContaining(searchInterval);
    assertThat(result).containsExactly(intervalA);
  }

  @Test
  void testFindContaining() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, props);
    var intervalB = fixture.createInterval(IntervalOffset._4, IntervalOffset._7, props);

    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);

    var timestamp = fixture.createTimePoint(IntervalOffset._3);

    var result = timeline.find().findContaining(timestamp);
    assertThat(result).isNotEmpty().contains(intervalA);

    timestamp = fixture.createTimePoint(IntervalOffset._5);

    result = timeline.find().findContaining(timestamp);
    assertThat(result).isNotEmpty().contains(intervalB);

    timestamp = fixture.createTimePoint(IntervalOffset._1);

    result = timeline.find().findContaining(timestamp);
    assertThat(result).isEmpty();
  }

  @Test
  void findLeftNearest() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    var intervalA2 = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, props);
    var intervalA3 = fixture.createInterval(IntervalOffset._3, IntervalOffset._4, props);
    var intervalB = fixture.createInterval(IntervalOffset._6, IntervalOffset._7, props);

    timeline.modify().insert(intervalA3);
    timeline.modify().insert(intervalA2);
    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);

    var intervalC = fixture.createInterval(IntervalOffset._5, IntervalOffset._6, props);

    var result = timeline.find().findLeftNearest(intervalC);
    assertThat(result).isNotEmpty().contains(intervalA3);

    intervalC = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);

    result = timeline.find().findLeftNearest(intervalC);
    assertThat(result).isEmpty();
  }

  @Test
  void findRightNearest() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    var intervalB = fixture.createInterval(IntervalOffset._5, IntervalOffset._6, props);
    var intervalB2 = fixture.createInterval(IntervalOffset._6, IntervalOffset._7, props);

    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);
    timeline.modify().insert(intervalB2);

    var intervalC = fixture.createInterval(IntervalOffset._3, IntervalOffset._4, props);

    var result = timeline.find().findRightNearest(intervalC);
    assertThat(result).isNotEmpty().contains(intervalB);

    intervalC = fixture.createInterval(IntervalOffset._6, IntervalOffset._7, props);

    result = timeline.find().findRightNearest(intervalC);
    assertThat(result).isEmpty();
  }

  @Test
  void findNearestShouldReturnRightAsNearest() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    var intervalB = fixture.createInterval(IntervalOffset._6, IntervalOffset._7, props);

    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);

    var intervalC = fixture.createInterval(IntervalOffset._4, IntervalOffset._5, props);

    var result = timeline.find().findNearest(intervalC);
    assertThat(result).isNotEmpty().contains(intervalB);
  }

  @Test
  void findNearestShouldReturnLeftAsNearest() {
    var timeline = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, props);
    var intervalB = fixture.createInterval(IntervalOffset._6, IntervalOffset._7, props);

    timeline.modify().insert(intervalA);
    timeline.modify().insert(intervalB);

    var intervalC = fixture.createInterval(IntervalOffset._3, IntervalOffset._4, props);

    var result = timeline.find().findNearest(intervalC);
    assertThat(result).isNotEmpty().contains(intervalA);
  }
}
