package game.characters;

import game.Element;
import game.characterControllers.CharacterController;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.gameObjects.MovableGameObject;
import game.interfaces.IHealth;
import game.interfaces.IMana;
import game.mechanics.Mechanic;
import game.projectiles.Projectile;
import utils.ImageLoader;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Abstract class representing a character
 */
public abstract class Character extends MovableGameObject implements IHealth, IMana {
    /**
     * The cooldown for the mana regen in ticks
     */
    private static final int COOLDOWN_MANA_REGEN = 60;

    /**
     * The current cooldown for the mana regen
     */
    private int cooldownManaRegen = COOLDOWN_MANA_REGEN;

    /**
     * The mana (MP)
     */
    private int mana = 100;

    /**
     * The health (HP)
     */
    private int health = 100;

    /**
     * The character controller
     */
    private CharacterController controller;

    /**
     * The currently selected mechanic
     */
    private Mechanic mechanic;

    /**
     * The current render direction (W(0), A(1), S(2), D(3))
     */
    private int renderDirection;

    /**
     * The images
     */
    private Image[] images;

    /**
     * Constructor for the character
     * @param x The x coord
     * @param y The y coord
     * @param mechanic The mechanic
     */
    public Character(double x, double y, Mechanic mechanic) {
        super(x, y);
        this.mechanic = mechanic;
        renderDirection = 2; //S

        updateBounds();
    }

    /**
     * Sets the character controller
     * @param controller The character controller
     */
    public void setController(CharacterController controller) {
        this.controller = controller;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        //Update the render direction and the character bounds
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

        //Healthbar and mana bar
        controller.drawHealthBar(g, this);
        controller.drawManaBar(g, this);
    }

    /**
     * Updates the bounds of the character depending on the render direction
     */
    private void updateBounds() {
        Image characterImg = getImages()[renderDirection];
        bounds.width = characterImg.getWidth(null);
        bounds.height = characterImg.getHeight(null);
    }

    /**
     * Updates the render direction depending on the direction vector
     */
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
                    chooseDirection(0, 3); //W or D
                } else if (getVelY() < 0 && getVelX() < 0) { //SA
                    chooseDirection(2, 1); //S or A
                } else if (getVelY() > 0 && getVelX() < 0) { //WA
                    chooseDirection(0, 1); //W or A
                } else if (getVelY() < 0 && getVelX() > 0) { //SD
                    chooseDirection(2, 3); //S or D
                }
            }
        }
    }

    /**
     * Chooses the render direction based on the absolute values of the velocities
     * @param dirY The direction along the Y axis
     * @param dirX The direction along the X axis
     */
    private void chooseDirection(int dirY, int dirX) {
        if (Math.abs(getVelY()) > Math.abs(getVelX())) {
            renderDirection = dirY;
        } else {
            renderDirection = dirX;
        }
    }

    /**
     * Returns the current mechanic
     * @return The current mechanic
     */
    public Mechanic getMechanic() {
        return mechanic;
    }

    /**
     * Sets the mechanic
     * @param mechanic The mechanic
     */
    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    @Override
    public void tick() {
        //Character controller tick
        controller.tick(this);

        //If there is a new mechanic chosen, we set it
        Mechanic newMechanic = controller.getMechanic();
        if (newMechanic != null) {
            setMechanic(newMechanic);
        }

        //We get the movement velocities
        Point2D.Double velocities = controller.getVelocities();
        velX = getSpeed() * velocities.x;
        velY = getSpeed() * velocities.y;

        //The mana regeneration
        ++cooldownManaRegen;
        if (cooldownManaRegen >= COOLDOWN_MANA_REGEN) {
            cooldownManaRegen = 0;
            addMana(10);
        }

        //The attacks
        boolean attackLaunched = false;
        if (mana >= mechanic.firstAttackManaCost()) {
            Point2D.Double firstAttackVector = controller.getFirstAttackVector();
            if (firstAttackVector != null) {
                addMana(-mechanic.firstAttackManaCost());
                attackLaunched = true;
                mechanic.createFirstAttack(this, firstAttackVector);
            }
        }

        if (!attackLaunched && mana >= mechanic.secondAttackManaCost()) {
            Point2D.Double secondAttackVector = controller.getSecondAttackVector();
            if (secondAttackVector != null) {
                addMana(-mechanic.secondAttackManaCost());
                mechanic.createSecondAttack(this, secondAttackVector);
            }
        }

        //The movement
        double nextX = getX() + getVelX();
        double nextY = getY() - getVelY();

        if (!isOutOfBounds(nextX, nextY)) {
            super.tick();
        }
    }

    @Override
    public void onCollide(GameObject other) {
        //If the character collides with another character, we revert the movement
        if (other.getType() == GameObjectType.Character) {
            revert();
        }
    }

    /**
     * Damages the character with a projectile
     * @param p The projectile
     */
    public void damageWith(Projectile p) {
        //If the projectile element is the same as the character weakness, there is a 2 multiplier
        int multiplier = p.getProjectileElement() == getWeaknessElement() ? 2 : 1;

        //Remove health
        addHealth(-multiplier * p.getDamage());
    }

    @Override
    public void addHealth(int amount) {
        //We keep the health in the bounds [0, 100]
        health = Math.max(0, Math.min(100, health + amount));

        //If the health is 0, we destroy the character
        if (health == 0) {
            destroy();
        }
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void addMana(int amount) {
        //We keep the mana in the bounds [0, 100]
        mana = Math.max(0, Math.min(100, mana + amount));
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.Character;
    }

    /**
     * Load the movement images for the character (WASD)
     * @param clazz The class
     * @return The images
     */
    private static Image[] loadImages(Class<? extends Character> clazz) {
        Image[] images = new Image[4];
        images[0] = ImageLoader.getImage("w", clazz);
        images[1] = ImageLoader.getImage("a", clazz);
        images[2] = ImageLoader.getImage("s", clazz);
        images[3] = ImageLoader.getImage("d", clazz);
        return images;
    }

    /**
     * Returns the images of the character
     * @return The images of the character (W(0) A(1) S(2) D(2))
     */
    private Image[] getImages() {
        if (images == null) {
            images = loadImages(this.getClass());
        }
        return images;
    }

    /**
     * Returns the character speed
     * @return The speed of the character
     */
    protected abstract double getSpeed();

    /**
     * Returns the character weakness element
     * @return The character weakness element
     */
    protected abstract Element getWeaknessElement();
}
