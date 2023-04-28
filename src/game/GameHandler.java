package game;

import game.gameObjects.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class GameHandler {
    private final LinkedList<GameObject> objects = new LinkedList<>();

    public void render(Graphics g, int x, int y) {
        for (GameObject obj : objects) {
            obj.render(g, x, y);
        }
    }

    public void tick() {
        LinkedList<GameObject> tempObjects = (LinkedList<GameObject>) objects.clone();

        for (GameObject obj : tempObjects) {
            obj.tick();
        }

        for (GameObject src : tempObjects) {
            for (GameObject dest : tempObjects) {
                if (src == dest) {
                    continue;
                }
                if (src.getCollider().intersects(dest.getCollider())) {
                    src.onCollide(dest);
                }
            }
        }
    }

    public void addGameObject(GameObject obj) {
        objects.add(obj);
    }

    public void removeGameObject(GameObject obj) {
        objects.remove(obj);
    }
}
