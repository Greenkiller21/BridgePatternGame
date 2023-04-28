package game.characters;

import game.ICollidable;
import game.weapons.Weapon;

import java.awt.*;

public class Elf extends Character {
    public Elf(Weapon weapon) {
        super(weapon);
    }

    @Override
    public void render(Graphics g, int x, int y) {
        //Draw here
        g.setColor(Color.GREEN);
        g.fillRect(x, y, getCollider().width, getCollider().height);

        super.render(g, x, y);
    }

    @Override
    public void onCollide(ICollidable other) {

    }

    @Override
    public Rectangle getCollider() {
        Rectangle collider = super.getCollider();
        collider.setSize(16, 23);
        return collider;
    }
}
