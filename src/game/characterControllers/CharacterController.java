package game.characterControllers;

import game.characters.Character;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class CharacterController {
    protected static final double SPEED = 2.0;

    public abstract Point2D.Double getVelocities();
    public abstract Point2D.Double getBigAttackVector(double x, double y);
    public abstract Point2D.Double getSmallAttackVector(double x, double y);
    public abstract void die(Character c);
    public abstract void drawHealthBar(Graphics g, Character c);

    protected void drawHealthBarReal(Graphics g, int hbWidth, int hbHeight, int hbX, int hbY, int margin, double currentHealthPercentage) {
        g.setColor(Color.GRAY);
        g.fillRect(hbX, hbY, hbWidth, hbHeight);

        g.setColor(Color.RED);
        g.fillRect(hbX + margin, hbY + margin, (int)((hbWidth - 2 * margin) * currentHealthPercentage), hbHeight - 2 * margin);
    }
}
