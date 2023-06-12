package game.mechanics;

import utils.ImageLoader;
import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Mechanic {
    public abstract void createFirstAttack(GameObject creator, Point2D.Double dirVect);
    public abstract void createSecondAttack(GameObject creator, Point2D.Double dirVect);
    public abstract int firstAttackCooldown();
    public abstract int secondAttackCooldown();

    public Image getImage(int direction) {
        return getImages()[direction];
    }

    /**
     * Images of the character (W, A, S, D, Representation)
     */
    protected abstract Image[] getImages();

    public Image getImage() {
        return getImages()[4];
    }

    protected static Image[] loadImages(Class<? extends Mechanic> clazz) {
        Image[] images = new Image[5];
        images[0] = ImageLoader.getImage("w", clazz);
        images[1] = ImageLoader.getImage("a", clazz);
        images[2] = ImageLoader.getImage("s", clazz);
        images[3] = ImageLoader.getImage("d", clazz);
        images[4] = ImageLoader.getImage("I", clazz);
        return images;
    }
}
