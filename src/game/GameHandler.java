package game;

import game.characters.Character;
import game.characters.Elf;
import game.characters.Orc;
import game.gameObjects.Background;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.gameObjects.Orb;
import game.gameObjects.orbs.HealthOrb;
import game.gameObjects.orbs.ManaOrb;
import game.interfaces.IRenderable;
import game.mechanics.magicMechanics.WoodMagicMechanic;
import utils.CharacterConstructor;
import game.mechanics.Mechanic;
import game.mechanics.magicMechanics.IceMagicMechanic;
import game.mechanics.physicalMechanics.SlingshotMechanic;
import game.screens.Game;
import utils.OrbConstructor;
import utils.Utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameHandler {
    private static final int MAX_ORBS = 5;
    private static final int ORB_SPAWN_COOLDOWN = 180;
    private int orbSpawnCooldown = 0;
    private final ConcurrentLinkedQueue<GameObject> objects = new ConcurrentLinkedQueue<>();
    private Character player;
    private static final Mechanic[] mechanics = { IceMagicMechanic.getInstance(), SlingshotMechanic.getInstance(), WoodMagicMechanic.getInstance()};
    private static final Map<Class<? extends Character>, CharacterConstructor> characters = new HashMap<>() {{
        put(Elf.class, Elf::new);
        put(Orc.class, Orc::new);
    }};
    private static final Map<Class<? extends Orb>, OrbConstructor> orbs = new HashMap<>() {{
        put(ManaOrb.class, ManaOrb::new);
        put(HealthOrb.class, HealthOrb::new);
    }};
    private IRenderable background;

    public void render(Graphics g, int x, int y) {
        background.render(g, x, y);

        for (GameObject obj : objects) {
            obj.render(g, x, y);
        }

        Mechanic[] mechanics = GameHandler.getMechanics();

        int mecWidth = 60;
        int mecHeight = 60;

        int mecBorder = 5;

        int mecSpacing = 5;

        int startY = Game.getInstance().getHeight() - mecHeight - 2 * mecSpacing;
        int startX = Game.getInstance().getWidth() - mechanics.length * (mecWidth + mecSpacing);

        Font oldFont = g.getFont();
        g.setFont(new Font(Utils.FONT_NAME, Font.BOLD, 15));

        Character player = Game.getInstance().getGameHandler().getPlayer();

        for (int i = 0; i < mechanics.length; ++i) {
            int cX = x + startX + i * (mecWidth + mecSpacing);
            int cY = y + startY;

            if (player.getMechanic() == mechanics[i]) {
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
            g.drawString(str, newCX + 2, newCY + Utils.getStringHeight(g, str));
        }

        g.setFont(oldFont);
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

        ++orbSpawnCooldown;
        if (orbSpawnCooldown >= ORB_SPAWN_COOLDOWN && Utils.getRandom().nextInt(3) == 0) {
            orbSpawnCooldown = 0;
            if (objects.stream().filter(go -> go.getType() == GameObjectType.Orb).count() < MAX_ORBS) {
                OrbConstructor constructor = GameHandler.getOrbs().values().stream().toList().get(Utils.getRandom().nextInt(0, GameHandler.getOrbs().size()));
                int screenWidth = Game.getInstance().getWidth();
                int screenHeight = Game.getInstance().getHeight();

                Orb orb;
                do {
                    double x = Utils.getRandom().nextInt(1, screenWidth);
                    double y = Utils.getRandom().nextInt(1, screenHeight);
                    orb = constructor.apply(x , y);
                } while (!orb.isGeneratedPositionValid());

                Game.getInstance().getGameHandler().addGameObject(orb);
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

    public static Mechanic[] getMechanics() {
        return mechanics;
    }

    public void reset() {
        objects.clear();
        player = null;
        background = new Background();
    }

    public ConcurrentLinkedQueue<GameObject> getGameObjects() {
        return objects;
    }

    public static Map<Class<? extends Character>, CharacterConstructor> getCharacters() {
        return characters;
    }

    public static Map<Class<? extends Orb>, OrbConstructor> getOrbs() {
        return orbs;
    }
}
