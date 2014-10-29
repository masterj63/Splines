class Thomas {
    static double[] solve(double[] diagBot, double[] diagMid, double[] diagTop, double[] vals) {
        int n = diagBot.length;

        double[] p = new double[1 + n];
        double[] q = new double[1 + n];
        for (int i = 1; i < n; i++) {
            p[i] = diagTop[i] / (-diagMid[i] - diagBot[i] * p[i - 1]);
            q[i] = (diagBot[i] * q[i - 1] - vals[i]) / (-diagMid[i] - diagBot[i] * p[i - 1]);
        }

        double[] answer = new double[1 + n];
        answer[n] = q[n];
        for (int i = n - 1; i >= 1; i--)
            answer[i] = p[i] * answer[1 + i] + q[i];

        return answer;
    }
}
