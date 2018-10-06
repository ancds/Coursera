package eric.algorithm.exercise.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

public class PointSet {

  private final TreeSet<Point2D> tree;

  /**
   * set of points.
   */
  public PointSet() {

    tree = new TreeSet<>();

  }

  /**
   * is the set empty.
   * 
   * @return
   */
  public boolean isEmpty() {
    return tree.isEmpty();
  }

  /**
   * number of points in the set.
   * 
   * @return
   */
  public int size() {
    return tree.size();
  }

  /**
   * add the point to the set (if it is not already in the set).
   * 
   * @param p 2d point
   */
  public void insert(Point2D p) {

    if (p == null) {
      throw new IllegalArgumentException();
    }

    tree.add(p);

  }

  /**
   * does the set contain point p.
   * 
   * @param p 2d point
   * @return
   */
  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    return tree.contains(p);
  }

  /**
   * draw all points to standard draw.
   */
  public void draw() {

    for (Point2D p : tree) {
      p.draw();
    }

  }

  /**
   * all points that are inside the rectangle (or on the boundary).
   * 
   * @param rect rectangle
   * @return
   */
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }

    Queue<Point2D> set = new LinkedList<>();

    for (Point2D p : tree) {
      if (rect.contains(p)) {
        set.add(p);
      }
    }

    return set;
  }

  /**
   * a nearest neighbor in the set to point p; null if the set is empty.
   * 
   * @param p 2d point
   * @return
   */
  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    Point2D nearest = null;
    double minDistance = Double.POSITIVE_INFINITY;
    for (Point2D q : tree) {

      double distance = p.distanceSquaredTo(q);
      if (distance < minDistance) {
        nearest = q;
        minDistance = distance;
      }

    }

    return nearest;
  }

  public static void main(String[] args) {
    // unit testing of the methods (optional)
  }
}
