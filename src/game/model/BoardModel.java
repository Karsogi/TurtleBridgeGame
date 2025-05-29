package game.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoardModel extends AbstractTableModel {
    public static final int ROWS = 12, COLS = 5;
    public static final int WATER = 0, HERO = 1, TURTLE = 2, FISH = 3;

    public static final int TURTLE_DIVING_DEPTH = 3;
    public static final int TURTLE_DIVE_TICKS = 3; // how many ticks turtle stays underwater

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

    public void updateFish() {
        for (Fish fish : fishes) {
            raiseFish(fish);
        }
    }

    private void raiseFish(Fish fish) {
        put(fish.getRow(), fish.getCol(), WATER);
        fish.up();
        put(fish.getRow(), fish.getCol(), FISH);
    }

    public void updateTurtles() {
        for (Turtle turtle : turtles) {
            if (hasFishClose(turtle)) {
                catchFish(turtle);
                // spawn new fish
            } else {
                if (divedForFish(turtle)) {
                    System.out.println("Turtle stays for: " + turtle.diveTicks);
                    if (turtle.isTimeTorReturn()) {
                        returnTurtleToSurface(turtle);
                    } else {
                        turtle.diveTicks--;
                    }
                }
            }
        }
        fireTableDataChanged();
    }

    private boolean hasFishClose(Turtle turtle) {
        return (int) getValueAt(turtle.row + TURTLE_DIVING_DEPTH, turtle.col) == FISH;
    }

    private void catchFish(Turtle turtle) {
        put(turtle.getRow(), turtle.getCol(), WATER);
        turtle.catchFish();
        fishes.stream().filter(f -> f.getRow() == turtle.getRow() && f.getCol() == turtle.getCol()).findFirst()
                .ifPresent(fishes::remove);
        put(turtle.getRow(), turtle.getCol(), TURTLE);
    }

    private static boolean divedForFish(Turtle turtle) {
        return turtle.getRow() == 1 + TURTLE_DIVING_DEPTH;
    }

    private void returnTurtleToSurface(Turtle turtle) {
        put(turtle.getRow(), turtle.getCol(), WATER);
        turtle.returnToSurface();
        put(turtle.getRow(), turtle.getCol(), TURTLE);
    }

    public void updateHero() {
        if ((int) getValueAt(hero.row + 1, hero.col) == WATER) {
            System.out.println("HERO DROWNED!");
            reset();
        }
    }

    public void reset() {
        fishes.clear();
        turtles.clear();
        hero.reset();

        Fish fish = new Fish(ROWS - 1, ThreadLocalRandom.current().nextInt(COLS));

        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                cells[r][c] = WATER;

        for (int c = 0; c < COLS; c++) {
            turtles.add(new Turtle(1, c));
            cells[1][c] = TURTLE;
        }

        cells[0][0] = HERO;
        cells[fish.getRow()][fish.getCol()] = FISH;
        fishes.add(fish);
        fireTableDataChanged();
    }

    private void put(int r, int c, int v) {
        cells[r][c] = v;
        fireTableCellUpdated(r, c);
    }
}
