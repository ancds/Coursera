package eric.algorithm.collection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class QueueTest {

  @Test
  public void dequeTest() {

    Deque<String> deque = new Deque<>();

    assertAll(
        () -> assertTrue(deque.isEmpty()),
        () -> assertEquals(deque.size(), 0));

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null)),
        () -> assertThrows(IllegalArgumentException.class, () -> deque.addLast(null)));

    deque.addFirst("two");

    deque.addFirst("toBeDequeue");
    deque.removeFirst();

    deque.addFirst("one");
    deque.addLast("three");

    deque.addLast("toBeDequeue");
    deque.removeLast();

    assertAll(
        () -> assertFalse(deque.isEmpty()),
        () -> assertEquals(deque.size(), 3));

    Iterator<String> iterator = deque.iterator();
    assertAll(
        () -> assertThrows(UnsupportedOperationException.class, () -> iterator.remove()),
        () -> assertTrue(iterator.hasNext()),
        () -> assertEquals(iterator.next(), "one"),
        () -> assertEquals(iterator.next(), "two"),
        () -> assertEquals(iterator.next(), "three"),
        () -> assertFalse(iterator.hasNext()),
        () -> assertThrows(NoSuchElementException.class, () -> iterator.next()));

    assertAll(
        () -> assertEquals(deque.removeFirst(), "one"),
        () -> assertEquals(deque.removeLast(), "three"),
        () -> assertEquals(deque.removeFirst(), "two"),
        () -> assertThrows(NoSuchElementException.class, () -> deque.removeFirst()),
        () -> assertThrows(NoSuchElementException.class, () -> deque.removeLast()));

  }

  /**
   * test.
   */
  @Test
  public void randomizedQueueTest() {

    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

    assertAll(
        () -> assertTrue(randomizedQueue.isEmpty()),
        () -> assertEquals(randomizedQueue.size(), 0));

    randomizedQueue.enqueue("toBeDequeue");
    randomizedQueue.dequeue();

    for (int i = 0; i < 10; i++) {
      randomizedQueue.enqueue("toBeDequeue");
    }
    for (int i = 0; i < 10; i++) {
      assertEquals(randomizedQueue.dequeue(), "toBeDequeue");
    }

    assertEquals(randomizedQueue.size(), 0);

    assertThrows(IllegalArgumentException.class, () -> randomizedQueue.enqueue(null));
    randomizedQueue.enqueue("one");
    randomizedQueue.enqueue("two");
    randomizedQueue.enqueue("three");

    assertAll(
        () -> assertFalse(randomizedQueue.isEmpty()),
        () -> assertEquals(randomizedQueue.size(), 3));

    Iterator<String> iterator = randomizedQueue.iterator();
    List<String> elemsInIterator = new ArrayList<>();
    assertAll(
        () -> assertThrows(UnsupportedOperationException.class, () -> iterator.remove()),
        () -> assertTrue(iterator.hasNext()),
        () -> assertTrue(elemsInIterator.add(iterator.next())),
        () -> assertTrue(elemsInIterator.add(iterator.next())),
        () -> assertTrue(elemsInIterator.add(iterator.next())),
        () -> assertTrue(elemsInIterator.contains("one")),
        () -> assertTrue(elemsInIterator.contains("two")),
        () -> assertTrue(elemsInIterator.contains("three")),
        () -> assertFalse(iterator.hasNext()),
        () -> assertThrows(NoSuchElementException.class, () -> iterator.next()));

    Set<String> elemsInQueueForSample = new HashSet<>();
    int loop = 0;
    while (elemsInQueueForSample.size() != randomizedQueue.size() || loop < 100) {
      elemsInQueueForSample.add(randomizedQueue.sample());
      loop++;
    }
    assertEquals(elemsInQueueForSample.size(), 3);

    Set<String> elemsInQueueForDequque = new HashSet<>();
    assertAll(
        () -> assertTrue(elemsInQueueForDequque.add(randomizedQueue.dequeue())),
        () -> assertTrue(elemsInQueueForDequque.add(randomizedQueue.dequeue())),
        () -> assertTrue(elemsInQueueForDequque.add(randomizedQueue.dequeue())),
        () -> assertThrows(NoSuchElementException.class, () -> randomizedQueue.dequeue()));

  }
}
