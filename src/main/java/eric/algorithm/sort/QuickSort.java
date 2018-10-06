package eric.algorithm.sort;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class QuickSort {

  /**
   * quick sort with three-way partition.
   */
  public static <T> void sort(T[] a, Comparator<T> comparator) {

    // protect against worst case
    StdRandom.shuffle(a);
    sortWithThreeWayPartition(a, 0, a.length - 1, comparator);

  }

  @SuppressWarnings("unused")
  private static <T> void sort(T[] a, int low, int high, Comparator<T> comparator) {

    if (high - low < 4) {
      sortForSmallSize(a, low, high, comparator);
      return;
    }
    int i = partition(a, low, high, comparator);
    sort(a, low, i - 1, comparator);
    sort(a, i + 1, high, comparator);

  }

  private static <T> void sortWithThreeWayPartition(T[] a, int low, int high,
      Comparator<T> comparator) {

    if (high <= low) {
      return;
    }

    T v = a[low];
    int i = low + 1;
    int lt = low;
    int gt = high;

    while (i <= gt) {

      int cmp = comparator.compare(a[i], v);
      if (cmp > 0) {
        SortUtil.exchange(a, i, gt);
        gt--;
      } else if (cmp < 0) {
        SortUtil.exchange(a, i, lt);
        i++;
        lt++;
      } else {
        i++;
      }

    }

    sortWithThreeWayPartition(a, low, lt - 1, comparator);
    sortWithThreeWayPartition(a, gt + 1, high, comparator);
  }

  private static <T> int partition(T[] a, int low, int high, Comparator<T> comparator) {

    T v = a[low];
    int i = low;
    int j = high + 1;

    while (true) {

      // last a[i] >= v
      while (SortUtil.isLessThan(a[++i], v, comparator)) {
        if (i == high) {
          break;
        }
      }

      // last a[j] <= v
      while (SortUtil.isLessThan(v, a[--j], comparator)) {
        if (j == low) {
          break;
        }
      }

      if (i >= j) {
        break;
      }

      // now a[i] <= v, a[j] >= v, ready for next scan (start with i-- & j--)
      SortUtil.exchange(a, i, j);

    }

    SortUtil.exchange(a, low, j);

    return j;
  }

  private static <T> void sortForSmallSize(T[] a, int low, int high, Comparator<T> comparator) {

    // insertion sort
    for (int i = low; i <= high; i++) {
      for (int j = i; j > low; j--) {
        if (SortUtil.isLessThan(a[j], a[j - 1], comparator)) {
          SortUtil.exchange(a, j, j - 1);
        } else {
          break;
        }
      }
    }

  }
}
