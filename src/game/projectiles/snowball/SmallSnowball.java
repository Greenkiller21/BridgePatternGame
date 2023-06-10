package game.projectiles.snowball;

import utils.ImageLoader;
import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;

public class SmallSnowball extends Snowball {
    public SmallSnowball(double x, double y, Point2D.Double dirVect, GameObject creator) {
        super(x, y, dirVect, creator);
    }

    @Override
    public double getSpeed() {
        return super.getSpeed() + 1;
    }

    @Override
    public int getDamage() {
        return super.getDamage() - 5;
    }

    @Override
    protected Image getImage() {
        return ImageLoader.getImage("small", Snowball.class);
    }
}
