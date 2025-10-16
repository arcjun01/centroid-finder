
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
    * The groups are sorted in DESCENDING order according to Group's compareTo method
    * (size first, then x, then y). That is, the largest group will be first, the 
    * smallest group will be last, and ties will be broken first by descending 
    * y value, then descending x value.
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
        //track visited pixels
        boolean[][] visited = new boolean[rows][cols];
        List<Group> groups = new ArrayList<>();

        // loop over the pixel
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                //start new group if pixel is white and not visited
                if (image[y][x] == 1 && !visited[y][x]) {
                    // Note: Call the helper here and implement later!
                    Group group= exploredPix(image, visited, x, y);
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

            //store pixels coordinates
            List<int[]> pixels= new ArrayList<>();

            //recursive dfs function
            dfs(image, visited, startX, startY, pixels);

            //find centroid
            int sumX= 0;
            int sumY=0;
            for(int[] pixel: pixels){
                sumX += pixel[0];
                sumY += pixel[1];
            }
            int size= pixels.size();
            return new Group(size, sumX/size, sumY/size);

        }
        // Integer division
        //dfs to visit all connected white pixel
        private void dfs(int[][] image, boolean[][] visited, int x, int y, List<int[]>pixels ){
            int rows = image.length;
            int cols = image[0].length;

            //check if pixel is out of bound
            if(x<0|| x>=cols ||y<0 || y>=rows){
                return;
            }

            //check if visited or not white
            if(visited[y][x] || image[y][x] !=1){
                return;
            }

            //mark visited
            visited[y][x]= true;
            //add to group
            pixels.add(new int []{x, y});

             // explore neighbors(up, down, left, right)
             dfs(image, visited, x, y-1, pixels);
             dfs(image, visited, x, y+1, pixels);
             dfs(image, visited, x-1, y, pixels);
             dfs(image, visited, x+1, y, pixels);
             
             


        }

    
}
