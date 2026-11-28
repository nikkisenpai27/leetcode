import java.util.Arrays;

class Solution {
    public int maxCollectedFruits(int[][] fruits) {
        int n = fruits.length;
        final int INF = 1 << 29;
        int[][] dp = new int[n][n];
        
        for (int[] row : dp) {
            Arrays.fill(row, -INF);
        }

        // Child starting from (0, n-1) — stays strictly above the main diagonal
        dp[0][n - 1] = fruits[0][n - 1];
        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1]) + fruits[i][j];
                if (j + 1 < n) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j + 1] + fruits[i][j]);
                }
            }
        }

        // Child starting from (n-1, 0) — stays strictly below the main diagonal
        dp[n - 1][0] = fruits[n - 1][0];
        for (int j = 1; j < n; j++) {
            for (int i = j + 1; i < n; i++) {
                dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j - 1]) + fruits[i][j];
                if (i + 1 < n) {
                    dp[i][j] = Math.max(dp[i][j], dp[i + 1][j - 1] + fruits[i][j]);
                }
            }
        }

        // Child from (0,0) must take the main diagonal
        int ans = dp[n - 2][n - 1] + dp[n - 1][n - 2];
        for (int i = 0; i < n; i++) {
            ans += fruits[i][i];
        }
        return ans;
    }
}