package eric.algorithm.exercise.percolation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PercolationTest {

  @Test
  public void boundaryCheckTest() {

    assertThrows(IllegalArgumentException.class, () -> {
      Percolation percolation = new Percolation(0);
    });

    Percolation percolation = new Percolation(10);
    assertThrows(IllegalArgumentException.class, () -> {
      percolation.isOpen(0, 1);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      percolation.isOpen(1, 11);
    });

    percolation.isOpen(1, 10);

  }

  @Test
  public void percolateTest() {

    Percolation percolation = new Percolation(3);

    assertFalse(percolation.isOpen(1, 1), "(1,1) Should not be opened at first");

    percolation.open(1, 1);
    assertAll("(1,1) site status", () -> assertTrue(percolation.isOpen(1, 1), "Should be opened"),
        () -> assertTrue(percolation.isFull(1, 1), "Should be full"),
        () -> assertFalse(percolation.percolates(), "Should not percolate"));

    percolation.open(2, 2);
    assertAll("(2,2) site status", () -> assertTrue(percolation.isOpen(2, 2), "Should be opened"),
        () -> assertFalse(percolation.isFull(2, 2), "Should not be full"),
        () -> assertFalse(percolation.percolates(), "Should not percolate"));

    percolation.open(2, 1);
    assertAll("(2,1) site status", () -> assertTrue(percolation.isOpen(2, 1), "Should be opened"),
        () -> assertTrue(percolation.isFull(2, 1), "Should be full"),
        () -> assertFalse(percolation.percolates(), "Should not percolate"));

    percolation.open(3, 3);
    assertAll("(3,3) site status", () -> assertTrue(percolation.isOpen(3, 3), "Should be opened"),
        () -> assertFalse(percolation.isFull(3, 3), "Should not be full"),
        () -> assertFalse(percolation.percolates(), "Should not percolate"));

    percolation.open(2, 3);
    assertAll("(2,3) site status", () -> assertTrue(percolation.isOpen(2, 3), "Should be opened"),
        () -> assertTrue(percolation.isFull(2, 3), "Should be full"),
        () -> assertTrue(percolation.percolates(), "Should percolate"));

  }

  /**
   * percolateTest.
   * 
   * @param n           size of grid
   * @param coordinates coordinates
   */
  @ParameterizedTest
  @MethodSource("percolateSizeAndCoordinatesProvider")
  public void percolateTestWithGivenData(int n, Queue<Integer> coordinates) {

    Percolation percolation = new Percolation(n);

    int row;
    int col;
    while (!coordinates.isEmpty()) {
      row = coordinates.poll();
      col = coordinates.poll();

      percolation.open(row, col);
    }

    assertTrue(percolation.percolates(), "Should percolate");

  }

  /**
   * notPercolateTestWithGivenData.
   * 
   * @param n           size of grid
   * @param coordinates coordinates
   */
  @ParameterizedTest
  @MethodSource("notPercolateSizeAndCoordinatesProvider")
  public void notPercolateTestWithGivenData(int n, Queue<Integer> coordinates) {

    Percolation percolation = new Percolation(n);

    int row;
    int col;
    while (!coordinates.isEmpty()) {
      row = coordinates.poll();
      col = coordinates.poll();

      percolation.open(row, col);
    }

    assertFalse(percolation.percolates(), "Should not percolate");

  }

  static Stream<Arguments> percolateSizeAndCoordinatesProvider() {
    return Stream.of(
        readSizeAndCoordinates("input1.txt"),
        readSizeAndCoordinates("input2.txt"),
        readSizeAndCoordinates("input5.txt"),
        readSizeAndCoordinates("input10.txt"));
  }

  static Stream<Arguments> notPercolateSizeAndCoordinatesProvider() {
    return Stream.of(
        readSizeAndCoordinates("input1-no.txt"),
        readSizeAndCoordinates("input2-no.txt"),
        readSizeAndCoordinates("input10-no.txt"));
  }

  static Arguments readSizeAndCoordinates(String fileName) {

    Arguments args;

    try (
        InputStream is = PercolationTest.class.getResourceAsStream("/data/percolation/" + fileName);
        Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.toString());) {

      int size = scanner.nextInt();
      Queue<Integer> queue = new LinkedList<Integer>();

      while (scanner.hasNextInt()) {
        queue.add(scanner.nextInt());
      }

      args = Arguments.of(size, queue);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return args;
  }

}
