import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class CellRenderer extends JLabel implements TableCellRenderer {
    public CellRenderer(){ setOpaque(true); setHorizontalAlignment(CENTER); }
    @Override public Component getTableCellRendererComponent(JTable table,Object value,boolean isSel,boolean hasFocus,int r,int c){
        int v=(Integer)value;
        switch(v){
            case Board.WATER  -> setBackground(new Color(0x004488));
            case Board.TURTLE -> setBackground(new Color(0x339933));
            case Board.HERO   -> setBackground(new Color(0xFFCC00));
            default           -> setBackground(Color.BLACK);
        }
        return this;
    }
}