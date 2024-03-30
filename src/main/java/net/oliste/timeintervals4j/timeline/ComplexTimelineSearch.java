package net.oliste.timeintervals4j.timeline;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public class ComplexTimelineSearch<T> implements TimelineSearch<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private final ComplexTimeline<T> timeline;

  public ComplexTimelineSearch(ComplexTimeline<T> timeline) {
    this.timeline = timeline;
  }

  @Override
  public List<SingleTimeInterval<T>> findOverlapping(SingleTimeInterval<T> interval) {
    return timeline.getIntervals().stream().filter(interval::overlaps).toList();
  }

  @Override
  public List<SingleTimeInterval<T>> findContaining(SingleTimeInterval<T> interval) {
    return timeline.getIntervals().stream().filter(iv -> iv.contains(interval)).collect(Collectors.toList());
  }

  @Override
  public Optional<SingleTimeInterval<T>> findContaining(ZonedDateTime timestamp) {
    return timeline.getIntervals().stream().filter(iv -> iv.contains(timestamp)).findFirst();
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
