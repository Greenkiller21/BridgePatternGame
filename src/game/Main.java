package game;

import game.characters.Elf;
import game.characters.Orc;
import game.gameObjects.Background;
import game.mechanics.magicMechanics.IceMagicMechanic;
import game.mechanics.physicalMechanics.SlingshotMechanic;
import game.projectiles.snowball.Snowball;
import game.projectiles.stone.Stone;

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
        addMovementAliases(Elf.class);
        addMovementAliases(Orc.class);

        addMovementAndImageAliases(SlingshotMechanic.class);
        addMovementAndImageAliases(IceMagicMechanic.class);

        addAlias("grass_2_stalks", Background.class);
        addAlias("grass_3_stalks", Background.class);
        addAlias("grass_2_stones", Background.class);

        addAlias("big", Snowball.class);
        addAlias("small", Snowball.class);

        addAlias("big", Stone.class);
        addAlias("small", Stone.class);
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
}
