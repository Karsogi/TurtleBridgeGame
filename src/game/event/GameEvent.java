package game.event;

import java.util.EventObject;

/** Базовый класс всех игровых событий. */
public abstract class GameEvent extends EventObject {
    protected GameEvent(Object source) { super(source); }
}
