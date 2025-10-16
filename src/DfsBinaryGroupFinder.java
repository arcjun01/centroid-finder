import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DfsBinaryGroupFinder implements BinaryGroupFinder {
   /**
    * Finds connected pixel groups of 1s in an integer array representing a binary image.
    * 
    * The input is a non-empty rectangular 2D array containing only 1s and 0s.
    * If the array or any of its subarrays are null, a NullPointerException
    * is thrown. If the array is otherwise invalid, an IllegalArgumentException
    * is thrown.
    *
    * Pixels are considered connected vertically and horizontally, NOT diagonally.
    * The top-left cell of the array (row:0, column:0) is considered to be coordinate
    * (x:0, y:0). Y increases downward and X increases to the right. For example,
    * (row:4, column:7) corresponds to (x:7, y:4).
    *
    * The method returns a list of sorted groups. The group's size is the number 
    * of pixels in the group. The centroid of the group
    * is computed as the average of each of the pixel locations across each dimension.
    * For example, the x coordinate of the centroid is the sum of all the x
    * coordinates of the pixels in the group divided by the number of pixels in that group.
    * Similarly, the y coordinate of the centroid is the sum of all the y
    * coordinates of the pixels in the group divided by the number of pixels in that group.
    * The division should be done as INTEGER DIVISION.
    *
    * The groups are sorted in DESCENDING order according to Group's compareTo method.
    * 
    * @param image a rectangular 2D array containing only 1s and 0s
    * @return the found groups of connected pixels in descending order
    */
    @Override
    public List<Group> findConnectedGroups(int[][] image) {

        if (image == null) {
            throw new NullPointerException("Cannot be null");
        }
        if (image.length == 0 || image[0].length == 0) {
            return new ArrayList<>();
        }

        int cols = image[0].length;
        for (int[] row : image) {
            if (row == null) {
                throw new NullPointerException("Row cannot be null.");
            }
            if (row.length != cols) {
                throw new IllegalArgumentException("Must have the same length!");
            }
        }

        int rows = image.length;
        boolean[][] visited = new boolean[rows][cols];
        List<Group> groups = new ArrayList<>();

        // This will loop through the group
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (image[y][x] == 1 && !visited[y][x]) {
                    // Note: Call the helper here and implement later!
                    Group group = exploredPix(image, visited, x, y);
                    groups.add(group);
                }
            }
        }

        // This sorts it by descending order
        // https://www.geeksforgeeks.org/java/collections-sort-java-examples/
        Collections.sort(groups, Collections.reverseOrder());
        return groups;
    }

    // This helper will explore the group or each pixel
    private Group exploredPix(int[][] image, boolean[][] visited, int startX, int startY){
        Stack<int[]> connected = new Stack<>();
        connected.push(new int[]{startX, startY});

        int sumX = 0;
        int sumY = 0;
        int count = 0;

        while(!connected.isEmpty()){
            int[] current = connected.pop();
            int x = current[0];
            int y = current[1];

            if (startX < 0 || startX >= image[0].length || startY < 0 || startY >= image.length) continue;
            if (visited[y][x]) continue;
            if (image[y][x] == 0) continue;

            visited[y][x] = true;
            sumX += x;
            sumY += y;
            count++;

            // Check all the neighbors (up, down, left, right)
            connected.push(new int[]{x, y - 1});
            connected.push(new int[]{x, y + 1});
            connected.push(new int[]{x - 1, y});
            connected.push(new int[]{x + 1, y});
        } 
        // Integer division
        int centerX = sumX / count;
        int centerY = sumY / count;
        
        Coordinate centroid = new Coordinate(centerX, centerY);

        return new Group(count, centroid);
    }
}
