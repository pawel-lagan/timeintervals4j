package net.oliste.timeintervals4j.timeline;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public interface Timeline<T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {

  TimelineSearch<T, S, V> find();

  TimelineOperation<T, S, V> modify();

  TimelineJoinOperation<T, S, V> join();

  boolean isEmpty();

  void forEach(Consumer<S> interval);

  Iterator<S> iterator();

  Stream<S> stream();
}
