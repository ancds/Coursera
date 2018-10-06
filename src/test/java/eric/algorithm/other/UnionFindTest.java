package eric.algorithm.other;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class UnionFindTest {

  private static final Logger LOGGER = LogManager.getLogger(UnionFindTest.class);

  private static final int N = 10;

  /** QuickFindTest. */
  @ParameterizedTest
  @MethodSource("unionFindProvider")
  public void connectedIffUnion(UnionFind unionFind) {

    LOGGER.debug("connected components: {}", "0,1,2,3,4,5,6,7,8,9");
    // one to one not connected
    assertFalse(unionFind.connected(4, 3), "(4,3) get connected before union");
    unionFind.union(4, 3);
    assertTrue(unionFind.connected(4, 3), "(4,3) not connected after union");

    LOGGER.debug("connected components: {}", "0,1,2,{3,4},5,6,7,8,9");
    // one to one connected
    unionFind.union(4, 3);
    assertTrue(unionFind.connected(4, 3), "(4,3) not connected after union");

    // one to group not connected
    assertFalse(unionFind.connected(4, 5), "(4,5) get connected before union");
    unionFind.union(4, 5);
    assertAll("(3,4,5) not connected after union",
        () -> assertTrue(unionFind.connected(4, 3), "(4,3) not connected after union"),
        () -> assertTrue(unionFind.connected(4, 5), "(4,5) not connected after union"),
        () -> assertTrue(unionFind.connected(3, 5), "(3,5) not connected after union"));

    LOGGER.debug("connected components: {}", "0,1,2,{3,4,5},6,7,8,9");
    // one to one not connected
    assertFalse(unionFind.connected(0, 9), "(0,9) get connected before union");
    unionFind.union(0, 9);
    assertTrue(unionFind.connected(0, 9), "(0,9) not connected after union");

    LOGGER.debug("connected components: {}", "{0,9},1,2,{3,4,5},6,7,8");
    // group to group not connected
    assertFalse(unionFind.connected(4, 9), "(4,9) get connected before union");
    unionFind.union(4, 9);
    assertAll("4's group and 9's group not connected after union",
        () -> assertTrue(unionFind.connected(4, 9), "(4,9) not connected after union"),
        () -> assertTrue(unionFind.connected(0, 5), "(0,5) not connected after union"),
        () -> assertTrue(unionFind.connected(0, 4), "(0,4) not connected after union"));

    LOGGER.debug("connected components: {}", "{0,3,4,5,9},1,2,6,7,8");
  }

  static Stream<UnionFind> unionFindProvider() {

    UnionFind quickFind = new QuickFind(N);
    UnionFind quickUnion = new QuickUnion(N);

    return Stream.of(quickFind, quickUnion);
  }

}
