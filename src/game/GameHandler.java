package game;

import game.gameObjects.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class GameHandler {
    private final LinkedList<GameObject> objects = new LinkedList<>();

    public void render(Graphics g) {
        for (GameObject obj : objects) {
            obj.render(g);
        }
    }

    public void tick() {
        for (GameObject obj : objects) {
            obj.tick();
        }
    }

    public void addGameObject(GameObject obj) {
        objects.add(obj);
    }

    public void removeGameObject(GameObject obj) {
        objects.remove(obj);
    }
}
