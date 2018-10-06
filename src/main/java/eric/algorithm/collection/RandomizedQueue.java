package eric.algorithm.collection;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * <p>queue that supports randomly return item.</p>  
 * 
 * @author Eric Chou
 *
 */
public class RandomizedQueue<T> implements Iterable<T> {

  private T[] elems;
  private int size;
  private int pos;

  /**
   * constructor.
   */
  @SuppressWarnings("unchecked")
  public RandomizedQueue() {

    this.elems = (T[]) new Object[1];
    size = 0;
    pos = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  /**
   * enqueue.
   * 
   * @param item item
   */
  public void enqueue(T item) {

    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (pos == elems.length) {
      elems = resizeArray(size * 2);
      pos = size;
    }

    elems[pos] = item;
    size++;
    pos++;

  }

  /**
   * remove and return a random item.
   */
  public T dequeue() {

    T elem;

    if (size == 0) {
      throw new NoSuchElementException();
    }

    int random = randomAccess();
    elem = elems[random];
    elems[random] = null;
    size--;

    if (size < elems.length / 4) {
      elems = resizeArray(size * 2);
      pos = size;
    }

    return elem;
  }

  /**
   * return a random item (but do not remove it).
   * 
   * @return
   */
  public T sample() {

    if (size == 0) {
      throw new NoSuchElementException();
    }
    
    return elems[randomAccess()];
  }

  /**
   * return an independent iterator over items in random order.
   */
  public Iterator<T> iterator() {

    return new Iterator<T>() {

      private T[] elemsForIterator;
      private int sizeForIterator;

      {
        elemsForIterator = resizeArray(size);
        sizeForIterator = size;
      }

      @Override
      public boolean hasNext() {
        return sizeForIterator > 0;
      }

      @Override
      public T next() {

        if (!hasNext()) {
          throw new NoSuchElementException();
        }

        int random;
        do {
          random = StdRandom.uniform(elemsForIterator.length);
        } while (elemsForIterator[random] == null);

        T elem = elemsForIterator[random];
        elemsForIterator[random] = null;
        sizeForIterator--;

        return elem;
      }

    };

  }

  @SuppressWarnings("unchecked")
  private T[] resizeArray(int capacity) {

    if (capacity < 2) {
      capacity = 2;
    }

    T[] copy = (T[]) new Object[capacity];
    int copyIndex = 0;

    for (int i = 0; i < elems.length; i++) {
      if (elems[i] != null) {
        copy[copyIndex] = elems[i];
        copyIndex++;
      }
    }

    return copy;
  }

  private int randomAccess() {

    int random;
    do {
      random = StdRandom.uniform(pos);
    } while (elems[random] == null);

    return random;
  }

  public static void main(String[] args) {
    // unit testing (optional)
  }
}
