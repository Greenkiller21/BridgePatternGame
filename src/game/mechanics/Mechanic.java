package game.mechanics;

import utils.ImageLoader;
import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Abstract class representing a mechanic
 */
public abstract class Mechanic {
    private Image[] images;

    /**
     * Creates the first attack
     * @param creator The creator of the attack
     * @param dirVect The directional vector
     */
    public abstract void createFirstAttack(GameObject creator, Point2D.Double dirVect);

    /**
     * Creates the second attack
     * @param creator The creator of the attack
     * @param dirVect The directional vector
     */
    public abstract void createSecondAttack(GameObject creator, Point2D.Double dirVect);

    /**
     * The mana cost of the first attack
     * @return The mana cost
     */
    public abstract int firstAttackManaCost();

    /**
     * The mana cost of the second attack
     * @return The mana cost
     */
    public abstract int secondAttackManaCost();

    /**
     * Returns the image for a direction
     * @param direction The direction (W(0) A(1) S(2) D(3) I(4))
     * @return The image
     */
    public Image getImage(int direction) {
        return getImages()[direction];
    }

    /**
     * Images for the mechanic (W, A, S, D, Representation)
     * @return The images
     */
    private Image[] getImages() {
        if (images == null) {
            images = loadImages(this.getClass());
        }
        return images;
    }

    /**
     * The representation image (I)
     * @return The representation image
     */
    public Image getImage() {
        return getImages()[4];
    }

    /**
     * Loads all the images for a class
     * @param clazz The class
     * @return The images
     */
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
