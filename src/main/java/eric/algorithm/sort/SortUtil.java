package eric.algorithm.sort;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Comparator;

public class SortUtil {

  /**
   * generate n random number [0,1).
   * 
   * @param n size
   * @return
   */
  public static Double[] genRandomNumber(int n) {

    Double[] numbers = new Double[n];
    for (int i = 0; i < numbers.length; i++) {
      numbers[i] = StdRandom.uniform();
    }

    return numbers;
  }

  @SuppressWarnings("unchecked")
  public static <T> boolean isLessThan(Comparable<T> a, Comparable<T> b) {
    return a.compareTo((T) b) < 0;
  }

  public static <T> boolean isLessThan(T a, T b, Comparator<T> comparator) {
    return comparator.compare(a, b) < 0;
  }

  /**
   * exchange two items.
   * 
   * @param a array
   * @param i index
   * @param j index
   */
  public static <T> void exchange(Object[] a, int i, int j) {
    Object temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }

  /**
   * indicate if a array is sorted.
   * 
   * @param a array
   * @return
   */
  public static <T> boolean isSorted(Comparable<T>[] a) {

    for (int i = 0; i < a.length - 1; i++) {

      if (isLessThan(a[i + 1], a[i])) {
        return false;
      }
    }
    return true;
  }

}
