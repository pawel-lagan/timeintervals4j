package net.oliste.timeintervals4j.timeline.complex;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;
import net.oliste.timeintervals4j.timeline.Timeline;
import net.oliste.timeintervals4j.timeline.TimelineJoinOperation;
import net.oliste.timeintervals4j.timeline.TimelineOperation;
import net.oliste.timeintervals4j.timeline.TimelineSearch;
import net.oliste.timeintervals4j.timeline.complex.tree.BtpTree;
import net.oliste.timeintervals4j.timeline.complex.tree.BtpTreeNode;

/**
 * Represents complex timeline that consist of {@link SingleTimeInterval} based non overlapping
 * intervals.
 *
 * @author Paweł Łagan
 * @param <T> time interval properties type
 */
@ToString
@EqualsAndHashCode
public class ComplexTimeline<T> implements Timeline<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private final BtpTree<T, SingleTimeInterval<T>> intervalTree = new BtpTree<>();

  /** Default constructor. */
  public ComplexTimeline() {}

  /**
   * Copy constructor.
   *
   * @param src the source timeline
   */
  public ComplexTimeline(ComplexTimeline<T> src) {
    src.forEach(iv -> intervalTree.insert(iv.createCopy()));
  }

  void addInOrder(Collection<SingleTimeInterval<T>> intervals) {
    intervals.forEach(this::addInOrder);
  }

  void addInOrder(SingleTimeInterval<T> interval) {
    intervalTree.insert(interval);
  }

  List<BtpTreeNode<T, SingleTimeInterval<T>>> search(
      SingleTimeInterval<T> interval, Predicate<SingleTimeInterval<T>> predicate) {
    return intervalTree.search(interval, predicate);
  }

  List<BtpTreeNode<T, SingleTimeInterval<T>>> search(
      ZonedDateTime timestamp, Predicate<SingleTimeInterval<T>> predicate) {
    return intervalTree.search(timestamp, predicate);
  }

  void removeInRange(SingleTimeInterval<T> interval) {
    var nodes = intervalTree.search(interval, interval::contains);
    nodes.forEach(intervalTree::removeNode);
  }

  @Override
  public TimelineSearch<T, SingleTimeInterval<T>, ComplexTimeline<T>> find() {
    return new ComplexTimelineSearch<>(this);
  }

  @Override
  public TimelineOperation<T, SingleTimeInterval<T>, ComplexTimeline<T>> modify() {
    return new ComplexTimelineOperation<>(this);
  }

  @Override
  public TimelineJoinOperation<T, SingleTimeInterval<T>, ComplexTimeline<T>> join() {
    return new ComplexTimelineJoinOperation<>(this);
  }

  @Override
  public boolean isEmpty() {
    return intervalTree.isEmpty();
  }

  @Override
  public void forEach(Consumer<SingleTimeInterval<T>> interval) {
    intervalTree.forEach(interval);
  }

  @Override
  public Iterator<SingleTimeInterval<T>> iterator() {
    return getIntervals().iterator();
  }

  @Override
  public Stream<SingleTimeInterval<T>> stream() {
    return getIntervals().stream();
  }

  List<SingleTimeInterval<T>> getIntervals() {
    return intervalTree.getIntervals();
  }
}
