package game.characterControllers;

import game.Game;
import game.Utils;
import game.characters.Character;

import java.awt.*;
import java.awt.geom.Point2D;

public class AI extends CharacterController {
    @Override
    public Point2D.Double getVelocities() {
        return new Point2D.Double(0, 0);
    }

    @Override
    public Point2D.Double getBigAttackVector(double x, double y) {
        return getAttackVector(x, y, null);
    }

    @Override
    public Point2D.Double getSmallAttackVector(double x, double y) {
        return getAttackVector(x, y, null);
    }

    private Point2D.Double getAttackVector(double x, double y, Point aimLocation) {
        if (aimLocation != null) {
            Point2D.Double p = new Point2D.Double(aimLocation.x - x, y - aimLocation.y);
            return Utils.normalize(p);
        }
        return null;
    }

    @Override
    public void die(Character c) {
        c.destroy();
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
}
