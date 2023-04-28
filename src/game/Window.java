import javax.swing.*;
import java.awt.*;

public class Window {
    private JFrame frame;

    public Window(int width, int height, String title, Game game) {
        frame = new JFrame(title);

        Dimension dim = new Dimension(width, height);

        frame.setPreferredSize(dim);
        frame.setSize(dim);

        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Dimension getCurrentSize() {
        return frame.getSize();
    }
}
