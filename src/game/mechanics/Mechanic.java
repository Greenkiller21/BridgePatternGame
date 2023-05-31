package game.mechanics;

import game.IRenderable;
import game.gameObjects.GameObject;

import java.awt.geom.Point2D;

public abstract class Mechanic implements IRenderable {
    public abstract void createBigAttack(GameObject creator, Point2D.Double dirVect);
    public abstract void createSmallAttack(GameObject creator, Point2D.Double dirVect);
}
