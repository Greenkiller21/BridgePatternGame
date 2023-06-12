package game.gameObjects.orbs;

import game.characters.Character;
import game.gameObjects.Orb;

public class ManaOrb extends Orb {
    public ManaOrb(double x, double y) {
        super(x, y);
    }

    @Override
    public void doActionOnCollide(Character c) {
        c.addMana(20);
    }
}
