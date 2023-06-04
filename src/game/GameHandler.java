package game;

import game.characters.Character;
import game.gameObjects.GameObject;
import game.mechanics.Mechanic;
import game.mechanics.magicMechanics.IceMagicMechanic;
import game.mechanics.physicalMechanics.SlingshotMechanic;
import game.gameScreens.MainGameGameScreen;
import game.gameScreens.GameScreen;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameHandler {
    private final ConcurrentLinkedQueue<GameObject> objects = new ConcurrentLinkedQueue<>();
    private Character player;
    private GameScreen currentGameScreen;
    private final Mechanic[] mechanics = { new IceMagicMechanic(), new SlingshotMechanic() };

    public void render(Graphics g, int x, int y) {
        currentGameScreen.render(g, x, y);
    }

    public void tick() {
        currentGameScreen.tick();
    }

    public MainGameGameScreen getNewGameScreen() {
        return new MainGameGameScreen(objects, player);
    }

    public void setCurrentScreen(GameScreen gameScreen) {
        currentGameScreen = gameScreen;
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

    public Mechanic[] getMechanics() {
        return mechanics;
    }
}
