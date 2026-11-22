class Solution {
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        int[] next = new int[n];
        for (int i = 0; i < n; i++) next[i] = i + 1;
        int[] res = new int[queries.length];
        int dist = n - 1;
        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0], v = queries[i][1];
            if (next[u] < v) {
                int cur = next[u];
                while (cur < v) {
                    int nxt = next[cur];
                    next[cur] = v;
                    cur = nxt;
                    dist--;
                }
                next[u] = v;
            }
            res[i] = dist;
        }
        return res;
    }
}