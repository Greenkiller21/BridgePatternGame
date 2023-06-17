package game.interfaces;

import game.gameObjects.GameObject;

import java.awt.geom.Rectangle2D;

/**
 * Interface for elements that can be collided against
 */
public interface ICollidable {
    /**
     * Returns the bounds (collider) of the element
     * @return The bounds of the element
     */
    Rectangle2D.Double getCollider();

    /**
     * Executed when the element collides against a game object
     * @param other The game object
     */
    void onCollide(GameObject other);
}
