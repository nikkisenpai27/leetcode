import java.util.*;

class Solution {
    public int maxValue(int[] nums, int k) {
        int n = nums.length;
        int maxOr = 128; // 2^7
        
        // dpLeft[i][j][v]: possible to pick j elements from nums[0..i] with OR value v
        boolean[][][] dpLeft = new boolean[n + 1][k + 1][maxOr];
        dpLeft[0][0][0] = true;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                for (int v = 0; v < maxOr; v++) {
                    if (!dpLeft[i][j][v]) continue;
                    // Skip nums[i]
                    dpLeft[i + 1][j][v] = true;
                    // Take nums[i]
                    if (j < k) {
                        dpLeft[i + 1][j + 1][v | nums[i]] = true;
                    }
                }
            }
        }
        
        // dpRight[i][j][v]: possible to pick j elements from nums[i..n-1] with OR value v
        boolean[][][] dpRight = new boolean[n + 1][k + 1][maxOr];
        dpRight[n][0][0] = true;
        
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= k; j++) {
                for (int v = 0; v < maxOr; v++) {
                    if (!dpRight[i + 1][j][v]) continue;
                    // Skip nums[i]
                    dpRight[i][j][v] = true;
                    // Take nums[i]
                    if (j < k) {
                        dpRight[i][j + 1][v | nums[i]] = true;
                    }
                }
            }
        }
        
        int ans = 0;
        // Split point: left uses nums[0..i-1], right uses nums[i..n-1]
        // Both sides need at least k elements
        for (int i = k; i <= n - k; i++) {
            for (int or1 = 0; or1 < maxOr; or1++) {
                if (!dpLeft[i][k][or1]) continue;
                for (int or2 = 0; or2 < maxOr; or2++) {
                    if (dpRight[i][k][or2]) {
                        ans = Math.max(ans, or1 ^ or2);
                    }
                }
            }
        }
        
        return ans;
    }
}