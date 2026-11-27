class Solution {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int n = nums.size();
        int lo = 1, hi = n / 2, ans = 0;
        
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (canForm(nums, mid)) {
                ans = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return ans;
    }
    
    private boolean canForm(List<Integer> nums, int k) {
        int n = nums.size();
        // run[i] = length of strictly increasing subarray ending at i
        int[] run = new int[n];
        run[0] = 1;
        for (int i = 1; i < n; i++) {
            run[i] = (nums.get(i) > nums.get(i - 1)) ? run[i - 1] + 1 : 1;
        }
        
        // For a split point at index i (0-based), left subarray ends at i-1, right starts at i
        // Left needs run[i-1] >= k, Right needs run[i+k-1] >= k
        // Wait - right subarray of length k starting at i: it's increasing iff run[i+k-1] >= k
        // But we need the whole subarray starting at i to be increasing: 
        // nums[i] < nums[i+1] < ... < nums[i+k-1]
        // This is true iff run[i+k-1] >= k AND the run starts at or before i.
        // Since run[i+k-1] >= k means there are k consecutive increasing elements ending at i+k-1,
        // which are exactly nums[i..i+k-1]. Correct.
        
        for (int i = k; i + k <= n; i++) {
            // left subarray: nums[i-k .. i-1], increasing iff run[i-1] >= k
            // right subarray: nums[i .. i+k-1], increasing iff run[i+k-1] >= k
            if (run[i - 1] >= k && run[i + k - 1] >= k) {
                return true;
            }
        }
        return false;
    }
}