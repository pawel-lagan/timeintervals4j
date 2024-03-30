package net.oliste.timeintervals4j.timeline;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
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
    return timeline.getIntervals().stream().filter(interval::contains).toList();
  }

  @Override
  public Optional<SingleTimeInterval<T>> findContaining(ZonedDateTime timestamp) {
    return timeline.getIntervals().stream().filter(iv -> iv.contains(timestamp)).findFirst();
  }

  @Override
  public Optional<SingleTimeInterval<T>> findLeftNearest(SingleTimeInterval<T> interval) {
    var intervals = timeline.getIntervals();
    var it = intervals.listIterator();
    if (intervals.isEmpty()) {
      return Optional.empty();
    }

    if (intervals.getFirst().isAfter(interval)) {
      return Optional.of(intervals.getFirst());
    }

    SingleTimeInterval<T> prev = null;
    while (it.hasNext()) {
      var iv = it.next();
      if (!iv.isBefore(interval)) {
        return prev == null ? Optional.empty() : Optional.of(prev);
      }
      prev = iv;
    }
    return Optional.empty();
  }

  @Override
  public Optional<SingleTimeInterval<T>> findRightNearest(SingleTimeInterval<T> interval) {
    var intervals = timeline.getIntervals();
    var it = intervals.listIterator();
    if (intervals.isEmpty()) {
      return Optional.empty();
    }

    if (intervals.getLast().isBefore(interval)) {
      return Optional.of(intervals.getLast());
    }

    while (it.hasNext()) {
      var iv = it.next();
      if (iv.isAfter(interval)) {
        return Optional.of(iv);
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<SingleTimeInterval<T>> findNearest(SingleTimeInterval<T> interval) {
    var optLeft = findLeftNearest(interval);
    var optRight = findRightNearest(interval);

    if (optLeft.isEmpty()) {
      return optRight;
    }

    if (optRight.isEmpty()) {
      return optLeft;
    }

    var left = optLeft.get();
    var right = optRight.get();

    var distLeft = interval.getFrom().toInstant().toEpochMilli() - left.getTo().toInstant().toEpochMilli();
    var distRight = right.getFrom().toInstant().toEpochMilli() - interval.getTo().toInstant().toEpochMilli();

    return distLeft < distRight ? optLeft : optRight;
  }

}
