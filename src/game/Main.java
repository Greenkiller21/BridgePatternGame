package game;

import game.characters.Character;
import game.gameObjects.Background;
import game.gameObjects.Orb;
import game.mechanics.Mechanic;
import game.projectiles.leech.Leech;
import game.projectiles.snowball.Snowball;
import game.projectiles.stone.Stone;
import utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

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

    private static void addAllAliases() {
        for (Class<? extends Character> c : GameHandler.getCharacters().keySet()) {
            addMovementAliases(c);
        }

        for (Mechanic m : GameHandler.getMechanics()) {
            addMovementAndImageAliases(m.getClass());
        }

        for (Class<? extends Orb> o : GameHandler.getOrbs().keySet()) {
            addAlias(o.getSimpleName().toLowerCase(), Orb.class);
        }

        addAlias("grass_2_stalks", Background.class);
        addAlias("grass_3_stalks", Background.class);
        addAlias("grass_2_stones", Background.class);

        addBigAndSmallAliases(Snowball.class);
        addBigAndSmallAliases(Stone.class);
        addAlias("normal", Leech.class);
    }

    private static void addMovementAliases(Class<?> clazz) {
        addAlias("w", clazz);
        addAlias("a", clazz);
        addAlias("s", clazz);
        addAlias("d", clazz);
    }

    private static void addMovementAndImageAliases(Class<?> clazz) {
        addMovementAliases(clazz);
        addAlias("I", clazz);
    }

    private static void addAlias(String alias, Class<?> clazz) {
        String name = clazz.getSimpleName().toLowerCase();
        ImageLoader.addAlias(alias, "%1$s/%1$s_%2$s.png".formatted(name, alias), clazz);
    }

    private static void addBigAndSmallAliases(Class<?> clazz) {
        addAlias("big", clazz);
        addAlias("small", clazz);
    }
}
