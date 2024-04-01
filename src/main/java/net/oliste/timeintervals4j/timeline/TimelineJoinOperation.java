package net.oliste.timeintervals4j.timeline;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public interface TimelineJoinOperation<
    T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {

  TimelineJoinOperation<T, S, V> withMergeStrategy(BinaryOperator<T> strategy);

  TimelineJoinOperation<T, S, V> withSplitStrategy(UnaryOperator<T> strategy);

  V merge(V timeline);

  V intersection(V timeline);

  V diff(V timeline);

  V alignIntervalsTo(V timeline);
}
