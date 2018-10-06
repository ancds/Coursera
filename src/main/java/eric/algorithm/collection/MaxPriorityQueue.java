package eric.algorithm.collection;

import eric.algorithm.sort.SortUtil;

import java.util.Arrays;

public class MaxPriorityQueue<T extends Comparable<T>> {

  private T[] elems;
  private int size;

  /**
   * create a priority queue.
   */
  @SuppressWarnings("unchecked")
  public MaxPriorityQueue() {

    elems = (T[]) new Comparable[2];
    size = 0;

  }

  /**
   * create a priority queue from the keys in a[].
   * 
   * @param a keys array
   */
  @SuppressWarnings("unchecked")
  public MaxPriorityQueue(T[] a) {

    size = 0;
    elems = (T[]) new Comparable[a.length + 1];

    int j = 1;
    for (int i = 0; i < a.length; i++) {
      if (a[i] != null) {
        elems[j++] = a[i];
        size++;
      }
    }

    for (int k = size / 2; k > 0; k--) {
      sink(k);
    }

  }

  /**
   * insert a key into the priority queue.
   * 
   * @param v key
   */
  void insert(T v) {

    size++;
    if (size >= elems.length) {
      elems = resizeArray(elems, elems.length * 2);
    }

    elems[size] = v;
    swim(size);

  }

  /**
   * return the largest key.
   * 
   * @return the largest key
   */
  public T max() {

    return elems[1];
  }

  /**
   * return and remove the largest key.
   * 
   * @return the largest key
   */
  public T delMax() {

    T v;

    v = elems[1];
    SortUtil.exchange(elems, 1, size);

    elems[size] = null;
    size--;

    sink(1);

    if (size <= elems.length / 4 && size > 0) {
      elems = resizeArray(elems, elems.length / 2);
    }

    return v;
  }

  /**
   * is the priority queue empty.
   * 
   * @return is the priority queue empty?
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * number of keys in the priority queue.
   * 
   * @return number of keys in the priority queue
   */
  int size() {
    return size;
  }

  private int swim(int k) {

    while (k > 1) {

      int parent = k / 2;

      if (SortUtil.isLessThan(elems[parent], elems[k])) {
        SortUtil.exchange(elems, k, parent);
        k = parent;
      } else {
        break;
      }
    }

    return k;
  }

  private int sink(int k) {

    while (2 * k <= size) {

      int child = 2 * k;
      if (child < size && SortUtil.isLessThan(elems[child], elems[child + 1])) {
        child++;
      }

      if (SortUtil.isLessThan(elems[k], elems[child])) {
        SortUtil.exchange(elems, k, child);
        k = child;
      } else {
        break;
      }
    }

    return k;
  }

  private T[] resizeArray(T[] oldArray, int capacity) {

    return Arrays.copyOf(oldArray, capacity);

  }
}
