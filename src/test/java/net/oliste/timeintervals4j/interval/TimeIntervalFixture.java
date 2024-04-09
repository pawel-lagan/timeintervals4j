package net.oliste.timeintervals4j.interval;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;
import net.oliste.timeintervals4j.math.TimeMath;

public class TimeIntervalFixture {
  private final ZonedDateTime NOW =
      LocalDateTime.parse("2024-03-25T12:00:00").atZone(ZoneId.of("UTC"));
  private final IntervalSize gridSize;

  public TimeIntervalFixture(IntervalSize gridSize) {
    this.gridSize = gridSize;
  }

  public ZonedDateTime getNowUtc() {
    return NOW;
  }

  public ZonedDateTime createTimePoint(IntervalOffset offsetFrom) {
    return getNowUtc().plusMinutes(offsetFrom.getOffsetInMinutes(gridSize));
  }

  public <T> SingleTimeInterval<T> createInterval(
      IntervalOffset offsetFrom, IntervalOffset offsetTo, T props) {
    var from = getNowUtc().plusMinutes(offsetFrom.getOffsetInMinutes(gridSize));
    var to = getNowUtc().plusMinutes(offsetTo.getOffsetInMinutes(gridSize));
    return SingleTimeInterval.of(from, to, props);
  }

  public <T> SingleTimeInterval<T> createInterval(int fromOffset, int toOffset, T props) {

    var from = getNowUtc().plusMinutes((long) gridSize.getSizeMinutes() * fromOffset);
    var to = getNowUtc().plusMinutes((long) gridSize.getSizeMinutes() * toOffset);
    return SingleTimeInterval.of(from, to, props);
  }

  public <T> SingleTimeInterval<T> createLeftOpenedInterval(IntervalOffset offsetTo, T props) {
    var to = getNowUtc().plusMinutes(offsetTo.getOffsetInMinutes(gridSize));
    return SingleTimeInterval.of(TimeMath.PAST_INFINITY, to, props);
  }

  public <T> SingleTimeInterval<T> createRightOpenedInterval(IntervalOffset offsetFrom, T props) {
    var from = getNowUtc().plusMinutes(offsetFrom.getOffsetInMinutes(gridSize));
    return SingleTimeInterval.of(from, TimeMath.FUTURE_INFINITY, props);
  }

  public <T> SingleTimeInterval<T> createInfinityInterval(T props) {
    return SingleTimeInterval.of(TimeMath.PAST_INFINITY, TimeMath.FUTURE_INFINITY, props);
  }

  public enum IntervalSize {
    S(1),
    M(10),
    L(30),
    XL(40);

    @Getter private final int sizeMinutes;

    IntervalSize(int sizeMinutes) {
      this.sizeMinutes = sizeMinutes;
    }
  }

  /** Left Start Center End Right ----- | ------ | ------ | ------ | ------ | ------- */
  public enum IntervalOffset {
    _1(-1f),
    _2(0),
    _3(0.25f),
    _4(0.5f),
    _5(0.75f),
    _6(1),
    _7(2);

    @Getter private final float offsetMultiplier;

    IntervalOffset(float offsetMultiplier) {
      this.offsetMultiplier = offsetMultiplier;
    }

    public int getOffsetInMinutes(IntervalSize gridSize) {
      return Math.round(gridSize.getSizeMinutes() * offsetMultiplier);
    }
  }
}
