package net.oliste.timeintervals4j.timeline;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;
import net.oliste.timeintervals4j.interval.TimeIntervalException;

public class ComplexTimelineOperation<T> implements TimelineOperation<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private final ComplexTimeline<T> timeline;

  public ComplexTimelineOperation(ComplexTimeline<T> timeline) {
    this.timeline = timeline;
  }

  @Override
  public void insert(SingleTimeInterval<T> interval) {
    var overlappingIntervals = timeline.find().findOverlaping(interval);
    if (overlappingIntervals.isEmpty()) {
      timeline.getIntervals().add(interval);
    }
    else {
      throw new TimeIntervalException(String.format("Overlapping interval found [%s]", overlappingIntervals.stream().map(
          SingleTimeInterval::toString).collect(
          Collectors.joining(", "))));
    }
  }

  @Override

  public void overwrite(SingleTimeInterval<T> interval) {
  }

  @Override
  public void remove(SingleTimeInterval<T> interval) {

  }

  @Override
  public void removeIn(SingleTimeInterval<T> interval) {

  }

  @Override
  public void divide(ZonedDateTime timestamp) {

  }
}
