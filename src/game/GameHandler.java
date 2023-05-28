package game;

import game.gameObjects.GameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;

public class GameHandler {
    private final LinkedList<GameObject> objects = new LinkedList<>();

    public void render(Graphics g, int x, int y) {
        for (GameObject obj : objects) {
            obj.render(g, x, y);
        }
    }

    public void tick() {
        /*LinkedList<GameObject> tempObjects = (LinkedList<GameObject>) objects.clone();

        for (GameObject obj : tempObjects) {
            obj.tick();
        }

        for (GameObject src : tempObjects) {
            for (GameObject dest : tempObjects) {
                if (src == dest) {
                    break;
                }
                if (src.getCollider().intersects(dest.getCollider())) {
                    src.onCollide(dest);
                    dest.onCollide(src);
                }
            }
        }*/
        LinkedList<GameObject> tempObjects = new LinkedList<>(objects);

        for (GameObject obj : tempObjects) {
            obj.tick();

            for (GameObject other : tempObjects) {
                if (obj == other) {
                    break;
                }
                Rectangle2D intersection = obj.getCollider().createIntersection(other.getCollider());
                if (!intersection.isEmpty()) {
                    /*int dx = 0;
                    int dy = 0;
                    if (intersection.getWidth() < intersection.getHeight()) {
                        if (obj.getCollider().getCenterX() < other.getCollider().getCenterX()) {
                            dx = (int) (intersection.getWidth() + 1);
                        } else {
                            dx = -((int) (intersection.getWidth() + 1));
                        }
                    } else {
                        if (obj.getCollider().getCenterY() < other.getCollider().getCenterY()) {
                            dy = (int) (intersection.getHeight() + 1);
                        } else {
                            dy = -((int) (intersection.getHeight() + 1));
                        }
                    }

                    obj.setX(obj.getX() + dx);
                    obj.setY(obj.getY() + dy);
                    other.setX(other.getX() - dx);
                    other.setY(other.getY() - dy);*/

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
}
