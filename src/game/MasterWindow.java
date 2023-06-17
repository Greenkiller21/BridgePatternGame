package game;

import game.screens.Game;
import game.screens.GameFinishedScreen;
import game.screens.HomeScreen;
import utils.CharacterConstructor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * The master window
 */
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
        //Loading custom font
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getResourceAsStream("/assets/fonts/blomberg.otf")));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
    }

    /**
     * Switch the currently shown screen to the home screen
     */
    public void goToHomeScreen() {
        changeScreen(new HomeScreen());
    }

    /**
     * Switch the currently shown screen to the game over screen
     */
    public void goToGameOverScreen() {
        changeScreen(new GameFinishedScreen(false));
    }

    /**
     * Switch the currently shown screen to the win screen
     */
    public void goToWinScreen() {
        changeScreen(new GameFinishedScreen(true));
    }

    /**
     * Switch to the game screen and starts a new game
     * @param playerCreator The function to use to create the player character
     */
    public void launchGame(CharacterConstructor playerCreator) {
        Game game = Game.getInstance();
        changeScreen(game);
        game.beginGame(playerCreator);
    }

    /**
     * Go to the next stage
     */
    public void nextStage() {
        Game game = Game.getInstance();
        changeScreen(game);
        game.nextStage();
    }

    /**
     * Switch screen
     * @param newScreen New screen
     */
    private void changeScreen(Component newScreen) {
        if (screen != null) {
            remove(screen);
        }

        screen = newScreen;
        add(screen);
        pack();
    }
}
