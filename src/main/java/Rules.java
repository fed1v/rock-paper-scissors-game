public class Rules {
    int[][] rules;

    public Rules(String[] moves) {
        rules = new int[moves.length][moves.length];
        generateRules();
    }

    public void generateRules() {
        int m = rules.length;
        int n = rules[0].length;

        int mid = m / 2;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) rules[i][j] = 0;
                if (i > j && i - j <= mid) rules[i][j] = 1;
                if (j > i && j - i > mid) rules[i][j] = 1;
                if (i != j && rules[i][j] == 0) rules[i][j] = -1;
            }
        }
    }
}
