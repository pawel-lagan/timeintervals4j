package net.oliste.timeintervals4j.timeline;

import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public interface TimelineJoinOperation<T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {

  V merge(V timeline);

  V intersection(V timeline);

  V diff(V timeline);

  V alignTo(V timeline);

}
