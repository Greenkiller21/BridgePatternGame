package game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public interface ICollidable {
    Rectangle2D.Double getCollider();
    void onCollide(ICollidable other);
}
