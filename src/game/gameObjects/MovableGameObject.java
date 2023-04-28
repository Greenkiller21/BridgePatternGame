package game.gameObjects;

public abstract class MovableGameObject extends GameObject {
    protected double velX, velY;
    private double dx, dy;

    public MovableGameObject(int x, int y) {
        super(x, y);
        dx = x;
        dy = y;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    @Override
    public void tick() {
        dx += velX;
        dy -= velY;

        x = (int)Math.round(dx);
        y = (int)Math.round(dy);
    }
}
