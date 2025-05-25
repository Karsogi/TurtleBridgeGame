/** Любой объект, который хочет слушать GameEvent. */
public interface GameEventListener {
    void handle(GameEvent e);
}