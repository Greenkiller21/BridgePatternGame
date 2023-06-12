package game.projectiles;

import game.Element;
import utils.Utils;
import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.gameObjects.MovableGameObject;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Projectile extends MovableGameObject {
    protected final GameObject creator;
    private final Point2D.Double created;

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
            case Character -> {
                Character character = (Character) other;
                if (creator != character) {
                    doActionOnCollide(creator, character);
                    destroy();
                }
            }
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

    public int getDamage() {
        return 20;
    }

    public double getSpeed() {
        return 1.5;
    }

    public int getMaxDistance() {
        return 200;
    }

    protected abstract Image getImage();

    public abstract Element getProjectileElement();

    public void doActionOnCollide(GameObject creator, Character collide) {
        collide.damageWith(this);
    }
}
