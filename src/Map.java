import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map {
    ArrayList<BufferedImage> background = new ArrayList<>();
    Map(){
        try {
            background.add(ImageIO.read(getClass().getResourceAsStream("/map/background1.jpg")));
            background.add(ImageIO.read(getClass().getResourceAsStream("/map/background2.jpg")));
            background.add(ImageIO.read(getClass().getResourceAsStream("/map/background3.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage renderMAP(int level){
        return background.get(level);
    }
}
