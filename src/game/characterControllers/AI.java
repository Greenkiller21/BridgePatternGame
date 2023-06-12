package game.characterControllers;

import game.screens.Game;
import utils.Utils;
import game.characters.Character;
import game.mechanics.Mechanic;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class AI extends CharacterController {
    private static final int BARS_WIDTH = 50;
    private static final int BARS_HEIGHT = 6;
    private static final int BARS_MARGIN = 1;

    private static final int MANABAR_Y = -5;
    private static final int HEALTHBAR_Y = MANABAR_Y - BARS_HEIGHT - 1;

    private boolean isFirstAttackNext = Utils.getRandom().nextBoolean();

    @Override
    public Point2D.Double getVelocities(Character current) {
        Character player = Game.getInstance().getGameHandler().getPlayer();
        Rectangle2D.Double playerCollider = player.getCollider();
        Point2D.Double moveLocation = new Point2D.Double(playerCollider.getCenterX(), playerCollider.getCenterY());

        double distToPlayer = moveLocation.distance(new Point2D.Double(current.getX(), current.getY()));
        if (distToPlayer >= 200 || distToPlayer <= 120) {
            return new Point2D.Double(0, 0);
        }

        Point2D.Double p = new Point2D.Double(moveLocation.x - current.getCollider().width / 2 - current.getX(), current.getY() + current.getCollider().height / 2 - moveLocation.y);
        return Utils.normalize(p);
    }

    @Override
    public Point2D.Double getFirstAttackVector(double x, double y) {
        return isFirstAttackNext ? getAttackVector(x, y) : null;
    }

    @Override
    public Point2D.Double getSecondAttackVector(double x, double y) {
        return !isFirstAttackNext ? getAttackVector(x, y) : null;
    }

    private Point2D.Double getAttackVector(double x, double y) {
        Character player = Game.getInstance().getGameHandler().getPlayer();

        Rectangle2D.Double playerCollider = player.getCollider();
        Point2D.Double aimLocation = new Point2D.Double(playerCollider.getCenterX(), playerCollider.getCenterY());
        double distToPlayer = aimLocation.distance(new Point2D.Double(x, y));
        if (distToPlayer >= 200) {
            return null;
        }

        Point2D.Double p = new Point2D.Double(aimLocation.x - x, y - aimLocation.y);
        isFirstAttackNext = Utils.getRandom().nextBoolean();
        return Utils.normalize(p);
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
    public Mechanic getMechanic() {
        return null;
    }
}
