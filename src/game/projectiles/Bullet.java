package game.projectiles;

import game.ICollidable;
import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Bullet extends Projectile {
    public Bullet(double x, double y, GameObject creator) {
        super(x, y, creator);
        bounds.width = 4;
        bounds.height = 4;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.yellow);
        g.drawRect((int)getX() + x, (int)getY() + y, (int)getCollider().width, (int)getCollider().height);
    }
}
