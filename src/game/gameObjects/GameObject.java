package game.gameObjects;

import game.Game;
import game.ICollidable;
import game.IRenderable;
import game.ITickable;

import java.awt.*;
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
}
