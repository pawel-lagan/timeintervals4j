package net.oliste.timeintervals4j.interval;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.oliste.timeintervals4j.math.TimeMath;

@Getter
@RequiredArgsConstructor(staticName = "of")
@ToString
@EqualsAndHashCode
public class TimeIntervalOperation<T, S extends TimeInterval<S, T>> {

  private final S a;
  private BinaryOperator<T> mergeStrategy = (propertiesA, propertiesB) -> propertiesA;
  private UnaryOperator<T> splitStrategy = propertiesA -> propertiesA;

  public TimeIntervalOperation<T, S> withMergeStrategy(BinaryOperator<T> strategy) {
    this.mergeStrategy = strategy;
    return this;
  }

  public TimeIntervalOperation<T, S> withSplitStrategy(UnaryOperator<T> strategy) {
    this.splitStrategy = strategy;
    return this;
  }

  public S intersection(@NonNull TimeInterval<S, T> interval) {
    if (!a.overlaps(interval)) {
      throw new TimeIntervalException("Internals do not overlaps");
    }

    ZonedDateTime iFrom = TimeMath.max(a.getFrom(), interval.getFrom());
    ZonedDateTime iTo = TimeMath.min(a.getTo(), interval.getTo());
    validateTimeInterval(iFrom, iTo);
    return a.create(iFrom, iTo, mergeStrategy.apply(a.getProperties(), interval.getProperties()));
  }
  public S union(@NonNull TimeInterval<S, T> interval) {
    if (!a.overlaps(interval)) {
      throw new TimeIntervalException("Internals do not overlaps");
    }

    ZonedDateTime iFrom = TimeMath.min(a.getFrom(), interval.getFrom());
    ZonedDateTime iTo = TimeMath.max(a.getTo(), interval.getTo());
    validateTimeInterval(iFrom, iTo);
    return a.create(iFrom, iTo, mergeStrategy.apply(a.getProperties(), interval.getProperties()));
  }

  public List<S> split(@NonNull ZonedDateTime time) {
    if (!a.contains(time)) {
      throw new TimeIntervalException("time point not withing interval boundary");
    }
    var left = a.create(a.getFrom(), time, a.getProperties());
    var right = a.create(time, a.getTo(), splitStrategy.apply(a.getProperties()));

    return List.of(left, right);
  }

  private static void validateTimeInterval(ZonedDateTime iFrom, ZonedDateTime iTo) {
    if (iFrom != null && iTo != null && !iFrom.isBefore(iTo)) {
      throw new TimeIntervalException("Invalid state of interval calculation");
    }
  }
}

