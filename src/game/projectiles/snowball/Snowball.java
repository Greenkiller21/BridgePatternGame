package game.projectiles.snowball;

import game.ImageLoader;
import game.gameObjects.GameObject;
import game.projectiles.Projectile;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Snowball extends Projectile {
    public Snowball(double x, double y, Point2D.Double dirVect, GameObject creator) {
        super(x, y, dirVect, creator);
    }
}
