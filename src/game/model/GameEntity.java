package game.model;

public abstract class GameEntity {

    protected int row;
    protected int col;

    protected GameEntity(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
