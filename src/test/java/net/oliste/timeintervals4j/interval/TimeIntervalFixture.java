package net.oliste.timeintervals4j.interval;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;
import net.oliste.timeintervals4j.math.TimeMath;

/**
 * Fixture for the tests of the time intervals. It contains methods that creates frequently used
 * test data.
 *
 * @author Paweł Łagan
 */
public class TimeIntervalFixture {
  private static final ZonedDateTime NOW =
      LocalDateTime.parse("2024-03-25T12:00:00").atZone(ZoneId.of("UTC"));
  private final IntervalSize gridSize;

  /**
   * Constructor for the time interval fixture.
   *
   * @param gridSize grid size for the fixture
   */
  public TimeIntervalFixture(IntervalSize gridSize) {
    this.gridSize = gridSize;
  }

  /**
   * Get date time representing current time in the unit tests.
   *
   * @return {@link ZonedDateTime} date time with timezone
   */
  public ZonedDateTime getNowUtc() {
    return NOW;
  }

  public ZonedDateTime createTimePoint(IntervalOffset offsetFrom) {
    return getNowUtc().plusMinutes(offsetFrom.getOffsetInMinutes(gridSize));
  }

  /**
   * Creates interval object based on defined offsets and properties.
   *
   * @param <T> time interval properties type
   * @param offsetFrom {@link IntervalOffset} from offset on the grid _1, _2, _3, ...
   * @param offsetTo {@link IntervalOffset} to offset on the grid _1, _2, _3, ...
   * @param props properties of the interval
   * @return {@link SingleTimeInterval} interval object
   */
  public <T> SingleTimeInterval<T> createInterval(
      IntervalOffset offsetFrom, IntervalOffset offsetTo, T props) {
    var from = getNowUtc().plusMinutes(offsetFrom.getOffsetInMinutes(gridSize));
    var to = getNowUtc().plusMinutes(offsetTo.getOffsetInMinutes(gridSize));
    return SingleTimeInterval.of(from, to, props);
  }

  /**
   * Creates interval object based on defined offsets and properties.
   *
   * @param <T> time interval properties type
   * @param fromOffset from offset on the grid as integer 1, 2, 3, ...
   * @param toOffset to offset on the grid as integer 1, 2, 3, ...
   * @param props properties of the interval
   * @return {@link SingleTimeInterval} interval object
   */
  public <T> SingleTimeInterval<T> createInterval(int fromOffset, int toOffset, T props) {

    var from = getNowUtc().plusMinutes((long) gridSize.getSizeMinutes() * fromOffset);
    var to = getNowUtc().plusMinutes((long) gridSize.getSizeMinutes() * toOffset);
    return SingleTimeInterval.of(from, to, props);
  }

  /**
   * Creates interval object based on defined to offset, undefined past as from, and properties.
   *
   * @param <T> time interval properties type
   * @param offsetTo {@link IntervalOffset} to offset on the grid _1, _2, _3, ...
   * @param props properties of the interval
   * @return {@link SingleTimeInterval} interval object
   */
  public <T> SingleTimeInterval<T> createLeftOpenedInterval(IntervalOffset offsetTo, T props) {
    var to = getNowUtc().plusMinutes(offsetTo.getOffsetInMinutes(gridSize));
    return SingleTimeInterval.of(TimeMath.PAST_INFINITY, to, props);
  }

  /**
   * Creates interval object based on defined from offset, undefined future as to, and properties.
   *
   * @param <T> time interval properties type
   * @param offsetFrom {@link IntervalOffset} from offset on the grid _1, _2, _3, ...
   * @param props properties of the interval
   * @return {@link SingleTimeInterval} interval object
   */
  public <T> SingleTimeInterval<T> createRightOpenedInterval(IntervalOffset offsetFrom, T props) {
    var from = getNowUtc().plusMinutes(offsetFrom.getOffsetInMinutes(gridSize));
    return SingleTimeInterval.of(from, TimeMath.FUTURE_INFINITY, props);
  }

  /**
   * Creates interval object based on undefined past and future, and properties.
   *
   * @param <T> time interval properties type
   * @param props properties of the interval
   * @return {@link SingleTimeInterval} interval object
   */
  public <T> SingleTimeInterval<T> createInfinityInterval(T props) {
    return SingleTimeInterval.of(TimeMath.PAST_INFINITY, TimeMath.FUTURE_INFINITY, props);
  }

  /** Grid size for the intervals. */
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

  /** Left Start Center End Right ----- | ------ | ------ | ------ | ------ | -------. */
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

    /**
     * Returns the offset in minutes for specific point in the time defined in enum.
     *
     * @param gridSize size of the grid: L, S, ...
     * @return Offset in minutes for specific point in the time
     */
    public int getOffsetInMinutes(IntervalSize gridSize) {
      return Math.round(gridSize.getSizeMinutes() * offsetMultiplier);
    }
  }
}
