package eric.algorithm.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class BinarySearchTreeTest {

  @Test
  public void testBinarySearchTree() {

    RedBlackBst<Integer, String> bst = new RedBlackBst<>();

    assertTrue(bst.isEmpty());

    String[] arr = "SEARCHEXAMPLE".split("");
    int[] keys = new int[arr.length];
    for (int i = 0; i < keys.length; i++) {
      keys[i] = i * 2;
    }

    StdRandom.shuffle(keys);

    for (int i = 0; i < arr.length; i++) {
      bst.put(keys[i], arr[i]);
    }

    assertEquals(bst.size(), arr.length);

    assertFalse(bst.isEmpty());

    assertTrue(bst.contains(arr.length - 1));
    assertFalse(bst.contains(arr.length));

    for (int i = 0; i < keys.length; i++) {

      assertEquals(bst.get(keys[i]), arr[i]);

      assertEquals(bst.rank(bst.select(i)), i);
      if (keys[i] <= bst.size()) {
        assertTrue(bst.select(bst.rank(keys[i])) == keys[i]);
      }

      if (keys[i] % 2 == 0) {
        assertTrue(bst.floor(keys[i]) == keys[i]);
        assertTrue(bst.ceiling(keys[i]) == keys[i]);
      } else {

        assertTrue(bst.floor(keys[i]) == keys[i] - 1);
        assertTrue(bst.ceiling(keys[i]) == keys[i] + 1);
      }

    }

    int maxKeyIndex = 0;
    int minKeyIndex = 0;
    for (int i = 0; i < keys.length; i++) {
      if (keys[i] > keys[maxKeyIndex]) {
        maxKeyIndex = i;
      }
      if (keys[i] < keys[minKeyIndex]) {
        minKeyIndex = i;
      }
    }

    assertTrue(bst.max() == keys[maxKeyIndex]);
    assertTrue(bst.min() == keys[minKeyIndex]);

    int[] keysCopy = Arrays.copyOf(keys, keys.length);
    Arrays.sort(keysCopy);

    Iterator<Integer> bstKeys = bst.keys().iterator();
    for (int i = 0; i < keysCopy.length; i++) {
      assertEquals(bstKeys.next(), (Integer) keysCopy[i]);
    }

    bst.deleteMax();
    bst.deleteMin();
    assertNull(bst.get(keys[minKeyIndex]));
    assertNull(bst.get(keys[maxKeyIndex]));

    assertEquals(bst.size(), keys.length - 2);

    for (int i = 0; i < arr.length; i++) {

      bst.delete(keys[i]);
      assertNull(bst.get(keys[i]));

    }
    assertTrue(bst.isEmpty());
    assertEquals(bst.size(), 0);

  }

}
