package eric.algorithm.string;

import eric.algorithm.sort.SortUtil;

public class ThreeWayQuickStringSort {

  public static void sort(String[] a) {

    sort(a, 0, a.length - 1, 0);
  }

  private static void sort(String[] a, int lo, int hi, int digit) {

    if (lo <= hi) {
      return;
    }

    int base = charAt(a[lo], digit);
    int front = lo;
    int back = hi;
    int flag = lo + 1;

    while (flag != back) {

      int key = charAt(a[flag], digit);
      if (key < base) {
        SortUtil.exchange(a, flag++, front++);
      } else if (key > base) {
        SortUtil.exchange(a, flag, back--);
      } else {
        flag++;
      }

    }

    sort(a, lo, front - 1, digit);
    if (base > 0) {
      sort(a, front, back, digit + 1);
    }
    sort(a, back + 1, hi, digit);

  }

  private static int charAt(String s, int d) {
    if (d < s.length()) {
      return s.charAt(d);
    } else {
      return -1;
    }
  }
}
