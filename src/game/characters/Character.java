package game.characters;

import game.ICollidable;
import game.IRenderable;
import game.mechanics.Mechanic;

import java.awt.*;

public abstract class Character implements IRenderable, ICollidable {
    protected Mechanic mechanic;
    private int lastX, lastY;

    public Character(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        lastX = x;
        lastY = y;
        mechanic.render(g, x, y);
    }

    public Mechanic getWeapon() {
        return mechanic;
    }

    public void setWeapon(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    @Override
    public Rectangle getCollider() {
        return new Rectangle(lastX, lastY, 0, 0);
    }
}
