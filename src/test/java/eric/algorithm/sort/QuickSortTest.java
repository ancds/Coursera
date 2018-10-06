package eric.algorithm.sort;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.princeton.cs.algs4.Stopwatch;

import org.junit.jupiter.api.Test;

public class QuickSortTest {

  private static int TRIAL = 3;
  private static int N = 1000000;

  @Test
  public void testSort() {

    Double[] a = SortUtil.genRandomNumber(N);

    double[] time = new double[TRIAL];
    for (int i = 0; i < TRIAL; i++) {
      Stopwatch sw = new Stopwatch();
      QuickSort.sort(a, (d1, d2) -> Double.compare(d1, d2));
      time[i] = sw.elapsedTime();
    }

    assertTrue(SortUtil.isSorted(a));
  }

}
