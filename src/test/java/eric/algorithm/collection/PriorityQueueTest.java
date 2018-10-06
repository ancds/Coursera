package eric.algorithm.collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.princeton.cs.algs4.StdRandom;

import eric.algorithm.sort.SortUtil;

import org.junit.jupiter.api.Test;

public class PriorityQueueTest {

  private static final int N = 100;

  @Test
  public void testMaxPriorityQueue() {

    MaxPriorityQueue<Double> queue = new MaxPriorityQueue<>();

    // test array resize
    for (int i = 0; i < N; i++) {

      while (!queue.isEmpty() && StdRandom.bernoulli()) {
        queue.delMax();
      }

      queue.insert(StdRandom.uniform());
    }

    while (!queue.isEmpty()) {
      queue.delMax();
    }

    for (int i = 0; i < N; i++) {
      queue.insert(StdRandom.uniform());

    }

    assertTrue(queue.size() == N);

    Double[] outputArray = new Double[N];
    int j = N - 1;
    while (!queue.isEmpty()) {
      outputArray[j--] = queue.delMax();
    }

    assertTrue(SortUtil.isSorted(outputArray));

    StdRandom.shuffle(outputArray);
    queue = new MaxPriorityQueue<>(outputArray);

    assertTrue(!SortUtil.isSorted(outputArray));

    j = N - 1;
    while (!queue.isEmpty()) {
      outputArray[j--] = queue.delMax();
    }

    assertTrue(SortUtil.isSorted(outputArray));

  }

}
