class Solution {
    public int minArraySum(int[] nums, int k, int op1, int op2) {
        int n = nums.length;
        int[][][] dp = new int[n + 1][op1 + 1][op2 + 1];
        for (int i = 0; i <= n; i++) {
            for (int a = 0; a <= op1; a++) {
                Arrays.fill(dp[i][a], Integer.MAX_VALUE);
            }
        }
        dp[0][0][0] = 0;
        for (int i = 0; i < n; i++) {
            for (int a = 0; a <= op1; a++) {
                for (int b = 0; b <= op2; b++) {
                    if (dp[i][a][b] == Integer.MAX_VALUE) continue;
                    int cur = nums[i];
                    dp[i + 1][a][b] = Math.min(dp[i + 1][a][b], dp[i][a][b] + cur);
                    if (a < op1) {
                        int v = (cur + 1) / 2;
                        dp[i + 1][a + 1][b] = Math.min(dp[i + 1][a + 1][b], dp[i][a][b] + v);
                    }
                    if (b < op2 && cur >= k) {
                        int v = cur - k;
                        dp[i + 1][a][b + 1] = Math.min(dp[i + 1][a][b + 1], dp[i][a][b] + v);
                    }
                    if (a < op1 && b < op2) {
                        int v1 = (cur + 1) / 2;
                        int v2 = v1 >= k ? v1 - k : v1;
                        dp[i + 1][a + 1][b + 1] = Math.min(dp[i + 1][a + 1][b + 1], dp[i][a][b] + v2);
                        int v3 = cur >= k ? cur - k : cur;
                        int v4 = (v3 + 1) / 2;
                        dp[i + 1][a + 1][b + 1] = Math.min(dp[i + 1][a + 1][b + 1], dp[i][a][b] + v4);
                    }
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int a = 0; a <= op1; a++) {
            for (int b = 0; b <= op2; b++) {
                res = Math.min(res, dp[n][a][b]);
            }
        }
        return res;
    }
}