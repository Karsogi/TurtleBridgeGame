package game.model;

public class Hero extends GameEntity {

    private boolean hasPackage;
    private int currentState;

    public Hero(int row, int col) {
        super(row, col);
        this.hasPackage = true;
        this.currentState = BoardModel.HERO_FULL;
    }

    public void move(int dx) {
        this.col += dx;
    }

    public void drown() {
        this.row += 1;
    }

    public void reset() {
        this.row = 0;
        this.col = 0;
        this.hasPackage = true;
        this.currentState = BoardModel.HERO_FULL;
    }

    public boolean deliverPackage() {
        if (hasPackage) {
            currentState = BoardModel.HERO_EMPTY;
            hasPackage = false;
            return true;
        }
        return false;
    }

    public void collectPackage() {
        if (!hasPackage) {
            currentState = BoardModel.HERO_FULL;
            hasPackage = true;
        }
    }

    public boolean hasPackage() {
        return hasPackage;
    }

    public int getCurrentState() {
        return currentState;
    }
}
