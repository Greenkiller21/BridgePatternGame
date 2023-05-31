package game;

import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;

public class GameHandler {
    private final LinkedList<GameObject> objects = new LinkedList<>();
    private boolean isGameOver = false;

    public void render(Graphics g, int x, int y) {
        if (isGameOver) {
            g.drawString("GAME OVER", x, y);
            return;
        }

        for (GameObject obj : objects) {
            obj.render(g, x, y);
        }
    }

    public void tick() {
        LinkedList<GameObject> tempObjects = new LinkedList<>(objects);

        for (GameObject obj : tempObjects) {
            obj.tick();

            for (GameObject other : tempObjects) {
                if (obj == other) {
                    break;
                }
                if (obj.getCollider().intersects(other.getCollider())) {
                    obj.onCollide(other);
                    other.onCollide(obj);
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

    public void gameOver() {
        isGameOver = true;
    }
}
