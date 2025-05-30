package game.event;

/** Любой объект, который хочет слушать game.event.GameEvent. */
public interface GameEventListener {
    void handle(GameEvent e);
}