import java.util.Arrays;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] count = new int[n + 1];
        long minPrice = Long.MAX_VALUE;

        // Count frequency of each factor and find the cheapest price
        for (int[] item : items) {
            count[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        // For each factor f, compute how many free items it can unlock
        // (number of items whose factor is a multiple of f, excluding itself)
        int[] freeCount = new int[n + 1];
        for (int f = 1; f <= n; f++) {
            if (count[f] > 0) {
                int multiples = 0;
                for (int j = f; j <= n; j += f) {
                    multiples += count[j];
                }
                freeCount[f] = multiples - 1; // exclude itself
            }
        }

        // Collect "profitable" deals: price < 2 * minPrice and freeCount > 0
        // Each such purchase gives 2 items (1 paid + 1 free)
        long[] deals = new long[n];
        int dealCnt = 0;
        long limit = minPrice * 2;

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];
            if (price < limit && freeCount[factor] > 0) {
                // Pack price in high 32 bits, free count in low 32 bits
                deals[dealCnt++] = ((long) price << 32) | (freeCount[factor] & 0xFFFFFFFFL);
            }
        }

        // Sort deals by price ascending
        Arrays.sort(deals, 0, dealCnt);

        long total = 0;
        long remaining = budget;

        // Greedily take the cheapest profitable deals first
        for (int i = 0; i < dealCnt; i++) {
            long packed = deals[i];
            int price = (int) (packed >>> 32);
            int maxFree = (int) (packed & 0xFFFFFFFFL);

            if (remaining < price) break;

            long take = Math.min(maxFree, remaining / price);
            remaining -= take * price;
            total += 2 * take;
        }

        // Spend leftover budget on the cheapest item (each gives only 1 item)
        total += remaining / minPrice;

        return (int) total;
    }
}