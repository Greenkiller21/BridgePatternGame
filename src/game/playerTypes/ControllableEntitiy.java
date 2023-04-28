package game.playerTypes;

import game.characters.Character;
import game.gameObjects.MovableGameObject;

import java.awt.*;

public abstract class ControllableEntitiy extends MovableGameObject {
    protected Character character;

    public ControllableEntitiy(int x, int y) {
        super(x, y);
    }

    @Override
    public void render(Graphics g) {
        character.render(g);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
