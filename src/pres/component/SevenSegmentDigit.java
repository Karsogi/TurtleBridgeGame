package pres.component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;


public class SevenSegmentDigit extends JComponent {

    private static final Color ON_COLOR = new Color(0, 255, 0);
    private static final Color OFF_COLOR = new Color(0, 60, 0);

    private static final int[] DIGIT_MASKS = {
            0b1111110, // 0
            0b0110000, // 1
            0b1101101, // 2
            0b1111001, // 3
            0b0110011, // 4
            0b1011011, // 5
            0b1011111, // 6
            0b1110000, // 7
            0b1111111, // 8
            0b1111011  // 9
    };

    private int value = 0;

    public SevenSegmentDigit() {
        setPreferredSize(new Dimension(40, 60));
    }

    public void setValue(int v) {
        if (v < 0 || v > 9) v = 0;
        if (value != v) {
            value = v;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int thickness = Math.max(2, Math.min(width, height) / 10);

        int segmentWidth = width - 2 * thickness;
        int segmentHeight = (height - 3 * thickness) / 2;

        // precompute rectangles for 7 segments
        Rectangle2D[] seg = new Rectangle2D[7];
        // a
        seg[0] = new Rectangle2D.Double(thickness, 0, segmentWidth, thickness);
        // b
        seg[1] = new Rectangle2D.Double(width - thickness, thickness, thickness, segmentHeight);
        // c
        seg[2] = new Rectangle2D.Double(width - thickness, thickness + segmentHeight + thickness, thickness, segmentHeight);
        // d
        seg[3] = new Rectangle2D.Double(thickness, height - thickness, segmentWidth, thickness);
        // e
        seg[4] = new Rectangle2D.Double(0, thickness + segmentHeight + thickness, thickness, segmentHeight);
        // f
        seg[5] = new Rectangle2D.Double(0, thickness, thickness, segmentHeight);
        // g (middle)
        seg[6] = new Rectangle2D.Double(thickness, thickness + segmentHeight, segmentWidth, thickness);

        int mask = DIGIT_MASKS[value];
        for (int i = 0; i < 7; i++) {
            g2.setColor(((mask >> (6 - i)) & 1) == 1 ? ON_COLOR : OFF_COLOR);
            g2.fill(seg[i]);
        }
        g2.dispose();
    }
}