package game.playerTypes;

import game.Game;
import game.ICollidable;
import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.MovableGameObject;
import game.projectiles.Bullet;
import game.projectiles.Projectile;

import java.awt.*;

public abstract class ControllableEntity extends MovableGameObject {
    protected Character character;

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
        //use damage / heal / ... and not onCollide

        /*switch (other) {
            case Projectile projectile -> character.damage(projectile);
            default ->
                    throw new IllegalStateException("Unexpected value: " + other);
        }*/

        character.onCollide(other);
    }

    @Override
    public Rectangle getCollider() {
        return character.getCollider();
    }
}
