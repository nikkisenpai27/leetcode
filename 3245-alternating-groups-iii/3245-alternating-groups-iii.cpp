#include <vector>
#include <set>
#include <map>
using namespace std;

class Solution {
    int n;
    set<int> breaks;
    map<int, int> cnt;

    void add(int len) {
        if (len >= 3) cnt[len]++;
    }

    void remove(int len) {
        if (len >= 3) {
            auto it = cnt.find(len);
            if (it != cnt.end() && --(it->second) == 0) {
                cnt.erase(it);
            }
        }
    }

    int getLen(int prev, int curr) {
        if (prev < curr) return curr - prev;
        return curr + n - prev;
    }

    void insertBreak(int i) {
        if (breaks.empty()) {
            cnt.clear();
            add(n);
        } else if (breaks.size() == 1) {
            int p = *breaks.begin();
            remove(getLen(p, p));
            add(getLen(p, i));
            add(getLen(i, p));
        } else {
            auto it = breaks.upper_bound(i);
            int next = (it == breaks.end()) ? *breaks.begin() : *it;
            int p = (it == breaks.begin()) ? *breaks.rbegin() : *std::prev(it);
            remove(getLen(p, next));
            add(getLen(p, i));
            add(getLen(i, next));
        }
        breaks.insert(i);
    }

    void eraseBreak(int i) {
        if (breaks.size() == 1) {
            cnt.clear();
            add(n);
        } else if (breaks.size() == 2) {
            int p = (i == *breaks.begin()) ? *breaks.rbegin() : *breaks.begin();
            remove(getLen(p, i));
            remove(getLen(i, p));
            cnt.clear();
            add(n);
        } else {
            auto it = breaks.find(i);
            auto nit = std::next(it);
            int next = (nit == breaks.end()) ? *breaks.begin() : *nit;
            int p = (it == breaks.begin()) ? *breaks.rbegin() : *std::prev(it);
            remove(getLen(p, i));
            remove(getLen(i, next));
            add(getLen(p, next));
        }
        breaks.erase(i);
    }

public:
    vector<int> numberOfAlternatingGroups(vector<int>& colors, vector<vector<int>>& queries) {
        n = colors.size();

        for (int i = 0; i < n; ++i) {
            if (colors[i] == colors[(i + 1) % n]) {
                breaks.insert(i);
            }
        }

        if (breaks.empty()) {
            add(n);
        } else {
            vector<int> b(breaks.begin(), breaks.end());
            int m = b.size();
            for (int i = 0; i < m; ++i) {
                int prev = b[i];
                int curr = b[(i + 1) % m];
                add(getLen(prev, curr));
            }
        }

        vector<int> ans;
        for (const auto& q : queries) {
            if (q[0] == 1) {
                int k = q[1];
                long long total = 0;
                // If the entire array is alternating
                if (breaks.empty()) {
                    total = n; 
                } else {
                    for (auto it = cnt.lower_bound(k); it != cnt.end(); ++it) {
                        total += (long long)it->second * (it->first - k + 1);
                    }
                }
                ans.push_back((int)total);
            } else {
                int idx = q[1];
                int newColor = q[2];
                if (colors[idx] == newColor) continue;

                int p1 = (idx - 1 + n) % n;
                int p2 = idx;

                bool b1 = (colors[p1] == colors[(p1 + 1) % n]);
                bool b2 = (colors[p2] == colors[(p2 + 1) % n]);

                if (b1) eraseBreak(p1);
                if (b2) eraseBreak(p2);

                colors[idx] = newColor;

                if (colors[p1] == colors[(p1 + 1) % n]) insertBreak(p1);
                if (colors[p2] == colors[(p2 + 1) % n]) insertBreak(p2);
            }
        }
        return ans;
    }
};