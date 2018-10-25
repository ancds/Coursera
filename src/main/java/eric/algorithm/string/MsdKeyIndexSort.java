package eric.algorithm.string;

import edu.princeton.cs.algs4.Insertion;

public class MsdKeyIndexSort {

  private static int R = 256; // radix
  private static final int M = 15; // cutoff for small subarrays
  private static String[] aux;

  public static void sort(String[] a) {
    aux = new String[a.length];
    sort(a, 0, a.length - 1, 0);
  }

  private static void sort(String[] a, int low, int high, int digit) {

    if (high <= low + M) {
      Insertion.sort(a, low, high,
          (o1, o2) -> ((String) o1).substring(digit).compareTo(((String) o2).substring(digit)));
      return;
    }

    int[] count = new int[R + 2];

    // Compute frequency.
    // for count[r],
    // r = 0: not used.
    // r = 1: number of String of length digit.
    // r = 2 to R + 1: char index with value r - 2.
    for (int i = low; i <= high; i++) {
      int index = charAt(a[i], digit);
      count[index + 1]++;
    }

    // Transform counts to indices.
    // for count[r],
    // start index of subarray for strings whose dth character value is r-1
    for (int i = 0; i < count.length - 1; i++) {
      count[i + 1] += count[i];
    }

    // Distribute.
    for (int i = low; i <= high; i++) {
      int index = charAt(a[i], digit);
      aux[count[index + 1] + low] = a[i];
      count[index + 1]++;
    }

    // Copy back.
    for (int i = low; i < high; i++) {
      a[i] = aux[i];
    }

    for (int i = 0; i < count.length; i++) {

      int l = low + count[i];
      int h = low + count[i + 1];

      sort(a, l, h, digit + 1);

    }

  }

  private static int charAt(String str, int i) {

    if (i >= str.length()) {
      return -1;
    }

    return str.charAt(i);
  }
}
