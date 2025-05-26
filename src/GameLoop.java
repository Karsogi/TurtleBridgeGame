public class GameLoop extends Thread implements GameEventListener {
    private static final GameLoop INSTANCE = new GameLoop();

    public static GameLoop getInstance() {
        return INSTANCE;
    }

    private final GameEventSupport events = new GameEventSupport();
    private boolean running = false;
    private long tickInterval = 600; // names should be more descriptive, like `tickInterval`

    private GameLoop() {
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            if (running) {
                events.fire(new TickEvent(this));
                try {
                    sleep(tickInterval);
                } catch (InterruptedException ignored) {
                }
            } else {
                try {
                    sleep(50);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    /* ---------- обработка входящих событий ---------- */
    @Override
    public void handle(GameEvent e) {
        if (e instanceof StartEvent) {
            startLoop();
        } else if (e instanceof ResetEvent) {
            stopLoop();
            tickInterval = 600;
        } else if (e instanceof PlusOneEvent && tickInterval > 120) {
            tickInterval -= 50;
        }
    }

    /* ---------- управление циклом ---------- */
    private void startLoop() {
        if (!isAlive()) start();
        running = true;
    }

    private void stopLoop() {
        running = false;
    }

    /* ---------- доступ к «шине» ---------- */
    public void addGameEventListener(GameEventListener l) {
        events.addGameEventListener(l);
    }

    public void removeGameEventListener(GameEventListener l) {
        events.removeGameEventListener(l);
    }
}

