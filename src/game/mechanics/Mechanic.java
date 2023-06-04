package game.mechanics;

import game.IRenderable;
import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Mechanic {
    public abstract void createBigAttack(GameObject creator, Point2D.Double dirVect);
    public abstract void createSmallAttack(GameObject creator, Point2D.Double dirVect);

    public Image getImage(int direction) {
        return getImages()[direction];
    }

    /**
     * Images of the character (W, A, S, D, Representation)
     * @return
     */
    protected abstract Image[] getImages();

    public Image getImage() {
        return getImages()[4];
    }
}
