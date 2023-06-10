package game.projectiles.stone;

import game.Element;
import game.gameObjects.GameObject;
import game.projectiles.Projectile;

import java.awt.geom.Point2D;

public abstract class Stone extends Projectile {
    public Stone(double x, double y, Point2D.Double dirVect, GameObject creator) {
        super(x, y, dirVect, creator);
    }

    @Override
    public int getMaxDistance() {
        return super.getMaxDistance() + 50;
    }

    @Override
    public Element getProjectileElement() {
        return Element.Earth;
    }
}
