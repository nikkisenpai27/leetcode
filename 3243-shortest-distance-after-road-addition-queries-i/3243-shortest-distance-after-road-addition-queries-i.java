class Solution {
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) graph[i].add(i + 1);
        int[] res = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int u = queries[q][0], v = queries[q][1];
            graph[u].add(v);
            int[] dist = new int[n];
            Arrays.fill(dist, -1);
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(0);
            dist[0] = 0;
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                for (int next : graph[cur]) {
                    if (dist[next] == -1) {
                        dist[next] = dist[cur] + 1;
                        queue.offer(next);
                    }
                }
            }
            res[q] = dist[n - 1];
        }
        return res;
    }
}