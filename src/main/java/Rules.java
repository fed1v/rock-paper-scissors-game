public class Rules {
    int[][] rules;

    public Rules(String[] moves) {
        rules = new int[moves.length][moves.length];
        generateTable();
    }

    public void generateTable() {
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

    public void showTable() {
        for(int i = 0; i < rules.length; i++){
            for(int j = 0; j < rules[0].length; j++){
                System.out.printf("%3d", rules[i][j]);
            }
            System.out.println();
        }
    }

}
