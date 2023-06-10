package game.interfaces;

import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public interface ICollidable {
    Rectangle2D.Double getCollider();
    void onCollide(GameObject other);
}
