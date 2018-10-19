package eric.algorithm.exercise.wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class WordNet {

  private final Map<String, Set<Integer>> wordsMap;
  private final Map<Integer, String> verticesMap;
  private final SAP sap;

  /**
   * constructor takes the name of the two input files.
   */
  public WordNet(String synsets, String hypernyms) {

    if (synsets == null || hypernyms == null) {
      throw new IllegalArgumentException();
    }

    try (
        FileInputStream fis = new FileInputStream(synsets);
        BufferedInputStream bis = new BufferedInputStream(fis);
        Scanner scan = new Scanner(bis, "UTF-8")) {

      wordsMap = new HashMap<>();
      verticesMap = new HashMap<>();

      while (scan.hasNextLine()) {

        String[] line = scan.nextLine().split(",");

        int vertex = Integer.parseInt(line[0]);
        String synset = line[1];

        verticesMap.put(vertex, synset);

        for (String word : synset.split(" ")) {

          Set<Integer> vertices = wordsMap.get(word);
          if (vertices == null) {
            vertices = new HashSet<>();
            wordsMap.put(word, vertices);
          }

          vertices.add(vertex);

        }

      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try (
        FileInputStream fis = new FileInputStream(hypernyms);
        BufferedInputStream bis = new BufferedInputStream(fis);
        Scanner scan = new Scanner(bis, "UTF-8")) {

      Digraph dg = new Digraph(verticesMap.size());

      while (scan.hasNextLine()) {

        String[] line = scan.nextLine().split(",");

        int v = Integer.parseInt(line[0]);
        for (int i = 1; i < line.length; i++) {
          int w = Integer.parseInt(line[i]);
          dg.addEdge(v, w);
        }

      }

      DirectedCycle dc = new DirectedCycle(dg);

      // not DAG
      if (dc.hasCycle()) {
        throw new IllegalArgumentException();
      }

      int noOutdegreeVertices = 0;
      for (Integer v : verticesMap.keySet()) {

        if (dg.outdegree(v) < 1) {

          noOutdegreeVertices++;

          // the input to the constructor does not correspond to a rooted DAG.
          if (noOutdegreeVertices > 1) {
            throw new IllegalArgumentException();
          }

        }
      }

      sap = new SAP(dg);

    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  // returns all WordNet nouns
  public Iterable<String> nouns() {
    return wordsMap.keySet();
  }

  /**
   * is the word a WordNet noun.
   */
  public boolean isNoun(String word) {

    if (word == null) {
      throw new IllegalArgumentException();
    }

    return wordsMap.get(word) != null;
  }

  /**
   * distance between nounA and nounB.
   */
  public int distance(String nounA, String nounB) {

    Set<Integer> v = wordsMap.get(nounA);
    Set<Integer> w = wordsMap.get(nounB);

    if (v == null || w == null) {
      throw new IllegalArgumentException();
    }

    return sap.length(v, w);
  }

  /**
   * a synset that is the common ancestor of nounA and nounB in a shortest
   * ancestral path.
   */
  public String sap(String nounA, String nounB) {

    Set<Integer> v = wordsMap.get(nounA);
    Set<Integer> w = wordsMap.get(nounB);

    if (v == null || w == null) {
      throw new IllegalArgumentException();
    }

    int ancestor = sap.ancestor(v, w);

    return verticesMap.get(ancestor);
  }

  /**
   * do unit testing of this class.
   */
  public static void main(String[] args) {

  }
}