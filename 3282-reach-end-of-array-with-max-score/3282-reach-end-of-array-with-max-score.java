class Solution {
    public long findMaximumScore(List<Integer> nums) {
        int n = nums.size();
        long score = 0;
        int max = nums.get(0);
        for (int i = 1; i < n; i++) {
            score += max;
            max = Math.max(max, nums.get(i));
        }
        return score;
    }
}