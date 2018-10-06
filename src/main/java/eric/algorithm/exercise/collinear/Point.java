package eric.algorithm.exercise.collinear;

import com.google.common.base.Objects;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

  private final int x;
  private final int y;

  /**
   * Initializes a new point.
   *
   * @param x the <em>x</em>-coordinate of the point
   * @param y the <em>y</em>-coordinate of the point
   */
  public Point(int x, int y) {
    /* DO NOT MODIFY */
    this.x = x;
    this.y = y;
  }

  /**
   * Draws this point to standard draw.
   */
  public void draw() {
    /* DO NOT MODIFY */
    StdDraw.point(x, y);
  }

  /**
   * Draws the line segment between this point and the specified point to standard
   * draw.
   *
   * @param that the other point
   */
  public void drawTo(Point that) {
    /* DO NOT MODIFY */
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  /**
   * Returns the slope between this point and the specified point. Formally, if
   * the two points are (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 -
   * x0). For completeness, the slope is defined to be +0.0 if the line segment
   * connecting the two points is horizontal; Double.POSITIVE_INFINITY if the line
   * segment is vertical; and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1)
   * are equal.
   *
   * @param that the other point
   * @return the slope between this point and the specified point
   */
  public double slopeTo(Point that) {

    if (that.x == this.x) {
      if (that.y == this.y) {
        return Double.NEGATIVE_INFINITY;
      }
      return Double.POSITIVE_INFINITY;
    }

    double slope = (double) (that.y - this.y) / (that.x - this.x);
    if (slope == -0) {
      slope = 0;
    }

    return slope;
  }

  /**
   * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally,
   * the invoking point (x0, y0) is less than the argument point (x1, y1) if and
   * only if either y0 < y1 or if y0 = y1 and x0 < x1.
   *
   * @param that the other point
   * @return the value <tt>0</tt> if this point is equal to the argument point (x0
   *         = x1 and y0 = y1); a negative integer if this point is less than the
   *         argument point; and a positive integer if this point is greater than
   *         the argument point
   */
  public int compareTo(Point that) {
    int thisKey;
    int thatKey;
    if (this.y != that.y) {
      thisKey = this.y;
      thatKey = that.y;
    } else {
      thisKey = this.x;
      thatKey = that.x;
    }

    if (thisKey > thatKey) {
      return 1;
    } else if (thisKey < thatKey) {
      return -1;
    } else {
      return 0;
    }
  }

  /**
   * Compares two points by the slope they make with this point. The slope is
   * defined as in the slopeTo() method.
   *
   * @return the Comparator that defines this ordering on points
   */
  public Comparator<Point> slopeOrder() {
    return new SlopeOrderComparator();
  }

  /**
   * Returns a string representation of this point. This method is provide for
   * debugging; your program should not rely on the format of the string
   * representation.
   *
   * @return a string representation of this point
   */
  public String toString() {
    /* DO NOT MODIFY */
    return "(" + x + ", " + y + ")";
  }

  /**
   * Unit tests the Point data type.
   */
  public static void main(String[] args) {
    /* YOUR CODE HERE */
  }

  private class SlopeOrderComparator implements Comparator<Point> {

    @Override
    public int compare(Point p1, Point p2) {

      double p1Slope = p1.slopeTo(Point.this);
      double p2Slope = p2.slopeTo(Point.this);

      return Double.compare(p1Slope, p2Slope);
    }

  }

  @Override
  public boolean equals(Object that) {

    if (that == null) {
      return false;
    }
    if (that == this) {
      return true;
    }
    if (!(that instanceof Point)) {
      return false;
    }

    return this.compareTo((Point) that) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.x, this.y);

  }
}
