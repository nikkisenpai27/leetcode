class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            long prod = 1;
            long lcm = 1;
            long gcd = 0;
            for (int j = i; j < n; j++) {
                prod *= nums[j];
                if (prod > 100000000000L) break;
                lcm = lcm(lcm, nums[j]);
                gcd = gcd(gcd, nums[j]);
                if (prod == lcm * gcd) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
}