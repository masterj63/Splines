import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PlotFrame {
    private final int WIDTH = 600;
    private final int HEIGHT = 500;
    private final Coordinates COORDS = new Coordinates(WIDTH, HEIGHT);
    private final int PLOT_PX_SZ = 4;
    private final int SPLINE_PX_SZ = 2;
    private final BufferedImage plotImage = new BufferedImage(600, 500, BufferedImage.TYPE_INT_ARGB);
    private final Color TRANSPARENT_COLOR = new Color(0f, 0f, 0f, 0f);
    private final Color PLOT_COLOR = new Color(0.4f, 0.2f, 0f, 0.25f);
    private final Color SPLINE_COLOR = new Color(0f, 0.4f, 0.2f, 0.5f);
    private final Color POINTS_COLOR = new Color(1f, 0.2f, 0.2f, 0.8f);
    private final List<Integer> pointList = new ArrayList<>();
    private final JFrame JFRAME;
    private BufferedImage splineImage = null;
    private boolean allowedToRepaint = false;

    PlotFrame() {
        JFRAME = new JFrame("Title");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                allowedToRepaint = true;
                JFRAME.repaint();
            }
        }).start();

        JPanel jPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!allowedToRepaint)
                    return;
                g.drawImage(plotImage, 0, 0, null);
                g.drawImage(splineImage, 0, 0, null);
            }
        };
        JFRAME.add(jPanel);

        JFRAME.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                pointList.add(e.getX());
                Collections.sort(pointList);

                if (pointList.size() >= 3)
                    drawSpline();

                drawPoints();
            }
        });

        JFRAME.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JFRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JFRAME.pack();
        JFRAME.setVisible(true);
        init();
        drawPlot();
    }

    private void drawPlot() {
        Graphics g = plotImage.getGraphics();
        g.setColor(PLOT_COLOR);

        for (int x = 0; x <= WIDTH; x++) {
            int y = COORDS.yPlotToFrame(Function.f(COORDS.xFrameToPlot(x)));
            g.fillRect(x, y, PLOT_PX_SZ, PLOT_PX_SZ);
            System.out.printf("function %d %d \n", x, y);
        }
    }

    private void drawSpline() {
        splineImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = splineImage.getGraphics();
        g.setColor(SPLINE_COLOR);

        Spline spline = new Spline(pointList, COORDS);

        for (int x = 0; x <= WIDTH; x++) {
            int y = COORDS.yPlotToFrame(
                    spline.approximate(COORDS.xFrameToPlot(x))
            );
            g.fillRect(x, y, SPLINE_PX_SZ, SPLINE_PX_SZ);
            System.out.printf("spline %d %d \n", x, y);
        }

        JFRAME.repaint();
    }

    private void drawPoints() {
        if (splineImage == null)
            splineImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        Graphics g = splineImage.getGraphics();
        g.setColor(POINTS_COLOR);
        for (int x : pointList) {
            int y = COORDS.yPlotToFrame(Function.f(COORDS.xFrameToPlot(x)));
            g.drawOval(x - SPLINE_PX_SZ * 2, y - SPLINE_PX_SZ * 2, SPLINE_PX_SZ * 4, SPLINE_PX_SZ * 4);
        }
        JFRAME.repaint();
    }

    private void init() {
    }
}
