package game.screens;

import game.MasterWindow;
import game.ThreeParametersFunction;
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
        setLayout(new FlowLayout());

        add(getCharacterButton(Orc.getImage(), Orc::new));
        add(getCharacterButton(Elf.getImage(), Elf::new));
    }

    private JButton getCharacterButton(Image image, ThreeParametersFunction<Double, Double, Mechanic, Character> funcCrea) {
        JButton btn = new JButton();
        btn.setIcon(new ImageIcon(image));
        btn.addActionListener(e -> MasterWindow.getInstance().launchGame(funcCrea));

        return btn;
    }
}
