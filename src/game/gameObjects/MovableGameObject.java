package game.gameObjects;

public abstract class MovableGameObject extends GameObject {
    protected double velX, velY;

    public MovableGameObject(int x, int y) {
        super(x, y);
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
}
