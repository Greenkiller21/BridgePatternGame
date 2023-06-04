package game.characters;

import game.mechanics.Mechanic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Orc extends Character {
    private static final Image[] images = new Image[4];

    static {
        try {
            images[0] = ImageIO.read(new File("assets/orc/orc_w.png"));
            images[1] = ImageIO.read(new File("assets/orc/orc_a.png"));
            images[2] = ImageIO.read(new File("assets/orc/orc_s.png"));
            images[3] = ImageIO.read(new File("assets/orc/orc_d.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getImage() {
        return images[2];
    }

    public Orc(double x, double y, Mechanic mechanic) {
        super(x, y, mechanic);
    }

    protected Image[] getImages() {
        return images;
    }

    @Override
    protected double getSpeed() {
        return 1.8;
    }
}
