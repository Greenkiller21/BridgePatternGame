package game.screens;

import game.ImageLoader;
import game.MasterWindow;
import game.interfaces.ThreeParametersFunction;
import game.Utils;
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
        buttons.add(getCharacterButton(ImageLoader.getImage("s", Orc.class), Orc::new));
        buttons.add(getCharacterButton(ImageLoader.getImage("s", Elf.class), Elf::new));
        buttons.setAlignmentX(CENTER_ALIGNMENT);

        add(buttons);
        add(Box.createVerticalGlue());
    }

    private JButton getCharacterButton(Image image, ThreeParametersFunction<Double, Double, Mechanic, Character> funcCrea) {
        JButton btn = new JButton();
        btn.setIcon(new ImageIcon(image));
        btn.addActionListener(e -> MasterWindow.getInstance().launchGame(funcCrea));
        return btn;
    }
}
