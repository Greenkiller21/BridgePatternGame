package game.interfaces;

import java.awt.*;

/**
 * Interface for elements that can be rendered
 */
public interface IRenderable {
    /**
     * Renders the element
     * @param g The graphics
     * @param x The x coord
     * @param y The y coord
     */
    void render(Graphics g, int x, int y);
}
