package game.characterControllers;

import game.characters.Character;
import game.mechanics.Mechanic;
import game.mechanics.magicMechanics.WoodMagicMechanic;
import game.screens.Game;
import utils.Utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * AI controller
 */
public class AI extends CharacterController {
    /**
     * The attack cooldown in ticks
     */
    private static final int ATTACK_COOLDOWN = 60;

    /**
     * The current attack cooldown
     */
    private int attackCooldown = ATTACK_COOLDOWN;

    /**
     * The width of the progress bars
     */
    private static final int BARS_WIDTH = 50;

    /**
     * The height of the progress bars
     */
    private static final int BARS_HEIGHT = 6;

    /**
     * The border of the progress bars
     */
    private static final int BARS_MARGIN = 1;

    /**
     * The mana bar y coord
     */
    private static final int MANABAR_Y = -5;

    /**
     * The health bar y coord
     */
    private static final int HEALTHBAR_Y = MANABAR_Y - BARS_HEIGHT - 1;

    /**
     * Whether a first attack is next
     */
    private boolean isFirstAttackNext;

    /**
     * The attack vector
     */
    private Point2D.Double attackVector;

    /**
     * The velocities
     */
    private Point2D.Double velocities;

    @Override
    public Point2D.Double getVelocities() {
        return velocities;
    }

    @Override
    public Mechanic getMechanic() {
        return null; //The AI never changes its mechanic
    }

    @Override
    public Point2D.Double getFirstAttackVector() {
        return isFirstAttackNext ? attackVector : null;
    }

    @Override
    public Point2D.Double getSecondAttackVector() {
        return !isFirstAttackNext ? attackVector : null;
    }

    @Override
    public void drawHealthBar(Graphics g, Character c) {
        int hbX = (int)c.getX();
        int hbY = (int)c.getY();

        int xCharacterCenter = (int)(c.getCollider().width / 2);

        drawProgressBar(g, BARS_WIDTH, BARS_HEIGHT, hbX + xCharacterCenter - (BARS_WIDTH / 2), hbY + HEALTHBAR_Y, BARS_MARGIN, c.getHealth() / 100., Color.RED);
    }

    @Override
    public void drawManaBar(Graphics g, Character c) {
        int hbX = (int)c.getX();
        int hbY = (int)c.getY();

        int xCharacterCenter = (int)(c.getCollider().width / 2);

        drawProgressBar(g, BARS_WIDTH, BARS_HEIGHT, hbX + xCharacterCenter - (BARS_WIDTH / 2), hbY + MANABAR_Y, BARS_MARGIN, c.getMana() / 100., Color.BLUE);
    }

    @Override
    public void tick(Character c) {
        //Attack vector
        attackVector = null;
        ++attackCooldown;
        if (attackCooldown >= ATTACK_COOLDOWN) {
            Character player = Game.getInstance().getGameHandler().getPlayer();

            Rectangle2D.Double playerCollider = player.getCollider();
            Point2D.Double aimLocation = new Point2D.Double(playerCollider.getCenterX(), playerCollider.getCenterY());
            double distToPlayer = aimLocation.distance(new Point2D.Double(c.getX(), c.getY()));
            if (distToPlayer < 200) {
                attackCooldown = 0;

                Point2D.Double p = new Point2D.Double(aimLocation.x - c.getX(), c.getY() - aimLocation.y);
                isFirstAttackNext = Utils.getRandom().nextBoolean();

                //Avoid AI with WoodMagicMechanic to just wait to have full mana to cast heal while they are at max health
                if (!isFirstAttackNext && c.getMechanic().getClass() == WoodMagicMechanic.class && c.getHealth() == 100) {
                    isFirstAttackNext = true;
                }

                attackVector = Utils.normalize(p);
            }
        }

        //Velocities
        velocities = new Point2D.Double(0, 0);
        Character player = Game.getInstance().getGameHandler().getPlayer();
        Rectangle2D.Double playerCollider = player.getCollider();
        Point2D.Double moveLocation = new Point2D.Double(playerCollider.getCenterX(), playerCollider.getCenterY());

        double distToPlayer = moveLocation.distance(new Point2D.Double(c.getX(), c.getY()));
        if (distToPlayer > 120 && distToPlayer < 200) {
            Point2D.Double p = new Point2D.Double(moveLocation.x - c.getCollider().width / 2 - c.getX(), c.getY() + c.getCollider().height / 2 - moveLocation.y);
            velocities = Utils.normalize(p);
        }
    }
}
