package game.projectiles;

import game.gameObjects.GameObject;

import java.awt.*;

public class Stone extends Projectile {
    public Stone(double x, double y, GameObject creator) {
        super(x, y, creator);
        bounds.width = 4;
        bounds.height = 4;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.yellow);
        g.drawRect((int)getX() + x, (int)getY() + y, (int)getCollider().width, (int)getCollider().height);
    }

    @Override
    public int getDamage() {
        return 15;
    }

    @Override
    public int maxDistance() {
        return 200;
    }
}
