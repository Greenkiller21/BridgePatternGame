package game.projectiles;

import game.GameHandler;
import game.ICollidable;
import game.Utils;
import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.MovableGameObject;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class Projectile extends MovableGameObject {
    private GameObject creator;

    public Projectile(double x, double y, GameObject creator) {
        super(x, y);
        this.creator = creator;
    }

    @Override
    public void onCollide(ICollidable other) {
        switch (other) {
            case Character c -> {
                if (c != creator) {
                    destroy();
                }
            }
            case Projectile p -> {
                destroy();
            }
            default -> { }
        }
    }
}
