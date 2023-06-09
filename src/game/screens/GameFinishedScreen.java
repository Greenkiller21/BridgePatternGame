package game.screens;

import game.Utils;

import javax.swing.*;
import java.awt.*;

public class GameFinishedScreen extends JPanel {
    public GameFinishedScreen(boolean won) {
        setLayout(new GridBagLayout());

        setBackground(Color.LIGHT_GRAY);

        JLabel text = new JLabel();
        text.setText(won ? "You won !" : "Game over !");
        text.setFont(new Font(Utils.FONT_NAME, Font.BOLD, 40));
        text.setForeground(won ? Color.GREEN : Color.RED);
        add(text);
    }
}
