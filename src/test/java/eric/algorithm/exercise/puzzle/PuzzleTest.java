package eric.algorithm.exercise.puzzle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.princeton.cs.algs4.In;

import java.net.URL;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PuzzleTest {

  @Test
  public void testBoard() {

    int[][] blocks = { { 1, 2, 3 }, { 6, 0, 8 }, { 5, 7, 4 } };

    Board board = new Board(blocks);
    blocks[0] = null;

    assertEquals(board.dimension(), 3);
    assertEquals(board.hamming(), 5);
    assertEquals(board.manhattan(), 10);
    assertFalse(board.isGoal());

    Iterator<Board> iterable = board.neighbors().iterator();
    int iterableSize = 0;
    while (iterable.hasNext()) {

      iterable.next();
      iterableSize++;

    }
    assertEquals(iterableSize, 4);

    blocks = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
    board = new Board(blocks);
    assertEquals(board.dimension(), 3);
    assertEquals(board.hamming(), 0);
    assertEquals(board.manhattan(), 0);
    assertTrue(board.isGoal());

    Board same = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
    Board diff = new Board(new int[][] { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 8, 5 } });
    assertTrue(board.equals(same));
    assertFalse(board.equals(diff));
  }

  /**
   * testSolver.
   */
  @ParameterizedTest
  @MethodSource("blocksProvider")
  public void testSolver(int[][] blocks, int moves) {

    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    assertEquals(moves, solver.moves());

    if (moves == -1) {
      assertFalse(solver.isSolvable());
    }

  }

  static Stream<Arguments> blocksProvider() {

    int[][] blocks1 = readBlocks("puzzle04.txt");
    int numberShouldBe1 = 4;

    int[][] blocks2 = readBlocks("puzzle07.txt");
    int numberShouldBe2 = 7;

    int[][] blocks3 = readBlocks("puzzle11.txt");
    int numberShouldBe3 = 11;

    int[][] blocks4 = readBlocks("puzzle3x3-unsolvable.txt");
    int numberShouldBe4 = -1;

    return Stream.of(
        Arguments.of(blocks1, numberShouldBe1),
        Arguments.of(blocks2, numberShouldBe2),
        Arguments.of(blocks3, numberShouldBe3),
        Arguments.of(blocks4, numberShouldBe4));
  }

  static int[][] readBlocks(String fileName) {

    URL url = PuzzleTest.class.getResource("/data/puzzle/" + fileName);

    In in = new In(url);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = in.readInt();
      }
    }

    return blocks;

  }
}
