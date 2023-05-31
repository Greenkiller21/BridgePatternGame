package game.characters;

import game.ICollidable;
import game.IDamageable;
import game.characterControllers.CharacterController;
import game.gameObjects.MovableGameObject;
import game.mechanics.Mechanic;
import game.projectiles.Projectile;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Character extends MovableGameObject implements IDamageable {
    private final static int COOLDOWN_TICK = 60;
    private int cooldownStatus = COOLDOWN_TICK;
    protected int health = 100;
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
        controller.drawHealthBar(g, this);
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

        ++cooldownStatus;
        if (cooldownStatus >= COOLDOWN_TICK) {
            Point2D.Double bigAttackVector = controller.getBigAttackVector(getX(), getY());
            if (bigAttackVector != null) {
                cooldownStatus = 0;
                mechanic.createBigAttack(this, bigAttackVector);
            } else {
                Point2D.Double smallAttackVector = controller.getSmallAttackVector(getX(), getY());
                if (smallAttackVector != null) {
                    cooldownStatus = 0;
                    mechanic.createSmallAttack(this, smallAttackVector);
                }
            }
        }

        super.tick();
    }

    @Override
    public void onCollide(ICollidable other) {
        switch (other) {
            case Projectile p -> {
                //System.out.println("projectile");
                //p.destroy();
            }
            case Character e -> {
                revert();
            }
            default -> throw new IllegalStateException("Unexpected value: " + other);
        }
    }

    @Override
    public void damageWith(Projectile p) {
        health -= p.getDamage();

        if (health <= 0) {
            controller.die(this);
        }
    }

    @Override
    public int getHealth() {
        return health;
    }
}
