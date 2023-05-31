package game.projectiles;

import game.GameHandler;
import game.ICollidable;
import game.IDamageable;
import game.Utils;
import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.MovableGameObject;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class Projectile extends MovableGameObject {
    private GameObject creator;
    private int age;

    public Projectile(double x, double y, GameObject creator) {
        super(x, y);
        this.creator = creator;
    }

    @Override
    public void onCollide(ICollidable other) {
        switch (other) {
            case IDamageable d -> {
                if (d != creator) {
                    d.damageWith(this);
                    destroy();
                }
            }
            case Projectile p -> {
                destroy();
            }
            default -> { }
        }
    }

    @Override
    public void tick() {
        if (++age == 500) {
            destroy();
        } else {
            super.tick();
        }
    }

    public abstract int getDamage();
}
