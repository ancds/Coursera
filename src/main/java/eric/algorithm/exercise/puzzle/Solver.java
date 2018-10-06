package eric.algorithm.exercise.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

  private final Board[] solution;
  private final boolean isSolvable;

  /**
   * find a solution to the initial board (using the A* algorithm).
   * 
   * @param initial board
   */
  public Solver(Board initial) {

    if (initial == null) {
      throw new IllegalArgumentException();
    }

    Comparator<SearchNode> byManhattan = (node1, node2) -> node1.manhattan() - node2.manhattan();

    MinPQ<SearchNode> queue = new MinPQ<>(byManhattan);
    MinPQ<SearchNode> twinQueue = new MinPQ<>(byManhattan);

    queue.insert(new SearchNode(initial, 0, null));
    twinQueue.insert(new SearchNode(initial.twin(), 0, null));

    ResizingArrayStack<SearchNode> process = new ResizingArrayStack<>();

    while (true) {

      SearchNode node = queue.delMin();

      process.push(node);

      boolean isGoal = node.board.isGoal();

      if (isGoal) {
        isSolvable = true;
        break;
      }

      SearchNode twinNode = twinQueue.delMin();
      if (twinNode.board.isGoal()) {
        isSolvable = false;
        break;
      }

      Iterable<Board> neighbors = node.board.neighbors();
      Iterable<Board> twinNeighbors = twinNode.board.neighbors();

      for (Board neighbor : neighbors) {
        if (!neighbor.equals(node.predecessor) || node.predecessor == null) {
          queue.insert(new SearchNode(neighbor, node.moves + 1, node.board));
        }
      }

      for (Board twinNeighbor : twinNeighbors) {
        if (!twinNeighbor.equals(twinNode.predecessor) || twinNode.predecessor == null) {
          twinQueue.insert(new SearchNode(twinNeighbor, twinNode.moves + 1, twinNode.board));
        }
      }

    }

    if (isSolvable) {

      solution = new Board[process.peek().moves + 1];
      SearchNode prev = null;

      for (int i = solution.length - 1; i >= 0; i--) {

        SearchNode current = process.pop();

        while (prev != null && current.board != prev.predecessor) {

          current = process.pop();

        }

        solution[i] = current.board;
        prev = current;
      }

    } else {
      solution = null;
    }

  }

  /**
   * is the initial board solvable?.
   * 
   * @return isSolvable
   */
  public boolean isSolvable() {
    return isSolvable;

  }

  /**
   * min number of moves to solve initial board; -1 if unsolvable.
   * 
   * @return min number of moves to solve initial board; -1 if unsolvable
   */
  public int moves() {
    if (!isSolvable()) {
      return -1;
    }
    return solution.length - 1;

  }

  /**
   * sequence of boards in a shortest solution; null if unsolvable.
   * 
   * @return sequence of boards in a shortest solution; null if unsolvable
   */
  public Iterable<Board> solution() {

    if (!isSolvable) {
      return null;
    }

    ResizingArrayQueue<Board> queue = new ResizingArrayQueue<>();
    for (int i = 0; i < solution.length; i++) {
      queue.enqueue(solution[i]);
    }

    return queue;
  }

  private static class SearchNode {

    private final Board board;
    private final Board predecessor;
    private final int moves;
    private final int manhattan;

    private SearchNode(Board board, int moves, Board predecessor) {

      this.board = board;
      this.predecessor = predecessor;
      this.moves = moves;
      this.manhattan = board.manhattan() + moves;

    }

    private int manhattan() {
      return manhattan;
    }

  }

  /**
   * solve a slider puzzle.
   * 
   * @param args args
   */
  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable()) {
      StdOut.println("No solution possible");
    } else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution()) {
        StdOut.println(board);
      }
    }

  }
}
