package game.screens;

import game.GameHandler;
import utils.CharacterConstructor;
import utils.ImageLoader;
import game.MasterWindow;
import utils.ThreeParametersFunction;
import utils.Utils;
import game.characters.Character;
import game.characters.Elf;
import game.characters.Orc;
import game.mechanics.Mechanic;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JPanel {
    public HomeScreen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);

        add(Box.createVerticalGlue());

        JLabel lblChoice = new JLabel();
        lblChoice.setFont(new Font(Utils.FONT_NAME, Font.PLAIN, 30));
        lblChoice.setText("Choose your race");
        lblChoice.setAlignmentX(CENTER_ALIGNMENT);

        add(lblChoice);

        JPanel buttons = new JPanel();

        buttons.setBackground(getBackground());
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        for (Class<? extends Character> clazz : GameHandler.getCharacters().keySet()) {
            buttons.add(getCharacterButton(clazz));
        }

        buttons.setAlignmentX(CENTER_ALIGNMENT);

        add(buttons);
        add(Box.createVerticalGlue());
    }

    private JButton getCharacterButton(Class<? extends Character> clazz) {
        JButton btn = new JButton();
        btn.setIcon(new ImageIcon(ImageLoader.getImage("s", clazz)));
        btn.addActionListener(e -> MasterWindow.getInstance().launchGame(GameHandler.getCharacters().get(clazz)));
        return btn;
    }
}
