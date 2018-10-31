package eric.algorithm.exercise.boggle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoggleSolver {

  private static final int RADIX = 26;
  private final int[] scoreTable;
  private final Node root;

  private static class Node {
    private final Node[] next = new Node[RADIX];
    private boolean isString;
  }

  /**
   * Initializes the data structure using the given array of strings as the
   * dictionary.
   */
  public BoggleSolver(String[] dictionaryWords) {

    root = new Node();
    for (String word : dictionaryWords) {

      Node node = root;
      for (int i = 0; i < word.length(); i++) {

        int index = charAt(word, i);
        if (node.next[index] == null) {
          node.next[index] = new Node();
        }

        node = node.next[index];
      }

      node.isString = true;
    }

    scoreTable = new int[] { 0, 0, 0, 1, 1, 2, 3, 5, 11 };

  }

  /**
   * Returns the set of all valid words in the given Boggle board, as an Iterable.
   */
  public Iterable<String> getAllValidWords(BoggleBoard board) {

    int row = board.rows();
    int col = board.cols();
    int size = row * col;

    char[] letters = new char[size];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {

        int index = i * col + j;
        letters[index] = board.getLetter(i, j);

      }
    }

    for (int i = 0; i < size; i++) {
      letters[i] = board.getLetter(i / col, i % col);
    }

    List<Integer>[] adj = constructAdj(row, col);
    boolean[] mark = new boolean[size];

    StringBuilder sb = new StringBuilder();
    Set<String> matched = new HashSet<>();
    for (int i = 0; i < size; i++) {

      depthFirstSearch(root.next[charCode(letters[i])], i, letters, adj, mark, sb, matched);

    }

    return matched;
  }

  private void depthFirstSearch(Node node, int current, char[] letters, List<Integer>[] adj,
      boolean[] mark, StringBuilder sb, Set<String> matched) {

    if (node == null) {
      return;
    }

    if (letters[current] == 'Q') {

      node = node.next[charCode('U')];
      if (node == null) {
        return;
      }
      sb.append("QU");

    } else {
      sb.append(letters[current]);
    }
    String str = sb.toString();

    if (str.length() > 2 && node.isString) {
      matched.add(str);
    }

    mark[current] = true;

    for (int adjacent : adj[current]) {
      if (!mark[adjacent]) {
        depthFirstSearch(node.next[charCode(letters[adjacent])], adjacent, letters, adj, mark, sb,
            matched);
      }
    }

    sb.deleteCharAt(sb.length() - 1);
    if (sb.length() > 0 && sb.charAt(sb.length() - 1) == 'Q') {
      sb.deleteCharAt(sb.length() - 1);
    }
    mark[current] = false;
  }

  private List<Integer>[] constructAdj(int row, int col) {

    int size = row * col;
    List<Integer>[] adj = new ArrayList[size];

    for (int i = 0; i < size; i++) {
      adj[i] = new ArrayList<Integer>(8);
    }

    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {

        int index = i * col + j;

        addAdjLetter(adj[index], i - 1, j - 1, row, col);
        addAdjLetter(adj[index], i - 1, j, row, col);
        addAdjLetter(adj[index], i - 1, j + 1, row, col);
        addAdjLetter(adj[index], i, j - 1, row, col);
        addAdjLetter(adj[index], i, j + 1, row, col);
        addAdjLetter(adj[index], i + 1, j - 1, row, col);
        addAdjLetter(adj[index], i + 1, j, row, col);
        addAdjLetter(adj[index], i + 1, j + 1, row, col);

      }
    }

    return adj;
  }

  private void addAdjLetter(List<Integer> list, int row, int col, int rowNum, int colNum) {

    if (row >= 0 && row < rowNum && col >= 0 && col < colNum) {
      list.add(row * colNum + col);
    }
  }

  /**
   * Returns the score of the given word if it is in the dictionary, zero
   * otherwise.
   */
  public int scoreOf(String word) {

    if (contains(word)) {
      int index = Math.min(word.length(), scoreTable.length - 1);
      return scoreTable[index];
    }
    return 0;
  }

  private boolean contains(String word) {

    Node node = root;
    for (int i = 0; i < word.length(); i++) {
      node = node.next[charAt(word, i)];
      if (node == null) {
        return false;
      }
    }
    return node.isString;
  }

  private int charAt(String str, int i) {
    return str.charAt(i) - 65;
  }

  private int charCode(char c) {
    return c - 65;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(args[1]);
    int score = 0;
    for (String word : solver.getAllValidWords(board)) {
      StdOut.println(word);
      score += solver.scoreOf(word);
    }
    StdOut.println("Score = " + score);
  }

}
