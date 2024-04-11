package net.oliste.timeintervals4j.interval;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.oliste.timeintervals4j.math.TimeMath;

/**
 * Represents a time interval operations that might be performed on a single or two intervals.
 *
 * @author Paweł Łagan
 * @param <S> type of the time interval that implements {@link TimeInterval}
 * @param <T> type of the properties related to intervals
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@ToString
@EqualsAndHashCode
public class TimeIntervalOperation<T, S extends TimeInterval<S, T>> {

  private final S argumentA;
  private BinaryOperator<T> mergeStrategy = (propertiesA, propertiesB) -> propertiesA;
  private UnaryOperator<T> splitStrategy = propertiesA -> propertiesA;

  /**
   * Sets the merge strategy of the two intervals for all marge type operations. The resulting
   * interval will have the properties returned by the strategy function.
   *
   * @param strategy the merge method
   * @return {@link TimeIntervalOperation} reference to this object
   */
  public TimeIntervalOperation<T, S> withMergeStrategy(BinaryOperator<T> strategy) {
    this.mergeStrategy = strategy;
    return this;
  }

  /**
   * Sets the split strategy of the single interval for all split type operations. The resulting new
   * interval will have the properties returned by the strategy function.
   *
   * @param strategy the split method
   * @return {@link TimeIntervalOperation} reference to this object
   */
  public TimeIntervalOperation<T, S> withSplitStrategy(UnaryOperator<T> strategy) {
    this.splitStrategy = strategy;
    return this;
  }

  /**
   * Performs an intersection of the current interval with the given interval using the specified
   * strategy. The resulting interval will have the properties of both intervals based on the
   * current merge strategy.
   *
   * @param interval the interval to intersect with
   * @return the intersection of the two intervals
   */
  public S intersection(@NonNull TimeInterval<S, T> interval) {
    if (!argumentA.overlaps(interval)) {
      throw new TimeIntervalException("Internals do not overlaps");
    }

    ZonedDateTime newFrom = TimeMath.max(argumentA.getFrom(), interval.getFrom());
    ZonedDateTime newTo = TimeMath.min(argumentA.getTo(), interval.getTo());
    validateTimeInterval(newFrom, newTo);
    return argumentA.create(
        newFrom, newTo, mergeStrategy.apply(argumentA.getProperties(), interval.getProperties()));
  }

  /**
   * Performs a union of the current interval with the given interval using the specified strategy.
   * The resulting interval will have the properties of both intervals based on the current merge
   * strategy.
   *
   * @param interval the interval to union with
   * @return the union of the two intervals
   */
  public S union(@NonNull TimeInterval<S, T> interval) {
    if (!argumentA.overlaps(interval)) {
      throw new TimeIntervalException("Internals do not overlaps");
    }

    ZonedDateTime newFrom = TimeMath.min(argumentA.getFrom(), interval.getFrom());
    ZonedDateTime newTo = TimeMath.max(argumentA.getTo(), interval.getTo());
    validateTimeInterval(newFrom, newTo);
    return argumentA.create(
        newFrom, newTo, mergeStrategy.apply(argumentA.getProperties(), interval.getProperties()));
  }

  /**
   * Returns a list of time intervals representing the difference between the two time intervals.
   *
   * @param interval the second time interval
   * @return a list of time intervals representing the difference
   */
  public List<S> diff(@NonNull TimeInterval<S, T> interval) {
    if (!argumentA.overlaps(interval)) {
      throw new TimeIntervalException("Internals do not overlaps");
    }

    var list = new LinkedList<S>();

    if (TimeMath.isBefore(argumentA.getFrom(), interval.getFrom())) {
      list.add(
          argumentA.create(argumentA.getFrom(), interval.getFrom(), argumentA.getProperties()));
    }

    if (TimeMath.isBefore(interval.getTo(), argumentA.getTo())) {
      list.add(
          argumentA.create(
              interval.getTo(), argumentA.getTo(), splitStrategy.apply(argumentA.getProperties())));
    }

    return list;
  }

  /**
   * Performs a split of the current interval into two intervals using the specified strategy. The
   * resulting intervals will have the properties of the original interval based on the current
   * split strategy.
   *
   * @param time the point in time to split at
   * @return a list of two intervals, one containing everything before the split point and one
   *     containing everything after
   */
  public List<S> split(@NonNull ZonedDateTime time) {
    if (!argumentA.contains(time)) {
      throw new TimeIntervalException("time point not withing interval boundary");
    }

    var list = new LinkedList<S>();
    if (argumentA.getFrom().isBefore(time)) {
      list.add(argumentA.create(argumentA.getFrom(), time, argumentA.getProperties()));
    }

    if (time.isBefore(argumentA.getTo())) {
      list.add(
          argumentA.create(
              time, argumentA.getTo(), splitStrategy.apply(argumentA.getProperties())));
    }

    return list;
  }

  private static void validateTimeInterval(ZonedDateTime from, ZonedDateTime to) {
    if (from != null && to != null && !from.isBefore(to)) {
      throw new TimeIntervalException("Invalid state of interval calculation");
    }
  }
}
