package eric.algorithm.exercise.collinear;

public class LineSegment {

  private final Point pointP;
  private final Point pointQ;

  /**
   * Initializes a new line segment.
   *
   * @param p one endpoint
   * @param q the other endpoint
   * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt> is
   *                              <tt>null</tt>
   */
  public LineSegment(Point p, Point q) {
    if (p == null || q == null) {
      throw new NullPointerException("argument is null");
    }
    this.pointP = p;
    this.pointQ = q;
  }

  /**
   * Draws this line segment to standard draw.
   */
  public void draw() {
    pointP.drawTo(pointQ);
  }

  /**
   * Returns a string representation of this line segment This method is provide
   * for debugging; your program should not rely on the format of the string
   * representation.
   *
   * @return a string representation of this line segment
   */
  public String toString() {
    return pointP + " -> " + pointQ;
  }

  /**
   * Throws an exception if called. The hashCode() method is not supported because
   * hashing has not yet been introduced in this course. Moreover, hashing does
   * not typically lead to good *worst-case* performance guarantees, as required
   * on this assignment.
   *
   * @throws UnsupportedOperationException if called
   */
  public int hashCode() {
    throw new UnsupportedOperationException();
  }

}