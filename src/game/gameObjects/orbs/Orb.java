package game.gameObjects.orbs;

import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import utils.ImageLoader;

import java.awt.*;
import java.util.Objects;

/**
 * The abstract class representing an orb
 */
public abstract class Orb extends GameObject {
    private Image image;

    /**
     * Constructor of an orb
     * @param x The x coord
     * @param y The y coord
     */
    public Orb(double x, double y) {
        super(x, y);

        updateBounds();
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.Orb;
    }

    @Override
    public void onCollide(GameObject other) {
        //If a character collides with this orb, we execute the action and then destroy the orb
        if (other.getType() == GameObjectType.Character) {
            Character character = (Character) other;
            doActionOnCollide(character);
            destroy();
        }
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getImage(), x + (int)getX(), y + (int)getY(), null);
    }

    @Override
    public void tick() { }

    /**
     * Update the bounds with the current image width
     */
    private void updateBounds() {
        bounds.width = getImage().getWidth(null);
        bounds.height = getImage().getHeight(null);
    }

    /**
     * Returns the image
     * @return The image
     */
    private Image getImage() {
        if (image == null) {
            image = ImageLoader.getImage(this.getClass().getSimpleName().toLowerCase(), Orb.class);
        }
        return image;
    }

    /**
     * The action to execute when colliding with a character
     * @param c The collided character
     */
    public abstract void doActionOnCollide(Character c);
}
