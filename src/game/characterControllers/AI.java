package game.characterControllers;

import game.GameHandler;
import game.mechanics.magicMechanics.WoodMagicMechanic;
import game.screens.Game;
import utils.Utils;
import game.characters.Character;
import game.mechanics.Mechanic;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class AI extends CharacterController {
    private static final int ATTACK_COOLDOWN = 60;
    private int attackCooldown = ATTACK_COOLDOWN;

    private static final int BARS_WIDTH = 50;
    private static final int BARS_HEIGHT = 6;
    private static final int BARS_MARGIN = 1;

    private static final int MANABAR_Y = -5;
    private static final int HEALTHBAR_Y = MANABAR_Y - BARS_HEIGHT - 1;

    private boolean isFirstAttackNext;
    private Point2D.Double attackVector;
    private Point2D.Double velocities;

    @Override
    public Point2D.Double getVelocities() {
        return velocities;
    }

    @Override
    public Mechanic getMechanic() {
        return null;
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
