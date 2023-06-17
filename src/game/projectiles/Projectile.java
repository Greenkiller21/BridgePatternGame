package game.projectiles;

import game.Element;
import utils.Utils;
import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.gameObjects.MovableGameObject;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * The projectile abstract class
 */
public abstract class Projectile extends MovableGameObject {
    /**
     * The game object that created this projectile
     */
    protected final GameObject creator;

    /**
     * The position of the projectile at its creation
     */
    private final Point2D.Double created;

    /**
     * Create a projectile
     * @param x The x coord
     * @param y The y coord
     * @param dirVect The directional vector
     * @param creator The creator
     */
    public Projectile(double x, double y, Point2D.Double dirVect, GameObject creator) {
        super(x, y);
        this.creator = creator;

        created = new Point2D.Double(x, y);
        bounds.width = getImage().getWidth(null);
        bounds.height = getImage().getHeight(null);

        Point2D.Double norm = Utils.normalize(dirVect);
        velX = norm.x * getSpeed();
        velY = norm.y * getSpeed();
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getImage(), (int)getX() + x, (int)getY() + y, null);
    }

    @Override
    public void onCollide(GameObject other) {
        switch (other.getType()) {
            //If the projectile collides with a character, and it is not its creator
            //we execute doActionOnCollide and then destroy the projectile
            case Character -> {
                Character character = (Character) other;
                if (creator != character) {
                    doActionOnCollide(creator, character);
                    destroy();
                }
            }
            //If the projectile collides with a projectile that has not the same
            //creator, we destroy the projectile
            case Projectile -> {
                Projectile projectile = (Projectile) other;
                if (projectile.creator != creator) {
                    destroy();
                }
            }
        }
    }

    @Override
    public void tick() {
        //If the projectile has travelled more that its max distance, we destroy it
        if (new Point2D.Double(getX(), getY()).distance(created) > getMaxDistance()) {
            destroy();
        } else {
            super.tick();
        }
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.Projectile;
    }

    /**
     * The base amount of damage that the projectile inflicts
     * @return The base amount of damage
     */
    public int getDamage() {
        return 20;
    }

    /**
     * The speed
     * @return The speed
     */
    public double getSpeed() {
        return 1.5;
    }

    /**
     * The max distance allowed
     * @return The max distance allowed
     */
    public int getMaxDistance() {
        return 200;
    }

    /**
     * Return the image to render
     * @return The image to render
     */
    protected abstract Image getImage();

    /**
     * Return the element of the projectile
     * @return The element
     */
    public abstract Element getProjectileElement();

    /**
     * The action to do when a projectile collides with a character
     * @param creator The creator of the projectile
     * @param collide The character collided
     */
    public void doActionOnCollide(GameObject creator, Character collide) {
        collide.damageWith(this);
    }
}
