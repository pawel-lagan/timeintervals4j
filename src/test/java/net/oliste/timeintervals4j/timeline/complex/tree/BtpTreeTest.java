package net.oliste.timeintervals4j.timeline.complex.tree;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import net.oliste.timeintervals4j.interval.SingleTimeInterval;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture;
import net.oliste.timeintervals4j.interval.TimeIntervalFixture.IntervalSize;
import net.oliste.timeintervals4j.timeline.complex.tree.BtpTreeNode.Color;
import org.junit.jupiter.api.Test;

class BtpTreeTest {
  private final TimeIntervalFixture fixture = new TimeIntervalFixture(IntervalSize.L);

  @Test
  void initialStateOfTheTreeIsEmpty() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();
    assertThat(tree.isEmpty()).isTrue();
  }

  @Test
  void rootNodeShouldBeBlack() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(5, 6, "A");
    tree.insert(intervalA);

    assertThat(tree.isEmpty()).isFalse();
    assertThat(tree.getRoot())
        .isNotNull()
        .extracting(BtpTreeNode::getInterval)
        .isEqualTo(intervalA);
    assertThat(tree.getRoot().getColor()).isEqualTo(Color.BLACK);
  }

  @Test
  void parentNodeIsBlack() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(5, 6, "A");
    var intervalB = fixture.createInterval(1, 2, "B");

    tree.insert(intervalA);
    tree.insert(intervalB);

    assertThat(tree.getRoot()).isNotNull();
    assertThat(tree.getRoot().getLeft().getColor()).isEqualTo(Color.RED);
  }

  @Test
  void parentNodeIsRedAndThereIsNoUncleLeftRightRotationIsRequired() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(5, 6, "A");
    var intervalB = fixture.createInterval(1, 2, "B");
    var intervalC = fixture.createInterval(3, 4, "C");

    tree.insert(intervalA);
    tree.insert(intervalB);
    tree.insert(intervalC);

    assertThat(tree.getRoot()).isNotNull();
    assertThat(tree.getRoot().getInterval()).isEqualTo(intervalC);
    assertThat(tree.getRoot().getColor()).isEqualTo(Color.BLACK);
    assertThat(tree.getRoot().getLeft().getInterval()).isEqualTo(intervalB);
    assertThat(tree.getRoot().getLeft().getColor()).isEqualTo(Color.RED);
    assertThat(tree.getRoot().getRight().getInterval()).isEqualTo(intervalA);
    assertThat(tree.getRoot().getRight().getColor()).isEqualTo(Color.RED);
  }

  @Test
  void parentNodeIsRedAndThereIsNoUncleRightLeftRotationIsRequired() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(1, 2, "A");
    var intervalB = fixture.createInterval(5, 6, "B");
    var intervalC = fixture.createInterval(3, 4, "C");

    tree.insert(intervalA);
    tree.insert(intervalB);
    tree.insert(intervalC);

    assertThat(tree.getRoot()).isNotNull();
    assertThat(tree.getRoot().getInterval()).isEqualTo(intervalC);
    assertThat(tree.getRoot().getColor()).isEqualTo(Color.BLACK);
    assertThat(tree.getRoot().getLeft().getInterval()).isEqualTo(intervalA);
    assertThat(tree.getRoot().getLeft().getColor()).isEqualTo(Color.RED);
    assertThat(tree.getRoot().getRight().getInterval()).isEqualTo(intervalB);
    assertThat(tree.getRoot().getRight().getColor()).isEqualTo(Color.RED);
  }

  @Test
  void parentNodeIsRedAndThereIsNoUncleLeftLeftRotationIsRequired() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(5, 6, "A");
    var intervalB = fixture.createInterval(3, 4, "B");
    var intervalC = fixture.createInterval(1, 2, "C");

    tree.insert(intervalA);
    tree.insert(intervalB);
    tree.insert(intervalC);

    assertThat(tree.getRoot()).isNotNull();
    assertThat(tree.getRoot().getInterval()).isEqualTo(intervalB);
    assertThat(tree.getRoot().getColor()).isEqualTo(Color.BLACK);
    assertThat(tree.getRoot().getLeft().getInterval()).isEqualTo(intervalC);
    assertThat(tree.getRoot().getLeft().getColor()).isEqualTo(Color.RED);
    assertThat(tree.getRoot().getRight().getInterval()).isEqualTo(intervalA);
    assertThat(tree.getRoot().getRight().getColor()).isEqualTo(Color.RED);
  }

  @Test
  void parentNodeIsRedAndThereIsNoUncleRightRightRotationIsRequired() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(1, 2, "A");
    var intervalB = fixture.createInterval(3, 4, "B");
    var intervalC = fixture.createInterval(5, 6, "C");

    tree.insert(intervalA);
    tree.insert(intervalB);
    tree.insert(intervalC);

    assertThat(tree.getRoot()).isNotNull();
    assertThat(tree.getRoot().getInterval()).isEqualTo(intervalB);
    assertThat(tree.getRoot().getColor()).isEqualTo(Color.BLACK);
    assertThat(tree.getRoot().getLeft().getInterval()).isEqualTo(intervalA);
    assertThat(tree.getRoot().getLeft().getColor()).isEqualTo(Color.RED);
    assertThat(tree.getRoot().getRight().getInterval()).isEqualTo(intervalC);
    assertThat(tree.getRoot().getRight().getColor()).isEqualTo(Color.RED);
  }

  @Test
  void parentNodeIsRedAndUncleIsRedThenRecolor() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(1, 2, "A");
    var intervalB = fixture.createInterval(3, 4, "B");
    var intervalC = fixture.createInterval(5, 6, "C");
    var intervalD = fixture.createInterval(2, 3, "D");

    tree.insert(intervalA);
    tree.insert(intervalB);
    tree.insert(intervalC);
    tree.insert(intervalD);

    assertThat(tree.getRoot()).isNotNull();
    assertThat(tree.getRoot().getInterval()).isEqualTo(intervalB);
    assertThat(tree.getRoot().getColor()).isEqualTo(Color.BLACK);
    var left = tree.getRoot().getLeft();
    assertThat(left.getInterval()).isEqualTo(intervalA);
    assertThat(left.getColor()).isEqualTo(Color.BLACK);
    var right = tree.getRoot().getRight();
    assertThat(right.getInterval()).isEqualTo(intervalC);
    assertThat(right.getColor()).isEqualTo(Color.BLACK);

    assertThat(left.getRight().getInterval()).isEqualTo(intervalD);
    assertThat(left.getRight().getColor()).isEqualTo(Color.RED);
  }

  @Test
  void addingDandEdontRequireAction() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(1, 2, "A");
    var intervalB = fixture.createInterval(3, 4, "B");
    var intervalC = fixture.createInterval(5, 6, "C");
    var intervalD = fixture.createInterval(2, 3, "D");
    var intervalE = fixture.createInterval(0, 1, "E");

    tree.insert(intervalA);
    tree.insert(intervalB);
    tree.insert(intervalC);
    tree.insert(intervalD);
    tree.insert(intervalE);

    assertThat(tree.getRoot()).isNotNull();
    assertThat(tree.getRoot().getInterval()).isEqualTo(intervalB);
    assertThat(tree.getRoot().getColor()).isEqualTo(Color.BLACK);
    var left = tree.getRoot().getLeft();
    assertThat(left.getInterval()).isEqualTo(intervalA);
    assertThat(left.getColor()).isEqualTo(Color.BLACK);
    var right = tree.getRoot().getRight();
    assertThat(right.getInterval()).isEqualTo(intervalC);
    assertThat(right.getColor()).isEqualTo(Color.BLACK);

    assertThat(left.getRight().getInterval()).isEqualTo(intervalD);
    assertThat(left.getRight().getColor()).isEqualTo(Color.RED);
    assertThat(left.getLeft().getInterval()).isEqualTo(intervalE);
    assertThat(left.getLeft().getColor()).isEqualTo(Color.RED);
  }

  @Test
  void iteratorShouldKeepOrder() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(1, 2, "A");
    var intervalB = fixture.createInterval(3, 4, "B");
    var intervalC = fixture.createInterval(5, 6, "C");
    var intervalD = fixture.createInterval(2, 3, "D");
    var intervalE = fixture.createInterval(0, 1, "E");

    tree.insert(intervalA);
    tree.insert(intervalB);
    tree.insert(intervalC);
    tree.insert(intervalD);
    tree.insert(intervalE);

    var result = tree.getIntervals();

    assertThat(tree.getRoot()).isNotNull();
    assertThat(result).containsExactly(intervalE, intervalA, intervalD, intervalB, intervalC);
  }

  @Test
  void addInOrderOperation() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var n = 50;
    for (var i = 1; i < n; i++) {
      tree.insert(fixture.createInterval(i - 1, i, String.valueOf(i)));
    }
    assertThat(tree.getIntervals()).hasSize(n - 1);
  }

  @Test
  void searchRange() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var n = 20;
    var list = new ArrayList<SingleTimeInterval<String>>();
    for (var i = 1; i < n; i++) {
      var iv = fixture.createInterval(i - 1, i, String.valueOf(i));
      tree.insert(iv);
      list.add(iv);
    }
    assertThat(tree.getIntervals()).hasSize(n - 1);

    var searchRange = fixture.createInterval(2, 6, "");

    var result = tree.search(searchRange, (node) -> true);
    assertThat(result)
        .extracting(BtpTreeNode::getInterval)
        .containsExactly(list.get(2), list.get(3), list.get(4), list.get(5));
  }

  @Test
  void treeShouldBeEmptyAfterRemovingTheRoot() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(1, 2, "A");

    tree.insert(intervalA);

    tree.removeNode(tree.getRoot());

    assertThat(tree.isEmpty()).isTrue();
    assertThat(tree.getRoot()).isNull();
  }

  @Test
  void removeNodeWithTwoChildren() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(5, 6, "A");
    var intervalB = fixture.createInterval(1, 2, "B");
    var intervalC = fixture.createInterval(3, 4, "C");

    tree.insert(intervalA);
    tree.insert(intervalB);
    tree.insert(intervalC);

    tree.removeNode(tree.getRoot());

    assertThat(tree.isEmpty()).isFalse();
    assertThat(tree.getRoot()).extracting(BtpTreeNode::getInterval).isEqualTo(intervalA);
    assertThat(tree.getRoot().getLeft()).extracting(BtpTreeNode::getInterval).isEqualTo(intervalB);
  }

  @Test
  void removeLeafNode() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(5, 6, "A");
    var intervalB = fixture.createInterval(1, 2, "B");
    var intervalC = fixture.createInterval(3, 4, "C");

    tree.insert(intervalA);
    tree.insert(intervalB);
    tree.insert(intervalC);

    tree.removeNode(tree.getRoot().getLeft());

    assertThat(tree.isEmpty()).isFalse();
    assertThat(tree.getRoot()).extracting(BtpTreeNode::getInterval).isEqualTo(intervalC);
    assertThat(tree.getRoot().getRight()).extracting(BtpTreeNode::getInterval).isEqualTo(intervalA);
  }

  @Test
  void removeRedNode() {
    var tree = new BtpTree<String, SingleTimeInterval<String>>();

    var intervalA = fixture.createInterval(1, 2, "A");
    var intervalB = fixture.createInterval(3, 4, "B");
    var intervalC = fixture.createInterval(5, 6, "C");
    var intervalD = fixture.createInterval(2, 3, "D");
    var intervalE = fixture.createInterval(0, 1, "E");

    tree.insert(intervalA);
    tree.insert(intervalB);
    tree.insert(intervalC);
    tree.insert(intervalD);
    tree.insert(intervalE);

    tree.removeNode(tree.getRoot().getLeft().getRight());

    assertThat(tree.getRoot()).isNotNull();
    assertThat(tree.getIntervals()).containsExactly(intervalE, intervalA, intervalB, intervalC);
  }
}
