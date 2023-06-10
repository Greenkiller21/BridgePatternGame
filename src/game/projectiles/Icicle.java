package game.projectiles;

import game.gameObjects.GameObject;

import java.awt.*;

public class Icicle extends Projectile {
    public Icicle(double x, double y, GameObject creator) {
        super(x, y, creator);
        bounds.width = 3;
        bounds.height = 5;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.blue);
        g.drawRect((int)getX() + x, (int)getY() + y, (int)getCollider().width, (int)getCollider().height);
    }

    @Override
    public int getDamage() {
        return 10;
    }

    @Override
    public int maxDistance() {
        return 100;
    }
}
