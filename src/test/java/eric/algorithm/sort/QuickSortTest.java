package eric.algorithm.sort;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QuickSortTest {

  private static int TRIAL = 3;
  private static int N = 1000000;

  @Test
  public void testSort() {

    Double[] a = SortUtil.genRandomNumber(N);

    for (int i = 0; i < TRIAL; i++) {
      QuickSort.sort(a, (d1, d2) -> Double.compare(d1, d2));
    }

    assertTrue(SortUtil.isSorted(a));
  }

}
