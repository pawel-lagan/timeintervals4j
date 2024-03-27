package net.oliste.timeintervals4j.timeline;

import java.time.ZonedDateTime;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public class ComplexTimelineOperation<T> implements TimelineOperation<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private ComplexTimeline<T> timeline;

  public ComplexTimelineOperation(ComplexTimeline<T> timeline) {
    this.timeline = timeline;
  }

  @Override
  public void insert(SingleTimeInterval<T> interval) {

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
