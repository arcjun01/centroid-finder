import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Simplified core tests for DfsBinaryGroupFinder.
 */
public class DfsBinaryGroupFinderTest {

    private final DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();

    /** One white pixel should form one group of size 1 at (1,1). */
    @Test
    void testSinglePixelGroup() {
        int[][] image = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        List<Group> expected = List.of(new Group(1, new Coordinate(1, 1)));
        assertEquals(expected, finder.findConnectedGroups(image));
    }

    /** A 2x2 block of white pixels should be one group with centroid (0,0). */
    @Test
    void testConnectedGroup_2x2Block() {
        int[][] image = {
                {1, 1},
                {1, 1}
        };
        List<Group> expected = List.of(new Group(4, 0, 0));
        assertEquals(expected, finder.findConnectedGroups(image));
    }

    /** Four isolated pixels should form four separate groups sorted correctly. */
    @Test
    void testMultipleIsolatedGroups() {
        int[][] image = {
                {1, 0, 1},
                {0, 0, 0},
                {1, 0, 1}
        };
        List<Group> expected = List.of(
                new Group(1, 2, 2),
                new Group(1, 2, 0),
                new Group(1, 0, 2),
                new Group(1, 0, 0)
        );
        assertEquals(expected, finder.findConnectedGroups(image));
    }

    /** Diagonal pixels should NOT count as connected. */
    @Test
    void testDiagonalConnectivity() {
        int[][] image = {
                {1, 0, 0},
                {0, 0, 0},
                {0, 0, 1}
        };
        List<Group> expected = List.of(
                new Group(1, 2, 2),
                new Group(1, 0, 0)
        );
        assertEquals(expected, finder.findConnectedGroups(image));
    }

    /** Null image should throw a NullPointerException. */
    @Test
    void testNullImage() {
        assertThrows(NullPointerException.class, () -> finder.findConnectedGroups(null));
    }

    /** Non-rectangular array should throw IllegalArgumentException. */
    @Test
    void testNonRectangularArray() {
        int[][] image = {
                {1, 0, 1},
                {1, 0},
                {1, 0, 1}
        };
        assertThrows(IllegalArgumentException.class, () -> finder.findConnectedGroups(image));
    }
}