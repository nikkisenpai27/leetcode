import java.util.*;

class Solution {
    private List<int[]>[] g;
    private int k;

    public long maximizeSumOfWeights(int[][] edges, int k) {
        this.k = k;
        int n = edges.length + 1;
        g = new List[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            g[u].add(new int[]{v, w});
            g[v].add(new int[]{u, w});
        }
        
        long[] ans = dfs(0, -1);
        return Math.max(ans[0], ans[1]);
    }

    // Returns {max sum with up to k child edges, max sum with up to k-1 child edges}
    private long[] dfs(int u, int fa) {
        long base = 0;
        List<Long> gains = new ArrayList<>();
        
        for (int[] e : g[u]) {
            int v = e[0], w = e[1];
            if (v == fa) continue;
            
            long[] res = dfs(v, u);
            // res[0] = best when child can use up to k edges (parent edge not taken)
            // res[1] = best when child can use up to k-1 edges (parent edge taken)
            base += res[0];
            
            long gain = (long) w + res[1] - res[0];
            if (gain > 0) {
                gains.add(gain);
            }
        }
        
        gains.sort(Comparator.reverseOrder());
        
        long withKMinus1 = base;
        for (int i = 0; i < Math.min(gains.size(), k - 1); i++) {
            withKMinus1 += gains.get(i);
        }
        
        long withK = withKMinus1;
        if (gains.size() >= k) {
            withK += gains.get(k - 1);
        }
        
        return new long[]{withK, withKMinus1};
    }
}