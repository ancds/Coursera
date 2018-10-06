package eric.algorithm.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * <p>a double ended queue.</p>
 * 
 * @author Eric Chou
 *
 */
public class Deque<T> implements Iterable<T> {

  private Node<T> first;
  private Node<T> last;
  private int size;

  @SuppressWarnings("hiding")
  private class Node<T> {
    T value;
    Node<T> prev;
    Node<T> next;
  }

  public Deque() {
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  /**
   * add item to the front.
   */
  public void addFirst(T item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    Node<T> node = new Node<>();
    node.value = item;
    Node<T> oldFirst = first;
    if (oldFirst != null) {
      node.next = oldFirst;
      oldFirst.prev = node;
    }

    first = node;
    if (last == null) {
      last = node;
    }

    size++;
  }

  /**
   * add item to the end.
   */
  public void addLast(T item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    Node<T> node = new Node<>();
    node.value = item;
    Node<T> oldLast = last;
    if (oldLast != null) {
      node.prev = oldLast;
      oldLast.next = node;
    }

    last = node;
    if (first == null) {
      first = node;
    }

    size++;
  }

  /**
   * remove and return the item from the front.
   */
  public T removeFirst() {

    if (first == null) {
      throw new NoSuchElementException();
    }

    Node<T> node = first;

    first = node.next;
    if (first != null) {
      first.prev = null;
    } else {
      last = null;
    }

    size--;
    return node.value;

  }

  /**
   * remove and return the item from the end.
   */
  public T removeLast() {

    if (first == null) {
      throw new NoSuchElementException();
    }

    Node<T> node = last;

    last = node.prev;
    if (last != null) {
      last.next = null;
    } else {
      first = null;
    }

    size--;
    return node.value;
  }

  /**
   * return an iterator over items in order from front to end.
   */
  public Iterator<T> iterator() {
    return new Iterator<T>() {

      private Node<T> current = first;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public T next() {

        if (!hasNext()) {
          throw new NoSuchElementException();
        }

        Node<T> node = current;
        current = current.next;
        return node.value;
      }

    };

  }

  public static void main(String[] args) {
    // unit testing (optional)
  }
}