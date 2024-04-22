package net.oliste.timeintervals4j.timeline;

import java.time.ZonedDateTime;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

/**
 * Represents an object implementing all modify methods.
 *
 * @author Paweł Łagan
 * @param <T> time interval properties type
 * @param <S> time interval type based on {@link SingleTimeInterval}
 * @param <V> the timeline type implementing this interface
 */
public interface TimelineOperation<
    T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {

  /**
   * Sets strategy that will be used in merge like operations.
   *
   * @param strategy {@link BinaryOperator} merge operator
   * @return {@link TimelineOperation} object
   */
  TimelineOperation<T, S, V> withMergeStrategy(BinaryOperator<T> strategy);

  /**
   * Sets strategy that will be used in split like operations.
   *
   * @param strategy {@link BinaryOperator} split operator
   * @return {@link TimelineOperation} object
   */
  TimelineOperation<T, S, V> withSplitStrategy(UnaryOperator<T> strategy);

  /**
   * Insert operation on the timeline. Duplicates are not allowed.
   *
   * @param interval interval that will be added to the timeline.
   * @throws net.oliste.timeintervals4j.interval.TimeIntervalException when operation cannot be
   *     performed, like on overlapping intervals.
   */
  void insert(S interval);

  /**
   * Overwrite operation on the timeline. When no overlapping interval is found in the timeline then
   * insert operation will be performed. Otherwise, intersecting part will be overwritten.
   *
   * @param interval interval that will be added to the timeline.
   */
  void overwrite(S interval);

  /**
   * Remove operation on the timeline. Only intervals that are contained by the interval passed as a
   * param will be removed.
   *
   * @param interval interval in which timeline intervals will be removed
   */
  void remove(S interval);

  /**
   * Remove operation on the timeline. Intervals that are contained by the interval passed as a
   * param will be removed and intersecting part of the overlapping intervals.
   *
   * @param interval interval in which timeline intervals will be removed
   */
  void removeIn(S interval);

  /**
   * Divide operation on the timeline. Interval that contains the timestamp will be split into two
   * intervals exactly at timestamp.
   *
   * @param timestamp point in time that will split interval
   */
  void divide(ZonedDateTime timestamp);
}
