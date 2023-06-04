package game;

import game.characters.Character;
import game.mechanics.Mechanic;
import game.screens.Game;
import game.screens.GameOverScreen;
import game.screens.HomeScreen;
import game.screens.WinScreen;

import javax.swing.*;
import java.awt.*;

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

    }

    public void goToHomeScreen() {
        changeScreen(new HomeScreen());
    }

    public void goToGameOverScreen() {
        changeScreen(new GameOverScreen());
    }

    public void goToWinScreen() {
        changeScreen(new WinScreen());
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
