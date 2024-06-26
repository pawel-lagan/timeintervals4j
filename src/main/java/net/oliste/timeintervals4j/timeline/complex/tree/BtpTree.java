package net.oliste.timeintervals4j.timeline.complex.tree;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;
import net.oliste.timeintervals4j.interval.TimeIntervalException;
import net.oliste.timeintervals4j.timeline.complex.tree.BtpTreeNode.Color;

/**
 * Represents the red black tree of the intervals.
 *
 * @author Paweł Łagan
 * @param <T> type of the properties related to interval
 * @param <S> type of the interval
 */
public class BtpTree<T, S extends SingleTimeInterval<T>> {
  private BtpTreeNode<T, S> root;

  /**
   * Checks if the tree is empty.
   *
   * @return true if empty, false otherwise
   */
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * Returns the root node of the tree.
   *
   * @return {@link BtpTreeNode} the root node, null if the tree is empty
   */
  BtpTreeNode<T, S> getRoot() {
    return root;
  }

  /**
   * Search for all nodes matching the criteria: contains timestamp passed as a parameter and
   * matching predicate passed as a second one.
   *
   * @param timestamp {@link ZonedDateTime} timestamp
   * @param predicate {@link Predicate} predicate
   * @return {@link List} list of {@link BtpTreeNode} nodes matching the criteria
   */
  public List<BtpTreeNode<T, S>> search(ZonedDateTime timestamp, Predicate<S> predicate) {
    var list = new ArrayList<BtpTreeNode<T, S>>();
    var node = root;
    while (node != null) {
      if (timestamp.isBefore(node.getInterval().getFrom())) {
        node = node.getLeft();
      } else if (timestamp.isAfter(node.getInterval().getTo())) {
        node = node.getRight();
      } else {
        var parent = node.getParent();
        node = findMinimum(node);
        while (node != null && node != parent) {
          if (node.getInterval().contains(timestamp) && predicate.test(node.getInterval())) {
            list.add(node);
          }
          node = findSuccessorInOrder(node);
        }
        break;
      }
    }
    return list;
  }

  /**
   * Search for all nodes matching the criteria: overlaps interval passed as a parameter and
   * matching predicate passed as a second one.
   *
   * @param interval {@link SingleTimeInterval} interval
   * @param predicate {@link Predicate} predicate
   * @return {@link List} list of {@link BtpTreeNode} nodes matching the criteria
   */
  public List<BtpTreeNode<T, S>> search(S interval, Predicate<S> predicate) {
    var list = new ArrayList<BtpTreeNode<T, S>>();
    var node = root;
    while (node != null) {
      if (interval.isBefore(node.getInterval())) {
        node = node.getLeft();
      } else if (interval.isAfter(node.getInterval())) {
        node = node.getRight();
      } else {
        var parent = node.getParent();
        node = findMinimum(node);
        while (node != null && node != parent) {
          if (interval.overlaps(node.getInterval()) && predicate.test(node.getInterval())) {
            list.add(node);
          }
          node = findSuccessorInOrder(node);
        }
        break;
      }
    }
    return list;
  }

  /**
   * Inserts interval into the tree.
   *
   * @param interval {@link SingleTimeInterval} interval
   * @throws TimeIntervalException when there is already overlapping interval in the tree
   */
  public void insert(S interval) {
    var node = root;
    var lastLeft = false;
    BtpTreeNode<T, S> parent = null;

    // Traverse the tree to the left or right depending on the key
    while (node != null) {
      parent = node;

      if (interval.isBefore(node.getInterval())) {
        node = node.getLeft();
        lastLeft = true;
      } else if (interval.isAfter(node.getInterval())) {
        node = node.getRight();
        lastLeft = false;
      } else {
        throw new TimeIntervalException("overlapping");
      }
    }

    // Insert new node
    var newNode = new BtpTreeNode<>(Color.RED, interval, node, null, null);
    if (parent == null) {
      root = newNode;
    } else if (lastLeft) {
      parent.setLeft(newNode);
    } else {
      parent.setRight(newNode);
    }
    newNode.setParent(parent);

    fixRedBlackPropertiesAfterInsert(newNode);
  }

  /**
   * Removes node from the tree.
   *
   * @param node {@link BtpTreeNode} node
   */
  public void removeNode(BtpTreeNode<T, S> node) {
    // Node not found?
    if (node == null) {
      return;
    }
    // At this point, "node" is the node to be deleted
    // In this variable, we'll store the node at which we're going to start to fix the R-B
    // properties after deleting a node.
    BtpTreeNode<T, S> movedUpNode;
    BtpTreeNode.Color deletedNodeColor;

    // Node has zero or one child
    if (node.getLeft() == null || node.getRight() == null) {
      movedUpNode = deleteNodeWithZeroOrOneChild(node);
      deletedNodeColor = node.getColor();
    } else {
      // Node has two children
      // Find minimum node of right subtree ("inorder successor" of current node)
      var inOrderSuccessor = findMinimum(node.getRight());

      // Copy inorder successor's data to current node (keep its color!)
      node.setInterval(inOrderSuccessor.getInterval());

      // Delete inorder successor just as we would delete a node with 0 or 1 child
      movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
      deletedNodeColor = inOrderSuccessor.getColor();
    }

    if (Color.BLACK.equals(deletedNodeColor)) {
      fixRedBlackPropertiesAfterDelete(movedUpNode);

      // Remove the temporary NIL node
      if (movedUpNode.getClass() == NullNode.class) {
        swapParents(movedUpNode.getParent(), movedUpNode, null);
      }
    }
  }

  /**
   * Iteration over the tree nodes in order.
   *
   * @param intervalConsumer {@link Consumer} of time intervals
   */
  public void forEach(Consumer<S> intervalConsumer) {
    var it = iterator();
    while (it.hasNext()) {
      var nextNode = it.next();
      intervalConsumer.accept(nextNode.getInterval());
    }
  }

  /**
   * Returns all tree nodes in order.
   *
   * @return {@link List} list of the intervals
   */
  public List<S> getIntervals() {
    var list = new ArrayList<S>();
    var it = iterator();
    while (it.hasNext()) {
      var nextNode = it.next();
      list.add(nextNode.getInterval());
    }
    return list;
  }

  /**
   * Returns iterator over collection of time intervals.
   *
   * @return {@link Iterator} object
   */
  public Iterator<BtpTreeNode<T, S>> iterator() {
    return new Iterator<>() {
      private BtpTreeNode<T, S> next = findMinimum(root);

      @Override
      public boolean hasNext() {
        return next != null;
      }

      @Override
      public BtpTreeNode<T, S> next() {
        if (next == null) {
          throw new NoSuchElementException("there is no next interval");
        }

        var newNext = findSuccessorInOrder(next);
        var current = next;
        next = newNext;

        return current;
      }
    };
  }

  void rotateRight(BtpTreeNode<T, S> node) {
    var leftChild = node.getLeft();

    node.setLeft(leftChild.getRight());
    if (leftChild.getRight() != null) {
      leftChild.getRight().setParent(node);
    }

    leftChild.setRight(node);

    var parent = node.getParent();
    node.setParent(leftChild);
    swapParents(parent, node, leftChild);
  }

  void rotateLeft(BtpTreeNode<T, S> node) {
    var rightChild = node.getRight();

    node.setRight(rightChild.getLeft());
    if (rightChild.getLeft() != null) {
      rightChild.getLeft().setParent(node);
    }

    rightChild.setLeft(node);

    var parent = node.getParent();
    node.setParent(rightChild);
    swapParents(parent, node, rightChild);
  }

  private void swapParents(
      BtpTreeNode<T, S> parent, BtpTreeNode<T, S> oldChild, BtpTreeNode<T, S> newChild) {
    if (newChild != null) {
      newChild.setParent(parent);
    }
    if (parent == null) {
      root = newChild;
    } else {
      if (parent.getRight() == oldChild) {
        parent.setRight(newChild);
      } else {
        parent.setLeft(newChild);
      }
    }
  }

  private void fixRedBlackPropertiesAfterInsert(BtpTreeNode<T, S> node) {
    var parent = node.getParent();

    // Case 1: Parent is null, we've reached the root, the end of the recursion
    if (parent == null) {
      // Uncomment the following line if you want to enforce black roots (rule 2):
      node.setColor(Color.BLACK);
      return;
    }

    // Parent is black --> nothing to do
    if (isNodeBlack(parent)) {
      return;
    }

    // From here on, parent is red
    var grandparent = parent.getParent();

    // Get the uncle (might be null, in which case its color is BLACK)
    var uncle = node.uncle();

    // Case 3: Uncle is red -> recolor parent, grandparent and uncle
    if (uncle != null && isNodeRed(uncle)) {
      parent.setColor(Color.BLACK);
      grandparent.setColor(Color.RED);
      uncle.setColor(Color.BLACK);

      // Call recursively for grandparent, which is now red.
      // It might be root or have a red parent, in which case we need to fix more...
      fixRedBlackPropertiesAfterInsert(grandparent);
    } else if (parent == grandparent.getLeft()) { // Parent is left child of grandparent
      // Case 4a: Uncle is black and node is left->right "inner child" of its grandparent
      if (node == parent.getRight()) {
        rotateLeft(parent);

        // Let "parent" point to the new root node of the rotated subtree.
        // It will be recolored in the next step, which we're going to fall-through to.
        parent = node;
      }

      // Case 5a: Uncle is black and node is left->left "outer child" of its grandparent
      rotateRight(grandparent);

      // Recolor original parent and grandparent
      parent.recolor();
      grandparent.recolor();
    } else { // Parent is right child of grandparent
      // Case 4b: Uncle is black and node is right->left "inner child" of its grandparent
      if (node == parent.getLeft()) {
        rotateRight(parent);

        // Let "parent" point to the new root node of the rotated subtree.
        // It will be recolored in the next step, which we're going to fall-through to.
        parent = node;
      }

      // Case 5b: Uncle is black and node is right->right "outer child" of its grandparent
      rotateLeft(grandparent);

      // Recolor original parent and grandparent
      parent.recolor();
      grandparent.recolor();
    }
  }

  private void fixRedBlackPropertiesAfterDelete(BtpTreeNode<T, S> node) {
    // Case 1: Examined node is root, end of recursion
    if (node == root) {
      // Uncomment the following line if you want to enforce black roots (rule 2):
      node.setColor(Color.BLACK);
      return;
    }

    var sibling = node.sibling();

    // Case 2: Red sibling
    if (isNodeRed(sibling)) {
      handleRedSibling(node, sibling);
      sibling = node.sibling(); // Get new sibling for fall-through to cases 3-6
    }

    // Cases 3+4: Black sibling with two black children
    if (!isNodeRed(sibling.getLeft()) && !isNodeRed(sibling.getRight())) {
      sibling.setColor(Color.RED);

      // Case 3: Black sibling with two black children + red parent
      if (isNodeRed(node.getParent())) {
        node.getParent().setColor(Color.BLACK);
      } else {
        // Case 4: Black sibling with two black children + black parent
        fixRedBlackPropertiesAfterDelete(node.getParent());
      }
    } else {
      // Case 5+6: Black sibling with at least one red child
      handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
    }
  }

  private BtpTreeNode<T, S> deleteNodeWithZeroOrOneChild(BtpTreeNode<T, S> node) {
    // Node has ONLY a left child --> replace by its left child
    if (node.getLeft() != null) {
      swapParents(node.getParent(), node, node.getLeft());
      return node.getLeft(); // moved-up node
    } else if (node.getRight()
        != null) { // Node has ONLY a right child --> replace by its right child
      swapParents(node.getParent(), node, node.getRight());
      return node.getRight(); // moved-up node
    } else {
      // Node has no children -->
      // * node is red --> just remove it
      // * node is black --> replace it by a temporary NULL node (needed to fix the R-B rules)

      var newChild = !isNodeRed(node) ? new NullNode<T, S>() : null;
      swapParents(node.getParent(), node, newChild);
      return newChild;
    }
  }

  private void handleRedSibling(BtpTreeNode<T, S> node, BtpTreeNode<T, S> sibling) {
    // Recolor...
    sibling.setColor(Color.BLACK);
    node.getParent().setColor(Color.RED);

    // ... and rotate
    if (node == node.getParent().getLeft()) {
      rotateLeft(node.getParent());
    } else {
      rotateRight(node.getParent());
    }
  }

  private void handleBlackSiblingWithAtLeastOneRedChild(
      BtpTreeNode<T, S> node, BtpTreeNode<T, S> sibling) {
    boolean nodeIsLeftChild = node == node.getParent().getLeft();

    // Case 5: Black sibling with at least one red child + "outer nephew" is black
    // --> Recolor sibling and its child, and rotate around sibling
    if (nodeIsLeftChild && !isNodeRed(sibling.getRight())) {
      sibling.getLeft().setColor(Color.BLACK);
      sibling.setColor(Color.RED);
      rotateRight(sibling);
      sibling = node.getParent().getRight();
    } else if (!nodeIsLeftChild && !isNodeRed(sibling.getLeft())) {
      sibling.getRight().setColor(Color.BLACK);
      sibling.setColor(Color.RED);
      rotateLeft(sibling);
      sibling = node.getParent().getLeft();
    }

    // Fall-through to case 6...

    // Case 6: Black sibling with at least one red child + "outer nephew" is red
    // --> Recolor sibling + parent + sibling's child, and rotate around parent
    sibling.setColor(node.getParent().getColor());
    node.getParent().setColor(Color.BLACK);
    if (nodeIsLeftChild) {
      sibling.getRight().setColor(Color.BLACK);
      rotateLeft(node.getParent());
    } else {
      sibling.getLeft().setColor(Color.BLACK);
      rotateRight(node.getParent());
    }
  }

  private boolean isNodeRed(BtpTreeNode<T, S> node) {
    return node == null || Color.RED.equals(node.getColor());
  }

  private boolean isNodeBlack(BtpTreeNode<T, S> node) {
    return node != null && Color.BLACK.equals(node.getColor());
  }

  private BtpTreeNode<T, S> findSuccessorInOrder(BtpTreeNode<T, S> cur) {
    if (cur == null) {
      return null;
    }

    if (cur.getRight() != null) {
      return findMinimum(cur.getRight());
    }

    var possibleNext = cur.getParent();
    while (possibleNext != null && cur == possibleNext.getRight()) {
      cur = possibleNext;
      possibleNext = possibleNext.getParent();
    }

    return possibleNext;
  }

  private BtpTreeNode<T, S> findMinimum(BtpTreeNode<T, S> node) {
    var result = node;
    while (result != null && result.getLeft() != null) {
      result = result.getLeft();
    }
    return result;
  }

  private static class NullNode<T, S extends SingleTimeInterval<T>> extends BtpTreeNode<T, S> {
    private NullNode() {
      super(Color.BLACK, null, null, null, null);
    }
  }
}
