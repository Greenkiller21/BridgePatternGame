package game;

import java.awt.*;

public interface ICollidable {
    Rectangle getCollider();
    void onCollide(ICollidable other);
}
