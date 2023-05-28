package game.characterControllers;

import game.ICollidable;
import game.ITickable;
import game.characters.Character;
import game.gameObjects.MovableGameObject;
import game.projectiles.Projectile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class CharacterController {
    public abstract Point2D.Double getVelocities();
    public abstract Point2D.Double getBulletVector(double x, double y);
}
