import java.util.HashMap;
import java.util.Map;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        if (n == 1) return 1;

        // Precompute powers for the rolling hash
        long base = 131; // A common base, can be tuned
        long mod = 1_000_000_007L;
        long[] powers = new long[n + 1];
        powers[0] = 1;
        for (int i = 1; i <= n; i++) {
            powers[i] = (powers[i - 1] * base) % mod;
        }

        int left = 1, right = n;
        int ans = n;

        // Binary search on the length of the subarray
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (hasUniqueSubarray(nums, mid, base, mod, powers)) {
                ans = mid;
                right = mid - 1; // Try to find a smaller unique subarray
            } else {
                left = mid + 1; // Need a longer subarray
            }
        }
        return ans;
    }

    private boolean hasUniqueSubarray(int[] nums, int len, long base, long mod, long[] powers) {
        int n = nums.length;
        Map<Long, Integer> hashCount = new HashMap<>();
        long currentHash = 0;

        // Compute hash for the first window
        for (int i = 0; i < len; i++) {
            currentHash = (currentHash * base + nums[i]) % mod;
        }
        hashCount.put(currentHash, 1);

        // Slide the window
        for (int i = 1; i <= n - len; i++) {
            // Remove the contribution of the element leaving the window
            long leftContribution = (powers[len - 1] * nums[i - 1]) % mod;
            currentHash = (currentHash - leftContribution + mod) % mod;
            // Shift and add the new element
            currentHash = (currentHash * base + nums[i + len - 1]) % mod;
            
            hashCount.put(currentHash, hashCount.getOrDefault(currentHash, 0) + 1);
        }

        // Check if any hash appears exactly once
        for (int count : hashCount.values()) {
            if (count == 1) {
                return true;
            }
        }
        return false;
    }
}