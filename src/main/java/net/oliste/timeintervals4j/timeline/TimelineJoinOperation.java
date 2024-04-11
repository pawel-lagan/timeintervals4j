package net.oliste.timeintervals4j.timeline;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

/**
 * Represents an object implementing all merge like methods.
 *
 * @author Paweł Łagan
 * @param <T> time interval properties type
 * @param <S> time interval type based on {@link SingleTimeInterval}
 * @param <V> the timeline type implementing this interface
 */
public interface TimelineJoinOperation<
    T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {

  /**
   * Sets strategy that will be used in merge like operations.
   *
   * @param strategy {@link BinaryOperator} merge operator
   * @return {@link TimelineOperation} object
   */
  TimelineJoinOperation<T, S, V> withMergeStrategy(BinaryOperator<T> strategy);

  /**
   * Sets strategy that will be used in split like operations.
   *
   * @param strategy {@link BinaryOperator} split operator
   * @return {@link TimelineOperation} object
   */
  TimelineJoinOperation<T, S, V> withSplitStrategy(UnaryOperator<T> strategy);

  /**
   * Merge two timelines, intersecting parts will have properties replaced with merge using strategy
   * set before that operation. Diff parts will contain properties belonging to interval.
   * Overlapping intervals will be split to smaller chunks in a way that each from and to timestamp
   * from both timelines will be reflected in the result.
   *
   * @param timeline timeline to merge with
   * @return new timeline that is a result of the operation
   */
  V merge(V timeline);

  /**
   * Intersecting parts will have properties replaced with merge using strategy set before that
   * operation. Overlapping intervals will be split to smaller chunks in a way that each from and to
   * timestamp from both timelines will be reflected in the result.
   *
   * @param timeline timeline to intersect with
   * @return new timeline that is a result of the operation
   */
  V intersection(V timeline);

  /**
   * Only parts of the timeline that are not overlapping with any interval will be put into the
   * result timeline.
   *
   * @param timeline timeline to diff with
   * @return new timeline that is a result of the operation
   */
  V diff(V timeline);

  /**
   * Overlapping intervals will be split to smaller chunks in a way that each from and to timestamp
   * from both timelines will be reflected in the result.
   *
   * @param timeline timeline to align to
   * @return new timeline that is a result of the operation
   */
  V alignIntervalsTo(V timeline);

  /**
   * Returns timeline containing gaps between intervals in the current timeline.
   *
   * @return new timeline that is a result of the operation
   */
  V gaps();
}
