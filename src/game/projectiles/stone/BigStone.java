package game.projectiles.stone;

import utils.ImageLoader;
import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Class representing a big stone projectile
 */
public class BigStone extends Stone {
    public BigStone(double x, double y, Point2D.Double dirVect, GameObject creator) {
        super(x, y, dirVect, creator);
    }

    @Override
    protected Image getImage() {
        return ImageLoader.getImage("big", Stone.class);
    }
}
