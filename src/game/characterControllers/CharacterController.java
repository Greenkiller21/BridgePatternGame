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
    public abstract Mechanic getMechanic();
    protected void drawHealthBarReal(Graphics g, int hbWidth, int hbHeight, int hbX, int hbY, int margin, double currentHealthPercentage) {
        g.setColor(Color.GRAY);
        g.fillRect(hbX, hbY, hbWidth, hbHeight);

        g.setColor(Color.RED);
        g.fillRect(hbX + margin, hbY + margin, (int)((hbWidth - 2 * margin) * currentHealthPercentage), hbHeight - 2 * margin);
    }
}
