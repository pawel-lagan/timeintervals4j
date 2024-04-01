package net.oliste.timeintervals4j.timeline;


import static org.assertj.core.api.Assertions.assertThat;

import net.oliste.timeintervals4j.interval.TimeIntervalFixture;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalOffset;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalSize;
import org.junit.jupiter.api.Test;

class ComplexTimelineJoinOperationTest {

  private final TimeIntervalFixture fixture = new TimeIntervalFixture(IntervalSize.L);
  private final String propsA = "IntervalA";
  private final String propsB = "IntervalB";


  @Test
  void merge() {
    var timelineA = createTimelineA();
    var timelineB = createTimelineB();

    var result = timelineA.join().withMergeStrategy((a,b) -> a + b).merge(timelineB);

    var expectedA = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, propsB);
    var expectedB = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, propsA+propsB);
    var expectedC = fixture.createInterval(IntervalOffset._3, IntervalOffset._4, propsA);
    var expectedD = fixture.createInterval(IntervalOffset._4, IntervalOffset._5, propsB);
    var expectedE = fixture.createInterval(IntervalOffset._5, IntervalOffset._7, propsA+propsB);
    var expectedF = fixture.createRightOpenedInterval(IntervalOffset._7, propsB);


    assertThat(result.getIntervals()).containsExactly(expectedA, expectedB, expectedC, expectedD, expectedE, expectedF);
  }

  private ComplexTimeline<String> createTimelineB() {
    var timelineB = new ComplexTimeline<String>();

    var intervalC = fixture.createInterval(IntervalOffset._1, IntervalOffset._3, propsB);
    var intervalD = fixture.createRightOpenedInterval(IntervalOffset._4, propsB);

    timelineB.modify().insert(intervalC);
    timelineB.modify().insert(intervalD);
    return timelineB;
  }

  private ComplexTimeline<String> createTimelineA() {
    var timelineA = new ComplexTimeline<String>();

    var intervalA = fixture.createInterval(IntervalOffset._2, IntervalOffset._4, propsA);
    var intervalB = fixture.createInterval(IntervalOffset._5, IntervalOffset._7, propsA);

    timelineA.modify().insert(intervalA);
    timelineA.modify().insert(intervalB);
    return timelineA;
  }

  @Test
  void intersection() {
    var timelineA = createTimelineA();
    var timelineB = createTimelineB();

    var result = timelineA.join().withMergeStrategy((a,b) -> a + b).intersection(timelineB);

    var expectedB = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, propsA+propsB);
    var expectedE = fixture.createInterval(IntervalOffset._5, IntervalOffset._7, propsA+propsB);


    assertThat(result.getIntervals()).containsExactly(expectedB, expectedE);
  }

  @Test
  void diff() {
    var timelineA = createTimelineA();
    var timelineB = createTimelineB();

    var result = timelineA.join().withMergeStrategy((a,b) -> a + b).diff(timelineB);
    var expectedC = fixture.createInterval(IntervalOffset._3, IntervalOffset._4, propsA);

    assertThat(result.getIntervals()).containsExactly(expectedC);

    result = timelineB.join().withMergeStrategy((a,b) -> a + b).diff(timelineA);
    var expectedA = fixture.createInterval(IntervalOffset._1, IntervalOffset._2, propsB);
    var expectedD = fixture.createInterval(IntervalOffset._4, IntervalOffset._5, propsB);
    var expectedF = fixture.createRightOpenedInterval(IntervalOffset._7, propsB);

    assertThat(result.getIntervals()).containsExactly(expectedA,expectedD,expectedF);
  }

  @Test
  void alignIntervalsTo() {
    var timelineA = createTimelineA();
    var timelineB = createTimelineB();

    var result = timelineA.join().withMergeStrategy((a,b) -> a + b).alignIntervalsTo(timelineB);

    var expectedB = fixture.createInterval(IntervalOffset._2, IntervalOffset._3, propsA);
    var expectedC = fixture.createInterval(IntervalOffset._3, IntervalOffset._4, propsA);
    var expectedE = fixture.createInterval(IntervalOffset._5, IntervalOffset._7, propsA);

    assertThat(result.getIntervals()).containsExactly(expectedB, expectedC, expectedE);
  }
}