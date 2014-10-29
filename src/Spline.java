import java.util.List;

class Spline {
    private final double[] xs;
    private final double[] hs;
    private final double[] fs;

    private final double[] as;
    private final double[] bs;
    private final double[] cs;
    private final double[] ds;

    Spline(List<Integer> points) {
        int n = points.size();

        xs = new double[n];
        for (int i = 0; i < n; i++)
            xs[i] = points.get(i);

        hs = new double[n];
        for (int i = 1; i < n; i++)
            hs[i] = xs[i] - xs[i - 1];

        fs = new double[n];
        for (int i = 0; i < n; i++)
            fs[i] = Function.f(xs[i]);

        {
            double[] thomasVals = new double[n - 2];
            for (int i = 0; i < thomasVals.length; i++) {
                double t0 = (fs[i + 2] - fs[i + 1]) / hs[i + 2];
                double t1 = (fs[i + 1] - fs[i]) / hs[i + 1];
                thomasVals[i] = 6.0 * (t0 - t1);
            }

            double[] thomasBot = new double[n - 2];
            for (int i = 1; i < thomasBot.length; i++)
                thomasBot[i] = hs[1 + i];

            double[] thomasMid = new double[n - 2];
            for (int i = 0; i < thomasBot.length; i++)
                thomasMid[i] = 2.0 * (hs[1 + i] + hs[2 + i]);

            double[] thomasTop = new double[n - 2];
            for (int i = 0; i < thomasTop.length - 1; i++)
                thomasTop[i] = hs[2 + i];

            cs = Thomas.solve(thomasBot, thomasMid, thomasTop, thomasVals);
        }

        as = new double[n];
        for (int i = 1; i < as.length; i++)
            as[i] = fs[i];

        bs = new double[n];
        for (int i = 1; i < bs.length; i++) {
            double t0 = (fs[i] - fs[i - 1]) / hs[i];
            double t1 = (2.0 * cs[i] + cs[i - 1]) * hs[i] / 6.0;
            bs[i] = t0 + t1;
        }

        ds = new double[n];
        for (int i = 1; i < n; i++)
            ds[i] = (cs[i] - cs[i - 1]) / hs[i];
    }

    double approximate(double plotX) {
        int ind = -42;

        if (plotX <= xs[0])
            ind = 1;
        else if (xs[xs.length - 1] <= plotX)
            ind = xs.length - 1;
        else
            for (ind = 1; ind < xs.length; ind++)
                if (xs[ind - 1] <= plotX && plotX <= xs[ind])
                    break;

        assert ind != -42;

        double a = as[ind];
        double b = bs[ind];
        double c = cs[ind];
        double d = ds[ind];
        double delta = plotX - xs[ind];

        return a + delta * b + delta * delta * c / 2.0 + delta * delta * delta * d / 6.0;
    }
}
