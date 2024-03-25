package net.oliste.timeintervals4j.timeline;

import java.util.List;
import java.util.Optional;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

interface Timeline<T, S extends SingleTimeInterval<T>> {
  boolean isContinuous();
  boolean hasGaps();

  S getHead();

  S getTail();

  List<S> findOverlaping(S interval);

  Optional<S> findLeftNearest(S interval);

  Optional<S> findRightNearest(S interval);

  Optional<S> findLeft(S interval);

  Optional<S> findRight(S interval);

  List<S> getGaps(S interval);
}
