package game.gameObjects.orbs;

import game.characters.Character;

/**
 * Class representing a health orb
 */
public class HealthOrb extends Orb {
    public HealthOrb(double x, double y) {
        super(x, y);
    }

    @Override
    public void doActionOnCollide(Character c) {
        //Add 20 HP
        c.addHealth(20);
    }
}
