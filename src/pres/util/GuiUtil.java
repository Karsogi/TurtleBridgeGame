package pres.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuiUtil {

    /**
     * Прямой пересчёт размеров клеток
     */
    public static void scaleTable(JTable table, int rows, int cols, Dimension area) {
        if (area.width <= 0 || area.height <= 0) return;        // защита от нулевых размеров
        int cell = Math.min(area.width / cols, area.height / rows);
        if (cell < 1) cell = 1;                            // минимальная высота — 1 пиксель
        table.setRowHeight(cell);
        for (int col = 0; col < cols; col++) {
            table.getColumnModel().getColumn(col).setPreferredWidth(cell);
            table.getColumnModel().getColumn(col).setMinWidth(cell);
        }
    }

    /**
     * Вешает слушатель на viewport и автоматически подгоняет таблицу при ресайзе
     */
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
        // первичная подгонка
        scaleTable(table, rows, cols, target.getSize());
    }
}