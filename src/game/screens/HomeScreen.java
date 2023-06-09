package game.screens;

import game.MasterWindow;
import game.ThreeParametersFunction;
import game.Utils;
import game.characters.Character;
import game.characters.Elf;
import game.characters.Orc;
import game.mechanics.Mechanic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

public class HomeScreen extends JPanel {
    public HomeScreen() {
        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        JLabel lblChoice = new JLabel();
        lblChoice.setFont(new Font(Utils.FONT_NAME, Font.PLAIN, 30));
        lblChoice.setText("Choose your race ");

        add(lblChoice);

        JPanel buttons = new JPanel();

        buttons.setBackground(getBackground());
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(getCharacterButton(Orc.getImage(), Orc::new));
        buttons.add(getCharacterButton(Elf.getImage(), Elf::new));

        add(buttons);
    }

    private JButton getCharacterButton(Image image, ThreeParametersFunction<Double, Double, Mechanic, Character> funcCrea) {
        JButton btn = new JButton();
        btn.setIcon(new ImageIcon(image));
        btn.addActionListener(e -> MasterWindow.getInstance().launchGame(funcCrea));
        btn.setToolTipText("test");
        return btn;
    }
}
