package game.projectiles.snowball;

import game.Element;
import game.gameObjects.GameObject;
import game.projectiles.Projectile;

import java.awt.geom.Point2D;

/**
 * Abstract class representing a snowball projectile
 */
public abstract class Snowball extends Projectile {
    public Snowball(double x, double y, Point2D.Double dirVect, GameObject creator) {
        super(x, y, dirVect, creator);
    }

    @Override
    public Element getProjectileElement() {
        return Element.Ice;
    }
}
