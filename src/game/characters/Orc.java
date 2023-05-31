package game.characters;

import game.mechanics.Mechanic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Orc extends Character {
    private final Image img;

    public Orc(double x, double y, Mechanic mechanic) {
        super(x, y, mechanic);
        try {
            img = ImageIO.read(new File("assets/orc_dos.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bounds.width = img.getWidth(null);
        bounds.height = img.getHeight(null);
    }

    @Override
    public void render(Graphics g, int x, int y) {
        //Draw here
        //g.setColor(Color.BLACK);
        //g.drawRect((int)getX() + x, (int)getY() + y, (int)bounds.width, (int)bounds.height);
        g.drawImage(img, (int)getX() + x, (int)getY() + y, null);

        super.render(g, x, y);
    }
}
