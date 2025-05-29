package game.model;

public class Hero extends GameEntity {

    public Hero(int row, int col) {
        super(row, col);
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
    }
}
