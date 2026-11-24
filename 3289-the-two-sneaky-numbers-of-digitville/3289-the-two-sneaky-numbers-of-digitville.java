class Solution {
    public int[] getSneakyNumbers(int[] nums) {
        int n = nums.length - 2;
        boolean[] seen = new boolean[n];
        int[] result = new int[2];
        int idx = 0;
        
        for (int num : nums) {
            if (seen[num]) {
                result[idx++] = num;
                if (idx == 2) break;
            } else {
                seen[num] = true;
            }
        }
        return result;
    }
}