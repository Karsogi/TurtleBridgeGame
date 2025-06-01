package game.event;

import java.util.EventObject;

public abstract class GameEvent extends EventObject {
    protected GameEvent(Object source) { super(source); }
}
