package net.oliste.timeintervals4j.timeline;

import java.time.ZonedDateTime;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

interface TimelineOperation<T, S extends SingleTimeInterval<T>, V extends Timeline<T, S, V>> {

  void insert(S interval);

  void overwrite(S interval);

  void remove(S interval);

  void removeIn(S interval);

  void divide(ZonedDateTime timestamp);

}
