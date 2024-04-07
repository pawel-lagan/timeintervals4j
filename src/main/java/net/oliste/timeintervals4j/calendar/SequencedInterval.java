package net.oliste.timeintervals4j.calendar;

public interface SequencedInterval<S> {

  S getNext();

  S getPrev();
}
