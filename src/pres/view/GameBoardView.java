package pres.view;

import game.model.BoardModel;
import pres.component.CellRenderer;

import javax.swing.*;

public class GameBoardView extends JTable {
    public GameBoardView(BoardModel model) {
        super(model);
        setTableHeader(null);
        setShowGrid(false);
        setRowSelectionAllowed(false);
        setFocusable(false);
        setDefaultRenderer(Object.class, new CellRenderer());
    }
}
