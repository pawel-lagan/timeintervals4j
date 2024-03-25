package net.oliste.timeintervals4j.interval;

import java.time.ZonedDateTime;
import lombok.NonNull;
import net.oliste.timeintervals4j.math.TimeMath;

public interface TimeInterval<S extends TimeInterval<S, T>, T> {
  ZonedDateTime getFrom();
  ZonedDateTime getTo();

  T getProperties();

  default boolean hasNoFrom() {
    return getFrom() == null;
  }

  default boolean hasFrom() {
    return getFrom() != null;
  }

  default boolean hasNoTo() {
    return getTo() == null;
  }
  default boolean hasTo() {
    return getTo() != null;
  }

  default boolean contains(@NonNull ZonedDateTime timestamp) {
    return (hasNoFrom() || TimeMath.isBeforeOrEquals(getFrom(), timestamp)) &&
        (hasNoTo() || TimeMath.isAfterOrEquals(getTo(), timestamp));
  }

  default boolean contains(@NonNull TimeInterval<S, ?> interval) {
    if(hasNoFrom()) {
      if(interval.hasNoTo()) {
        return false;
      }
      return TimeMath.isBeforeOrEquals(interval.getTo(),getTo());
    }
    if(hasNoTo()) {
      if(interval.hasNoFrom()) {
        return false;
      }
      return TimeMath.isAfterOrEquals(interval.getFrom(),getFrom());
    }

    if(interval.hasNoFrom() || interval.hasNoTo()) {
      return false;
    }

    return TimeMath.isAfterOrEquals(interval.getFrom(),getFrom())
        && TimeMath.isBeforeOrEquals(interval.getTo(),getTo());
  }

  default boolean overlaps(@NonNull TimeInterval<S, ?> interval) {
    if(hasNoFrom() && hasNoTo()) {
      return true;
    }
    if(hasNoFrom()) {
      return interval.hasNoFrom() || TimeMath.isBeforeOrEquals(interval.getFrom(),getTo());
    }
    if(hasNoTo()) {
      return interval.hasNoTo() || TimeMath.isAfterOrEquals(interval.getTo(),getFrom());
    }

    return (TimeMath.isAfterOrEquals(interval.getFrom(),getFrom()) && TimeMath.isBeforeOrEquals(interval.getFrom(),getTo()))
      || (TimeMath.isAfterOrEquals(interval.getTo(),getFrom()) && TimeMath.isBeforeOrEquals(interval.getTo(),getTo()));
  }

  S create(ZonedDateTime form, ZonedDateTime to, T properties);

  S get();

  default TimeIntervalOperation<T, S> combine() {
    return TimeIntervalOperation.of(get());
  }


}
