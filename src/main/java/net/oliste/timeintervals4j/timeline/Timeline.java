package net.oliste.timeintervals4j.timeline;

import net.oliste.timeintervals4j.interval.SingleTimeInterval;

interface Timeline<T, S extends SingleTimeInterval<T>> {
  boolean isContinuous();
  boolean hasGaps();

  S getHead();

  S getTail();

  TimelineSearch<T, S> find();

 TimelineOperation<T, S> modify();

  TimelineJoinOperation<T, S> join();
}
