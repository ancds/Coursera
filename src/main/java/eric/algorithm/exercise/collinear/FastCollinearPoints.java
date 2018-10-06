package eric.algorithm.exercise.collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints implements CollinearPoints {

  private final LineSegment[] lineSegments;

  /**
   * finds all line segments containing 4 or more points.
   * 
   * @param points points
   */
  public FastCollinearPoints(Point[] points) {

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
    Point[] aux = Arrays.copyOf(points, points.length);

    for (int i = 0; i < points.length - 3; i++) {

      // keep segment order as original, let us find segment from small to big
      for (int k = i; k < points.length; k++) {
        aux[k] = points[k];
      }

      Arrays.sort(aux, i, aux.length, points[i].slopeOrder());

      int adjacent = 1;

      // aux[0] should be equal to points[i]
      for (int j = i; j < aux.length; j++) {

        if (j != aux.length - 1 && aux[j].slopeTo(aux[i]) == aux[j + 1].slopeTo(aux[i])) {
          adjacent++;
        } else {

          if (adjacent >= 3) {

            boolean newSegment = true;
            double currentSlope = aux[j].slopeTo(aux[i]);
            for (int k = 0; k < i; k++) {
              if (currentSlope == aux[k].slopeTo(aux[i])) {
                newSegment = false;
              }
            }

            if (newSegment) {
              queue.enqueue(new LineSegment(aux[i], aux[j]));

            }
          }

          adjacent = 1;
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
