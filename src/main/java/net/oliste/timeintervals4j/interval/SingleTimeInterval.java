package net.oliste.timeintervals4j.interval;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Represents a time interval that has a starting date and a finishing date, and may have additional
 * properties related to the interval.
 *
 * @author Paweł Łagan
 * @param <T> type of the properties related to interval
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class SingleTimeInterval<T> implements TimeInterval<SingleTimeInterval<T>, T> {

  private final ZonedDateTime from;
  private final ZonedDateTime to;

  private final T properties;

  /**
   * Creates a new time interval with the specified starting and finishing dates, and properties.
   *
   * @param from the starting date of the interval
   * @param to the finishing date of the interval
   * @param properties additional properties related to the interval
   */
  public SingleTimeInterval(ZonedDateTime from, ZonedDateTime to, T properties) {
    this.from = from;
    this.to = to;
    this.properties = properties;
  }

  /**
   * Creates a new time interval with the specified starting date, finishing date, and properties.
   *
   * @param from the starting date of the interval
   * @param to the finishing date of the interval
   * @param properties additional properties related to the interval
   */
  public static <S> SingleTimeInterval<S> of(ZonedDateTime from, ZonedDateTime to, S properties) {
    return new SingleTimeInterval<>(from, to, properties);
  }

  @Override
  public SingleTimeInterval<T> create(
      @NonNull ZonedDateTime from, @NonNull ZonedDateTime to, T properties) {
    return SingleTimeInterval.of(from, to, properties);
  }

  @Override
  public SingleTimeInterval<T> createCopy() {
    return SingleTimeInterval.of(getFrom(), getTo(), getProperties());
  }

  @Override
  public SingleTimeInterval<T> get() {
    return this;
  }
}
