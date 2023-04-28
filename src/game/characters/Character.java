package game.characters;

import game.IRenderable;
import game.weapons.Weapon;

import java.awt.*;

public abstract class Character implements IRenderable {
    protected Weapon weapon;

    @Override
    public void render(Graphics g) {
        weapon.render(g);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
