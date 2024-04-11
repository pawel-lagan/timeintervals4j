package net.oliste.timeintervals4j.calendar;

/**
 * Represents a time interval that creates a sequence like days, months, years.
 *
 * @param <S> type of the interval
 */
public interface SequencedInterval<S> {

  /**
   * Returns the next element in the sequence. The next element may be any point in time after the
   * current element, depending on the implementation of the interval.
   *
   * @return the next element in the sequence.
   */
  S getNext();

  /**
   * Returns the previous element in the sequence. The previous element may be any point in time
   * before the current element, depending on the implementation of the interval.
   *
   * @return the previous element in the sequence.
   */
  S getPrev();
}
