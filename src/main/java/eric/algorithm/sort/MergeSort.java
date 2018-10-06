package eric.algorithm.sort;

import java.util.Arrays;

public class MergeSort {

  /**
   * merge sort with following optimized techniques:
   * 1. insertion sort for small size
   * 2. interchange roles of target array and auxiliary array to eliminate the copy step
   * 
   */
  public static <T> void optimizedSort(Comparable<T>[] a) {

    Comparable<T>[] aux = Arrays.copyOf(a, a.length);

    optimizedSort(a, aux, 0, a.length - 1);
  }

  private static <T> void optimizedSort(Comparable<T>[] a, Comparable<T>[] aux, int low, int high) {

    if (high == low) {
      return;
    }

    int mid = (high + low) / 2;

    if (high - low < 16) {
      sortForSmallSize(a, low, high);
      return;
    }

    optimizedSort(aux, a, low, mid);
    optimizedSort(aux, a, mid + 1, high);

    // this can not be used together with optimizedMerge since array roles change
    // if (SortUtil.isLessThan(a[mid], a[mid + 1])) {
    // return;
    // }

    optimizedMerge(a, aux, low, mid, high);

  }

  /**
   * top-down merge sort without optimized.
   * 
   * @param a to be sorted
   */
  public static <T> void topDownSort(Comparable<T>[] a) {

    @SuppressWarnings("unchecked")
    Comparable<T>[] aux = new Comparable[a.length];
    topDownSort(a, aux, 0, a.length - 1);

  }

  private static <T> void topDownSort(Comparable<T>[] a, Comparable<T>[] aux, int low, int high) {

    if (high == low) {
      return;
    }

    int mid = (high + low) / 2;

    topDownSort(a, aux, low, mid);
    topDownSort(a, aux, mid + 1, high);
    merge(a, aux, low, mid, high);
  }

  /**
   * bottom-up merge sort without optimized.
   * 
   * @param a to be sorted
   */
  public static <T> void bottomUpSort(Comparable<T>[] a) {

    @SuppressWarnings("unchecked")
    Comparable<T>[] aux = new Comparable[a.length];

    for (int size = 1; size < a.length; size *= 2) {
      for (int low = 0; low < a.length - size; low += 2 * size) {
        int mid = low + size - 1;
        int high = Math.min(low + 2 * size - 1, a.length - 1);
        merge(a, aux, low, mid, high);
      }
    }

  }

  private static <T> void merge(Comparable<T>[] a, Comparable<T>[] aux, int low, int mid,
      int high) {

    for (int k = low; k <= high; k++) {
      aux[k] = a[k];
    }

    int i = low;
    int j = mid + 1;

    for (int k = low; k <= high; k++) {

      if (i > mid) {
        a[k] = aux[j++];
      } else if (j > high) {
        a[k] = aux[i++];
      } else if (SortUtil.isLessThan(aux[j], aux[i])) {
        a[k] = aux[j++];
      } else {
        a[k] = aux[i++];
      }

    }

  }

  private static <T> void optimizedMerge(Comparable<T>[] a, Comparable<T>[] aux, int low, int mid,
      int high) {

    int i = low;
    int j = mid + 1;

    for (int k = low; k <= high; k++) {

      if (i > mid) {
        a[k] = aux[j++];
      } else if (j > high) {
        a[k] = aux[i++];
      } else if (SortUtil.isLessThan(aux[j], aux[i])) {
        a[k] = aux[j++];
      } else {
        a[k] = aux[i++];
      }

    }

  }

  private static <T> void sortForSmallSize(Comparable<T>[] a, int low, int high) {

    // insertion sort
    for (int i = low; i <= high; i++) {
      for (int j = i; j > low; j--) {
        if (SortUtil.isLessThan(a[j], a[j - 1])) {
          SortUtil.exchange(a, j, j - 1);
        } else {
          break;
        }
      }
    }

  }
}
