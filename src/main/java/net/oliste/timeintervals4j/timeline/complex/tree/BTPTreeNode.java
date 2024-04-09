package net.oliste.timeintervals4j.timeline.complex.tree;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

@AllArgsConstructor
@Setter(AccessLevel.PACKAGE)
public class BTPTreeNode<T, S extends SingleTimeInterval<T>> {
  @Getter private Color color;
  @Getter private S interval;
  @Getter private BTPTreeNode<T, S> parent;
  @Getter private BTPTreeNode<T, S> left;
  @Getter private BTPTreeNode<T, S> right;

  public enum Color {
    RED,
    BLACK
  }

  void recolor() {
    setColor(Color.BLACK.equals(getColor()) ? Color.RED : Color.BLACK);
  }

  boolean isLeaf() {
    return left == null && right == null;
  }
}
