package net.oliste.timeintervals4j.timeline.complex;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;
import net.oliste.timeintervals4j.timeline.Timeline;
import net.oliste.timeintervals4j.timeline.TimelineJoinOperation;
import net.oliste.timeintervals4j.timeline.TimelineOperation;
import net.oliste.timeintervals4j.timeline.TimelineSearch;

@ToString
@EqualsAndHashCode
public class ComplexTimeline<T> implements Timeline<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private final LinkedList<SingleTimeInterval<T>> intervals = new LinkedList<>();

  public ComplexTimeline() {}

  public ComplexTimeline(ComplexTimeline<T> src) {
    src.getIntervals().forEach(iv -> intervals.add(iv.createCopy()));
  }

  @Override
  public Optional<SingleTimeInterval<T>> getHead() {
    return !intervals.isEmpty() ? Optional.of(intervals.getFirst()) : Optional.empty();
  }

  @Override
  public Optional<SingleTimeInterval<T>> getTail() {
    return !intervals.isEmpty() ? Optional.of(intervals.getLast()) : Optional.empty();
  }

  @Override
  public LinkedList<SingleTimeInterval<T>> getIntervals() {
    return intervals;
  }

  void addInOrder(Collection<SingleTimeInterval<T>> intervals) {
    intervals.forEach(this::addInOrder);
  }

  void addInOrder(SingleTimeInterval<T> interval) {
    var it = intervals.listIterator();

    if (intervals.isEmpty()) {
      intervals.add(interval);
      return;
    }

    if (intervals.getFirst().isAfter(interval)) {
      intervals.addFirst(interval);
      return;
    }

    while (it.hasNext()) {
      var iv = it.next();
      if (!interval.isAfter(iv)) {
        intervals.add(it.previousIndex(), interval);
        return;
      }
    }

    it.add(interval);
  }

  void removeInRange(SingleTimeInterval<T> interval) {
    intervals.removeIf(interval::contains);
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