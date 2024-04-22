package net.oliste.timeintervals4j.timeline.complex;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;
import net.oliste.timeintervals4j.timeline.TimelineSearch;
import net.oliste.timeintervals4j.timeline.complex.tree.BtpTreeNode;

/**
 * Represents an object implementing all search methods for {@link ComplexTimeline}.
 *
 * @author Paweł Łagan
 * @param <T> time interval properties type
 */
public class ComplexTimelineSearch<T>
    implements TimelineSearch<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private final ComplexTimeline<T> timeline;

  ComplexTimelineSearch(ComplexTimeline<T> timeline) {
    this.timeline = timeline;
  }

  @Override
  public List<SingleTimeInterval<T>> findOverlapping(SingleTimeInterval<T> interval) {
    return timeline.search(interval, iv -> iv.overlaps(interval)).stream()
        .map(BtpTreeNode::getInterval)
        .toList();
  }

  @Override
  public List<SingleTimeInterval<T>> findContaining(SingleTimeInterval<T> interval) {
    return timeline.search(interval, iv -> interval.contains(iv)).stream()
        .map(BtpTreeNode::getInterval)
        .toList();
  }

  @Override
  public Optional<SingleTimeInterval<T>> findContaining(ZonedDateTime timestamp) {
    return timeline.search(timestamp, iv -> iv.contains(timestamp)).stream()
        .map(BtpTreeNode::getInterval)
        .findFirst();
  }

  @Override
  public Optional<SingleTimeInterval<T>> findLeftNearest(SingleTimeInterval<T> interval) {
    var it = timeline.iterator();
    if (timeline.isEmpty()) {
      return Optional.empty();
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
    var it = timeline.iterator();
    if (timeline.isEmpty()) {
      return Optional.empty();
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

    var distLeft =
        interval.getFrom().toInstant().toEpochMilli() - left.getTo().toInstant().toEpochMilli();
    var distRight =
        right.getFrom().toInstant().toEpochMilli() - interval.getTo().toInstant().toEpochMilli();

    return distLeft < distRight ? optLeft : optRight;
  }
}
