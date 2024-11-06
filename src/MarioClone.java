import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MarioClone extends JPanel implements ActionListener{
    private KeyHandler keyListener;
    private MouseHandler mousehandle;
    private Map map;
    private Cat cat;
    private int level;
    private Timer timer;
    private int time = 150;
    private int x = 50, y = 273; // ตำแหน่งเริ่มต้นของตัวละคร
    private boolean jumping = false;
    private int jumpHeight = 100;
    private int nowHeight = 100;
    private int gravity = 4; // แรงโน้มถ่วง
    private int speed = 4; // ความเร็วในการเคลื่อนที่
    private BufferedImage backgroundImage;
    public int gameState;

    private boolean movingLeft = false;
    private boolean movingRight = false;

    // ประตู
    private Rectangle door;
    private Rectangle block_level0, block1_level0; // block for level 0
    private Rectangle block_level1, block1_level1, block2_level1, block3_level1, block4_level1, block5_level1; // block for level 1
    private boolean onblock = false, onblock1 = false, onblock2 = false;
    private Rectangle block_level2, block1_level2, block2_level2, block3_level2, block4_level2, block5_level2, block6_level2; // block for level 1
    private boolean onblock_level2 = false, onblock1_level2 = false, onblock2_level2 = false, onblock3_level2 = false;


    private int life = 9;

    private boolean passedDoor = false;
    private boolean onBlock = false;
    private boolean underBlock = false;

    private int elapsedTime = 0;
    private int seconds = 0;  

    private boolean collision = false;

    public MarioClone() {
        map = new Map();
        cat = new Cat();

        gameState = 0;
        // resetDefault();

        try {
            backgroundImage = map.renderMAP(level);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // กำหนดตำแหน่งและขนาดของประตู
        door = new Rectangle(600, 277, 30, 50); // x, y, width, height

        // กำหนดขนาดของ block ในเลเวล 0
        block_level0 = new Rectangle(200, 302, 20, 25); // x, y, width, height
        block1_level0 = new Rectangle(400, 277, 70, 25); // x, y, width, height

        // กำหนดขนาดของ block ในเลเวล 1
        block_level1 = new Rectangle(150, 268, 50, 25);
        block1_level1 = new Rectangle(255, 252, 50, 25);
        block2_level1 = new Rectangle(315, 227, 12, 80);
        block3_level1 = new Rectangle(340, 252, 50, 25);
        block4_level1 = new Rectangle(405, 192, 12, 120);
        block5_level1 = new Rectangle(442, 212, 60, 25);

        // กำหนดขนาดของ block ในเลเวล 2
        block_level2 = new Rectangle(150, 248, 50, 25);
        block1_level2 = new Rectangle(265, 288, 80, 15);
        block2_level2 = new Rectangle(355, 242, 12, 70);
        block3_level2 = new Rectangle(375, 252, 50, 25);
        block4_level2 = new Rectangle(433, 205, 12, 100);
        block5_level2 = new Rectangle(475, 212, 35, 20);
        block6_level2 = new Rectangle(140, 325, 550, 3);

        timer = new Timer(1, this); // ปรับเฟรมเรท
        timer.start();
        keyListener = new KeyHandler(this);
        addKeyListener(keyListener);
        
        mousehandle = new MouseHandler(this);
        addMouseListener(mousehandle);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // วาดภาพพื้นหลัง
        if(gameState == 0){
            g.setColor(Color.black);
            g.fillRect(0, 0, 700, 430);
             
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 80));
            g.drawString("Cat with 9 Life", 80, 110);

            g.drawRect(265, 180, 125, 50);
            g.drawRect(265, 260, 125, 50);

            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("START", 278, 217);
            g.drawString("EXIT", 296, 296);
        }
        else if(gameState == 1){
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
    
            // วาดตัวละคร
            if(!jumping){
                if(movingLeft){
                    g.drawImage(cat.move.get(0),x, y - jumpHeight, 70, 70, this);
                }
                else{
                    g.drawImage(cat.move.get(1),x, y - jumpHeight, 70, 70, this);
                }
            }
            else{
                if(movingLeft){
                    g.drawImage(cat.move.get(2),x, y - jumpHeight, 70, 70, this);
                }
                else{
                    g.drawImage(cat.move.get(3),x, y - jumpHeight, 70, 70, this);
                }
            }
            
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            // วาดประตู
            if(level != 2){
                g.fillRect(door.x, door.y, door.width, door.height); // วาดประตู
            }
            else {
                g.fillRect(600, 130, door.width, door.height); // วาดประตู
                g.setColor(Color.white);
            }
            g.drawString("" + life, 68, 45);
    
            // add images
            //g.drawImage(backgroundImage, 50, 50, 100, 100, this);
    
            // วาดหัวใจ
            g.setColor(new Color(255, 30, 74));
            g.fillArc(20, 20, 20, 20, 0, 180); 
            g.fillArc(38, 20, 20, 20, 0, 180); 
    
            int[] xPoints = {20, 39, 58};
            int[] yPoints = {30, 50, 30};
            g.fillPolygon(xPoints, yPoints, 3);
            
            if(level == 0){
                g.setColor(Color.RED);
                g.fillRect(block_level0.x, block_level0.y, block_level0.width, block_level0.height);
                g.setColor(Color.WHITE);
                g.fillRect(block1_level0.x, block1_level0.y, block1_level0.width, block1_level0.height);
            }
            if(level == 1){
                g.setColor(Color.WHITE);
                g.fillRect(block_level1.x, block_level1.y, block_level1.width, block_level1.height);
                g.setColor(Color.WHITE);
                g.fillRect(block1_level1.x, block1_level1.y, block1_level1.width, block1_level1.height);
                g.setColor(Color.RED);
                g.fillRect(block2_level1.x, block2_level1.y, block2_level1.width, block2_level1.height);
                g.setColor(Color.WHITE);
                g.fillRect(block3_level1.x, block3_level1.y, block3_level1.width, block3_level1.height);
                g.setColor(Color.RED);
                g.fillRect(block4_level1.x, block4_level1.y, block4_level1.width, block4_level1.height);
                g.setColor(Color.WHITE);
                g.fillRect(block5_level1.x, block5_level1.y, block5_level1.width, block5_level1.height);
            }
            if(level == 2){
                g.setColor(Color.WHITE);
                g.fillRect(block_level2.x, block_level2.y, block_level2.width, block_level2.height);
                g.setColor(Color.WHITE);
                g.fillRect(block1_level2.x, block1_level2.y, block1_level2.width, block1_level2.height);
                g.setColor(Color.RED);
                g.fillRect(block2_level2.x, block2_level2.y, block2_level2.width, block2_level2.height);
                g.setColor(Color.WHITE);
                g.fillRect(block3_level2.x, block3_level2.y, block3_level2.width, block3_level2.height);
                g.setColor(Color.RED);
                g.fillRect(block4_level2.x, block4_level2.y, block4_level2.width, block4_level2.height);
                g.setColor(Color.WHITE);
                g.fillRect(block5_level2.x, block5_level2.y, block5_level2.width, block5_level2.height);
                g.setColor(Color.RED);
                g.fillRect(block6_level2.x, block6_level2.y, block6_level2.width, block6_level2.height);
            }
    
            g.setColor(Color.BLACK);
            if(level == 2){
                g.setColor(Color.white);
            }
            g.setFont(new Font("Arial", Font.ITALIC, 20));
            g.drawString("Time: " + (time - seconds) + " lefts", 515, 30);
            g.setFont(new Font("Arial", Font.ITALIC, 20));
            g.drawString("Level: " + level, 515, 50);

            if(collision){
                g.setColor(new Color(255,111,111,180));
                g.fillRect(0, 0, 700, 430);
                collision = false;
            }
        }
        else if(gameState == 2){
            g.setColor(Color.black);
            g.fillRect(0, 0, 700, 430);

            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 80));
            g.drawString("YOU WIN!!!", 120, 110);

            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Score : " + ((time - seconds) * (level + 1)), 255, 180);

            g.drawRect(150, 230, 200, 50);
            g.drawRect(380, 230, 115, 50);

            g.drawString("PLAY AGAIN", 163, 267);
            g.drawString("EXIT", 406, 266);
        }
        else if(gameState == 3){
            g.setColor(Color.black);
            g.fillRect(0, 0, 700, 430);

            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 80));
            g.drawString("GAME OVER!!!", 65, 110);

            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Score : " + (seconds * (level + 1)), 255, 180);

            g.drawRect(150, 230, 200, 50);
            g.drawRect(380, 230, 115, 50);

            g.drawString("PLAY AGAIN", 163, 267);
            g.drawString("EXIT", 406, 266);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameState == 1){
        if (jumping) {
            if(underBlock){
                if(jumpHeight < 50){
                    jumpHeight += 5;
                }
                else {
                    jumping = false;
                }
            }
            else {
                if(nowHeight >= 0){
                    jumpHeight += 5;
                    nowHeight -= 5;
                }
                else {
                    nowHeight = 100;
                    jumping = false;
                }
            }
        } else {
            if (jumpHeight > 0 && !onBlock) {
                jumpHeight -= gravity; // ใช้แรงโน้มถ่วง
            }
            else if(onBlock){
                if(level == 0){
                    if(jumpHeight > 50){
                        jumpHeight -= gravity;
                    }
                }
                else if(level == 1){
                    if(onblock){
                        if(jumpHeight > 56){
                            jumpHeight -= gravity;
                        }
                    }
                    if(onblock1){
                        if(jumpHeight > 75){
                            jumpHeight -= gravity;
                        }
                    }
                    if(onblock2){
                        if(jumpHeight > 116){
                            jumpHeight -= gravity;
                        }
                    }
                }
                else if(level == 2){
                    if(onblock_level2){
                        if(jumpHeight > 80){
                            jumpHeight -= gravity;
                        }
                    }
                    if(onblock1_level2){
                        if(jumpHeight > 38){
                            jumpHeight -= gravity;
                        }
                    }
                    if(onblock2_level2){
                        if(jumpHeight > 78){
                            jumpHeight -= gravity;
                        }
                    }
                    if(onblock3_level2){
                        if(jumpHeight > 118){
                            jumpHeight -= gravity;
                        }
                    }
                }
            }
        }

        // ตรวจสอบการเคลื่อนที่
        if (movingLeft) {
            x -= speed; // เคลื่อนที่ไปซ้าย
        }
        if (movingRight) {
            x += speed; // เคลื่อนที่ไปขวา
        }

        // ตรวจสอบการชนกับประตู
        if(life == 0 || (time - seconds) == 0){
            gameState = 3;
        }
        if(level != 2){
            if (x >= 546 && x <= 596 &&  jumpHeight <= 20 && !passedDoor) {
                level++;
                x = 50;
                y = 273;
                backgroundImage = map.renderMAP(level);
                passedDoor = true;
            }
        }
        else {
            if((x >= 546 && x <= 596 &&  jumpHeight >= 105 && jumpHeight <= 150 && !passedDoor)){
                gameState = 2;
            }
        }

        if(x <= -15){
            x = -15;
        }
        if(x >= 625){
            x = 625;
        }

        //level 0
        if(level == 0){
            // ชน
            if(x >= 150 && x <= 200 && jumpHeight <= 25){
                collision = true;
                x = 50;
                time-=10;
                life--;
            }

            // block2
            if(x >= 343 && x <= 390 && jumpHeight <= 25){
                x = 343;
                y = 273;
            }
            if(x <= 463 && x >= 400 && jumpHeight <= 25){
                x = 464;
                y = 273;
            }
            if(x >= 345 && x <= 461){
                onBlock = true;
            }
            else {
                onBlock = false;
            }
        }
        
        //level 1
        if(level == 1){
            //block 0
            if(x <= 180){
                if(x >= 96 && x <= 106 && jumpHeight <= 59){
                    x = 95;
                }
                if(x <= 182 && x >= 170 && jumpHeight <= 59){
                    x = 182;
                }

                if(x >= 97 && x <= 180){
                    onBlock = true;
                    onblock = true;
                }
                else {
                    onBlock = false;
                    onblock = false;
                }
            }

            //block 1
            else if (x <= 260){
                onblock = false;
                if(x >= 201 && x <= 220 && jumpHeight <= 70 && jumpHeight >= 10){
                    x = 200;
                }
                if(x > 203 && x <= 260 && jumping && jumpHeight >= 0 && jumpHeight <= 10){
                    jumping = false;
                    jumpHeight = 0;
                }
                else if(x > 203 && x <= 260 && jumpHeight >= 68){
                    onBlock = true;
                    onblock1 = true;
                }
                else {
                    onblock1 = false;
                    onBlock = false;
                }
            }

            else if(x < 322){
                onBlock = false;
                if(x >= 260 && x <= 320 && jumpHeight <= 100){
                    collision = true;
                    x = 50;
                    jumpHeight = 0;
                    time-=10;
                    life--;
                }
            }
            else if(x <= 346){
                if(x >= 288 && x <= 300 && jumpHeight <= 70 && jumpHeight >= 10){
                    x = 288;
                }
                if(x > 288 && x <= 371 && jumpHeight >= 68){
                    onBlock = true;
                    onblock1 = true;
                }
            }
            else if(x <= 411){
                onBlock = false;
                onblock1 = false;
                if(x >= 349 && x <= 413 && jumpHeight <= 120){
                    collision = true;
                    x = 50;
                    jumpHeight = 0;
                    time-=10;
                    life--;
                }
            }
            else if(x <= 500){
                if(x <= 488 && x >= 480 && jumpHeight > 50 && jumpHeight <= 120){
                    x = 488;
                }
                if(x >= 415 && x <= 487 && jumpHeight <= 50){
                    underBlock = true;
                }
                else if(x >= 415 && x <= 487 && jumpHeight >= 120){
                    onBlock = true;
                    onblock2 = true;

                }
                else {
                    underBlock = false;
                }
            }
            else {
                onBlock = false;
            }
        }
        
        //level 2
        if(level == 2){
            //floor
            if(x >= 90 && jumpHeight <= 0){
                collision = true;
                x = 50;
                time-=10;
                life--;
            }

            //block 0
            if(x <= 190){
                if(x >= 95 && x <= 110 && jumpHeight >= 30 && jumpHeight <= 80){
                    x = 95;
                }
                else if(x <= 186 && x >= 180 && jumpHeight >= 30 && jumpHeight <= 80){
                    x = 186;
                }
                else if(x >= 95 && x <= 185 && jumpHeight >= 81){
                    onblock_level2 = true;
                    onBlock = true;
                }
                else if(x >= 186 || x < 95){
                    onblock_level2 = false;
                    onBlock = false;
                }
            }
            else if(x <= 297){
                if(x >= 208 && x <= 215 && jumpHeight <= 36){
                    x = 208;
                }
                else if(x >= 208 && x <= 297 && jumpHeight > 37){
                    onBlock = true;
                    onblock1_level2 = true;
                }
                else if(x < 208 || x > 297){
                    onBlock = false;
                    onblock1_level2 = false;
                }
            }   
            else if(x < 358){
                onblock1_level2 = false;
                onblock2_level2 = true;
                if(x >= 298 && x <= 355 && jumpHeight <= 80){
                    collision = true;
                    x = 50;
                    jumpHeight = 0;
                    time-=10;
                    life--;
                }
            }
            else if(x <= 378){
                if(x >= 320 && x <= 374 && jumpHeight >= 81){
                    onBlock = true;
                    onblock2_level2 = true;
                }
                else if(x < 320 || x > 374){
                    onBlock = false;
                    onblock2_level2 = false;
                }
            }
            else if(x < 440){
                onblock2_level2 = false;
                onblock3_level2 = false;
                if(x >= 378 && x <= 430 && jumpHeight <= 120){
                    collision = true;
                    x = 50;
                    jumpHeight = 0;
                    time-=10;
                    life--;
                }
            }
            else if(x <= 520){
                if(x > 424 && x <= 434 && jumpHeight < 100){
                    x = 423;
                }
                else if(x >= 424 && x <= 494 && jumpHeight >= 100){
                    onBlock = true;
                    onblock3_level2 = true;
                }
                else if(x > 494 || x < 424){
                    onBlock = false;
                    onblock3_level2 = false;
                }
            }
            else {
                onBlock = false;
            }
        }
        //timer
        elapsedTime++;
        seconds = elapsedTime / 60;
        passedDoor = false;
    }
        repaint();
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void resetDefault(){
        level = 0;
        time = 150;
        x = 50;
        y = 273;
        jumping = false;
        jumpHeight = 100;
        nowHeight = 100;
        movingLeft = false;
        movingRight = false;
        life = 9;
        passedDoor = false;
        elapsedTime = 0;
        seconds = 0;
        backgroundImage = map.renderMAP(level);
        collision = false;
        timer.start();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Cat with 9 life");
        MarioClone game = new MarioClone();
        frame.add(game);
        frame.setSize(700, 430);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
