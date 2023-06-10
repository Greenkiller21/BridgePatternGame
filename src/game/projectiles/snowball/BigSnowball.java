package game.projectiles.snowball;

import utils.ImageLoader;
import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;

public class BigSnowball extends Snowball {
    public BigSnowball(double x, double y, Point2D.Double dirVect, GameObject creator) {
        super(x, y, dirVect, creator);
    }

    @Override
    protected Image getImage() {
        return ImageLoader.getImage("big", Snowball.class);
    }
}
