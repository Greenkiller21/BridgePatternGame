package game.projectiles;

import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.gameObjects.MovableGameObject;

import java.awt.geom.Point2D;

public abstract class Projectile extends MovableGameObject {
    private final GameObject creator;
    private final Point2D.Double created;

    public Projectile(double x, double y, GameObject creator) {
        super(x, y);
        this.creator = creator;
        this.created = new Point2D.Double(x, y);
    }

    @Override
    public void onCollide(GameObject other) {
        switch (other.getType()) {
            case Character -> {
                Character character = (Character) other;
                if (creator != character) {
                    character.damageWith(this);
                    destroy();
                }
            }
            case Projectile -> {
                destroy();
            }
        }
    }

    @Override
    public void tick() {
        if (new Point2D.Double(getX(), getY()).distance(created) > maxDistance()) {
            destroy();
        } else {
            super.tick();
        }
    }

    public abstract int getDamage();

    @Override
    public GameObjectType getType() {
        return GameObjectType.Projectile;
    }

    public abstract int maxDistance();
}
