package net.oliste.timeintervals4j.timeline.complex.tree;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;

/**
 * Represents a node of the red black tree.
 *
 * @author Paweł Łagan
 * @param <T> type of the properties related to interval
 * @param <S> type of the interval
 */
@AllArgsConstructor
@Setter(AccessLevel.PACKAGE)
public class BtpTreeNode<T, S extends SingleTimeInterval<T>> {
  @Getter private Color color;
  @Getter private S interval;
  @Getter private BtpTreeNode<T, S> parent;
  @Getter private BtpTreeNode<T, S> left;
  @Getter private BtpTreeNode<T, S> right;

  /** Represents a color of the tree node. */
  public enum Color {
    RED,
    BLACK
  }

  /** Change color, red -> black or black -> red. */
  void recolor() {
    setColor(Color.BLACK.equals(getColor()) ? Color.RED : Color.BLACK);
  }

  /**
   * Checks if node is a leaf.
   *
   * @return true if node is a leaf, false otherwise
   */
  boolean isLeaf() {
    return left == null && right == null;
  }

  /**
   * Returns uncle node for current node.
   *
   * @return {@link BtpTreeNode} uncle node or null when there is no uncle
   */
  public BtpTreeNode<T, S> uncle() {
    if (parent == null) {
      return null;
    }

    var grandparent = parent.getParent();
    if (grandparent.getLeft() == parent) {
      return grandparent.getRight();
    } else if (grandparent.getRight() == parent) {
      return grandparent.getLeft();
    } else {
      throw new IllegalStateException("Parent is not a child of its grandparent");
    }
  }

  /**
   * Returns sibling node for current node.
   *
   * @return {@link BtpTreeNode} sibling node or null when there is no uncle
   */
  public BtpTreeNode<T, S> sibling() {
    if (this == parent.getLeft()) {
      return parent.getRight();
    } else if (this == parent.getRight()) {
      return parent.getLeft();
    } else {
      throw new IllegalStateException("Parent is not a child of its grandparent");
    }
  }
}
