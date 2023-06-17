package game;

import game.characters.Character;
import game.characters.Elf;
import game.characters.Orc;
import game.gameObjects.Background;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.gameObjects.orbs.Orb;
import game.gameObjects.orbs.HealthOrb;
import game.gameObjects.orbs.ManaOrb;
import game.interfaces.IRenderable;
import game.interfaces.ITickable;
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

/**
 * Game handler
 */
public class GameHandler implements IRenderable, ITickable {
    /**
     * The max orbs that can be on the field at the same time
     */
    private static final int MAX_ORBS = 5;

    /**
     * The orb spawn cooldown in ticks
     */
    private static final int ORB_SPAWN_COOLDOWN = 180;

    /**
     * The current cooldown for the orb generation
     */
    private int orbSpawnCooldown = 0;

    /**
     * Concurrent list of all the game objects
     */
    private final ConcurrentLinkedQueue<GameObject> objects = new ConcurrentLinkedQueue<>();

    /**
     * The current player
     */
    private Character player;

    /**
     * The list of all the mechanics available
     */
    private static final Mechanic[] mechanics = { IceMagicMechanic.getInstance(), SlingshotMechanic.getInstance(), WoodMagicMechanic.getInstance()};

    /**
     * The map containing the characters (class + constructor function)
     */
    private static final Map<Class<? extends Character>, CharacterConstructor> characters = new HashMap<>() {{
        put(Elf.class, Elf::new);
        put(Orc.class, Orc::new);
    }};

    /**
     * The map containing the orbs (class + constructor function)
     */
    private static final Map<Class<? extends Orb>, OrbConstructor> orbs = new HashMap<>() {{
        put(ManaOrb.class, ManaOrb::new);
        put(HealthOrb.class, HealthOrb::new);
    }};

    /**
     * The current background image
     */
    private IRenderable background;

    @Override
    public void render(Graphics g, int x, int y) {
        //Render the background image
        background.render(g, x, y);

        //Render the game objects
        for (GameObject obj : objects) {
            obj.render(g, x, y);
        }

        //Render the mechanic list (bottom-right of the screen)
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

            //Selected mechanic
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

            //Key to press to select mechanic (top-left of the mechanic)
            g.setColor(Color.BLACK);
            String str = String.valueOf((i + 1) % 10);
            g.drawString(str, newCX + 2, newCY + Utils.getStringHeight(g, str));
        }

        g.setFont(oldFont);
    }

    @Override
    public void tick() {
        //Tick the game objects
        for (GameObject obj : objects) {
            obj.tick();

            for (GameObject other : objects) {
                if (obj == other) {
                    break; //We can stop here to avoid useless collision check
                }

                //Check for collisions
                if (obj.getCollider().intersects(other.getCollider())) {
                    obj.onCollide(other);
                    other.onCollide(obj);
                }
            }
        }

        //If the game objects doesn't have the player -> Game over
        if (!objects.contains(player)) {
            Game.getInstance().gameOver();
        //If the game objects only have one character left -> Win
        } else if (objects.stream().filter(go -> go.getType() == GameObjectType.Character).toList().size() == 1) {
            Game.getInstance().win();
        }

        //Orb spawning
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

    /**
     * Adds a game object to the list of game objects
     * @param obj The game object to add
     */
    public void addGameObject(GameObject obj) {
        objects.add(obj);
    }

    /**
     * Adds the player to the list of game objects
     * @param player The player
     */
    public void addPlayer(Character player) {
        this.player = player;
        addGameObject(player);
    }

    /**
     * Returns the player character
     * @return The player character
     */
    public Character getPlayer() {
        return player;
    }

    /**
     * Removes a game object from the current game objects
     * @param obj The game object to remove
     */
    public void removeGameObject(GameObject obj) {
        objects.remove(obj);
    }

    /**
     * Returns a list of all the mechanics available
     * @return A list of all the mechanics available
     */
    public static Mechanic[] getMechanics() {
        return mechanics;
    }

    /**
     * Restarts the game
     */
    public void reset() {
        objects.clear();
        player = null;
        background = new Background();
    }

    /**
     * Returns the concurrent list of all the game objects
     * @return The concurrent list of all the game objects
     */
    public ConcurrentLinkedQueue<GameObject> getGameObjects() {
        return objects;
    }

    /**
     * Returns a map with the characters (class + constructor function)
     * @return A map with the characters (class + constructor function)
     */
    public static Map<Class<? extends Character>, CharacterConstructor> getCharacters() {
        return characters;
    }

    /**
     * Returns a map with the orbs (class + constructor function)
     * @return A map with the orbs (class + constructor function)
     */
    public static Map<Class<? extends Orb>, OrbConstructor> getOrbs() {
        return orbs;
    }
}
