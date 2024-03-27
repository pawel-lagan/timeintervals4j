package net.oliste.timeintervals4j.timeline;

import java.util.List;
import java.util.Optional;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public interface TimelineSearch<T, S extends SingleTimeInterval<T>, V extends Timeline<T, S>> {
  List<S> findOverlaping(S interval);

  Optional<S> findLeftNearest(S interval);

  Optional<S> findRightNearest(S interval);

  Optional<S> findNearest(S interval);

  Optional<S> findLeft(S interval);

  Optional<S> findRight(S interval);

  List<S> getGaps(S interval);
}
