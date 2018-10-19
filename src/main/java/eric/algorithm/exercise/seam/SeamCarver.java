package eric.algorithm.exercise.seam;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

  private int[][] rgb;
  private double[][] energy;

  /**
   * create a seam carver object based on the given picture.
   */
  public SeamCarver(Picture picture) {

    if (picture == null) {
      throw new IllegalArgumentException();
    }

    int w = picture.width();
    int h = picture.height();

    rgb = new int[w][h];
    energy = new double[w][h];

    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {

        rgb[x][y] = picture.getRGB(x, y);

      }
    }

    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {

        calEnergy(x, y);

      }
    }

  }

  /**
   * current picture.
   */
  public Picture picture() {

    int w = width();
    int h = height();

    Picture pic = new Picture(w, h);

    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {

        pic.setRGB(x, y, rgb[x][y]);

      }
    }

    return pic;
  }

  public int width() {
    return rgb.length;
  }

  public int height() {
    return rgb[0].length;
  }

  /**
   * energy of pixel at column x and row y.
   */
  public double energy(int x, int y) {

    checkXRange(x);
    checkYRange(y);

    return energy[x][y];
  }

  /**
   * sequence of indices for horizontal seam.
   */
  public int[] findHorizontalSeam() {

    int w = width();
    int h = height();

    double[][] dist = new double[w][h];
    int[][] lastPixel = new int[w][h];

    for (int x = 0; x < w; x++) {

      for (int y = 0; y < h; y++) {

        if (x == 0) {
          dist[x][y] = 1000;
        } else {
          dist[x][y] = Double.POSITIVE_INFINITY;
        }
      }
    }

    for (int x = 0; x < w - 1; x++) {
      for (int y = 1; y < h - 1; y++) {

        if (dist[x][y] + energy[x + 1][y + 1] < dist[x + 1][y + 1]) {
          dist[x + 1][y + 1] = dist[x][y] + energy[x + 1][y + 1];
          lastPixel[x + 1][y + 1] = y;
        }

        if (dist[x][y] + energy[x + 1][y] < dist[x + 1][y]) {
          dist[x + 1][y] = dist[x][y] + energy[x + 1][y];
          lastPixel[x + 1][y] = y;
        }

        if (dist[x][y] + energy[x + 1][y - 1] < dist[x + 1][y - 1]) {

          dist[x + 1][y - 1] = dist[x][y] + energy[x + 1][y - 1];
          lastPixel[x + 1][y - 1] = y;
        }

      }
    }

    int[] seam = new int[w];
    int min = 0;
    for (int y = 1; y < h; y++) {
      if (dist[w - 1][y] < dist[w - 1][min]) {
        min = y;
      }
    }

    for (int x = w - 1; x >= 0; x--) {

      seam[x] = min;
      min = lastPixel[x][min];
    }

    return seam;
  }

  /**
   * sequence of indices for vertical seam.
   */
  public int[] findVerticalSeam() {

    int w = width();
    int h = height();

    double[][] dist = new double[w][h];
    int[][] lastPixel = new int[w][h];

    for (int x = 0; x < w; x++) {

      for (int y = 0; y < h; y++) {

        if (y == 0) {
          dist[x][y] = 1000;
        } else {
          dist[x][y] = Double.POSITIVE_INFINITY;
        }
      }
    }

    for (int y = 0; y < h - 1; y++) {
      for (int x = 1; x < w - 1; x++) {

        if (dist[x][y] + energy[x + 1][y + 1] < dist[x + 1][y + 1]) {
          dist[x + 1][y + 1] = dist[x][y] + energy[x + 1][y + 1];
          lastPixel[x + 1][y + 1] = x;
        }

        if (dist[x][y] + energy[x][y + 1] < dist[x][y + 1]) {
          dist[x][y + 1] = dist[x][y] + energy[x][y + 1];
          lastPixel[x][y + 1] = x;
        }

        if (dist[x][y] + energy[x - 1][y + 1] < dist[x - 1][y + 1]) {

          dist[x - 1][y + 1] = dist[x][y] + energy[x - 1][y + 1];
          lastPixel[x - 1][y + 1] = x;
        }

      }
    }

    int[] seam = new int[h];
    int min = 0;
    for (int x = 1; x < w; x++) {
      if (dist[x][h - 1] < dist[min][h - 1]) {
        min = x;
      }
    }

    for (int y = h - 1; y >= 0; y--) {

      seam[y] = min;
      min = lastPixel[min][y];
    }

    return seam;
  }

  /**
   * remove horizontal seam from current picture.
   */
  public void removeHorizontalSeam(int[] seam) {

    if (height() <= 1) {
      throw new IllegalArgumentException();
    }

    checkSeam(seam, false);

    int w = width();
    int h = height() - 1;

    int[][] newRgb = new int[w][h];
    energy = new double[w][h];

    for (int x = 0; x < w; x++) {

      for (int y = 0; y < h; y++) {

        if (y < seam[x]) {

          newRgb[x][y] = rgb[x][y];

        } else {

          newRgb[x][y] = rgb[x][y + 1];

        }

      }

    }

    rgb = newRgb;

    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        calEnergy(x, y);
      }
    }

  }

  /**
   * remove vertical seam from current picture.
   */
  public void removeVerticalSeam(int[] seam) {

    if (width() <= 1) {
      throw new IllegalArgumentException();
    }

    checkSeam(seam, true);

    int w = width() - 1;
    int h = height();

    int[][] newRgb = new int[w][h];
    energy = new double[w][h];

    for (int y = 0; y < h; y++) {

      for (int x = 0; x < w; x++) {

        if (x < seam[y]) {

          newRgb[x][y] = rgb[x][y];

        } else {

          newRgb[x][y] = rgb[x + 1][y];

        }

      }

    }

    rgb = newRgb;

    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        calEnergy(x, y);
      }
    }

  }

  private void checkSeam(int[] seam, boolean isVertical) {

    if (seam == null) {
      throw new IllegalArgumentException();
    }

    if ((isVertical && seam.length != height())
        || (!isVertical && seam.length != width())) {

      throw new IllegalArgumentException();
    }

    for (int i = 0; i < seam.length; i++) {

      if (isVertical) {
        checkXRange(seam[i]);
      } else {
        checkYRange(seam[i]);
      }

      if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
        throw new IllegalArgumentException();
      }

    }

  }

  private void checkXRange(int x) {
    if (x < 0 || x > width() - 1) {
      throw new IllegalArgumentException();
    }
  }

  private void checkYRange(int y) {
    if (y < 0 || y > height() - 1) {
      throw new IllegalArgumentException();
    }
  }

  private void calEnergy(int x, int y) {

    if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
      energy[x][y] = 1000;
    } else {

      energy[x][y] = Math.sqrt(
          rbgDiffSqrt(rgb[x + 1][y], rgb[x - 1][y]) + rbgDiffSqrt(rgb[x][y + 1], rgb[x][y - 1]));

    }
  }

  private double rbgDiffSqrt(int rgb1, int rgb2) {

    int b1 = rgb1 & 0xFF;
    int g1 = (rgb1 >> 8) & 0xFF;
    int r1 = (rgb1 >> 16) & 0xFF;

    int b2 = rgb2 & 0xFF;
    int g2 = (rgb2 >> 8) & 0xFF;
    int r2 = (rgb2 >> 16) & 0xFF;

    return Math.pow(b1 - b2, 2) + Math.pow(g1 - g2, 2) + Math.pow(r1 - r2, 2);

  }

}
