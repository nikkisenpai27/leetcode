class NeighborSum {
    private int[][] grid;
    private int n;
    private Map<Integer, int[]> pos;

    public NeighborSum(int[][] grid) {
        this.grid = grid;
        this.n = grid.length;
        this.pos = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                pos.put(grid[i][j], new int[]{i, j});
            }
        }
    }

    public int adjacentSum(int value) {
        int[] p = pos.get(value);
        int r = p[0], c = p[1];
        int sum = 0;
        if (r > 0) sum += grid[r - 1][c];
        if (r < n - 1) sum += grid[r + 1][c];
        if (c > 0) sum += grid[r][c - 1];
        if (c < n - 1) sum += grid[r][c + 1];
        return sum;
    }

    public int diagonalSum(int value) {
        int[] p = pos.get(value);
        int r = p[0], c = p[1];
        int sum = 0;
        if (r > 0 && c > 0) sum += grid[r - 1][c - 1];
        if (r > 0 && c < n - 1) sum += grid[r - 1][c + 1];
        if (r < n - 1 && c > 0) sum += grid[r + 1][c - 1];
        if (r < n - 1 && c < n - 1) sum += grid[r + 1][c + 1];
        return sum;
    }
}