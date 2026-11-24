class Solution {
    public long maxScore(int[] a, int[] b) {
        long[] dp = new long[4];
        java.util.Arrays.fill(dp, (long) -1e11);
        
        for (int bi : b) {
            for (int i = 3; i >= 0; i--) {
                long prev = (i > 0) ? dp[i - 1] : 0;
                dp[i] = Math.max(dp[i], prev + (long) a[i] * bi);
            }
        }
        return dp[3];
    }
}