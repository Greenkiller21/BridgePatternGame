package game;

import game.characters.Character;
import game.interfaces.ThreeParametersFunction;
import game.mechanics.Mechanic;
import game.screens.Game;
import game.screens.GameFinishedScreen;
import game.screens.HomeScreen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MasterWindow extends JFrame {
    private static MasterWindow instance;
    private Component screen;
    public static MasterWindow getInstance() {
        if (instance == null) {
            instance = new MasterWindow();
        }
        return instance;
    }

    private MasterWindow() {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/blomberg.otf"));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
    }

    public void goToHomeScreen() {
        changeScreen(new HomeScreen());
    }

    public void goToGameOverScreen() {
        changeScreen(new GameFinishedScreen(false));
    }

    public void goToWinScreen() {
        changeScreen(new GameFinishedScreen(true));
    }

    public void launchGame(ThreeParametersFunction<Double, Double, Mechanic, Character> funcCrea) {
        Game game = Game.getInstance();
        changeScreen(game);
        game.beginGame(funcCrea);
    }

    private void changeScreen(Component newScreen) {
        if (screen != null) {
            remove(screen);
        }

        screen = newScreen;
        add(screen);
        pack();
    }
}
