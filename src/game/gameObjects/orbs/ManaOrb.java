package game.gameObjects.orbs;

import game.characters.Character;

/**
 * Class representing a mana orb
 */
public class ManaOrb extends Orb {
    public ManaOrb(double x, double y) {
        super(x, y);
    }

    @Override
    public void doActionOnCollide(Character c) {
        //Add 20 MP
        c.addMana(20);
    }
}
