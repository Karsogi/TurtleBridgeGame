package game.model;

import game.event.EventBus;
import game.event.PlusOneEvent;
import game.event.ResetEvent;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoardModel extends AbstractTableModel {
    public static final int ROWS = 12, COLS = 5;
    public static final int WATER = 0, HERO_EMPTY = 1, HERO_FULL = 2, TURTLE = 3, FISH = 4;

    public static final int TURTLE_DIVING_DEPTH = 4;
    public static final int TURTLE_NORMAL_DEPTH = 1;
    public static final int TURTLE_DIVE_TICKS = 2; // how many ticks turtle stays underwater

    private final int[][] cells = new int[ROWS][COLS];

    private final List<Fish> fishes;
    private final List<Turtle> turtles;
    private final Hero hero;

    private int score = 0;

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
        if (newX == -1) {
            collectPackage();
        } else if (newX == COLS) {
            deliverPackage();
        } else if (newX >= 0 && newX < COLS && cells[hero.getRow()][newX] == WATER) {
            moveHeroToNewPosition(dx);
        } else {
            System.out.println("Cannot move hero to (" + hero.getRow() + ", " + newX + ")");
        }
    }

    private void collectPackage() {
        hero.collectPackage();
        put(hero.getRow(), hero.getCol(), hero.getCurrentState());
    }

    private void deliverPackage() {
        if (hero.deliverPackage()) {
            put(hero.getRow(), hero.getCol(), hero.getCurrentState());
            System.out.println("Package delivered!");
            incScore();
        } else {
            System.out.println("No package to deliver.");
        }
    }

    public void incScore() {
        score++;
        EventBus.fire(new PlusOneEvent(this));
    }

    private void moveHeroToNewPosition(int dx) {
        put(hero.getRow(), hero.getCol(), WATER);
        hero.move(dx);
        put(hero.getRow(), hero.getCol(), hero.getCurrentState());
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

    public void spawnFishesIfNeeded() {
        while (fishes.size() < wantedFishCount()) {
            Fish fish = new Fish(ROWS - 1, ThreadLocalRandom.current().nextInt(COLS));
            if (cells[fish.getRow()][fish.getCol()] == WATER) {
                fishes.add(fish);
                put(fish.getRow(), fish.getCol(), FISH);
            }
        }
    }

    private int wantedFishCount() {
        int n = score;
        int cnt = 0;
        if (n % 10 != 0) cnt++;
        if ((n / 10) % 10 != 0) cnt++;
        if ((n / 100) % 10 != 0) cnt++;
        return cnt;   // не меньше 1, не больше 3
    }

    public void updateTurtles() {
        for (Turtle turtle : turtles) {
            if (hasFishClose(turtle)) {
                catchFish(turtle);
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
        return (int) getValueAt(TURTLE_DIVING_DEPTH, turtle.col) == FISH;
    }

    private void catchFish(Turtle turtle) {
        put(turtle.getRow(), turtle.getCol(), WATER);
        turtle.catchFish();
        fishes.stream().filter(f -> f.getRow() == turtle.getRow() && f.getCol() == turtle.getCol()).findFirst()
                .ifPresent(fishes::remove);
        put(turtle.getRow(), turtle.getCol(), TURTLE);
    }

    private static boolean divedForFish(Turtle turtle) {
        return turtle.getRow() == TURTLE_DIVING_DEPTH;
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
        EventBus.fire(new ResetEvent(this));
        fishes.clear();
        turtles.clear();
        hero.reset();
        score = 0;

        Fish fish = new Fish(ROWS - 1, ThreadLocalRandom.current().nextInt(COLS));

        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                cells[r][c] = WATER;

        for (int c = 0; c < COLS; c++) {
            turtles.add(new Turtle(TURTLE_NORMAL_DEPTH, c));
            cells[1][c] = TURTLE;
        }

        cells[0][0] = hero.getCurrentState();
        System.out.println(hero.getCurrentState());
        cells[fish.getRow()][fish.getCol()] = FISH;
        fishes.add(fish);
        fireTableDataChanged();
    }

    private void put(int r, int c, int v) {
        cells[r][c] = v;
        fireTableCellUpdated(r, c);
    }
}
