package game.characters;

import game.ICollidable;
import game.mechanics.Mechanic;

import java.awt.*;

public class Dwarf extends Character {
    public Dwarf(Mechanic mechanic) {
        super(mechanic);
    }

    @Override
    public void render(Graphics g, int x, int y) {
        //Draw here
        g.setColor(Color.BLACK);
        g.fillRect(x, y, getCollider().width, getCollider().height);

        super.render(g, x, y);
    }

    @Override
    public void onCollide(ICollidable other) {

    }

    @Override
    public Rectangle getCollider() {
        Rectangle collider = super.getCollider();
        collider.setSize(15, 15);
        return collider;
    }
}
