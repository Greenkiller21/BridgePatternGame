package game.characters;

import game.mechanics.Mechanic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Orc extends Character {
    private static final Image[] images = Character.loadImages(Orc.class);

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
