package game.gameObjects;

/**
 * Game object with a velocity
 */
public abstract class MovableGameObject extends GameObject {
    protected double velX, velY;
    private double dx, dy;
    private double lastdx, lastdy;

    /**
     * Represents a game object with a velocity
     * @param x The x coord
     * @param y The y coord
     */
    public MovableGameObject(double x, double y) {
        super(x, y);
        lastdx = dx = x;
        lastdy = dy = y;

        convertCurrent();
    }

    /**
     * Returns the current x velocity
     * @return The current x velocity
     */
    public double getVelX() {
        return velX;
    }

    /**
     * Returns the current y velocity
     * @return The current y velocity
     */
    public double getVelY() {
        return velY;
    }

    @Override
    public void tick() {
        lastdx = dx;
        lastdy = dy;

        dx += velX;
        dy -= velY;

        convertCurrent();
    }

    /**
     * Reverts the movement
     */
    public void revert() {
        dx = lastdx;
        dy = lastdy;

        convertCurrent();
    }

    /**
     * Sets the current calculated x and y to the bounds of the game object
     */
    private void convertCurrent() {
        setX(dx);
        setY(dy);
    }
}
