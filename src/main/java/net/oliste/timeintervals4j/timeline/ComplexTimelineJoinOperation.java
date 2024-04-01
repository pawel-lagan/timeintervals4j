package net.oliste.timeintervals4j.timeline;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

public class ComplexTimelineJoinOperation<T> implements TimelineJoinOperation<T, SingleTimeInterval<T>, ComplexTimeline<T>> {

  private final ComplexTimeline<T> timeline;

  private BinaryOperator<T> mergeStrategy = (propertiesA, propertiesB) -> propertiesA;
  private UnaryOperator<T> splitStrategy = propertiesA -> propertiesA;

  public ComplexTimelineJoinOperation(ComplexTimeline<T> timeline) {
    this.timeline = timeline;
  }
  @Override
  public ComplexTimelineJoinOperation<T> withMergeStrategy(BinaryOperator<T> strategy) {
    this.mergeStrategy = strategy;
    return this;
  }
  @Override
  public ComplexTimelineJoinOperation<T> withSplitStrategy(UnaryOperator<T> strategy) {
    this.splitStrategy = strategy;
    return this;
  }

  @Override
  public ComplexTimeline<T> merge(ComplexTimeline<T> timelineB) {
    var result = new ComplexTimeline<>(timeline);
    var resultsModifier = result.modify()
        .withMergeStrategy(mergeStrategy)
        .withSplitStrategy(splitStrategy);

    timeline.getIntervals().forEach(ivA -> timelineB.getIntervals().forEach(ivB -> {
      if (ivA.overlaps(ivB)) {
        resultsModifier.overwrite(ivA.combine()
            .withMergeStrategy(mergeStrategy)
            .withSplitStrategy(splitStrategy)
            .intersection(ivB));
        ivB.combine()
            .withMergeStrategy(mergeStrategy)
            .withSplitStrategy(splitStrategy)
            .diff(ivA).forEach(resultsModifier::insert);
      }
    }));

    return result;
  }
  @Override
  public ComplexTimeline<T> intersection(ComplexTimeline<T> timelineB) {
    var result = new ComplexTimeline<T>();

    timeline.getIntervals().forEach(ivA -> timelineB.getIntervals().forEach(ivB -> {
      if (ivA.overlaps(ivB)) {
        result.addInOrder(ivA.combine()
            .withMergeStrategy(mergeStrategy)
            .withSplitStrategy(splitStrategy)
            .intersection(ivB));
      }
    }));

    return result;
  }

  @Override
  public ComplexTimeline<T> diff(ComplexTimeline<T> timelineB) {
    var result = new ComplexTimeline<T>();

    timeline.getIntervals().forEach(ivA -> timelineB.getIntervals().forEach(ivB -> {
      if (ivA.overlaps(ivB)) {
        result.addInOrder(ivA.combine()
            .withMergeStrategy(mergeStrategy)
            .withSplitStrategy(splitStrategy)
            .diff(ivB));
      }
    }));

    return result;
  }

  @Override
  public ComplexTimeline<T> alignIntervalsTo(ComplexTimeline<T> timelineB) {
    var result = new ComplexTimeline<>(timeline);
    var resultModifier = result.modify();

    timeline.getIntervals().forEach(ivA -> timelineB.getIntervals().forEach(ivB -> {
      result.find().findContaining(ivB.getFrom())
          .ifPresent(iv -> iv.combine()
              .withMergeStrategy(mergeStrategy)
              .withSplitStrategy(splitStrategy)
              .split(ivB.getFrom()).forEach(resultModifier::overwrite));
      result.find().findContaining(ivB.getTo())
          .ifPresent(iv ->
              iv.combine()
              .withMergeStrategy(mergeStrategy)
              .withSplitStrategy(splitStrategy)
              .split(ivB.getTo()).forEach(resultModifier::overwrite));
    }));

    return result;
  }
}
