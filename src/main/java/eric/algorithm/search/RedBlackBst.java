package eric.algorithm.search;

import java.util.ArrayDeque;
import java.util.Queue;

public class RedBlackBst<K extends Comparable<K>, V> {

  protected static final boolean RED = true;
  protected static final boolean BLACK = false;

  protected Node<K, V> root;

  /**
   * put key and value.
   */
  public void put(K key, V value) {

    if (key == null) {
      throw new IllegalArgumentException();
    } else if (value == null) {
      delete(key);
    } else {

      root = put(root, key, value);
      root.color = BLACK;
    }
  }

  protected Node<K, V> put(Node<K, V> node, K key, V value) {

    if (node == null) {
      node = new Node<K, V>(key, value);
      node.color = RED;
      return node;
    }

    int cmp = key.compareTo(node.key);
    if (cmp < 0) {
      node.left = put(node.left, key, value);
    } else if (cmp > 0) {
      node.right = put(node.right, key, value);
    } else {
      node.value = value;
    }

    if (isRed(node.right) && !isRed(node.left)) {
      node = rotateLeft(node);
    }
    if (isRed(node.left) && isRed(node.left.left)) {
      node = rotateRight(node);
    }
    if (isRed(node.left) && isRed(node.right)) {
      flipColors(node);
    }

    node.size = 1 + size(node.left) + size(node.right);
    return node;

  }

  protected Node<K, V> rotateLeft(Node<K, V> origin) {

    Node<K, V> originRightChild = origin.right;
    origin.right = originRightChild.left;
    originRightChild.left = origin;

    originRightChild.color = origin.color;
    origin.color = RED;

    originRightChild.size = origin.size;
    origin.size = 1 + size(origin.left) + size(origin.right);

    return originRightChild;

  }

  protected Node<K, V> rotateRight(Node<K, V> origin) {

    Node<K, V> originLeftChild = origin.left;
    origin.left = originLeftChild.right;
    originLeftChild.right = origin;

    originLeftChild.color = origin.color;
    origin.color = RED;

    originLeftChild.size = origin.size;
    origin.size = 1 + size(origin.left) + size(origin.right);

    return originLeftChild;

  }

  protected boolean isRed(Node<K, V> node) {
    if (node == null) {
      return false;
    }
    return node.color == RED;
  }

  protected void flipColors(Node<K, V> node) {

    node.color = RED;
    node.left.color = BLACK;
    node.right.color = BLACK;
  }

  /**
   * get value by key.
   */
  public V get(K key) {

    if (key == null) {
      throw new IllegalArgumentException();
    }
    return get(root, key);
  }

  protected V get(Node<K, V> node, K key) {

    if (node == null) {
      return null;
    }

    int cmp = key.compareTo(node.key);
    if (cmp < 0) {
      return get(node.left, key);
    }

    if (cmp > 0) {
      return get(node.right, key);
    }

    return node.value;

  }

  /**
   * delete by key.
   */
  //TODO should be revised for red-black tree
  public void delete(K key) {

    if (key == null) {
      throw new IllegalArgumentException();
    }

    if (root != null) {
      root = delete(root, key);
    }

  }

  //TODO should be revised for red-black tree
  protected Node<K, V> delete(Node<K, V> node, K key) {

    if (node == null) {
      return null;
    }

    int cmp = key.compareTo(node.key);
    if (cmp < 0) {

      node.left = delete(node.left, key);

    } else if (cmp > 0) {

      node.right = delete(node.right, key);

    } else {

      if (node.left == null) {
        return node.right;

      } else if (node.right == null) {
        return node.left;
      } else {

        Node<K, V> toBeRemoved = node;
        node = min(toBeRemoved.right);
        node.right = deleteMin(toBeRemoved.right);
        node.left = toBeRemoved.left;

      }

    }
    node.size = 1 + size(node.left) + size(node.right);
    return node;
  }

  //TODO should be revised for red-black tree
  public void deleteMax() {
    root = deleteMax(root);
  }

  //TODO should be revised for red-black tree
  protected Node<K, V> deleteMax(Node<K, V> node) {

    if (node == null) {
      return null;
    }

    if (node.right == null) {
      return node.left;
    }

    node.right = deleteMax(node.right);
    node.size = 1 + size(node.left) + size(node.right);
    return node;

  }

  public void deleteMin() {
    root = deleteMin(root);
  }

  protected Node<K, V> deleteMin(Node<K, V> node) {

    if (node == null) {
      return null;
    }

    if (node.left == null) {
      return node.right;
    }

    node.left = deleteMin(node.left);
    node.size = 1 + size(node.left) + size(node.right);
    return node;

  }

  public boolean contains(K key) {
    return get(key) != null;
  }

  public int size() {
    return size(root);
  }

  protected int size(Node<K, V> node) {
    if (node == null) {
      return 0;
    }

    return node.size;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * smallest key.
   */
  public K min() {

    if (root == null) {
      return null;
    }
    Node<K, V> node = min(root);
    return node.key;
  }

  protected Node<K, V> min(Node<K, V> node) {

    if (node == null) {
      return null;
    }

    if (node.left != null) {
      return min(node.left);
    }

    return node;
  }

  /**
   * largest key.
   */
  public K max() {

    if (root == null) {
      return null;
    }
    Node<K, V> node = max(root);
    return node.key;
  }

  protected Node<K, V> max(Node<K, V> node) {

    if (node == null) {
      return null;
    }

    if (node.right != null) {
      return max(node.right);
    }

    return node;
  }

  /**
   * number of keys less than the key. key = select(rank(key)) for existing keys;
   * rank from 0 to size - 1
   */
  public int rank(K key) {

    if (key == null) {
      throw new IllegalArgumentException();
    }

    return rank(root, key);
  }

  protected int rank(Node<K, V> node, K key) {

    if (node == null) {
      return 0;
    }
    int cmp = key.compareTo(node.key);
    if (cmp < 0) {
      return rank(node.left, key);
    }

    if (cmp > 0) {

      return 1 + size(node.left) + rank(node.right, key);
    }

    return size(node.left);

  }

  /**
   * key of rank i. i = rank(select(i)) rank from 0 to size - 1
   */
  public K select(int k) {
    if (k < 0 || k > size()) {
      throw new IllegalArgumentException();
    }

    if (root == null) {
      return null;
    }
    return select(root, k).key;

  }

  protected Node<K, V> select(Node<K, V> node, int k) {

    if (node == null) {
      return null;
    }

    int leftSize = size(node.left);
    if (k < leftSize) {
      return select(node.left, k);
    }
    if (k > leftSize) {
      return select(node.right, k - leftSize - 1);
    }

    return node;

  }

  /**
   * the largest key which is less than or equals to the key.
   */
  public K floor(K key) {

    if (key == null) {
      throw new IllegalArgumentException();
    }

    if (root == null) {
      return null;
    }
    return floor(root, key).key;

  }

  protected Node<K, V> floor(Node<K, V> node, K key) {

    if (node == null) {
      return null;
    }

    int cmp = key.compareTo(node.key);
    if (cmp < 0) {
      return floor(node.left, key);
    } else if (cmp > 0) {
      Node<K, V> rightFloor = floor(node.right, key);
      if (rightFloor == null) {
        return node;
      } else {
        return rightFloor;
      }
    } else {
      return node;
    }

  }

  /**
   * smallest key which is greater than or equal to the key.
   */
  public K ceiling(K key) {

    if (key == null) {
      throw new IllegalArgumentException();
    }

    if (root == null) {
      return null;
    }
    return ceiling(root, key).key;
  }

  protected Node<K, V> ceiling(Node<K, V> node, K key) {

    if (node == null) {
      return null;
    }

    int cmp = key.compareTo(node.key);
    if (cmp < 0) {
      Node<K, V> leftCeiling = ceiling(node.left, key);
      if (leftCeiling == null) {
        return node;
      } else {
        return leftCeiling;
      }
    } else if (cmp > 0) {
      return ceiling(node.right, key);
    } else {
      return node;
    }

  }

  /**
   * all contained keys in order.
   */
  public Iterable<K> keys() {

    return keys(min(), max());
  }

  /**
   * keys in [lo, hi] in order.
   */
  public Iterable<K> keys(K lo, K hi) {

    if (lo == null || hi == null || lo.compareTo(hi) > 0) {
      throw new IllegalArgumentException();
    }

    Queue<K> queue = new ArrayDeque<>();
    inPlaceTraverse(queue, root, lo, hi);

    return queue;
  }

  protected Queue<K> inPlaceTraverse(Queue<K> queue, Node<K, V> node, K lo, K hi) {

    if (node == null) {
      return queue;
    }

    int cmpLo = lo.compareTo(node.key);
    int cmpHi = hi.compareTo(node.key);
    if (cmpLo < 0) {
      inPlaceTraverse(queue, node.left, lo, hi);
    }
    if (cmpLo <= 0 && cmpHi >= 0) {
      queue.add(node.key);
    }
    if (cmpHi > 0) {
      inPlaceTraverse(queue, node.right, lo, hi);
    }

    return queue;
  }

  protected static class Node<K extends Comparable<K>, V> {

    protected final K key;
    protected V value;
    protected Node<K, V> left;
    protected Node<K, V> right;
    protected int size;
    protected boolean color;

    protected Node(K key, V value) {

      this.key = key;
      this.value = value;
      this.size = 1;
    }
  }

}
