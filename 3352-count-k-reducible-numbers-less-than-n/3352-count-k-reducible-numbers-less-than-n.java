class Solution {
    private static final int MOD = 1_000_000_007;
    private Integer[][][] memo;
    private int[] ops;
    private String s;
    private int k;

    public int countKReducibleNumbers(String s, int k) {
        this.s = s;
        this.k = k;
        int n = s.length();

        // ops[c] = number of operations needed to reduce the integer c to 1
        ops = new int[n + 1];
        // ops[0] = 0, ops[1] = 0 (already 1)
        for (int i = 2; i <= n; i++) {
            ops[i] = 1 + ops[Integer.bitCount(i)];
        }

        memo = new Integer[n][n + 1][2];
        // The DP counts 0 as well, so we subtract 1
        return (dfs(0, 0, true) - 1 + MOD) % MOD;
    }

    private int dfs(int pos, int setBits, boolean tight) {
        if (pos == s.length()) {
            // Valid only if ops[setBits] < k and the number is strictly less than n
            return (ops[setBits] < k && !tight) ? 1 : 0;
        }

        if (memo[pos][setBits][tight ? 1 : 0] != null) {
            return memo[pos][setBits][tight ? 1 : 0];
        }

        int res = 0;
        int upper = tight ? (s.charAt(pos) - '0') : 1;

        for (int dig = 0; dig <= upper; dig++) {
            boolean nextTight = tight && (dig == upper);
            res = (res + dfs(pos + 1, setBits + dig, nextTight)) % MOD;
        }

        return memo[pos][setBits][tight ? 1 : 0] = res;
    }
}