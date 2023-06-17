package game;

import game.characters.Character;
import game.gameObjects.Background;
import game.gameObjects.orbs.Orb;
import game.mechanics.Mechanic;
import game.projectiles.leech.Leech;
import game.projectiles.snowball.Snowball;
import game.projectiles.stone.Stone;
import utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Main class for the game
 */
public class Main {
    public static void main(String[] args) {
        addAllAliases();

        MasterWindow masterWin = MasterWindow.getInstance();
        masterWin.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        masterWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
        masterWin.setUndecorated(true);
        masterWin.setVisible(true);
        masterWin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        masterWin.goToHomeScreen();
    }

    /**
     * Add all aliases needed for the game (ImageLoader)
     */
    private static void addAllAliases() {
        //Add movement images aliases for all the characters
        for (Class<? extends Character> c : GameHandler.getCharacters().keySet()) {
            addMovementAliases(c);
        }

        //Add movement images and mechanic representation image for all the mechanics
        for (Mechanic m : GameHandler.getMechanics()) {
            addMovementAndImageAliases(m.getClass());
        }

        //Add image for all the orbs
        for (Class<? extends Orb> o : GameHandler.getOrbs().keySet()) {
            addAlias(o.getSimpleName().toLowerCase(), Orb.class);
        }

        //Add images for the background generation
        addAlias("grass_2_stalks", Background.class);
        addAlias("grass_3_stalks", Background.class);
        addAlias("grass_2_stones", Background.class);

        //Add images for the projectiles
        addBigAndSmallAliases(Snowball.class);
        addBigAndSmallAliases(Stone.class);
        addAlias("normal", Leech.class);
    }

    /**
     * Add WASD movement images for a specific class
     * @param clazz The class
     */
    private static void addMovementAliases(Class<?> clazz) {
        addAlias("w", clazz);
        addAlias("a", clazz);
        addAlias("s", clazz);
        addAlias("d", clazz);
    }

    /**
     * Add WASD movement images + I (for the representation) for a specific class
     * @param clazz The class
     */
    private static void addMovementAndImageAliases(Class<?> clazz) {
        addMovementAliases(clazz);
        addAlias("I", clazz);
    }

    /**
     * Adds an alias for a class
     * The image must be stored under assets/{class_name_lowercase}/{class_name_lowercase}_{alias}.png
     * @param alias The alias to use
     * @param clazz The class to use
     */
    private static void addAlias(String alias, Class<?> clazz) {
        String name = clazz.getSimpleName().toLowerCase();
        ImageLoader.addAlias(alias, "%1$s/%1$s_%2$s.png".formatted(name, alias), clazz);
    }

    /**
     * Add big and small aliases for a specific class
     * @param clazz The class
     */
    private static void addBigAndSmallAliases(Class<?> clazz) {
        addAlias("big", clazz);
        addAlias("small", clazz);
    }
}
