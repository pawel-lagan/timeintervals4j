package net.oliste.timeintervals4j.timeline;

import java.util.List;
import java.util.Optional;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public class ComplexTimelineSearch<T> implements TimelineSearch<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private ComplexTimeline<T> timeline;

  public ComplexTimelineSearch(ComplexTimeline<T> timeline) {
    this.timeline = timeline;
  }

  @Override
  public List<SingleTimeInterval<T>> findOverlaping(SingleTimeInterval<T> interval) {
    return null;
  }

  @Override
  public Optional<SingleTimeInterval<T>> findLeftNearest(SingleTimeInterval<T> interval) {
    return Optional.empty();
  }

  @Override
  public Optional<SingleTimeInterval<T>> findRightNearest(SingleTimeInterval<T> interval) {
    return Optional.empty();
  }

  @Override
  public Optional<SingleTimeInterval<T>> findNearest(SingleTimeInterval<T> interval) {
    return Optional.empty();
  }

  @Override
  public Optional<SingleTimeInterval<T>> findLeft(SingleTimeInterval<T> interval) {
    return Optional.empty();
  }

  @Override
  public Optional<SingleTimeInterval<T>> findRight(SingleTimeInterval<T> interval) {
    return Optional.empty();
  }

  @Override
  public List<SingleTimeInterval<T>> getGaps(SingleTimeInterval<T> interval) {
    return null;
  }
}