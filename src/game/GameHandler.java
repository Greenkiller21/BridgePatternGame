package game;

import game.characters.Character;
import game.gameObjects.GameObject;
import game.screens.GameScreen;
import game.screens.Screen;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameHandler {
    private final ConcurrentLinkedQueue<GameObject> objects = new ConcurrentLinkedQueue<>();
    private Character player;
    private Screen currentScreen;

    public void render(Graphics g, int x, int y) {
        currentScreen.render(g, x, y);
    }

    public void tick() {
        currentScreen.tick();
    }

    public GameScreen getNewGameScreen() {
        return new GameScreen(objects, player);
    }

    public void setCurrentScreen(Screen screen) {
        currentScreen = screen;
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
}
