package net.oliste.timeintervals4j.timeline.complex;

import java.time.ZonedDateTime;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;
import net.oliste.timeintervals4j.interval.TimeIntervalException;
import net.oliste.timeintervals4j.timeline.TimelineOperation;

public class ComplexTimelineOperation<T>
    implements TimelineOperation<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private final ComplexTimeline<T> timeline;
  private BinaryOperator<T> mergeStrategy = (propertiesA, propertiesB) -> propertiesA;
  private UnaryOperator<T> splitStrategy = propertiesA -> propertiesA;

  public ComplexTimelineOperation(ComplexTimeline<T> timeline) {
    this.timeline = timeline;
  }

  @Override
  public ComplexTimelineOperation<T> withMergeStrategy(BinaryOperator<T> strategy) {
    this.mergeStrategy = strategy;
    return this;
  }

  @Override
  public ComplexTimelineOperation<T> withSplitStrategy(UnaryOperator<T> strategy) {
    this.splitStrategy = strategy;
    return this;
  }

  @Override
  public void insert(SingleTimeInterval<T> interval) {
    var overlappingIntervals = timeline.find().findOverlapping(interval);
    if (overlappingIntervals.isEmpty()) {
      timeline.addInOrder(interval);
    } else {
      throw new TimeIntervalException(
          String.format(
              "Overlapping interval found for %s => [%s]",
              interval,
              overlappingIntervals.stream()
                  .map(SingleTimeInterval::toString)
                  .collect(Collectors.joining(", "))));
    }
  }

  @Override
  public void overwrite(SingleTimeInterval<T> interval) {
    removeIn(interval);
    insert(interval);
  }

  @Override
  public void remove(SingleTimeInterval<T> interval) {
    timeline.removeInRange(interval);
  }

  @Override
  public void removeIn(SingleTimeInterval<T> interval) {
    var overlappingIntervals = timeline.find().findOverlapping(interval);
    overlappingIntervals.forEach(this::remove);
    overlappingIntervals.stream()
        .filter(interval::notContains)
        .forEach(iv -> timeline.addInOrder(iv.combine().diff(interval)));
  }

  @Override
  public void divide(ZonedDateTime timestamp) {
    var overlappingInterval = timeline.find().findContaining(timestamp);
    overlappingInterval.ifPresent(
        iv -> {
          timeline.removeInRange(iv);
          timeline.addInOrder(iv.combine().split(timestamp));
        });
  }
}
