package net.oliste.timeintervals4j.interval;

/**
 * General exception class for timeintervals4j. Most runtime exceptions will be thrown with this
 * class.
 *
 * @author Paweł Łagan
 */
public class TimeIntervalException extends RuntimeException {

  /**
   * Constructor.
   *
   * @param msg exception message.
   */
  public TimeIntervalException(String msg) {
    super(msg);
  }
}
