class Solution {
    public int maxPossibleScore(int[] start, int d) {
        Arrays.sort(start);
        int n = start.length;
        long left = 0, right = (long) 1e15;
        while (left < right) {
            long mid = left + (right - left + 1) / 2;
            if (canAchieve(start, d, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return (int) left;
    }

    private boolean canAchieve(int[] start, int d, long score) {
        long prev = start[0];
        for (int i = 1; i < start.length; i++) {
            long needed = prev + score;
            if (needed > start[i] + d) return false;
            prev = Math.max(needed, start[i]);
        }
        return true;
    }
}