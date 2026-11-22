import java.util.*;

class Solution {
    private List<Integer>[] graph;
    private int[][] dp; // dp[u][0] = max time down, dp[u][1] = 2nd max time down
    private int[] ans;

    public int[] timeTaken(int[][] edges) {
        int n = edges.length + 1;
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        dp = new int[n][2];
        ans = new int[n];

        // First DFS: compute downward times (post-order)
        dfs1(0, -1);

        // Second DFS: reroot and compute answers (pre-order)
        dfs2(0, -1, 0);

        return ans;
    }

    // Returns the max time to mark the subtree rooted at u
    private int dfs1(int u, int parent) {
        int max1 = 0, max2 = 0;
        for (int v : graph[u]) {
            if (v == parent) continue;
            int time = dfs1(v, u) + cost(v);
            if (time > max1) {
                max2 = max1;
                max1 = time;
            } else if (time > max2) {
                max2 = time;
            }
        }
        dp[u][0] = max1;
        dp[u][1] = max2;
        return max1;
    }

    // upTime: the max time to mark everything outside u's subtree, coming from the parent side
    private void dfs2(int u, int parent, int upTime) {
        ans[u] = Math.max(dp[u][0], upTime);
        
        for (int v : graph[u]) {
            if (v == parent) continue;
            
            // When moving to child v, the "up" time for v consists of:
            // 1. The time to reach u from v (cost(u))
            // 2. The best time available at u from other directions (upTime or other children)
            int bestFromOthers;
            if (dp[u][0] == dp[v][0] + cost(v)) {
                // The best path at u came from v, so we must use the 2nd best
                bestFromOthers = Math.max(upTime, dp[u][1]);
            } else {
                // The best path at u did not come from v
                bestFromOthers = Math.max(upTime, dp[u][0]);
            }
            
            int newUpTime = bestFromOthers + cost(u);
            dfs2(v, u, newUpTime);
        }
    }

    // Cost to enter node x: 1 if odd, 2 if even
    private int cost(int x) {
        return (x % 2 == 1) ? 1 : 2;
    }
}