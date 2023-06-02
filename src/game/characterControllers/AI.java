package game.characterControllers;

import game.Game;
import game.Utils;
import game.characters.Character;
import game.mechanics.Mechanic;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AI extends CharacterController {
    private final Random rdm = new Random();

    private boolean isBigAttackNext = rdm.nextBoolean();

    @Override
    public Point2D.Double getVelocities(double x, double y) {
        Character player = Game.getInstance().getGameHandler().getPlayer();
        Rectangle2D.Double playerCollider = player.getCollider();
        Point2D.Double moveLocation = new Point2D.Double(playerCollider.getCenterX(), playerCollider.getCenterY());

        double distToPlayer = moveLocation.distance(new Point2D.Double(x, y));
        if (distToPlayer >= 200 || distToPlayer <= 120) {
            return new Point2D.Double(0, 0);
        }

        Point2D.Double p = new Point2D.Double(moveLocation.x - x, y - moveLocation.y);
        return Utils.normalize(p);
    }

    @Override
    public Point2D.Double getBigAttackVector(double x, double y) {
        return isBigAttackNext ? getAttackVector(x, y) : null;
    }

    @Override
    public Point2D.Double getSmallAttackVector(double x, double y) {
        return !isBigAttackNext ? getAttackVector(x, y) : null;
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
        isBigAttackNext = rdm.nextBoolean();
        return Utils.normalize(p);
    }

    @Override
    public void drawHealthBar(Graphics g, Character c) {
        int hbWidth = 50;
        int hbHeight = 6;
        int hbX = (int)c.getX();
        int hbY = (int)c.getY();

        int margin = 1;

        int xCharacterCenter = (int)(c.getCollider().width / 2);

        drawHealthBarReal(g, hbWidth, hbHeight, hbX + xCharacterCenter - (hbWidth / 2), hbY, margin, c.getHealth() / 100.);
    }

    @Override
    public Mechanic getMechanic() {
        return null;
    }
}
