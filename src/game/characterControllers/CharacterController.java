package game.characterControllers;

import game.characters.Character;
import game.mechanics.Mechanic;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Character controller
 */
public abstract class CharacterController {
    /**
     * Returns the new velocities
     * @return The new velocities
     */
    public abstract Point2D.Double getVelocities();

    /**
     * Returns the new mechanic
     * @return The new mechanic
     */
    public abstract Mechanic getMechanic();

    /**
     * Returns the new vector for the first attack
     * @return The new vector for the first attack
     */
    public abstract Point2D.Double getFirstAttackVector();

    /**
     * Returns the new vector for the second attack
     * @return The new vector for the second attack
     */
    public abstract Point2D.Double getSecondAttackVector();

    /**
     * Method to draw the health bar
     * @param g The graphics
     * @param c The character to draw the health bar of
     */
    public abstract void drawHealthBar(Graphics g, Character c);

    /**
     * Method to draw the mana bar
     * @param g The graphics
     * @param c The character to draw the mana bar of
     */
    public abstract void drawManaBar(Graphics g, Character c);

    /**
     * Draws a progress bar
     * @param g The graphics
     * @param pbWidth The progress bar width
     * @param pbHeight The progress bar height
     * @param pbX The x coord of the progress bar
     * @param pbY The y coord of the progress bar
     * @param margin The border size of the progress bar
     * @param currentHealthPercentage The current health bar percentage
     * @param foreground The foreground color of the progress bar
     */
    protected static void drawProgressBar(Graphics g, int pbWidth, int pbHeight, int pbX, int pbY, int margin, double currentHealthPercentage, Color foreground) {
        g.setColor(Color.GRAY);
        g.fillRect(pbX, pbY, pbWidth, pbHeight);

        g.setColor(foreground);
        g.fillRect(pbX + margin,pbY + margin, (int)((pbWidth - 2 * margin) * currentHealthPercentage), pbHeight - 2 * margin);
    }

    /**
     * Retrieves all the new values (executed each tick)
     * @param c The character
     */
    public abstract void tick(Character c);
}
