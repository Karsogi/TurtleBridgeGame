import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameEventSupport {
    private final List<GameEventListener> listeners = new CopyOnWriteArrayList<>();

    public void addGameEventListener(GameEventListener listener) {
        if (listener != null) listeners.add(listener);
    }

    public void removeGameEventListener(GameEventListener listener) {
        listeners.remove(listener);
    }

    public void fire(GameEvent e) {
        for (GameEventListener listener : listeners) listener.handle(e);
    }
}
