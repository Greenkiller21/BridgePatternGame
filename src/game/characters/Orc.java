package game.characters;

import game.Element;
import game.mechanics.Mechanic;

public class Orc extends Character {
    public Orc(double x, double y, Mechanic mechanic) {
        super(x, y, mechanic);
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
