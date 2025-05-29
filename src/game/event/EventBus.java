package game.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EventBus {

    private EventBus() {}

    private static final List<GameEventListener> listeners = new CopyOnWriteArrayList<>();

    public static void addGameEventListener(GameEventListener listener) {
        if (listener != null) listeners.add(listener);
    }

    public static void removeGameEventListener(GameEventListener listener) {
        listeners.remove(listener);
    }

    public static void fire(GameEvent e) {
        for (GameEventListener listener : listeners) listener.handle(e);
    }
}
