package eric.algorithm.exercise.compress;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

  private static final int R = 256;

  /**
   * apply Burrows-Wheeler transform, reading from standard input and writing to
   * standard output.
   */
  public static void transform() {

    String s = BinaryStdIn.readString();
    CircularSuffixArray csa = new CircularSuffixArray(s);

    char[] chs = new char[s.length()];
    int first = -1;
    for (int i = 0; i < csa.length(); i++) {

      int index = csa.index(i);
      chs[i] = s.charAt((s.length() + index - 1) % s.length());

      if (index == 0) {
        first = i;
      }

    }

    BinaryStdOut.write(first);
    for (int i = 0; i < chs.length; i++) {
      BinaryStdOut.write(chs[i]);
    }

    BinaryStdIn.close();
    BinaryStdOut.close();

  }

  /**
   * apply Burrows-Wheeler inverse transform, reading from standard input and
   * writing to standard output.
   */
  public static void inverseTransform() {

    int first = BinaryStdIn.readInt();

    StringBuilder sb = new StringBuilder();
    while (!BinaryStdIn.isEmpty()) {
      sb.append(BinaryStdIn.readChar());
    }

    int len = sb.length();

    int[] count = new int[R + 1];
    char[] lastCol = new char[len];

    for (int i = 0; i < len; i++) {
      char ch = sb.charAt(i);
      lastCol[i] = ch;
      count[ch + 1]++;
    }

    for (int i = 1; i < R + 1; i++) {
      count[i] += count[i - 1];
    }

    int[] next = new int[len];
    for (int i = 0; i < len; i++) {

      char ch = lastCol[i];
      next[count[ch]] = i;
      count[ch]++;

    }

    int nextIndex = next[first];
    for (int i = 0; i < len; i++) {

      BinaryStdOut.write(lastCol[nextIndex]);
      nextIndex = next[nextIndex];

    }

    BinaryStdIn.close();
    BinaryStdOut.close();
  }

  /**
   * if args[0] is '-', apply Burrows-Wheeler transform. if args[0] is '+', apply
   * Burrows-Wheeler inverse transform.
   */
  public static void main(String[] args) {

    if ("-".equals(args[0])) {
      transform();
    }

    if ("+".equals(args[0])) {
      inverseTransform();
    }
  }

}
