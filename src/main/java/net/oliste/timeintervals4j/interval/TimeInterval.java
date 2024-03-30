package net.oliste.timeintervals4j.interval;

import java.time.ZonedDateTime;
import lombok.NonNull;
import net.oliste.timeintervals4j.math.TimeMath;

public interface TimeInterval<S extends TimeInterval<S, T>, T> {
  ZonedDateTime getFrom();
  ZonedDateTime getTo();

  T getProperties();

  default boolean contains(@NonNull ZonedDateTime timestamp) {
    return TimeMath.isBeforeOrEquals(getFrom(), timestamp) && TimeMath.isAfter(getTo(), timestamp);
  }

  default boolean notContains(@NonNull ZonedDateTime timestamp) {
    return !contains(timestamp);
  }

  default boolean contains(@NonNull TimeInterval<S, ?> interval) {
    return TimeMath.isAfterOrEquals(interval.getFrom(),getFrom())
        && TimeMath.isBeforeOrEquals(interval.getTo(),getTo());
  }

  default boolean notContains(@NonNull TimeInterval<S, ?> interval) {
    return !contains(interval);
  }

  default boolean overlaps(@NonNull TimeInterval<S, ?> interval) {
    return (TimeMath.isAfterOrEquals(interval.getFrom(), getFrom()) && TimeMath.isBefore(interval.getFrom(), getTo())) ||
        (TimeMath.isAfter(interval.getTo(), getFrom()) && TimeMath.isBeforeOrEquals(interval.getTo(), getTo())) ||
        (TimeMath.isAfterOrEquals(getFrom(), interval.getFrom()) && TimeMath.isBeforeOrEquals(getTo(), interval.getTo())) ||
        (TimeMath.isAfterOrEquals(interval.getFrom(), getFrom()) && TimeMath.isBeforeOrEquals(interval.getTo(), getTo()));
  }

  S create(@NonNull ZonedDateTime form, @NonNull ZonedDateTime to, T properties);

  S get();

  default TimeIntervalOperation<T, S> combine() {
    return TimeIntervalOperation.of(get());
  }

  default boolean isAfter(@NonNull TimeInterval<S, ?> interval) {
    return TimeMath.isAfterOrEquals(getFrom(), interval.getTo());
  }

  default boolean isBefore(@NonNull TimeInterval<S, ?> interval) {
    return TimeMath.isBeforeOrEquals(getTo(), interval.getFrom());
  }

}
