package eric.algorithm.exercise.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private static final double CONFIDENCE_95 = 1.96;
  private final double[] numberOfOpenSites;
  private Double mean;
  private Double stddev;

  /**
   * perform trials independent experiments on an n-by-n grid.
   * 
   * @param n      n
   * @param trials trials
   */
  public PercolationStats(int n, int trials) {

    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("n and trials are required to be > 0");
    }

    numberOfOpenSites = new double[trials];

    for (int i = 0; i < trials; i++) {

      Percolation percolation = new Percolation(n);

      while (!percolation.percolates()) {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);

        percolation.open(row, col);
      }

      double total = n * n;
      numberOfOpenSites[i] = percolation.numberOfOpenSites() / total;

    }
  }

  /**
   * sample mean of percolation threshold.
   * 
   * @return
   */
  public double mean() {

    if (mean == null) {
      mean = StdStats.mean(numberOfOpenSites);
    }
    return mean;

  }

  /**
   * sample standard deviation of percolation threshold.
   * 
   * @return
   */
  public double stddev() {

    if (stddev == null) {
      stddev = StdStats.stddev(numberOfOpenSites);
    }
    return stddev;
  }

  /**
   * low endpoint of 95% confidence interval.
   * 
   * @return
   */
  public double confidenceLo() {

    return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(numberOfOpenSites.length);
  }

  /**
   * // high endpoint of 95% confidence interval.
   * 
   * @return
   */
  public double confidenceHi() {

    return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(numberOfOpenSites.length);
  }

  /**
   * test client.
   * 
   * @param args n trials
   */
  public static void main(String[] args) {

    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);

    PercolationStats percolationStats = new PercolationStats(n, trials);

    StdOut.printf("mean                    = %f%n", percolationStats.mean());
    StdOut.printf("stddev                  = %f%n", percolationStats.stddev());
    StdOut.printf("95%% confidence interval = [%f, %f]%n", percolationStats.confidenceLo(),
        percolationStats.confidenceHi());

  }
}
