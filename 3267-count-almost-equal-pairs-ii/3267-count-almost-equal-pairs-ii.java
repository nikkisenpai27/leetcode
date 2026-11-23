class Solution {
    public int countPairs(int[] nums) {
        int ans = 0;
        Map<Integer, Integer> count = new HashMap<>();
        
        // Determine maximum length for zero-padding (at most 7 since nums[i] < 10^7)
        int maxLen = 0;
        for (int num : nums) {
            maxLen = Math.max(maxLen, String.valueOf(num).length());
        }
        
        for (int num : nums) {
            // Pad the number to maxLen with leading zeros
            String digits = String.format("%0" + maxLen + "d", num);
            
            // Generate all numbers reachable within at most 2 swaps
            Set<Integer> reachable = getReachable(digits);
            
            // For each reachable number, add its previous occurrence count
            for (int val : reachable) {
                ans += count.getOrDefault(val, 0);
            }
            
            // Add current number to the frequency map
            count.merge(num, 1, Integer::sum);
        }
        
        return ans;
    }
    
    // Returns all numbers obtainable from the given digit string with at most 2 swaps
    private Set<Integer> getReachable(String digits) {
        int n = digits.length();
        Set<Integer> result = new HashSet<>();
        result.add(Integer.parseInt(digits)); // Original number (0 swaps)
        
        // All numbers after exactly 1 swap
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                char[] arr = digits.toCharArray();
                swap(arr, i, j);
                result.add(Integer.parseInt(new String(arr)));
            }
        }
        
        // All numbers after exactly 2 swaps
        for (int i1 = 0; i1 < n; i1++) {
            for (int j1 = i1 + 1; j1 < n; j1++) {
                char[] afterFirst = digits.toCharArray();
                swap(afterFirst, i1, j1);
                
                for (int i2 = 0; i2 < n; i2++) {
                    for (int j2 = i2 + 1; j2 < n; j2++) {
                        char[] afterSecond = afterFirst.clone();
                        swap(afterSecond, i2, j2);
                        result.add(Integer.parseInt(new String(afterSecond)));
                    }
                }
            }
        }
        
        return result;
    }
    
    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}