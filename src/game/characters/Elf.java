package game.characters;

import game.Element;
import game.mechanics.Mechanic;

/**
 * Class representing an elf
 */
public class Elf extends Character {
    public Elf(double x, double y, Mechanic mechanic) {
        super(x, y, mechanic);
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
