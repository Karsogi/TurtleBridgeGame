package game.model;

public class Fish extends GameEntity {

    public Fish(int row, int col) {
        super(row, col);
    }

    public void up() {
        this.row -= 1;
    }
}
