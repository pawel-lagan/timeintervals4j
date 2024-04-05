package net.oliste.timeintervals4j.interval;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class SingleTimeInterval<T> implements TimeInterval<SingleTimeInterval<T>, T> {

  private final ZonedDateTime from;
  private final ZonedDateTime to;

  private final T properties;

  public SingleTimeInterval(ZonedDateTime from, ZonedDateTime to, T properties) {
    this.from = from;
    this.to = to;
    this.properties = properties;
  }

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
