class Solution {
    public int minFlips(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int ans = 0;
        int middleOnes = 0;
        int mismatchedPairs = 0;

        // 1. Handle groups of 4 symmetric cells (quadrants)
        // For each group, we can flip to make all 0s or all 1s.
        for (int i = 0; i < m / 2; ++i) {
            for (int j = 0; j < n / 2; ++j) {
                int ones = grid[i][j] + grid[i][n - 1 - j] 
                         + grid[m - 1 - i][j] + grid[m - 1 - i][n - 1 - j];
                ans += Math.min(ones, 4 - ones);
            }
        }

        // 2. Handle the middle row (if m is odd)
        if (m % 2 == 1) {
            for (int j = 0; j < n / 2; ++j) {
                int left = grid[m / 2][j];
                int right = grid[m / 2][n - 1 - j];
                if (left != right) {
                    mismatchedPairs++;
                } else {
                    middleOnes += left + right;
                }
            }
        }

        // 3. Handle the middle column (if n is odd)
        if (n % 2 == 1) {
            for (int i = 0; i < m / 2; ++i) {
                int top = grid[i][n / 2];
                int bottom = grid[m - 1 - i][n / 2];
                if (top != bottom) {
                    mismatchedPairs++;
                } else {
                    middleOnes += top + bottom;
                }
            }
        }

        // 4. Adjust for the requirement that total 1s must be divisible by 4
        if (mismatchedPairs == 0) {
            // No mismatched pairs, so we can only adjust by changing matching pairs.
            // If middleOnes % 4 == 2, we need 2 extra flips to fix the parity.
            if (middleOnes % 4 == 2) {
                ans += 2;
            }
        } else {
            // If there are mismatched pairs, we can flip one of them to help fix the modulo.
            ans += mismatchedPairs;
        }

        // 5. Handle the very center cell (if both dimensions are odd)
        // It must be 0 to keep the total count of 1s even (and divisible by 4).
        if (m % 2 == 1 && n % 2 == 1) {
            ans += grid[m / 2][n / 2];
        }

        return ans;
    }
}