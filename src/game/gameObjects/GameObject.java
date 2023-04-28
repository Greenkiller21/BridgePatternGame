package game.gameObjects;

import game.Game;
import game.ICollidable;
import game.IRenderable;
import game.ITickable;

public abstract class GameObject implements IRenderable, ITickable, ICollidable {
    protected int x, y;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void destroy() {
        Game.getInstance().getGameHandler().removeGameObject(this);
    }
}
