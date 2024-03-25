package net.oliste.timeintervals4j.timeline;

import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public interface TimelineJoinOperation<T, S extends SingleTimeInterval<T>> {

  Timeline<T, S> merge(Timeline<T, S> timeline);

  Timeline<T, S> intersection(Timeline<T, S> timeline);

  Timeline<T, S> diff(Timeline<T, S> timeline);

  Timeline<T, S> alignTo(Timeline<T, S> timeline);

}
