package eric.algorithm.exercise.collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints implements CollinearPoints {

  private final LineSegment[] lineSegments;

  /**
   * finds all line segments containing 4 points.
   * 
   * @param points all points
   */
  public BruteCollinearPoints(Point[] points) {

    if (points == null) {
      throw new IllegalArgumentException("null");
    }

    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) {
        throw new IllegalArgumentException("null in array index: " + i);
      }
    }

    // defensive copy
    points = Arrays.copyOf(points, points.length);

    Arrays.sort(points);
    for (int i = 0; i < points.length - 1; i++) {
      if (points[i].compareTo(points[i + 1]) == 0) {
        throw new IllegalArgumentException("repeated points exists");
      }
    }

    LinkedQueue<LineSegment> queue = new LinkedQueue<>();

    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        for (int k = j + 1; k < points.length; k++) {
          for (int z = k + 1; z < points.length; z++) {

            double slopeToJ = points[i].slopeTo(points[j]);
            if (slopeToJ == points[i].slopeTo(points[k])
                && slopeToJ == points[i].slopeTo(points[z])) {

              queue.enqueue(new LineSegment(points[i], points[z]));

            }

          }
        }
      }
    }

    lineSegments = new LineSegment[queue.size()];
    for (int i = 0; i < lineSegments.length; i++) {
      lineSegments[i] = queue.dequeue();
    }

  }

  /**
   * the number of line segments.
   * 
   * @return
   */
  public int numberOfSegments() {
    return lineSegments.length;
  }

  /**
   * the line segments.
   * 
   * @return
   */
  public LineSegment[] segments() {

    return Arrays.copyOf(lineSegments, lineSegments.length);
  }

  /**
   * test.
   * 
   * @param args size point1X point1Y point2X point2Y...
   */
  public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
