package game.projectiles;

import game.gameObjects.MovableGameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Projectile extends MovableGameObject {
    private double lastX, lastY;

    public Projectile(double x, double y) {
        super(x, y);
    }

    @Override
    public void render(Graphics g, int x, int y) {
        lastX = getX() + x;
        lastY = getY() + y;
    }

    @Override
    public Rectangle2D.Double getCollider() {
        return bounds;
    }
}
