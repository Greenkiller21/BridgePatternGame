package game.playerTypes;

import game.ICollidable;
import game.characters.Character;
import game.gameObjects.MovableGameObject;

import java.awt.*;

public abstract class ControllableEntity extends MovableGameObject {
    protected Character character;
    private double dx, dy;

    public ControllableEntity(int x, int y, Character character) {
        super(x, y);
        this.character = character;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        character.render(g, x + this.x, y + this.y);
    }

    @Override
    public void onCollide(ICollidable other) {
        character.onCollide(other);
    }

    @Override
    public Rectangle getCollider() {
        return character.getCollider();
    }

    @Override
    public void tick() {
        dx += velX;
        dy -= velY;

        x = (int)Math.round(dx);
        y = (int)Math.round(dy);
    }
}
