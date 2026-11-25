class Solution {
    public char kthCharacter(long k, int[] operations) {
        long len = 1;
        int i = 0;
        while (len < k && i < operations.length) {
            len *= 2;
            i++;
        }
        int shifts = 0;
        while (i > 0) {
            i--;
            len /= 2;
            if (k > len) {
                k -= len;
                if (operations[i] == 1) {
                    shifts++;
                }
            }
        }
        return (char) ('a' + shifts % 26);
    }
}