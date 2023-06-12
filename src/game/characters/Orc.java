package game.characters;

import game.Element;
import game.mechanics.Mechanic;

import java.awt.*;

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

    @Override
    protected Element getWeaknessElement() {
        return Element.Ice;
    }
}
