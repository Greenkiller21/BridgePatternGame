package game.gameObjects.orbs;

import game.characters.Character;
import game.gameObjects.Orb;

public class HealthOrb extends Orb {
    public HealthOrb(double x, double y) {
        super(x, y);
    }

    @Override
    public void doActionOnCollide(Character c) {
        c.addHealth(20);
    }
}
