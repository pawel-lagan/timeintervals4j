package net.oliste.timeintervals4j.interval;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeIntervalFixture {
  private final ZonedDateTime NOW = LocalDateTime.parse("2024-03-23T12:21:35").atZone(ZoneId.of("UTC"));

  public ZonedDateTime getNowUtc() {
    return NOW;
  }
}
