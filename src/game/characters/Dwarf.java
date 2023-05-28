package game.characters;

import game.ICollidable;
import game.mechanics.Mechanic;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Dwarf extends Character {
    public Dwarf(double x, double y, Mechanic mechanic) {
        super(x, y, mechanic);
        bounds.width = 15;
        bounds.height = 15;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        //Draw here
        g.setColor(Color.BLACK);
        g.fillRect((int)getX() + x, (int)getY() + y, (int)bounds.width, (int)bounds.height);

        super.render(g, x, y);
    }
}
