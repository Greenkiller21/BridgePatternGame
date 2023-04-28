package game.projectiles;

import game.gameObjects.MovableGameObject;

import java.awt.*;

public abstract class Projectile extends MovableGameObject {
    private int lastX, lastY;

    public Projectile(int x, int y) {
        super(x, y);
    }

    @Override
    public void render(Graphics g, int x, int y) {
        lastX = x;
        lastY = y;
    }

    @Override
    public Rectangle getCollider() {
        return new Rectangle(lastX, lastY, 0, 0);
    }
}
