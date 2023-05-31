package game;

import game.characters.Character;
import game.gameObjects.GameObject;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameHandler {
    private final ConcurrentLinkedQueue<GameObject> objects = new ConcurrentLinkedQueue<>();
    private Character player;
    private boolean isGameOver = false;

    public void render(Graphics g, int x, int y) {
        if (isGameOver) {
            g.setColor(Color.RED);
            Font old = g.getFont();
            g.setFont(old.deriveFont(Font.BOLD, 40));
            Utils.drawCenteredString(g, "Game over !", Game.getInstance().getWidth() / 2, Game.getInstance().getHeight() / 2, Game.getInstance().getWidth(), Game.getInstance().getHeight());
            g.setFont(old);
            return;
        }

        for (GameObject obj : objects) {
            obj.render(g, x, y);
        }
    }

    public void tick() {
        if (isGameOver) {
            return;
        }

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
    }

    public void addGameObject(GameObject obj) {
        objects.add(obj);
    }

    public void addPlayer(Character player) {
        this.player = player;
        addGameObject(player);
    }

    public Character getPlayer() {
        return player;
    }

    public void removeGameObject(GameObject obj) {
        objects.remove(obj);
    }

    public void gameOver() {
        isGameOver = true;
    }
}
