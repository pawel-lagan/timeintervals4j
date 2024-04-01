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

  public List<S> diff(@NonNull TimeInterval<S, T> interval) {
    if (!a.overlaps(interval)) {
      throw new TimeIntervalException("Internals do not overlaps");
    }

    var list = new LinkedList<S>();

    if (TimeMath.isBefore(a.getFrom(), interval.getFrom())) {
      list.add(a.create(a.getFrom(), interval.getFrom(), a.getProperties()));
    }

    if (TimeMath.isBefore(interval.getTo(), a.getTo())) {
      list.add(a.create(interval.getTo(), a.getTo(), splitStrategy.apply(a.getProperties())));
    }

    return list;
  }

  public List<S> split(@NonNull ZonedDateTime time) {
    if (!a.contains(time)) {
      throw new TimeIntervalException("time point not withing interval boundary");
    }

    var list = new LinkedList<S>();
    if (a.getFrom().isBefore(time)) {
      list.add(a.create(a.getFrom(), time, a.getProperties()));
    }

    if (time.isBefore(a.getTo())) {
      list.add(a.create(time, a.getTo(), splitStrategy.apply(a.getProperties())));
    }

    return list;
  }

  private static void validateTimeInterval(ZonedDateTime iFrom, ZonedDateTime iTo) {
    if (iFrom != null && iTo != null && !iFrom.isBefore(iTo)) {
      throw new TimeIntervalException("Invalid state of interval calculation");
    }
  }
}
