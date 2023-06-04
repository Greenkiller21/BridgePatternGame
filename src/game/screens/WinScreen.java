package game.screens;

import javax.swing.*;
import java.awt.*;

public class WinScreen extends JPanel {
    public WinScreen() {
        setLayout(new GridBagLayout());

        JLabel text = new JLabel();
        text.setText("You won !");
        text.setFont(new Font("Serif", Font.BOLD, 40));
        text.setForeground(Color.GREEN);
        add(text);
    }
}
