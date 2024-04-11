package net.oliste.timeintervals4j.timeline;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

/**
 * Represents an object implementing all search methods.
 *
 * @author Paweł Łagan
 * @param <T> time interval properties type
 * @param <S> time interval type based on {@link SingleTimeInterval}
 * @param <V> the timeline type implementing this interface
 */
public interface TimelineSearch<T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {

  /**
   * Returns all intervals that overlaps with interval passed as a parameter.
   *
   * @param interval interval in which search method will be performed
   * @return {@link List} of matching time intervals
   */
  List<S> findOverlapping(S interval);

  /**
   * Returns all intervals that interval passed as a parameter contains - in other words, all that
   * are inside specified interval.
   *
   * @param interval interval in which search method will be performed
   * @return {@link List} of matching time intervals
   */
  List<S> findContaining(S interval);

  /**
   * Returns interval that contains timestamp.
   *
   * @param timestamp timestamp that should be checked against intervals in the timeline
   * @return {@link Optional} optional containing an interval if has been found or empty otherwise
   */
  Optional<S> findContaining(ZonedDateTime timestamp);

  /**
   * Returns interval that is before the interval passed as a parameter.
   *
   * @param interval interval in which search method will be performed
   * @return {@link Optional} optional containing an interval if has been found or empty otherwise
   */
  Optional<S> findLeftNearest(S interval);

  /**
   * Returns interval that is after the interval passed as a parameter.
   *
   * @param interval interval in which search method will be performed
   * @return {@link Optional} optional containing an interval if has been found or empty otherwise
   */
  Optional<S> findRightNearest(S interval);

  /**
   * Returns the nearest interval that is before or after the interval passed as a parameter.
   *
   * @param interval interval in which search method will be performed
   * @return {@link Optional} optional containing an interval if has been found or empty otherwise
   */
  Optional<S> findNearest(S interval);
}
