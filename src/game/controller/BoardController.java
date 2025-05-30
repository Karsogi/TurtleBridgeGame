package game.controller;

import game.event.*;
import game.model.BoardModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicInteger;

public final class BoardController implements GameEventListener {

    private final BoardModel model;
    private final AtomicInteger pendingDx = new AtomicInteger(0); // -1 / 0 / +1

    public BoardController(BoardModel model, Component focusTarget) {
        this.model = model;
        focusTarget.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {          // только буфер
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A -> pendingDx.set(-1);
                    case KeyEvent.VK_D -> pendingDx.set(+1);
                    case KeyEvent.VK_R -> {
                        model.reset();              // данные
                        EventBus.fire(new ResetEvent(this));
                    }
                    case KeyEvent.VK_S -> EventBus.fire(new StartEvent(this));
                }
            }
        });
        EventBus.addGameEventListener(this);                         // слушаем Tick
    }

    @Override
    public void handle(GameEvent e) {
        if (e instanceof TickEvent) {
            SwingUtilities.invokeLater(() -> {      // на EDT
                int dx = pendingDx.getAndSet(0);    // забираем + обнуляем
                if (dx != 0) model.moveHero(dx);
                model.updateFish();
                model.spawnFishesIfNeeded();
                model.updateTurtles();
                model.updateHero();
            });
        } else if (e instanceof ResetEvent) {
            pendingDx.set(0);                       // чистим буфер
        } else if (e instanceof PlusOneEvent) {

        }
    }
}

