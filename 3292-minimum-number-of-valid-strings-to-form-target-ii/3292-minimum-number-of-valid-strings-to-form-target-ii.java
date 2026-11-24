class Solution {
    public int minValidStrings(String[] words, String target) {
        int n = target.length();
        int[] maxMatch = new int[n + 1];

        for (String word : words) {
            int m = word.length();
            int[] pi = getLPS(word + "#" + target);
            int offset = m + 1;
            for (int i = 0; i < n; i++) {
                maxMatch[i + 1] = Math.max(maxMatch[i + 1], pi[offset + i]);
            }
        }

        int ans = 0;
        int len = n;
        while (len > 0) {
            if (maxMatch[len] == 0) return -1;
            len -= maxMatch[len];
            ans++;
        }
        return ans;
    }

    private int[] getLPS(String s) {
        int n = s.length();
        int[] pi = new int[n];
        for (int i = 1, j = 0; i < n; i++) {
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = pi[j - 1];
            }
            if (s.charAt(i) == s.charAt(j)) j++;
            pi[i] = j;
        }
        return pi;
    }
}