package pres.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuiUtil {

    private GuiUtil() {}

    public static void scaleTable(JTable table, int rows, int cols, Dimension area) {
        if (area.width <= 0 || area.height <= 0) return;
        int cell = Math.min(area.width / cols, area.height / rows);
        if (cell < 1) cell = 1;
        table.setRowHeight(cell);
        for (int col = 0; col < cols; col++) {
            table.getColumnModel().getColumn(col).setPreferredWidth(cell);
            table.getColumnModel().getColumn(col).setMinWidth(cell);
        }
    }

    public static void setupAutoScale(JTable table, int rows, int cols) {
        Component target = (table.getParent() instanceof JViewport)
                ? table.getParent()
                : table;

        target.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleTable(table, rows, cols, target.getSize());
            }
        });
        scaleTable(table, rows, cols, target.getSize());
    }
}