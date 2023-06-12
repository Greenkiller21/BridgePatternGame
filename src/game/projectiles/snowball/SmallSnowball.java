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
        return super.getSpeed() + 1.5;
    }

    @Override
    public int getDamage() {
        return super.getDamage() - 4;
    }

    @Override
    public int getMaxDistance() {
        return super.getMaxDistance() + 40;
    }

    @Override
    protected Image getImage() {
        return ImageLoader.getImage("small", Snowball.class);
    }
}
