package net.oliste.timeintervals4j.timeline;

import java.time.ZonedDateTime;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public interface TimelineOperation<
    T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {

  TimelineOperation<T, S, V> withMergeStrategy(BinaryOperator<T> strategy);

  TimelineOperation<T, S, V> withSplitStrategy(UnaryOperator<T> strategy);

  void insert(S interval);

  void overwrite(S interval);

  void remove(S interval);

  void removeIn(S interval);

  void divide(ZonedDateTime timestamp);
}
