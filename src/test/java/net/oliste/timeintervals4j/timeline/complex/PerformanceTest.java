package net.oliste.timeintervals4j.timeline.complex;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.noconnor.junitperf.JUnitPerfInterceptor;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JUnitPerfInterceptor.class)
class PerformanceTest {

  public static final int ITEMS_ADDED = 100;
  private final TimeIntervalFixture fixture = new TimeIntervalFixture(IntervalSize.L);
  private final String props = "props";

  @Test
  @JUnitPerfTest(threads = 1, durationMs = 10_000, warmUpMs = 2_000)
  @JUnitPerfTestRequirement(
      percentiles = "90:7,95:7,98:7,99:8",
      executionsPerSec = 25000, // performence for CI on i7 bare metal should be around 82k - 95k
      allowedErrorPercentage = 0)
  void addInOrderOperation() {
    var timeline = new ComplexTimeline<>();
    var timelineModification = timeline.modify();
    for (var i = 1; i < ITEMS_ADDED; i++) {
      timelineModification.insert(fixture.createInterval(i - 1, i, props));
    }
    assertThat(timeline.getIntervals()).hasSize(ITEMS_ADDED - 1);
  }
}
