class Solution {
    public int maxMoves(int kx, int ky, int[][] positions) {
        int n = positions.length;
        int[][] pts = new int[n + 1][2];
        pts[0][0] = kx;
        pts[0][1] = ky;
        for (int i = 0; i < n; i++) {
            pts[i + 1][0] = positions[i][0];
            pts[i + 1][1] = positions[i][1];
        }
        int m = n + 1;
        int[][] dist = new int[m][m];
        for (int i = 0; i < m; i++) {
            dist[i] = bfs(pts[i][0], pts[i][1], pts);
        }
        int full = 1 << n;
        int[][] dp = new int[full][n];
        for (int[] row : dp) Arrays.fill(row, -1);
        int ans = 0;
        for (int j = 0; j < n; j++) {
            ans = Math.max(ans, dist[0][j + 1] + solve(1 << j, j, dist, dp, n));
        }
        return ans;
    }

    private int solve(int mask, int last, int[][] dist, int[][] dp, int n) {
        if (mask == (1 << n) - 1) return 0;
        if (dp[mask][last] != -1) return dp[mask][last];
        int cnt = Integer.bitCount(mask);
        boolean aliceTurn = (cnt % 2 == 0);
        int best = aliceTurn ? -1 : Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            if ((mask & (1 << j)) != 0) continue;
            int d = dist[last + 1][j + 1];
            int val = d + solve(mask | (1 << j), j, dist, dp, n);
            if (aliceTurn) best = Math.max(best, val);
            else best = Math.min(best, val);
        }
        dp[mask][last] = best;
        return best;
    }

    private int[] bfs(int sx, int sy, int[][] pts) {
        int[][] dirs = {{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};
        int[][] d = new int[50][50];
        for (int[] row : d) Arrays.fill(row, -1);
        Queue<int[]> q = new LinkedList<>();
        d[sx][sy] = 0;
        q.offer(new int[]{sx, sy});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int[] dir : dirs) {
                int nx = cur[0] + dir[0];
                int ny = cur[1] + dir[1];
                if (nx >= 0 && nx < 50 && ny >= 0 && ny < 50 && d[nx][ny] == -1) {
                    d[nx][ny] = d[cur[0]][cur[1]] + 1;
                    q.offer(new int[]{nx, ny});
                }
            }
        }
        int[] res = new int[pts.length];
        for (int i = 0; i < pts.length; i++) {
            res[i] = d[pts[i][0]][pts[i][1]];
        }
        return res;
    }
}