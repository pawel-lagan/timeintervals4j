package net.oliste.timeintervals4j.timeline;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public interface TimelineSearch<T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {
  List<S> findOverlapping(S interval);

  List<S> findContaining(S interval);

  Optional<S> findContaining(ZonedDateTime timestamp);

  Optional<S> findLeftNearest(S interval);

  Optional<S> findRightNearest(S interval);

  Optional<S> findNearest(S interval);

}
