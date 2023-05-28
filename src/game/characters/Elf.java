package game.characters;

import game.ICollidable;
import game.mechanics.Mechanic;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Elf extends Character {
    public Elf(double x, double y, Mechanic mechanic) {
        super(x, y, mechanic);
        getCollider().width = 16;
        getCollider().height = 23;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        //Draw here
        g.setColor(Color.GREEN);
        g.fillRect((int)getX() + x, (int)getY() + y, (int)getCollider().width, (int)getCollider().height);

        super.render(g, x, y);
    }
}
