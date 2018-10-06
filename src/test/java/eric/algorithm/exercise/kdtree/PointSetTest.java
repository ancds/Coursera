package eric.algorithm.exercise.kdtree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import org.junit.jupiter.api.Test;

public class PointSetTest {

  /** testPointSet. */
  @Test
  public void testPointSet() {

    double[] pointX = { 0.0, 1.0, 0.54, 0.32, 0.72, 0.2, 0.8, 0.5, 0.0, 0.32 };
    double[] pointY = { 0.0, 0.1, 0.45, 0.92, 0.72, 0.8, 0.5, 0.5, 1.0, 0.64 };
    Point2D[] points = new Point2D[pointX.length];

    for (int i = 0; i < pointX.length; i++) {
      points[i] = new Point2D(pointX[i], pointY[i]);
    }

    PointSet pointSet = new PointSet();
    KdTree kdTree = new KdTree();

    for (Point2D p : points) {
      pointSet.insert(p);
      kdTree.insert(p);
      assertTrue(pointSet.contains(p));
      assertTrue(kdTree.contains(p));
    }

    assertTrue(pointSet.size() == 10);
    assertTrue(kdTree.size() == 10);

    double[] minX = { 0, 0, 0.5, 0.51, 0.49, 0.1 };
    double[] minY = { 0, 0, 0.45, 0.46, 0.44, 0.5 };
    double[] maxX = { 1, 0, 0.8, 0.79, 0.81, 0.4 };
    double[] maxY = { 1, 0, 0.72, 0.71, 0.73, 1.0 };
    int[] counts = { pointSet.size(), 1, 4, 0, 4, 3 };

    for (int i = 0; i < minX.length; i++) {
      int count = 0;
      RectHV rect1 = new RectHV(minX[i], minY[i], maxX[i], maxY[i]);
      for (Point2D p : pointSet.range(rect1)) {
        count++;
        assertTrue(rect1.contains(p));
      }
      assertTrue(count == counts[i]);

      count = 0;
      for (Point2D p : kdTree.range(rect1)) {
        count++;
        assertTrue(rect1.contains(p));
      }
      assertTrue(count == counts[i]);
    }

    double[] ptX = { 0.5, 0.1, 0.3, 0.8 };
    double[] ptY = { 0.5, 0.1, 0.9, 0.1 };
    double[] nearestX = { 0.5, 0, 0.32, 1.0 };
    double[] nearestY = { 0.5, 0, 0.92, 0.1 };

    for (int i = 0; i < ptX.length; i++) {
      Point2D p = new Point2D(ptX[i], ptY[i]);
      Point2D nearest = pointSet.nearest(p);
      assertEquals(nearest, kdTree.nearest(p));
      assertEquals(nearest.x(), nearestX[i]);
      assertEquals(nearest.y(), nearestY[i]);
    }

  }

}
