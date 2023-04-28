package game.projectiles;

import game.ICollidable;

import java.awt.*;

public class Bullet extends Projectile {
    public Bullet(int x, int y) {
        super(x, y);
    }

    @Override
    public Rectangle getCollider() {
        Rectangle collider = super.getCollider();
        collider.setSize(4, 4);
        return collider;
    }

    @Override
    public void onCollide(ICollidable other) {

    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.yellow);
        g.drawRect(this.x + x, this.y + y, getCollider().width, getCollider().height);

        super.render(g, x, y);
    }
}
