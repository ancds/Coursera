package eric.algorithm.exercise.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class KdTree {

  private Node root;

  /**
   * set of points.
   */
  public KdTree() {
  }

  /**
   * is the set empty.
   * 
   * @return
   */
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * number of points in the set.
   * 
   * @return
   */
  public int size() {

    if (isEmpty()) {
      return 0;
    }

    return root.size;
  }

  private int size(Node node) {
    if (node == null) {
      return 0;
    } else {
      return node.size;
    }
  }

  /**
   * add the point to the set (if it is not already in the set).
   * 
   * @param p 2d point
   */
  public void insert(Point2D p) {
    root = insert(root, p, null);
  }

  private Node insert(Node node, Point2D p, Comparator<Point2D> comparator) {

    if (p == null) {
      throw new IllegalArgumentException();
    }

    if (node == null) {
      return new Node(p);
    }

    comparator = alternateComparator(comparator);
    int cmp = compare(p, node.point, comparator);

    if (cmp < 0) {

      node.left = insert(node.left, p, comparator);

    } else if (cmp > 0) {

      node.right = insert(node.right, p, comparator);

    } else {

      return node;

    }

    node.size = 1 + size(node.left) + size(node.right);

    return node;

  }

  private Comparator<Point2D> alternateComparator(Comparator<Point2D> comparator) {
    if (comparator == Point2D.X_ORDER) {
      return Point2D.Y_ORDER;
    } else {
      return Point2D.X_ORDER;
    }
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

    return contains(root, p, null);
  }

  private boolean contains(Node node, Point2D p, Comparator<Point2D> comparator) {

    if (node == null) {
      return false;
    }

    comparator = alternateComparator(comparator);
    int cmp = compare(p, node.point, comparator);

    if (cmp < 0) {

      return contains(node.left, p, comparator);

    } else if (cmp > 0) {

      return contains(node.right, p, comparator);

    } else {

      return true;
    }

  }

  /**
   * draw all points to standard draw.
   */
  public void draw() {

    if (isEmpty()) {
      return;
    }

    draw(root, plane(), null);

  }

  private void draw(Node node, RectHV rect, Comparator<Point2D> comparator) {

    if (node == null) {
      return;
    }

    comparator = alternateComparator(comparator);

    double x = node.point.x();
    double y = node.point.y();

    RectHV leftRect = leftRect(x, y, rect, comparator);
    RectHV rightRect = rightRect(x, y, rect, comparator);

    if (comparator == Point2D.X_ORDER) {

      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(leftRect.xmax(), leftRect.ymin(), leftRect.xmax(), leftRect.ymax());

    } else {

      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(leftRect.xmin(), leftRect.ymax(), leftRect.xmax(), leftRect.ymax());

    }

    StdDraw.setPenColor(StdDraw.BLACK);
    node.point.draw();

    draw(node.left, leftRect, comparator);
    draw(node.right, rightRect, comparator);

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

    Queue<Point2D> list = new LinkedList<>();

    range(root, rect, null, list);

    return list;
  }

  private void range(Node node, RectHV rect, Comparator<Point2D> comparator, Queue<Point2D> list) {

    if (node == null) {
      return;
    }

    if (rect.contains(node.point)) {
      list.add(node.point);
    }

    comparator = alternateComparator(comparator);
    if (comparator == Point2D.X_ORDER) {

      double x = node.point.x();

      if (rect.xmin() <= x) {
        range(node.left, rect, comparator, list);
      }

      if (rect.xmax() >= x) {
        range(node.right, rect, comparator, list);
      }

    } else {

      double y = node.point.y();

      if (rect.ymin() <= y) {
        range(node.left, rect, comparator, list);
      }

      if (rect.ymax() >= y) {
        range(node.right, rect, comparator, list);
      }

    }

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

    if (isEmpty()) {
      return null;
    }

    Node nearest = nearest(root, p, null, Double.POSITIVE_INFINITY, plane());
    return nearest.point;
  }

  private Node nearest(Node node, Point2D p, Comparator<Point2D> comparator, double currentMin,
      RectHV rect) {

    if (node == null) {
      return null;
    }

    comparator = alternateComparator(comparator);
    int cmp = compare(p, node.point, comparator);
    if (cmp == 0) {
      return node;
    }

    Node closer = null;
    double min = currentMin;
    double distance = p.distanceSquaredTo(node.point);
    if (distance < min) {
      min = distance;
      closer = node;
    }

    double x = node.point.x();
    double y = node.point.y();

    RectHV leftRect = leftRect(x, y, rect, comparator);
    RectHV rightRect = rightRect(x, y, rect, comparator);

    Node firstNode;
    RectHV firstRect;

    Node secondNode;
    RectHV secondRect;

    if (cmp < 0) {

      firstNode = node.left;
      firstRect = leftRect;

      secondNode = node.right;
      secondRect = rightRect;

    } else {

      firstNode = node.right;
      firstRect = rightRect;

      secondNode = node.left;
      secondRect = leftRect;
    }

    Node firstFind = nearest(firstNode, p, comparator, min, firstRect);
    if (firstFind != null) {
      min = p.distanceSquaredTo(firstFind.point);
      closer = firstFind;
    }

    if (secondRect.distanceSquaredTo(p) < min) {
      Node secondFind = nearest(secondNode, p, comparator, min, secondRect);
      if (secondFind != null) {
        min = p.distanceSquaredTo(secondFind.point);
        closer = secondFind;
      }
    }

    return closer;

  }

  private RectHV leftRect(double x, double y, RectHV rect, Comparator<Point2D> comparator) {
    if (comparator == Point2D.X_ORDER) {

      return new RectHV(rect.xmin(), rect.ymin(), x, rect.ymax());

    } else {

      return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), y);

    }
  }

  private RectHV rightRect(double x, double y, RectHV rect, Comparator<Point2D> comparator) {
    if (comparator == Point2D.X_ORDER) {

      return new RectHV(x, rect.ymin(), rect.xmax(), rect.ymax());

    } else {

      return new RectHV(rect.xmin(), y, rect.xmax(), rect.ymax());

    }
  }

  private RectHV plane() {
    return new RectHV(0, 0, 1, 1);
  }

  private int compare(Point2D p, Point2D q, Comparator<Point2D> comparator) {
    int cmp = comparator.compare(p, q);
    if (cmp == 0) {
      cmp = p.compareTo(q);
    }
    return cmp;
  }

  public static void main(String[] args) {

  }

  private static class Node {

    private final Point2D point;
    private Node left;
    private Node right;
    private int size;

    private Node(Point2D point) {
      this.point = point;
      this.size = 1;
    }

  }

}
