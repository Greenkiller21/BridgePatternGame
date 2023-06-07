package game;

import game.characters.Character;
import game.gameObjects.Background;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.mechanics.Mechanic;
import game.mechanics.magicMechanics.IceMagicMechanic;
import game.mechanics.physicalMechanics.SlingshotMechanic;
import game.screens.Game;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameHandler {
    private final ConcurrentLinkedQueue<GameObject> objects = new ConcurrentLinkedQueue<>();
    private Character player;
    private final Mechanic[] mechanics = { new IceMagicMechanic(), new SlingshotMechanic() };
    private final IRenderable background = new Background();

    public void render(Graphics g, int x, int y) {
        background.render(g, x, y);

        for (GameObject obj : objects) {
            obj.render(g, x, y);
        }

        Mechanic[] mechanics = Game.getInstance().getGameHandler().getMechanics();

        int mecWidth = 60;
        int mecHeight = 60;

        int mecBorder = 5;

        int mecSpacing = 5;

        int startY = Game.getInstance().getHeight() - mecHeight - 2 * mecSpacing;
        int startX = Game.getInstance().getWidth() - mechanics.length * (mecWidth + mecSpacing);

        for (int i = 0; i < mechanics.length; ++i) {
            int cX = x + startX + i * (mecWidth + mecSpacing);
            int cY = y + startY;


            if (Game.getInstance().getGameHandler().getPlayer().getMechanic().getClass() == mechanics[i].getClass()) {
                g.setColor(Color.YELLOW);
                g.fillRect(cX, cY, mecWidth, mecHeight);
            }

            int newCX = cX + mecBorder;
            int newCY = cY + mecBorder;
            int newMecWidth = mecWidth - 2 * mecBorder;
            int newMecHeight = mecHeight - 2 * mecBorder;

            g.setColor(Color.GRAY);
            g.fillRect(newCX, newCY, newMecWidth, newMecHeight);

            g.drawImage(mechanics[i].getImage(), newCX, newCY, newMecWidth, newMecHeight, null);

            g.setColor(Color.BLACK);
            String str = String.valueOf((i + 1) % 10);
            g.drawString(str, newCX + 1, newCY + 1 + Utils.getStringHeight(g, str));
        }
    }

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
