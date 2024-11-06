import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Cat {
    public ArrayList<BufferedImage> move = new ArrayList<>();
    Cat(){
        try {
            move.add(ImageIO.read(getClass().getResourceAsStream("/cat/cat-left.png")));
            move.add(ImageIO.read(getClass().getResourceAsStream("/cat/cat-right.png")));
            move.add(ImageIO.read(getClass().getResourceAsStream("/cat/cat-left-jump.png")));
            move.add(ImageIO.read(getClass().getResourceAsStream("/cat/cat-right-jump.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
