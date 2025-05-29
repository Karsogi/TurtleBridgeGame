package game;

import game.event.*;

import javax.swing.table.AbstractTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Board extends AbstractTableModel implements GameEventListener {
    public static final int ROWS = 12, COLS = 5;
    public static final int EMPTY = 0, HERO = 1, TURTLE = 2, WATER = 3;

    private final int[][] cells = new int[ROWS][COLS];
    private int heroRow = 0, heroCol = 0;

    private final Random rnd = new Random();

    public Board() {
        reset();
    }

    @Override
    public int getRowCount() {
        return ROWS;
    }

    @Override
    public int getColumnCount() {
        return COLS;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return cells[row][column];
    }

    private void put(int row, int column, int value) {
        cells[row][column] = value;
        fireTableCellUpdated(row, column);
    }

    public KeyAdapter createKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        moveHero(-1);
                        break;
                    case KeyEvent.VK_D:
                        moveHero(1);
                        break;
                    case KeyEvent.VK_R:
                        reset();
                        break;
                    case KeyEvent.VK_S:
                        EventBus.fire(new StartEvent(this));
                        break;
                    case KeyEvent.VK_P:
                        EventBus.fire(new PlusOneEvent(this));
                        break;
                }
            }
        };
    }

    private void moveHero(int dx) {
        int nc = heroCol + dx; // what's nc? dx is delta x?
        if (nc < 0 || nc >= COLS) return;
        put(heroRow, heroCol, WATER);
        heroCol = nc;
        put(heroRow, heroCol, HERO);
    }

    public void reset() {
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++) cells[r][c] = WATER;

        for (int r = 1; r < ROWS; r++)
            for (int c = 0; c < COLS; c++) if (rnd.nextBoolean()) cells[r][c] = TURTLE;

        heroRow = 0;
        heroCol = 0;
        cells[0][0] = HERO;

        fireTableDataChanged();
        EventBus.fire(new ResetEvent(this));
    }

    public void addGameEventListener(GameEventListener listener) {
        EventBus.addGameEventListener(listener);
    }

    public void removeGameEventListener(GameEventListener listener) {
        EventBus.removeGameEventListener(listener);
    }

    @Override
    public void handle(GameEvent e) {
        if (e instanceof TickEvent) stepTurtles();
    }

    private void stepTurtles() {
        for (int row = 1; row < ROWS; row++)
            for (int col = 0; col < COLS; col++) {
                if (cells[row][col] == TURTLE) cells[row][col] = WATER;
                else if (cells[row][col] == WATER && rnd.nextDouble() < 0.25) cells[row][col] = TURTLE;
            }
        fireTableDataChanged();
    }
}
