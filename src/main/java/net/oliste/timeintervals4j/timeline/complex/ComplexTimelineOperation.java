package net.oliste.timeintervals4j.timeline.complex;

import java.time.ZonedDateTime;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;
import net.oliste.timeintervals4j.timeline.TimelineOperation;

/**
 * Represents an object implementing all modify methods.
 *
 * @author Paweł Łagan
 * @param <T> time interval properties type
 */
public class ComplexTimelineOperation<T>
    implements TimelineOperation<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private final ComplexTimeline<T> timeline;
  private BinaryOperator<T> mergeStrategy = (propertiesA, propertiesB) -> propertiesA;
  private UnaryOperator<T> splitStrategy = propertiesA -> propertiesA;

  ComplexTimelineOperation(ComplexTimeline<T> timeline) {
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
    timeline.addInOrder(interval);
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
