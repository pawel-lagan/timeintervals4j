package net.oliste.timeintervals4j.timeline;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

@ToString
@EqualsAndHashCode
public class ComplexTimeline<T> implements Timeline<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private final LinkedList<SingleTimeInterval<T>> intervals = new LinkedList<>();

  @Override
  public Optional<SingleTimeInterval<T>> getHead() {
    return intervals.size() > 0 ? Optional.of(intervals.getFirst()) : Optional.empty();
  }

  @Override
  public Optional<SingleTimeInterval<T>> getTail() {
    return intervals.size() > 0 ? Optional.of(intervals.getLast()) : Optional.empty();
  }

  @Override
  public List<SingleTimeInterval<T>> getIntervals() {
    return intervals;
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
