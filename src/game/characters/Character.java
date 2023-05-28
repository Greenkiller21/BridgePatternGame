package game.characters;

import game.ICollidable;
import game.IRenderable;
import game.characterControllers.CharacterController;
import game.gameObjects.MovableGameObject;
import game.mechanics.Mechanic;
import game.projectiles.Bullet;
import game.projectiles.Projectile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class Character extends MovableGameObject {
    protected CharacterController controller;
    protected Mechanic mechanic;

    public Character(double x, double y, Mechanic mechanic) {
        super(x, y);
        this.mechanic = mechanic;
    }

    public void setController(CharacterController controller) {
        this.controller = controller;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        mechanic.render(g, (int)getX() + x, (int)getY() + y);
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    @Override
    public void tick() {
        Point2D.Double velocities = controller.getVelocities();
        velX = velocities.x;
        velY = velocities.y;

        Point2D.Double bulletVector = controller.getBulletVector(getX(), getY());
        if (bulletVector != null) {
            Bullet b = new Bullet(getX(), getY());
            b.setVelX(bulletVector.x);
            b.setVelY(bulletVector.y);
        }

        super.tick();
    }

    @Override
    public void onCollide(ICollidable other) {
        switch (other) {
            case Projectile p -> {
                System.out.println("projectile");
                //p.destroy();
            }
            case Character e -> {
                System.out.println("COLLIDE STOP");
                setVelX(0);
                setVelY(0);
                revert();
            }
            default -> throw new IllegalStateException("Unexpected value: " + other);
        }
    }
}
