import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Outcast {

  private final WordNet wordNet;

  /**
   * constructor takes a WordNet object.
   */
  public Outcast(WordNet wordNet) {

    this.wordNet = wordNet;

  }

  /**
   * given an array of WordNet nouns, return an outcast.
   */
  public String outcast(String[] nouns) {

    int[] distances = new int[nouns.length];

    for (int i = 0; i < nouns.length; i++) {
      for (int j = 0; j < nouns.length; j++) {
        distances[i] += wordNet.distance(nouns[i], nouns[j]);
      }
    }

    int max = 0;
    for (int i = 1; i < distances.length; i++) {
      if (distances[i] > distances[max]) {
        max = i;
      }
    }

    return nouns[max];
  }

  /**
   * test.
   */
  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}
