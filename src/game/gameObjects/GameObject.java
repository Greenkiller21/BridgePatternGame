package game.gameObjects;

import game.screens.Game;
import game.interfaces.ICollidable;
import game.interfaces.IRenderable;
import game.interfaces.ITickable;

import java.awt.geom.Rectangle2D;

public abstract class GameObject implements IRenderable, ITickable, ICollidable {
    protected Rectangle2D.Double bounds = new Rectangle2D.Double();

    public GameObject(double x, double y) {
        bounds.x = x;
        bounds.y = y;
    }

    public double getX() {
        return bounds.x;
    }

    public void setX(double x) {
        bounds.x = x;
    }

    public double getY() {
        return bounds.y;
    }

    public void setY(double y) {
        bounds.y = y;
    }

    public void destroy() {
        Game.getInstance().getGameHandler().removeGameObject(this);
    }

    @Override
    public Rectangle2D.Double getCollider() {
        return bounds;
    }

    public abstract GameObjectType getType();

    public boolean isOutOfBounds(double x, double y) {
        return x <= 0
                || y <= 0
                || x + bounds.getWidth() >= Game.getInstance().getWidth()
                || y + bounds.getHeight() >= Game.getInstance().getHeight();
    }

    public boolean isGeneratedPositionValid() {
        if (isOutOfBounds(getX(), getY())) {
            return false;
        } else {
            for (GameObject o : Game.getInstance().getGameHandler().getGameObjects()) {
                if (getCollider().intersects(o.getCollider())) {
                    return false;
                }
            }
        }

        return true;
    }
}
