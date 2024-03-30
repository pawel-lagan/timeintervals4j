package net.oliste.timeintervals4j.timeline;

import java.util.List;
import java.util.Optional;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

interface Timeline<T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {
  Optional<S> getHead();

  Optional<S> getTail();

  List<S> getIntervals();

  TimelineSearch<T, S, V> find();

  TimelineOperation<T, S, V> modify();

  TimelineJoinOperation<T, S, V> join();
}
