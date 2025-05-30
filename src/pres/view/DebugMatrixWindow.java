package pres.view;

import game.model.BoardModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Независимое окно для просмотра текущей int‑матрицы game.Board.
 * Полезно при отладке: отображает raw‑коды ячеек.
 */
public class DebugMatrixWindow extends JFrame {

    public DebugMatrixWindow(BoardModel board) {
        super("Debug: Matrix");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTable raw = new JTable(board); // тот же TableModel
        raw.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        add(new JScrollPane(raw), BorderLayout.CENTER);

        setPreferredSize(new Dimension(220, 300));
        pack();
        setLocationByPlatform(true);
    }
}
