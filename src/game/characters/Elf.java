package game.characters;

import game.Element;
import game.mechanics.Mechanic;

import java.awt.*;

public class Elf extends Character {
    private static final Image[] images = Character.loadImages(Elf.class);

    public Elf(double x, double y, Mechanic mechanic) {
        super(x, y, mechanic);
    }

    protected Image[] getImages() {
        return images;
    }

    @Override
    protected double getSpeed() {
        return 2.5;
    }

    @Override
    protected Element getWeaknessElement() {
        return Element.Earth;
    }
}
