class Solution {
    public int sumOfGoodSubsequences(int[] nums) {
        final int MOD = 1_000_000_007;
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        // f[x] = sum of all good subsequences ending with value x
        // g[x] = number of good subsequences ending with value x
        long[] f = new long[mx + 1];
        long[] g = new long[mx + 1];

        for (int x : nums) {
            // Start a new subsequence with just x
            f[x] = (f[x] + x) % MOD;
            g[x] = (g[x] + 1) % MOD;

            // Append x to all subsequences ending with (x-1)
            if (x > 0) {
                f[x] = (f[x] + f[x - 1] + g[x - 1] * x % MOD) % MOD;
                g[x] = (g[x] + g[x - 1]) % MOD;
            }

            // Append x to all subsequences ending with (x+1)
            if (x + 1 <= mx) {
                f[x] = (f[x] + f[x + 1] + g[x + 1] * x % MOD) % MOD;
                g[x] = (g[x] + g[x + 1]) % MOD;
            }
        }

        long ans = 0;
        for (long val : f) {
            ans = (ans + val) % MOD;
        }
        return (int) ans;
    }
}