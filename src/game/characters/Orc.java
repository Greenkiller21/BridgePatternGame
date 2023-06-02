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
            images[0] = ImageIO.read(new File("assets/orc_dos.png"));
            images[1] = ImageIO.read(new File("assets/orc_gauche.png"));
            images[2] = ImageIO.read(new File("assets/orc_face.png"));
            images[3] = ImageIO.read(new File("assets/orc_droite.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
