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

    public GameWindow() {
        super("Turtle Bridge – MVC demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        BoardModel model = new BoardModel();
        GameBoardView table = new GameBoardView(model);
        new BoardController(model, this);

        add(new ScorePanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        GuiUtil.setupAutoScale(table, BoardModel.ROWS, BoardModel.COLS);

        setSize(500, 600);
        setLocationRelativeTo(null);
        setFocusable(true);

        GameLoop.getInstance();
        EventBus.fire(new ResetEvent(this));
    }

    public void start() {
        setVisible(true);
    }
}


