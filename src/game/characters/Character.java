package game.characters;

import game.ICollidable;
import game.IRenderable;
import game.weapons.Weapon;

import java.awt.*;

public abstract class Character implements IRenderable, ICollidable {
    protected Weapon weapon;
    private int lastX, lastY;

    public Character(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        lastX = x;
        lastY = y;
        weapon.render(g, x, y);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public Rectangle getCollider() {
        return new Rectangle(lastX, lastY, 0, 0);
    }
}
