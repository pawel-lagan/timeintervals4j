package net.oliste.timeintervals4j.interval;

import java.time.ZonedDateTime;
import lombok.NonNull;
import net.oliste.timeintervals4j.math.TimeMath;

/**
 * Interface representing time interval which has: - starting date "from" - finishing date "to" -
 * properties related to that interval.
 *
 * @author Pawel Lagan
 * @param <S> type of the time interval
 * @param <T> type of the properties related to interval
 */
public interface TimeInterval<S extends TimeInterval<S, T>, T> {
  ZonedDateTime getFrom();

  ZonedDateTime getTo();

  T getProperties();

  /**
   * Checks if interval contains timestamp.
   *
   * @param timestamp timestamp to check
   * @return true if interval contains timestamp, false - otherwise
   */
  default boolean contains(@NonNull ZonedDateTime timestamp) {
    return TimeMath.isBeforeOrEquals(getFrom(), timestamp) && TimeMath.isAfter(getTo(), timestamp);
  }

  /**
   * Checks if this interval contains interval passed as a parameter.
   *
   * @param interval interval to check if it is contained
   * @return true if interval is contained, false - otherwise
   */
  default boolean contains(@NonNull TimeInterval<S, ?> interval) {
    return TimeMath.isAfterOrEquals(interval.getFrom(), getFrom())
        && TimeMath.isBeforeOrEquals(interval.getTo(), getTo());
  }

  /**
   * Checks if timestamp is outside interval.
   *
   * @param timestamp timestamp to check
   * @return true if interval not contains timestamp, false - otherwise
   */
  default boolean notContains(@NonNull ZonedDateTime timestamp) {
    return !contains(timestamp);
  }

  /**
   * Checks if this interval not contains interval passed as a parameter.
   *
   * @param interval interval to check if it is contained
   * @return true if interval is not contained, false - otherwise
   */
  default boolean notContains(@NonNull TimeInterval<S, ?> interval) {
    return !contains(interval);
  }

  /**
   * Checks if two intervals overlaps.
   *
   * @param interval interval to check if overlaps with
   * @return true if interval overlaps, false - otherwise
   */
  default boolean overlaps(@NonNull TimeInterval<S, ?> interval) {
    return (TimeMath.isAfterOrEquals(interval.getFrom(), getFrom())
            && TimeMath.isBefore(interval.getFrom(), getTo()))
        || (TimeMath.isAfter(interval.getTo(), getFrom())
            && TimeMath.isBeforeOrEquals(interval.getTo(), getTo()))
        || (TimeMath.isAfterOrEquals(getFrom(), interval.getFrom())
            && TimeMath.isBeforeOrEquals(getTo(), interval.getTo()))
        || (TimeMath.isAfterOrEquals(interval.getFrom(), getFrom())
            && TimeMath.isBeforeOrEquals(interval.getTo(), getTo()));
  }

  S createCopy();

  S create(@NonNull ZonedDateTime form, @NonNull ZonedDateTime to, T properties);

  S get();

  /**
   * Returns {@link TimeIntervalOperation} object witch allows provides set of basic operations on
   * the intervals.
   *
   * @return {@link TimeIntervalOperation} object
   */
  default TimeIntervalOperation<T, S> combine() {
    return TimeIntervalOperation.of(get());
  }

  /**
   * Checks if this interval is after specified by the parameter interval.
   *
   * @return true if it is after, false - otherwise.
   */
  default boolean isAfter(@NonNull TimeInterval<S, ?> interval) {
    return TimeMath.isAfterOrEquals(getFrom(), interval.getTo());
  }

  /**
   * Checks if this interval is before specified by the parameter interval.
   *
   * @return true if it is before, false - otherwise.
   */
  default boolean isBefore(@NonNull TimeInterval<S, ?> interval) {
    return TimeMath.isBeforeOrEquals(getTo(), interval.getFrom());
  }
}
