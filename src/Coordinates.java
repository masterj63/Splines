final class Coordinates {
    final int FRAME_WIDTH, FRAME_HEIGHT;

    Coordinates(int frameWidth, int frameHeight) {
        this.FRAME_WIDTH = frameWidth;
        this.FRAME_HEIGHT = frameHeight;
    }

    double xFrameToPlot(int x) {
        return Function.PLOT_WIDTH * x / FRAME_WIDTH + Function.MIN_X;
    }

    double yFrameToPlot(int y) {
        return Function.PLOT_HEIGHT * (FRAME_HEIGHT - y) / FRAME_HEIGHT + Function.MIN_Y;
    }

    int yPlotToFrame(double y) {
        double t = FRAME_HEIGHT * (y - Function.MIN_Y) / Function.PLOT_HEIGHT;
        return FRAME_HEIGHT - (int) Math.round(t);
    }
}
