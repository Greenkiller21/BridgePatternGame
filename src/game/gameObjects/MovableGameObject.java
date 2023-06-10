package game.gameObjects;

public abstract class MovableGameObject extends GameObject {
    protected double velX, velY;
    private double dx, dy;
    private double lastdx, lastdy;

    public MovableGameObject(double x, double y) {
        super(x, y);
        lastdx = dx = x;
        lastdy = dy = y;

        convertCurrent();
    }

    public double getVelX() {
        return velX;
    }

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

    public void revert() {
        dx = lastdx;
        dy = lastdy;

        convertCurrent();
    }

    private void convertCurrent() {
        setX(dx);
        setY(dy);
    }
}
