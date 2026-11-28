class Solution {
    public boolean isPossibleToRearrange(String s, String t, int k) {
        int n = s.length();
        int len = n / k;
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i += len) {
            map.merge(s.substring(i, i + len), 1, Integer::sum);
        }
        for (int i = 0; i < n; i += len) {
            String piece = t.substring(i, i + len);
            if (!map.containsKey(piece) || map.get(piece) == 0) return false;
            map.merge(piece, -1, Integer::sum);
        }
        return true;
    }
}