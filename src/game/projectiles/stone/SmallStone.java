package game.projectiles.stone;

import utils.ImageLoader;
import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;

public class SmallStone extends Stone {
    public SmallStone(double x, double y, Point2D.Double dirVect, GameObject creator) {
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
    public int getMaxDistance() {
        return super.getMaxDistance() + 50;
    }

    @Override
    protected Image getImage() {
        return ImageLoader.getImage("small", Stone.class);
    }
}
