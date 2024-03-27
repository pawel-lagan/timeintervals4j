package net.oliste.timeintervals4j.interval;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@RequiredArgsConstructor(staticName = "of")
@ToString
@EqualsAndHashCode
public class SingleTimeInterval<T> implements TimeInterval<SingleTimeInterval<T>, T> {

  private final ZonedDateTime from;
  private final ZonedDateTime to;

  private final T properties;

  @Override
  public SingleTimeInterval<T> create(ZonedDateTime from, ZonedDateTime to, T properties) {
    return SingleTimeInterval.of(from, to, properties);
  }

  @Override
  public SingleTimeInterval<T> get() {
    return this;
  }


}
