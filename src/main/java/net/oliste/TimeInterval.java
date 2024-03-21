package net.oliste;

import java.time.ZonedDateTime;
import lombok.NonNull;

public interface TimeInterval<S extends TimeInterval<S, T>, T> {
  ZonedDateTime getFrom();
  ZonedDateTime getTo();

  T getProperties();

  default boolean hasNoFrom() {
    return getFrom() == null;
  }

  default boolean hasNoTo() {
    return getTo() == null;
  }

  default boolean contains(@NonNull ZonedDateTime timestamp) {
    return (hasNoFrom() || getFrom().isBefore(timestamp) || getFrom().isEqual(timestamp)) &&
        (hasNoTo() || getTo().isAfter(timestamp) || getTo().isEqual(timestamp));
  }

  default boolean contains(@NonNull TimeInterval<S, ?> interval) {
    return (interval.hasNoFrom() || contains(interval.getFrom())) &&
        (interval.hasNoTo() || contains(interval.getTo()));
  }

  default boolean overlaps(@NonNull TimeInterval<S, ?> interval) {
    return (interval.hasNoFrom() || contains(interval.getFrom())) ||
        (interval.hasNoTo() || contains(interval.getTo()));
  }

  S create(ZonedDateTime form, ZonedDateTime to, T properties);

  S get();

  default TimeIntervalOperation<T, S> combine() {
    return TimeIntervalOperation.of(get());
  }


}
