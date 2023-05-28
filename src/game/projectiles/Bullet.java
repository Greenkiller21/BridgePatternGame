package game.projectiles;

import game.ICollidable;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Bullet extends Projectile {
    public Bullet(double x, double y) {
        super(x, y);
    }

    @Override
    public Rectangle2D.Double getCollider() {
        super.getCollider().width = 4;
        super.getCollider().height = 4;
        return super.getCollider();
    }

    @Override
    public void onCollide(ICollidable other) {

    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.yellow);
        g.drawRect((int)getX() + x, (int)getY() + y, (int)getCollider().width, (int)getCollider().height);

        super.render(g, x, y);
    }
}
