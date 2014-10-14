class Function {
    static final double MIN_X = 0;
    static final double MAX_X = 12;
    static final double MIN_Y = -4.5;
    static final double MAX_Y = +1.5;

    private Function() {
    }

    final double f(double x) {
        assert MIN_X <= x && x <= MAX_X;
        return Math.sin(x) + x * x / 15.0 - x;
    }
}
