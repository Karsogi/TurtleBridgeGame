package pres.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

/**
 * Полноценный семисегментный индикатор 0‑9.
 * Масштабируется под текущий размер компонента.
 */
public class SevenSegmentDigit extends JComponent {

    private static final long serialVersionUID = 1L; // TODO: Check if needed

    private static final Color ON_COLOR = new Color(0, 255, 0);
    private static final Color OFF_COLOR = new Color(0, 60, 0);

    // порядок битов: a b c d e f g (MSB → LSB)
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

    /**
     * Установить отображаемую цифру (0‑9).
     */
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
        int thickness = Math.max(2, Math.min(width, height) / 10); // толщина сегмента

        // размеры внутренних сегментов
        int segmentWidth = width - 2 * thickness;             // горизонтальная длина
        int segmentHeight = (height - 3 * thickness) / 2;       // вертикальная длина

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