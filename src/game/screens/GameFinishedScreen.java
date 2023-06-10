package game.screens;

import game.MasterWindow;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class GameFinishedScreen extends JPanel {
    public GameFinishedScreen(boolean won) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBackground(Color.LIGHT_GRAY);

        add(Box.createVerticalGlue());

        JLabel text = new JLabel();
        text.setText(won ? "You won !" : "Game over !");
        text.setFont(new Font(Utils.FONT_NAME, Font.BOLD, 40));
        text.setForeground(won ? Color.GREEN : Color.RED);
        text.setAlignmentX(CENTER_ALIGNMENT);
        add(text);

        JButton btnHome = new JButton();
        btnHome.addActionListener(e -> MasterWindow.getInstance().goToHomeScreen());
        btnHome.setFont(new Font(Utils.FONT_NAME, Font.PLAIN, 20));
        btnHome.setText("Home");
        btnHome.setAlignmentX(CENTER_ALIGNMENT);
        add(btnHome);

        add(Box.createVerticalGlue());
    }
}
