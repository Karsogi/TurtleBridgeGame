import java.awt.Color;
import javax.swing.JPanel;



/** Верхняя панель со счётчиком из трёх семисегментных индикаторов. */
public class ScorePanel extends JPanel implements GameEventListener {

    private final SevenSegmentDigit[] digits = new SevenSegmentDigit[3];
    private int score = 0;

    public ScorePanel(Board board) {
        setBackground(Color.BLACK);
        for (int i = 0; i < 3; i++) {
            digits[i] = new SevenSegmentDigit();
            add(digits[i]);
        }
        updateDisplay();
        // подписываемся на события от Board (через шину board.eventSupport)
        board.addGameEventListener(this);
    }

    private void updateDisplay() {
        int val = score;
        for (int i = 2; i >= 0; i--) {
            digits[i].setValue(val % 10);
            val /= 10;
        }
    }

    @Override public void handle(GameEvent e) {
        if (e instanceof ResetEvent || e instanceof StartEvent) {
            score = 0;
        } else if (e instanceof PlusOneEvent) {
            score = Math.min(999, score + 1);
        }
        updateDisplay();
    }
}

