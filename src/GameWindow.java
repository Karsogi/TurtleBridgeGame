import javax.swing.*;
import java.awt.*;


public class GameWindow extends JFrame {
    public GameWindow(){
        super("Turtle Bridge – demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Board board = new Board();
        JTable table = new JTable(board);
        table.setTableHeader(null);
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setShowGrid(false);
        table.setDefaultRenderer(Object.class, new CellRenderer());

        add(new ScorePanel(board), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        GuiUtil.setupAutoScale(table, Board.ROWS, Board.COLS);

        addKeyListener(board.createKeyListener());
        setFocusable(true);
        setSize(500,600);
        setLocationRelativeTo(null);

        new DebugMatrixWindow(board).setVisible(true);

        // connect events with loop
        GameLoop loop = GameLoop.getInstance();
        board.addGameEventListener(loop);
        loop.addGameEventListener(board);
    }
    public void start(){ setVisible(true);}
}


