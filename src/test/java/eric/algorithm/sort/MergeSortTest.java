package eric.algorithm.sort;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.function.Consumer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class MergeSortTest {

  private double optimizedSortTime;
  private double topDownSortTime;

  private static int TRIAL = 3;
  private static int N = 1000000;

  @Test
  public void testOptimizedSort() {

    Double[] a = SortUtil.genRandomNumber(N);
    optimizedSortTime = testSort(MergeSort::optimizedSort, a);

  }

  @Test
  public void testTopDownSort() {

    Double[] a = SortUtil.genRandomNumber(N);
    topDownSortTime = testSort(MergeSort::topDownSort, a);

  }

  @Test
  public void testBottomUpSort() {

    Double[] a = SortUtil.genRandomNumber(N);
    testSort(MergeSort::bottomUpSort, a);

  }

  private <T> double testSort(Consumer<Comparable<T>[]> action, Comparable<T>[] a) {

    double[] time = new double[TRIAL];
    for (int i = 0; i < TRIAL; i++) {
      Stopwatch sw = new Stopwatch();
      action.accept(a);
      time[i] = sw.elapsedTime();
    }

    assertTrue(SortUtil.isSorted(a));

    return StdStats.mean(time);
  }

  /**
   * testTimeElapsed.
   */
  @AfterAll
  public void testTimeElapsed() {

    assertTrue(optimizedSortTime < topDownSortTime,
        String.format("optimizedSortTime(%f) should be less than topDownSortTime(%f)",
            optimizedSortTime, topDownSortTime));
  }

}
