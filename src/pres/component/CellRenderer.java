package pres.component;

import game.model.BoardModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class CellRenderer extends JLabel implements TableCellRenderer {
    public CellRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        int valueInt = (Integer) value;
        switch (valueInt) {
            case BoardModel.WATER -> setBackground(new Color(0x004488));
            case BoardModel.TURTLE -> setBackground(new Color(0x339933));
            case BoardModel.HERO_EMPTY -> setBackground(new Color(0xFFCC00));
            case BoardModel.HERO_FULL -> setBackground(new Color(0xF30606));
            case BoardModel.FISH -> setBackground(new Color(0xD01A63));
            default -> setBackground(Color.BLACK);
        }
        return this;
    }
}