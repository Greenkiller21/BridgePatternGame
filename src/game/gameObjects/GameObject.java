package game.gameObjects;

import game.screens.Game;
import game.interfaces.ICollidable;
import game.interfaces.IRenderable;
import game.interfaces.ITickable;

import java.awt.geom.Rectangle2D;

/**
 * Represents a game object
 */
public abstract class GameObject implements IRenderable, ITickable, ICollidable {
    /**
     * The bounds (or collider)
     */
    protected Rectangle2D.Double bounds = new Rectangle2D.Double();

    /**
     * Creates a new game object
     * @param x The x coord
     * @param y The y coord
     */
    public GameObject(double x, double y) {
        bounds.x = x;
        bounds.y = y;
    }

    /**
     * Returns the x coordinate
     * @return The x coordinate
     */
    public double getX() {
        return bounds.x;
    }

    /**
     * Sets the current x coord
     * @param x The new x coord
     */
    public void setX(double x) {
        bounds.x = x;
    }

    /**
     * Returns the y coordinate
     * @return The y coordinate
     */
    public double getY() {
        return bounds.y;
    }

    /**
     * Sets the current y coord
     * @param y The new y coord
     */
    public void setY(double y) {
        bounds.y = y;
    }

    /**
     * Destroys the current game object
     */
    public void destroy() {
        Game.getInstance().getGameHandler().removeGameObject(this);
    }

    @Override
    public Rectangle2D.Double getCollider() {
        return bounds;
    }

    /**
     * The type of the game object
     * @return The type of the game object
     */
    public abstract GameObjectType getType();

    /**
     * Whether is current game object is out of screen bounds or not
     * @param x The x coord to test
     * @param y The y coord to test
     * @return True if out of bounds, false otherwise
     */
    public boolean isOutOfBounds(double x, double y) {
        return x <= 0
                || y <= 0
                || x + bounds.getWidth() >= Game.getInstance().getWidth()
                || y + bounds.getHeight() >= Game.getInstance().getHeight();
    }

    /**
     * Returns true if the current position is not of bounds and is colliding with nothing
     * @return True if the current position is not of bounds and is colliding with nothing
     */
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
