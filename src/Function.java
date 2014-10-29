class Function {
    static final double MIN_X = 0;
    static final double MAX_X = 13.5;
    static final double PLOT_WIDTH = MAX_X - MIN_X;
    static final double MIN_Y = -4.5;
    static final double MAX_Y = +0.5;
    static final double PLOT_HEIGHT = MAX_Y - MIN_Y;

    private Function() {
    }

    static final double f(double x) {
        assert MIN_X <= x && x <= MAX_X;
        return Math.sin(x) + x * x / 15.0 - x;
    }
}
