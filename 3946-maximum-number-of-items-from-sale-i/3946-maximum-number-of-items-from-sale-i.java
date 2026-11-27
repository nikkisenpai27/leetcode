class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int[] f = new int[budget + 1];
        int mn = Integer.MAX_VALUE;

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];
            mn = Math.min(mn, price);

            // Count how many items (including itself) this factor divides
            int cnt = 0;
            for (int[] jItem : items) {
                if (jItem[0] % factor == 0) {
                    cnt++;
                }
            }

            // 0-1 knapsack: decide whether to buy the *first* copy of this item
            for (int j = budget; j >= price; j--) {
                f[j] = Math.max(f[j], f[j - price] + cnt);
            }
        }

        int ans = 0;
        for (int i = 0; i <= budget; i++) {
            // Spend remaining budget on the cheapest item (unlimited)
            ans = Math.max(ans, f[i] + (budget - i) / mn);
        }
        return ans;
    }
}