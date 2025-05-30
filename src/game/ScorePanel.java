package game;

import game.event.*;
import pres.component.SevenSegmentDigit;

import javax.swing.*;
import java.awt.*;

/** Верхняя панель со счётчиком из трёх семисегментных индикаторов. */
public class ScorePanel extends JPanel implements GameEventListener {

    private final SevenSegmentDigit[] digits = new SevenSegmentDigit[3];
    private int shown = 0;

    public ScorePanel() {
        setBackground(Color.BLACK);
        for (int i = 0; i < 3; i++) {
            digits[i] = new SevenSegmentDigit();
            add(digits[i]);
        }
        updateDisplay();
        EventBus.addGameEventListener(this);
    }

    private void updateDisplay() {
        int val = shown;
        for (int i = 2; i >= 0; i--) {
            digits[i].setValue(val % 10);
            val /= 10;
        }
    }

    @Override
    public void handle(GameEvent e) {
        if (e instanceof ResetEvent || e instanceof StartEvent) {
            shown = 0;
        } else if (e instanceof PlusOneEvent) {
            shown = Math.min(999, shown + 1);
        }
        updateDisplay();
    }
}

