package game.characterControllers;

import game.characters.Character;
import game.mechanics.Mechanic;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class CharacterController {
    public abstract Point2D.Double getVelocities(Character current);
    public abstract Point2D.Double getFirstAttackVector(double x, double y);
    public abstract Point2D.Double getSecondAttackVector(double x, double y);
    public abstract void drawHealthBar(Graphics g, Character c);
    public abstract void drawManaBar(Graphics g, Character c);
    public abstract Mechanic getMechanic();
    protected void drawProgressBar(Graphics g, int hbWidth, int hbHeight, int hbX, int hbY, int margin, double currentHealthPercentage, Color foreground) {
        g.setColor(Color.GRAY);
        g.fillRect(hbX, hbY, hbWidth, hbHeight);

        g.setColor(foreground);
        g.fillRect(hbX + margin, hbY + margin, (int)((hbWidth - 2 * margin) * currentHealthPercentage), hbHeight - 2 * margin);
    }
}
