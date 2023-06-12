package game.characters;

import game.Element;
import utils.ImageLoader;
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
    private int firstAttackCooldownStatus = 0;
    private int secondAttackCooldownStatus = 0;
    protected int health = 100;
    protected CharacterController controller;
    protected Mechanic mechanic;
    private int renderDirection;

    public Character(double x, double y, Mechanic mechanic) {
        super(x, y);
        this.mechanic = mechanic;
        renderDirection = 2;

        firstAttackCooldownStatus = mechanic.firstAttackCooldown();
        secondAttackCooldownStatus = mechanic.secondAttackCooldown();

        updateBounds();
    }

    public void setController(CharacterController controller) {
        this.controller = controller;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        updateRenderDirection();
        updateBounds();

        Image characterImg = getImages()[renderDirection];
        Image mechanicImg = mechanic.getImage(renderDirection);

        if (renderDirection == 0) { //W
            //Mechanic
            g.drawImage(mechanicImg, (int)getX() + x, (int)getY() + y, null);
        }

        //Character
        g.drawImage(characterImg, (int)getX() + x, (int)getY() + y, null);

        if (renderDirection != 0) { //ASD
            //Mechanic
            g.drawImage(mechanicImg, (int)getX() + x, (int)getY() + y, null);
        }

        //Healthbar
        controller.drawHealthBar(g, this);
    }

    private void updateBounds() {
        Image characterImg = getImages()[renderDirection];
        bounds.width = characterImg.getWidth(null);
        bounds.height = characterImg.getHeight(null);
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

        boolean attackLaunched = false;
        ++firstAttackCooldownStatus;
        ++secondAttackCooldownStatus;

        if (firstAttackCooldownStatus >= mechanic.firstAttackCooldown()) {
            Point2D.Double firstAttackVector = controller.getFirstAttackVector(getX(), getY());
            if (firstAttackVector != null) {
                firstAttackCooldownStatus = 0;
                attackLaunched = true;
                mechanic.createFirstAttack(this, firstAttackVector);
            }
        }

        if (!attackLaunched && secondAttackCooldownStatus >= mechanic.secondAttackCooldown()) {
            Point2D.Double secondAttackVector = controller.getSecondAttackVector(getX(), getY());
            if (secondAttackVector != null) {
                secondAttackCooldownStatus = 0;
                mechanic.createSecondAttack(this, secondAttackVector);
            }
        }

        double nextX = getX() + getVelX();
        double nextY = getY() - getVelY();

        if (!isOutOfBounds(nextX, nextY)) {
            super.tick();
        }
    }

    @Override
    public void onCollide(GameObject other) {
        if (other.getType() == GameObjectType.Character) {
            revert();
        }
    }

    @Override
    public void damageWith(Projectile p) {
        double multiplier = p.getProjectileElement() == getWeaknessElement() ? 2 : 1;
        health -= multiplier * p.getDamage();

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

    protected static Image[] loadImages(Class<? extends Character> clazz) {
        Image[] images = new Image[4];
        images[0] = ImageLoader.getImage("w", clazz);
        images[1] = ImageLoader.getImage("a", clazz);
        images[2] = ImageLoader.getImage("s", clazz);
        images[3] = ImageLoader.getImage("d", clazz);
        return images;
    }

    /**
     * Images of the character (W, A, S, D)
     */
    protected abstract Image[] getImages();

    protected abstract double getSpeed();

    protected abstract Element getWeaknessElement();

    public boolean isOutOfBounds(double x, double y) {
        return x <= 0
            || y <= 0
            || x + bounds.getWidth() >= Game.getInstance().getWidth()
            || y + bounds.getHeight() >= Game.getInstance().getHeight();
    }
}
