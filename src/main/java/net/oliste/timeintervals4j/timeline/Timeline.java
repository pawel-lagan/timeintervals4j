package net.oliste.timeintervals4j.timeline;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

/**
 * Represents a timeline, which is a collection of time intervals. Main assumption of the timeline
 * is that it should consist of non overlapping intervals in chronological order.
 *
 * @author Paweł Łagan
 * @param <T> time interval properties type
 * @param <S> time interval type based on {@link SingleTimeInterval}
 * @param <V> the timeline type implementing this interface
 */
public interface Timeline<T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {

  /**
   * Returns {@link TimelineSearch} objects witch provides variety of search methods like
   * findOverlapping, findContaining, etc ... .
   *
   * @return {@link TimelineSearch} object
   */
  TimelineSearch<T, S, V> find();

  /**
   * Returns {@link TimelineOperation} objects witch provides variety of timeline modification
   * methods like: insert, delete, overwrite, ... . All operations modify state of the timeline.
   *
   * @return {@link TimelineOperation} object
   */
  TimelineOperation<T, S, V> modify();

  /**
   * Returns {@link TimelineJoinOperation} objects witch provides variety of timeline joining
   * methods like: merge, intersect, diff, gaps, alignTo All operations are immutable and doesn't
   * modify state of the timeline.
   *
   * @return {@link TimelineJoinOperation} object
   */
  TimelineJoinOperation<T, S, V> join();

  /**
   * Checks if timeline is empty (doesn't contain any time interval).
   *
   * @return true if empty, false otherwise
   */
  boolean isEmpty();

  /**
   * Iteration over timeline.
   *
   * @param interval {@link Consumer} of time intervals
   */
  void forEach(Consumer<S> interval);

  /**
   * Returns iterator over collection of time intervals.
   *
   * @return {@link Iterator} object
   */
  Iterator<S> iterator();

  /**
   * Returns stream of time intervals.
   *
   * @return {@link Stream} object
   */
  Stream<S> stream();
}
