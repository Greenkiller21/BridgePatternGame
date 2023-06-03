package game.characters;

import game.ICollidable;
import game.IDamageable;
import game.characterControllers.CharacterController;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
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
    private Image toRender;

    public Character(double x, double y, Mechanic mechanic) {
        super(x, y);
        this.mechanic = mechanic;
        toRender = getImages()[2];
    }

    public void setController(CharacterController controller) {
        this.controller = controller;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        if (getVelX() != 0 || getVelY() != 0) {
            if (getVelY() == getSpeed()) {
                toRender = getImages()[0]; //W
            } else if (getVelX() == -getSpeed()) {
                toRender = getImages()[1]; //A
            } else if (getVelY() == -getSpeed()) {
                toRender = getImages()[2]; //S
            } else if (getVelX() == getSpeed()) {
                toRender = getImages()[3]; //D
            } else {
                if (getVelY() > 0 && getVelX() > 0) { //WD
                    if (getVelY() > getVelX()) {
                        toRender = getImages()[0]; //W
                    } else {
                        toRender = getImages()[3]; //D
                    }
                } else if (getVelY() < 0 && getVelX() < 0) { //SA
                    if (-getVelY() > -getVelX()) {
                        toRender = getImages()[2]; //S
                    } else {
                        toRender = getImages()[1]; //A
                    }
                } else if (getVelY() > 0 && getVelX() < 0) { //WA
                    if (getVelY() > -getVelX()) {
                        toRender = getImages()[0]; //W
                    } else {
                        toRender = getImages()[1]; //A
                    }
                } else if (getVelY() < 0 && getVelX() > 0) { //SD
                    if (-getVelY() > getVelX()) {
                        toRender = getImages()[2]; //S
                    } else {
                        toRender = getImages()[3]; //D
                    }
                }
            }
        }

        g.drawImage(toRender, (int)getX() + x, (int)getY() + y, null);

        bounds.width = toRender.getWidth(null);
        bounds.height = toRender.getHeight(null);

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
        Mechanic newMechanic = controller.getMechanic();
        if (newMechanic != null) {
            setMechanic(newMechanic);
        }

        Point2D.Double velocities = controller.getVelocities(getX(), getY());
        velX = getSpeed() * velocities.x;
        velY = getSpeed() * velocities.y;

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
    public void onCollide(GameObject other) {
        switch (other.getType()) {
            case Character -> {
                revert();
            }
        }
    }

    @Override
    public void damageWith(Projectile p) {
        health -= p.getDamage();

        if (health <= 0) {
            destroy();
        }
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.Character;
    }

    /**
     * Images of the character (W, A, S, D)
     * @return
     */
    protected abstract Image[] getImages();

    protected abstract double getSpeed();
}
