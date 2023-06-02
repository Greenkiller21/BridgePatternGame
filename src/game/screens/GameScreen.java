package game.screens;

import game.Game;
import game.GameHandler;
import game.Utils;
import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameScreen extends Screen {
    private final ConcurrentLinkedQueue<GameObject> objects;
    private final Character player;

    public GameScreen(ConcurrentLinkedQueue<GameObject> objects, Character player) {
        this.objects = objects;
        this.player = player;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        for (GameObject obj : objects) {
            obj.render(g, x, y);
        }
    }

    @Override
    public void tick() {
        for (GameObject obj : objects) {
            obj.tick();

            for (GameObject other : objects) {
                if (obj == other) {
                    break;
                }
                if (obj.getCollider().intersects(other.getCollider())) {
                    obj.onCollide(other);
                    other.onCollide(obj);
                }
            }
        }

        if (!objects.contains(player)) {
            Game.getInstance().getGameHandler().setCurrentScreen(new GameOverScreen());
        } else if (objects.stream().filter(go -> go.getType() == GameObjectType.Character).toList().size() == 1) {
            Game.getInstance().getGameHandler().setCurrentScreen(new WinScreen());
        }
    }
}
