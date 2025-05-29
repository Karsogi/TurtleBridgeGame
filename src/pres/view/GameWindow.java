package pres.view;

import game.GameLoop;
import game.ScorePanel;
import game.controller.BoardController;
import game.event.EventBus;
import game.event.ResetEvent;
import game.model.BoardModel;
import pres.util.GuiUtil;

import javax.swing.*;
import java.awt.*;


public class GameWindow extends JFrame {
    //    public GameWindow() {
//        super("Turtle Bridge – demo");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        Board board = new Board();
//        JTable table = new JTable(board);
//        table.setTableHeader(null);
//        table.setFocusable(false);
//        table.setRowSelectionAllowed(false);
//        table.setCellSelectionEnabled(false);
//        table.setShowGrid(false);
//        table.setDefaultRenderer(Object.class, new CellRenderer());
//
//        add(new ScorePanel(), BorderLayout.NORTH);
//        add(new JScrollPane(table), BorderLayout.CENTER);
//
//        GuiUtil.setupAutoScale(table, Board.ROWS, Board.COLS);
//
//        addKeyListener(board.createKeyListener());
//        setFocusable(true);
//        setSize(500, 600);
//        setLocationRelativeTo(null);
//
//        new DebugMatrixWindow(board).setVisible(true);
//    }
//
//    public void start() {
//        setVisible(true);
//    }
    public GameWindow() {
        super("Turtle Bridge – MVC demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        /* Model + View + Controller */
        BoardModel model = new BoardModel();
        GameBoardView table = new GameBoardView(model);
        new BoardController(model, this);           // controller на весь фрейм

        add(new ScorePanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        GuiUtil.setupAutoScale(table, BoardModel.ROWS, BoardModel.COLS);

        setSize(500, 600);
        setLocationRelativeTo(null);
        setFocusable(true);

        // стартуем GameLoop один раз
        GameLoop.getInstance();
        // Сразу шлём Reset для начального состояния ScorePanel
        EventBus.fire(new ResetEvent(this));
    }

    public void start() {
        setVisible(true);
    }
}


