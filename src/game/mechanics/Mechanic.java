package game.mechanics;

import utils.ImageLoader;
import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Mechanic {
    private Image[] images;

    public abstract void createFirstAttack(GameObject creator, Point2D.Double dirVect);
    public abstract void createSecondAttack(GameObject creator, Point2D.Double dirVect);
    public abstract int firstAttackManaCost();
    public abstract int secondAttackManaCost();

    public Image getImage(int direction) {
        return getImages()[direction];
    }

    /**
     * Images of the character (W, A, S, D, Representation)
     */
    private Image[] getImages() {
        if (images == null) {
            images = loadImages(this.getClass());
        }
        return images;
    }

    public Image getImage() {
        return getImages()[4];
    }

    private static Image[] loadImages(Class<? extends Mechanic> clazz) {
        Image[] images = new Image[5];
        images[0] = ImageLoader.getImage("w", clazz);
        images[1] = ImageLoader.getImage("a", clazz);
        images[2] = ImageLoader.getImage("s", clazz);
        images[3] = ImageLoader.getImage("d", clazz);
        images[4] = ImageLoader.getImage("I", clazz);
        return images;
    }
}
