package net.oliste.timeintervals4j.timeline;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public interface Timeline<T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {
  Optional<S> getHead();

  Optional<S> getTail();

  List<S> getIntervals();

  TimelineSearch<T, S, V> find();

  TimelineOperation<T, S, V> modify();

  TimelineJoinOperation<T, S, V> join();

  default void forEach(Consumer<S> interval) {
    getIntervals().forEach(interval);
  }

  default Iterator<S> iterator() {
    return getIntervals().iterator();
  }

  default Stream<S> stream() {
    return stream();
  }
}
