import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    private MarioClone game;

    public MouseHandler(MarioClone game){
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(game.gameState == 0){
            if(e.getX() >= 265 && e.getX() <= 390 && e.getY() >= 180 && e.getY() <= 230){
                game.gameState = 1;
            }
            else if (e.getX() >= 265 && e.getX() <= 390 && e.getY() >= 260 && e.getY() <= 310) {
                System.exit(0);
            }
        }
        else if(game.gameState == 2 || game.gameState == 3){
            if (e.getX() >= 150 && e.getX() <= 350 && e.getY() >= 230 && e.getY() <= 280) {
                game.gameState = 1;
                game.resetDefault();
            }
            else if (e.getX() >= 380 && e.getX() <= 495 && e.getY() >= 230 && e.getY() <= 280) {
                System.exit(0);  // ปิดแอปพลิเคชันเมื่อคลิกในกรอบ
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
