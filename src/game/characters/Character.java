package game.characters;

import game.interfaces.IDamageable;
import game.characterControllers.CharacterController;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.gameObjects.MovableGameObject;
import game.mechanics.Mechanic;
import game.projectiles.Projectile;
import game.screens.Game;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Character extends MovableGameObject implements IDamageable {
    private final static int COOLDOWN_TICK = 60;
    private int cooldownStatus = COOLDOWN_TICK;
    protected int health = 100;
    protected CharacterController controller;
    protected Mechanic mechanic;
    private int renderDirection;

    public Character(double x, double y, Mechanic mechanic) {
        super(x, y);
        this.mechanic = mechanic;
        renderDirection = 2;
    }

    public void setController(CharacterController controller) {
        this.controller = controller;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        updateRenderDirection();

        Image characterImg = getImages()[renderDirection];
        Image mechanicImg = mechanic.getImage(renderDirection);

        if (renderDirection == 0) { //W
            //Mechanic
            g.drawImage(mechanicImg, (int)getX() + x, (int)getY() + y, null);
        }

        //Character
        g.drawImage(characterImg, (int)getX() + x, (int)getY() + y, null);

        bounds.width = characterImg.getWidth(null);
        bounds.height = characterImg.getHeight(null);

        if (renderDirection != 0) { //ASD
            //Mechanic
            g.drawImage(mechanicImg, (int)getX() + x, (int)getY() + y, null);
        }

        //Healthbar
        controller.drawHealthBar(g, this);
    }

    private void updateRenderDirection() {
        if (getVelX() != 0 || getVelY() != 0) {
            if (getVelY() == getSpeed()) {
                renderDirection = 0; //W
            } else if (getVelX() == -getSpeed()) {
                renderDirection = 1; //A
            } else if (getVelY() == -getSpeed()) {
                renderDirection = 2; //S
            } else if (getVelX() == getSpeed()) {
                renderDirection = 3; //D
            } else {
                if (getVelY() > 0 && getVelX() > 0) { //WD
                    if (getVelY() > getVelX()) {
                        renderDirection = 0; //W
                    } else {
                        renderDirection = 3; //D
                    }
                } else if (getVelY() < 0 && getVelX() < 0) { //SA
                    if (-getVelY() > -getVelX()) {
                        renderDirection = 2; //S
                    } else {
                        renderDirection = 1; //A
                    }
                } else if (getVelY() > 0 && getVelX() < 0) { //WA
                    if (getVelY() > -getVelX()) {
                        renderDirection = 0; //W
                    } else {
                        renderDirection = 1; //A
                    }
                } else if (getVelY() < 0 && getVelX() > 0) { //SD
                    if (-getVelY() > getVelX()) {
                        renderDirection = 2; //S
                    } else {
                        renderDirection = 3; //D
                    }
                }
            }
        }
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

        Point2D.Double velocities = controller.getVelocities(this);
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

        double nextX = getX() + getVelX();
        double nextY = getY() - getVelY();

        if (nextX <= 0 || nextX + bounds.getWidth() >= Game.getInstance().getWidth()) {
            return;
        }

        if (nextY <= 0 || nextY + bounds.getHeight() >= Game.getInstance().getHeight()) {
            return;
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
