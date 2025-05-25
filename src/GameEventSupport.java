import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameEventSupport {
    private final List<GameEventListener> listeners = new CopyOnWriteArrayList<>();

    public void addGameEventListener(GameEventListener l) {
        if (l != null) listeners.add(l);
    }

    public void removeGameEventListener(GameEventListener l) {
        listeners.remove(l);
    }

    public void fire(GameEvent e) {
        for (GameEventListener l : listeners) l.handle(e);
    }
}
