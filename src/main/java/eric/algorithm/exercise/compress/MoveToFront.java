package eric.algorithm.exercise.compress;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

  private static final int R = 256;

  /**
   * apply move-to-front encoding, reading from standard input and writing to.
   */
  public static void encode() {

    char[] words = new char[R];
    for (int i = 0; i < R; i++) {
      words[i] = (char) i;
    }

    while (!BinaryStdIn.isEmpty()) {

      char ch = BinaryStdIn.readChar();
      for (int i = 0; i < R; i++) {

        if (ch == words[i]) {

          BinaryStdOut.write(i, 8);

          for (int j = i; j > 0; j--) {
            words[j] = words[j - 1];
          }
          words[0] = ch;

          break;
        }
      }

    }

    BinaryStdIn.close();
    BinaryStdOut.close();

  }

  /**
   * apply move-to-front decoding, reading from standard input and writing to.
   */
  public static void decode() {

    char[] words = new char[R];
    for (int i = 0; i < R; i++) {
      words[i] = (char) i;
    }

    while (!BinaryStdIn.isEmpty()) {

      int code = BinaryStdIn.readInt(8);
      char word = words[code];

      BinaryStdOut.write(word, 8);
      for (int j = code; j > 0; j--) {
        words[j] = words[j - 1];
      }

      words[0] = word;

    }

    BinaryStdIn.close();
    BinaryStdOut.close();

  }

  /**
   * if args[0] is '-', apply move-to-front encoding. if args[0] is '+', apply
   * move-to-front decoding.
   */
  public static void main(String[] args) {

    if ("-".equals(args[0])) {
      encode();
    }

    if ("+".equals(args[0])) {
      decode();
    }

  }
}
