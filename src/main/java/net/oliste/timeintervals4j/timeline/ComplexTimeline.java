package net.oliste.timeintervals4j.timeline;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

@ToString
@EqualsAndHashCode
public class ComplexTimeline<T> implements Timeline<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  @Override
  public boolean isContinuous() {
    return false;
  }

  @Override
  public boolean hasGaps() {
    return false;
  }

  @Override
  public SingleTimeInterval<T> getHead() {
    return null;
  }

  @Override
  public SingleTimeInterval<T> getTail() {
    return null;
  }

  @Override
  public List<SingleTimeInterval<T>> getIntervals() {
    return null;
  }

  @Override
  public TimelineSearch<T, SingleTimeInterval<T>, ComplexTimeline<T>> find() {
    return new ComplexTimelineSearch<>(this);
  }

  @Override
  public TimelineOperation<T, SingleTimeInterval<T>, ComplexTimeline<T>> modify() {
    return new ComplexTimelineOperation<>(this);
  }

  @Override
  public TimelineJoinOperation<T, SingleTimeInterval<T>, ComplexTimeline<T>> join() {
    return new ComplexTimelineJoinOperation<>(this);
  }
}
