package game.model;

import static game.model.BoardModel.*;

public class Turtle extends GameEntity {

    protected int diveTicks = 0;

    public Turtle(int row, int col) {
        super(row, col);
    }

    public void catchFish() {
        this.row = TURTLE_DIVING_DEPTH;
        diveTicks = TURTLE_DIVE_TICKS - 1;
    }

    public void returnToSurface() {
        this.row = TURTLE_NORMAL_DEPTH;
    }

    public boolean isTimeTorReturn() {
        return diveTicks == 0;
    }
}
