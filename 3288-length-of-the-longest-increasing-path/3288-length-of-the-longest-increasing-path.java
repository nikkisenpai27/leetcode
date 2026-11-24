import java.util.*;

class Solution {
    public int maxPathLength(int[][] coordinates, int k) {
        int kx = coordinates[k][0];
        int ky = coordinates[k][1];
        
        List<int[]> left = new ArrayList<>();
        List<int[]> right = new ArrayList<>();
        
        for (int[] point : coordinates) {
            int x = point[0], y = point[1];
            if (x < kx && y < ky) {
                left.add(point);
            }
            if (x > kx && y > ky) {
                right.add(point);
            }
        }
        
        int leftLIS = getLIS(left);
        int rightLIS = getLIS(right);
        
        return leftLIS + rightLIS + 1;
    }
    
    private int getLIS(List<int[]> points) {
        if (points.isEmpty()) return 0;
        
        // Sort by x ascending, and for ties, y descending
        // (descending y prevents chaining points with the same x in LIS)
        points.sort((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(b[1], a[1]);
        });
        
        List<Integer> lis = new ArrayList<>();
        
        for (int[] point : points) {
            int y = point[1];
            int pos = Collections.binarySearch(lis, y);
            if (pos < 0) pos = -(pos + 1);
            
            if (pos == lis.size()) {
                lis.add(y);
            } else {
                lis.set(pos, y);
            }
        }
        
        return lis.size();
    }
}