package eric.algorithm.exercise.collinear;

/**
 * finds all line segments containing 4 points.
 */
public interface CollinearPoints {

  /**
   * the number of line segments.
   */
  int numberOfSegments();

  /**
   * the line segments.
   */
  public LineSegment[] segments();
}
