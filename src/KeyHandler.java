import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private MarioClone game;

    public KeyHandler(MarioClone game) {
        this.game = game;  // รับพารามิเตอร์เกมเพื่อสามารถเข้าถึงตัวแปรต่าง ๆ ได้
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            game.setJumping(true); // เริ่มกระโดด
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            game.setMovingLeft(true); // เริ่มเคลื่อนที่ซ้าย
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            game.setMovingRight(true); // เริ่มเคลื่อนที่ขวา
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            game.setMovingLeft(false); // หยุดเคลื่อนที่ซ้าย
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            game.setMovingRight(false); // หยุดเคลื่อนที่ขวา
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
