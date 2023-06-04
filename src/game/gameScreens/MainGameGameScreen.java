package game.gameScreens;

import game.screens.Game;
import game.MasterWindow;
import game.Utils;
import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.mechanics.Mechanic;
import game.screens.GameOverScreen;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainGameGameScreen extends GameScreen {
    private final ConcurrentLinkedQueue<GameObject> objects;
    private final Character player;

    public MainGameGameScreen(ConcurrentLinkedQueue<GameObject> objects, Character player) {
        this.objects = objects;
        this.player = player;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        for (GameObject obj : objects) {
            obj.render(g, x, y);
        }

        Mechanic[] mechanics = Game.getInstance().getGameHandler().getMechanics();

        int mecWidth = 40;
        int mecHeight = 40;

        int mecSpacing = 5;

        int startY = Game.getInstance().getHeight() - mecHeight - 2 * mecSpacing;
        int startX = Game.getInstance().getWidth() - mechanics.length * (mecWidth + mecSpacing);

        for (int i = 0; i < mechanics.length; ++i) {
            int cX = x + startX + i * (mecWidth + mecSpacing);
            int cY = y + startY;

            g.setColor(Color.GRAY);
            g.fillRect(cX, cY, mecWidth, mecHeight);
            g.setColor(Color.BLACK);
            String str = String.valueOf((i + 1) % 10);
            g.drawString(str, cX + 1, cY + 1 + Utils.getStringHeight(g, str));

            g.drawImage(mechanics[i].getImage(), cX, cY, mecWidth, mecHeight, null);
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
            Game.getInstance().gameOver();
        } else if (objects.stream().filter(go -> go.getType() == GameObjectType.Character).toList().size() == 1) {
            Game.getInstance().win();
        }
    }
}