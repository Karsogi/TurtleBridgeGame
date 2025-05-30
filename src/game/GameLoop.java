package game;

import game.event.*;

public class GameLoop extends Thread implements GameEventListener {

    private static final GameLoop INSTANCE = new GameLoop();

    public static GameLoop getInstance() {
        return INSTANCE;
    }

    private boolean running = false;
    private long tickInterval = 450; // names should be more descriptive, like `tickInterval`

    private GameLoop() {
        System.out.println("GameLoop: init");
        EventBus.addGameEventListener(this);
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            if (running) {
                EventBus.fire(new TickEvent(this));
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
            System.out.println("GameLoop: start");
            startLoop();
        } else if (e instanceof ResetEvent) {
            stopLoop();
            tickInterval = 600;
        } else if (e instanceof PlusOneEvent && tickInterval > 120) {
            tickInterval -= 10;
        }
    }

    private void startLoop() {
        if (!isAlive()) start();
        running = true;
    }

    private void stopLoop() {
        running = false;
    }
}

