package game.model;

import static game.model.BoardModel.*;

public class Turtle extends GameEntity {

    protected int diveTicks = 0;
    protected int currentState;

    public Turtle(int row, int col) {
        super(row, col);
        this.currentState = TURTLE_HEAD_DOWN;
    }

    public void catchFish(int depth) {
        this.row = depth;
        diveTicks = TURTLE_DIVE_TICKS - 1;
    }

    public void returnToSurface() {
        this.row = TURTLE_NORMAL_DEPTH;
    }

    public boolean isTimeTorReturn() {
        return diveTicks == 0;
    }

    public int changeState() {
        if (this.row != TURTLE_NORMAL_DEPTH) {
            return currentState; // Cannot change state while diving
        }
        if (currentState == TURTLE_HEAD_DOWN) {
            currentState = TURTLE_HEAD_UP;
        } else {
            currentState = TURTLE_HEAD_DOWN;
        }
        return currentState;
    }
}
