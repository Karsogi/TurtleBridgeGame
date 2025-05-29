package game.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoardModel extends AbstractTableModel {
    public static final int ROWS = 12, COLS = 5;
    public static final int WATER = 0, HERO = 1, TURTLE = 2, FISH = 3;

    private final int[][] cells = new int[ROWS][COLS];

    private final List<Fish> fishes;
    private final List<Turtle> turtles;
    private final Hero hero;

    public BoardModel() {
        fishes = new ArrayList<>(3);
        turtles = new ArrayList<>(5);
        hero = new Hero(0, 0);
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

    public void moveHero(int dx) {
        int newX = hero.getCol() + dx;
        if (newX < 0 || newX >= COLS) return;
        put(hero.getRow(), hero.getCol(), WATER);
        hero.move(dx);
        put(hero.getRow(), hero.getCol(), HERO);
    }

    public void raiseFish() {
        for (Fish fish : fishes) {
            if (fish.getRow() > 0) {
                put(fish.getRow(), fish.getCol(), WATER);
                fish.up();
                put(fish.getRow(), fish.getCol(), FISH);
            } else {
                put(fish.getRow(), fish.getCol(), WATER);
            }
        }
    }

//    public void updateTurtles() {           // мигают черепахи
//        for (int r = 1; r < ROWS; r++) {
//            for (int c = 0; c < COLS; c++) {
//                if (cells[r][c] == TURTLE) cells[r][c] = WATER;
//                else if (rnd.nextDouble() < .25) cells[r][c] = TURTLE;
//            }
//        }
//        fireTableDataChanged();
//    }

    public void reset() {
        fishes.clear();
        turtles.clear();
        hero.reset();

        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                cells[r][c] = WATER;

        for (int c = 0; c < COLS; c++) {
            turtles.add(new Turtle(1, c));
            cells[1][c] = TURTLE;
        }

        cells[0][0] = HERO;
        cells[ROWS - 1][ThreadLocalRandom.current().nextInt(COLS)] = FISH;
        fireTableDataChanged();
    }

    private void put(int r, int c, int v) {
        cells[r][c] = v;
        fireTableCellUpdated(r, c);
    }
}
