package eric.algorithm.exercise.collinear;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CollinearPointsTest {

  /**
   * testBruteCollinearPoitns.
   * 
   * @param points points
   */
  @ParameterizedTest
  @MethodSource("pointsProviderForBrute")
  public void testBruteCollinearPoitns(Point[] points, int numberShouldBe) {

    BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
    testCollinearPoints(bruteCollinearPoints, points, numberShouldBe);

  }

  /**
   * testBruteCollinearPoitns.
   * 
   * @param points points
   */
  @ParameterizedTest
  @MethodSource("pointsProviderForFast")
  public void testFastCollinearPoitns(Point[] points, int numberShouldBe) {

    FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
    testCollinearPoints(fastCollinearPoints, points, numberShouldBe);

  }

  private void testCollinearPoints(CollinearPoints collinearPoints, Point[] points,
      int numberShouldBe) {

    for (int i = 0; i < points.length; i++) {
      points[i] = null;
    }
    LineSegment[] segments = collinearPoints.segments();
    for (int i = 0; i < segments.length; i++) {
      segments[i] = null;
    }
    if (segments.length > 0) {
      segments = collinearPoints.segments();
      assertNotNull(segments[0]);
    }
    assertTrue(collinearPoints.numberOfSegments() == numberShouldBe);

  }

  /**
   * test invalid points input.
   */
  @Test
  public void testInvalidPoints() {

    Point[] nullRef = null;
    assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(nullRef));
    assertThrows(IllegalArgumentException.class, () -> new FastCollinearPoints(nullRef));

    Point[] nullInArray = new Point[2];
    nullInArray[1] = new Point(1, 2);
    assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(nullInArray));
    assertThrows(IllegalArgumentException.class, () -> new FastCollinearPoints(nullInArray));

    Point[] repeatedPoints = new Point[2];
    repeatedPoints[0] = new Point(1, 2);
    repeatedPoints[1] = new Point(1, 2);
    assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(repeatedPoints));
    assertThrows(IllegalArgumentException.class, () -> new FastCollinearPoints(repeatedPoints));

  }

  static Stream<Arguments> pointsProviderForBrute() {

    // Brute implementation here does not support line segments containing 4 or more
    // points
    Point[] points1 = readPoints("input8.txt");
    int numberShouldBe1 = 2;

    return Stream.of(
        Arguments.of(points1, numberShouldBe1));
  }

  static Stream<Arguments> pointsProviderForFast() {

    Point[] points1 = readPoints("input8.txt");
    int numberShouldBe1 = 2;
    Point[] points2 = readPoints("input6.txt");
    int numberShouldBe2 = 1;
    Point[] points3 = readPoints("input40.txt");
    int numberShouldBe3 = 4;

    return Stream.of(
        Arguments.of(points1, numberShouldBe1),
        Arguments.of(points2, numberShouldBe2),
        Arguments.of(points3, numberShouldBe3));
  }

  static Point[] readPoints(String fileName) {

    try (
        InputStream is = CollinearPointsTest.class
            .getResourceAsStream("/data/collinear/" + fileName);
        Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.toString());) {

      int size = scanner.nextInt();
      Point[] points = new Point[size];

      for (int i = 0; i < points.length; i++) {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        points[i] = new Point(x, y);
      }

      return points;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

}
