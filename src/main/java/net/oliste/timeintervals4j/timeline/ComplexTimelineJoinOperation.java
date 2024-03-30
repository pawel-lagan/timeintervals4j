package net.oliste.timeintervals4j.timeline;

import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public class ComplexTimelineJoinOperation<T> implements TimelineJoinOperation<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private final ComplexTimeline<T> timeline;

  public ComplexTimelineJoinOperation(ComplexTimeline<T> timeline) {
    this.timeline = timeline;
  }

  @Override
  public ComplexTimeline<T> merge(ComplexTimeline<T> timeline) {
    return null;
  }

  @Override
  public ComplexTimeline<T> intersection(ComplexTimeline<T> timeline) {
    return null;
  }

  @Override
  public ComplexTimeline<T> diff(ComplexTimeline<T> timeline) {
    return null;
  }

  @Override
  public ComplexTimeline<T> alignIntervalsTo(ComplexTimeline<T> timeline) {
    return null;
  }
}
