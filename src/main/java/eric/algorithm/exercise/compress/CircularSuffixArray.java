package eric.algorithm.exercise.compress;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {

  private final int[] index;

  /**
   * circular suffix array of s.
   */
  public CircularSuffixArray(String s) {

    if (s == null) {
      throw new IllegalArgumentException();
    }

    int len = s.length();

    index = new int[len];
    for (int i = 0; i < len; i++) {
      index[i] = i;
    }

    quick3way(s, index, 0, len - 1, 0);

  }

  private void quick3way(String s, int[] indexArray, int lo, int hi, int digit) {

    if (lo >= hi || digit >= s.length()) {
      return;
    }

    char val = s.charAt((indexArray[lo] + digit) % s.length());

    int lt = lo;
    int gt = hi;
    int i = lo + 1;

    while (i <= gt) {

      char current = s.charAt((indexArray[i] + digit) % s.length());
      if (current < val) {

        exchange(indexArray, i, lt);
        lt++;
        i++;

      } else if (current > val) {

        exchange(indexArray, i, gt);
        gt--;

      } else {
        i++;
      }

    }

    quick3way(s, indexArray, lo, lt - 1, digit);
    quick3way(s, indexArray, lt, gt, digit + 1);
    quick3way(s, indexArray, gt + 1, hi, digit);

  }

  private static void exchange(int[] a, int i, int j) {

    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;

  }

  /**
   * length of s.
   */
  public int length() {
    return index.length;
  }

  /**
   * returns index of ith sorted suffix.
   */
  public int index(int i) {

    if (i < 0 || i >= length()) {
      throw new IllegalArgumentException();
    }

    return index[i];
  }

  /**
   * unit testing.
   */
  public static void main(String[] args) {

    String s = "BABABBABBA";
    CircularSuffixArray csa = new CircularSuffixArray(s);

    for (int i = 0; i < csa.length(); i++) {
      StdOut.println(csa.index(i));
    }

  }

}
