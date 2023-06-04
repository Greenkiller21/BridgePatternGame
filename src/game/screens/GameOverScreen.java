package game.screens;

import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JPanel {
    public GameOverScreen() {
        setLayout(new GridBagLayout());

        JLabel text = new JLabel();
        text.setText("Game over");
        text.setFont(new Font("Serif", Font.BOLD, 40));
        text.setForeground(Color.RED);
        add(text);
    }
}
