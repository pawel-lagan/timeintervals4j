package net.oliste.timeintervals4j.interval;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TimeIntervalTest {

  private TimeIntervalFixture fixture = new TimeIntervalFixture();
  private String props = "Interval props";

  @Test
  void fromCheckIsWorkingCorrectly() {
    var interval = SingleTimeInterval.of(null, fixture.getNowUtc(), props);
    assertThat(interval.hasNoFrom()).isTrue();

    interval = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc(), props);
    assertThat(interval.hasNoFrom()).isFalse();

    interval = SingleTimeInterval.of(null, fixture.getNowUtc(), props);
    assertThat(interval.hasFrom()).isFalse();

    interval = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc(), props);
    assertThat(interval.hasFrom()).isTrue();
  }

  @Test
  void toCheckIsWorkingCorrectly() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), null, props);
    assertThat(interval.hasNoTo()).isTrue();

    interval = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc(), props);
    assertThat(interval.hasNoTo()).isFalse();

    interval = SingleTimeInterval.of(fixture.getNowUtc(), null, props);
    assertThat(interval.hasTo()).isFalse();

    interval = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc(), props);
    assertThat(interval.hasTo()).isTrue();
  }

  @Test
  void leftOpenedIntervalContainsTimePoint() {
    var interval = SingleTimeInterval.of(null, fixture.getNowUtc(), props);

    var case1 = fixture.getNowUtc();
    assertThat(interval.contains(case1)).isTrue();

    var case2 = fixture.getNowUtc().minusSeconds(1);
    assertThat(interval.contains(case2)).isTrue();

    var case3 = fixture.getNowUtc().plusSeconds(1);
    assertThat(interval.contains(case3)).isFalse();
  }

  @Test
  void leftOpenedIntervalContainsLeftOpenedInterval() {
    var interval = SingleTimeInterval.of(null, fixture.getNowUtc(), props);

    var case1 = SingleTimeInterval.of(null, fixture.getNowUtc(), props);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = SingleTimeInterval.of(null, fixture.getNowUtc().minusSeconds(1), props);
    assertThat(interval.contains(case2)).isTrue();

    var case3 = SingleTimeInterval.of(null, fixture.getNowUtc().plusSeconds(1), props);
    assertThat(interval.contains(case3)).isFalse();

    var case4 = SingleTimeInterval.of(null, null, props);
    assertThat(interval.contains(case4)).isFalse();

    var case5 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), fixture.getNowUtc(), props);
    assertThat(interval.contains(case5)).isTrue();
  }

  @Test
  void leftOpenedIntervalContainsRightOpenedInterval() {
    var interval = SingleTimeInterval.of(null, fixture.getNowUtc(), props);

    var case1 = SingleTimeInterval.of(fixture.getNowUtc(), null, props);
    assertThat(interval.contains(case1)).isFalse();

    var case2 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), null, props);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = SingleTimeInterval.of(fixture.getNowUtc().plusSeconds(1), null, props);
    assertThat(interval.contains(case3)).isFalse();

    var case4 = SingleTimeInterval.of(null, null, props);
    assertThat(interval.contains(case4)).isFalse();
  }

  @Test
  void leftOpenedIntervalContainsClosedInterval() {
    var interval = SingleTimeInterval.of(null, fixture.getNowUtc(), props);

    var case1 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), fixture.getNowUtc(), props);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc().plusSeconds(1), props);
    assertThat(interval.contains(case2)).isFalse();
  }

  @Test
  void rightOpenedIntervalContainsTimePoint() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), null, props);

    var case1 = fixture.getNowUtc();
    assertThat(interval.contains(case1)).isTrue();

    var case2 = fixture.getNowUtc().minusSeconds(1);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = fixture.getNowUtc().plusSeconds(1);
    assertThat(interval.contains(case3)).isTrue();
  }

  @Test
  void rightOpenedIntervalContainsLeftOpenedInterval() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), null, props);

    var case1 = SingleTimeInterval.of(null, fixture.getNowUtc(), props);
    assertThat(interval.contains(case1)).isFalse();

    var case2 = SingleTimeInterval.of(null, fixture.getNowUtc().minusSeconds(1), props);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = SingleTimeInterval.of(null, fixture.getNowUtc().plusSeconds(1), props);
    assertThat(interval.contains(case3)).isFalse();

    var case4 = SingleTimeInterval.of(null, null, props);
    assertThat(interval.contains(case4)).isFalse();
  }

  @Test
  void rightOpenedIntervalContainsRightOpenedInterval() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), null, props);

    var case1 = SingleTimeInterval.of(fixture.getNowUtc(), null, props);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), null, props);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = SingleTimeInterval.of(fixture.getNowUtc().plusSeconds(1), null, props);
    assertThat(interval.contains(case3)).isTrue();

    var case4 = SingleTimeInterval.of(null, null, props);
    assertThat(interval.contains(case4)).isFalse();
  }

  @Test
  void rightOpenedIntervalContainsClosedInterval() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), null, props);

    var case1 = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc().plusSeconds(1), props);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), fixture.getNowUtc(), props);
    assertThat(interval.contains(case2)).isFalse();
  }

  @Test
  void closedIntervalContainsTimePoint() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc().plusSeconds(10), props);

    var case1 = fixture.getNowUtc();
    assertThat(interval.contains(case1)).isTrue();

    var case2 = fixture.getNowUtc().minusSeconds(1);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = fixture.getNowUtc().plusSeconds(1);
    assertThat(interval.contains(case3)).isTrue();

    var case4 = fixture.getNowUtc().plusSeconds(10);
    assertThat(interval.contains(case4)).isTrue();

    var case5 = fixture.getNowUtc().plusSeconds(11);
    assertThat(interval.contains(case5)).isFalse();
  }

  @Test
  void closedIntervalContainsLeftOpenedInterval() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc().plusSeconds(10), props);

    var case1 = SingleTimeInterval.of(null, fixture.getNowUtc(), props);
    assertThat(interval.contains(case1)).isFalse();

    var case2 = SingleTimeInterval.of(null, fixture.getNowUtc().minusSeconds(1), props);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = SingleTimeInterval.of(null, fixture.getNowUtc().plusSeconds(1), props);
    assertThat(interval.contains(case3)).isFalse();

    var case4 = SingleTimeInterval.of(null, null, props);
    assertThat(interval.contains(case4)).isFalse();
  }

  @Test
  void closedIntervalContainsRightOpenedInterval() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc().plusSeconds(10), props);

    var case1 = SingleTimeInterval.of(fixture.getNowUtc(), null, props);
    assertThat(interval.contains(case1)).isFalse();

    var case2 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), null, props);
    assertThat(interval.contains(case2)).isFalse();

    var case3 = SingleTimeInterval.of(fixture.getNowUtc().plusSeconds(1), null, props);
    assertThat(interval.contains(case3)).isFalse();

    var case4 = SingleTimeInterval.of(null, null, props);
    assertThat(interval.contains(case4)).isFalse();
  }

  @Test
  void closedIntervalContainsClosedInterval() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc().plusSeconds(10), props);

    var case1 = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc().plusSeconds(10), props);
    assertThat(interval.contains(case1)).isTrue();

    var case2 = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc().plusSeconds(5), props);
    assertThat(interval.contains(case2)).isTrue();

    var case3 = SingleTimeInterval.of(fixture.getNowUtc().plusSeconds(5), fixture.getNowUtc().plusSeconds(10), props);
    assertThat(interval.contains(case3)).isTrue();

    var case4 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), fixture.getNowUtc().plusSeconds(10), props);
    assertThat(interval.contains(case4)).isFalse();

    var case5 = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc().plusSeconds(11), props);
    assertThat(interval.contains(case5)).isFalse();

    var case6 = SingleTimeInterval.of(null, null, props);
    assertThat(interval.contains(case6)).isFalse();
  }

  @Test
  void leftOpenedIntervalOverlapsLeftOpenedInterval() {
    var interval = SingleTimeInterval.of(null, fixture.getNowUtc(), props);

    var case1 = SingleTimeInterval.of(null, fixture.getNowUtc(), props);
    assertThat(interval.overlaps(case1)).isTrue();

    var case2 = SingleTimeInterval.of(null, fixture.getNowUtc().minusSeconds(1), props);
    assertThat(interval.overlaps(case2)).isTrue();

    var case3 = SingleTimeInterval.of(null, fixture.getNowUtc().plusSeconds(1), props);
    assertThat(interval.overlaps(case3)).isTrue();

    var case4 = SingleTimeInterval.of(null, null, props);
    assertThat(interval.overlaps(case4)).isTrue();

    var case5 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), fixture.getNowUtc(), props);
    assertThat(interval.overlaps(case5)).isTrue();

    var case6 = SingleTimeInterval.of(fixture.getNowUtc().plusSeconds(1), fixture.getNowUtc().plusSeconds(2), props);
    assertThat(interval.overlaps(case6)).isFalse();

    var case7 = SingleTimeInterval.of(fixture.getNowUtc().plusSeconds(1), null, props);
    assertThat(interval.overlaps(case7)).isFalse();
  }

  @Test
  void leftOpenedIntervalOverlapsRightOpenedInterval() {
    var interval = SingleTimeInterval.of(null, fixture.getNowUtc(), props);

    var case1 = SingleTimeInterval.of(fixture.getNowUtc(), null, props);
    assertThat(interval.overlaps(case1)).isTrue();

    var case2 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), null, props);
    assertThat(interval.overlaps(case2)).isTrue();

    var case3 = SingleTimeInterval.of(fixture.getNowUtc().plusSeconds(1), null, props);
    assertThat(interval.overlaps(case3)).isFalse();

    var case4 = SingleTimeInterval.of(null, null, props);
    assertThat(interval.overlaps(case4)).isTrue();
  }

  @Test
  void leftOpenedIntervalOverlapsClosedInterval() {
    var interval = SingleTimeInterval.of(null, fixture.getNowUtc(), props);

    var case1 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), fixture.getNowUtc(), props);
    assertThat(interval.overlaps(case1)).isTrue();

    var case2 = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc().plusSeconds(1), props);
    assertThat(interval.overlaps(case2)).isTrue();

    var case3 = SingleTimeInterval.of(fixture.getNowUtc(), null, props);
    assertThat(interval.overlaps(case3)).isTrue();
  }

  @Test
  void rightOpenedIntervalOverlapsLeftOpenedInterval() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), null, props);

    var case1 = SingleTimeInterval.of(null, fixture.getNowUtc(), props);
    assertThat(interval.overlaps(case1)).isTrue();

    var case2 = SingleTimeInterval.of(null, fixture.getNowUtc().minusSeconds(1), props);
    assertThat(interval.overlaps(case2)).isFalse();

    var case3 = SingleTimeInterval.of(null, fixture.getNowUtc().plusSeconds(1), props);
    assertThat(interval.overlaps(case3)).isTrue();

    var case4 = SingleTimeInterval.of(null, null, props);
    assertThat(interval.overlaps(case4)).isTrue();
  }

  @Test
  void rightOpenedIntervalOverlapsRightOpenedInterval() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), null, props);

    var case1 = SingleTimeInterval.of(fixture.getNowUtc(), null, props);
    assertThat(interval.overlaps(case1)).isTrue();

    var case2 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), null, props);
    assertThat(interval.overlaps(case2)).isTrue();

    var case3 = SingleTimeInterval.of(fixture.getNowUtc().plusSeconds(1), null, props);
    assertThat(interval.overlaps(case3)).isTrue();

    var case4 = SingleTimeInterval.of(null, null, props);
    assertThat(interval.overlaps(case4)).isTrue();
  }

  @Test
  void rightOpenedIntervalOverlapsClosedInterval() {
    var interval = SingleTimeInterval.of(fixture.getNowUtc(), null, props);

    var case1 = SingleTimeInterval.of(fixture.getNowUtc(), fixture.getNowUtc().plusSeconds(1), props);
    assertThat(interval.overlaps(case1)).isTrue();

    var case2 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(1), fixture.getNowUtc(), props);
    assertThat(interval.overlaps(case2)).isTrue();

    var case3 = SingleTimeInterval.of(fixture.getNowUtc().minusSeconds(10), fixture.getNowUtc().minusSeconds(1), props);
    assertThat(interval.overlaps(case3)).isFalse();
  }


  @Test
  void testContains() {
  }

  @Test
  void overlaps() {
  }

  @Test
  void combine() {
  }

}