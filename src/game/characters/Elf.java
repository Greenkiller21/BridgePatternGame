package game.characters;

import game.ICollidable;
import game.mechanics.Mechanic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class Elf extends Character {
    private final Image img;

    public Elf(double x, double y, Mechanic mechanic) {
        super(x, y, mechanic);
        try {
            img = ImageIO.read(new File("assets/orc_face.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bounds.width = img.getWidth(null);
        bounds.height = img.getHeight(null);
    }

    @Override
    public void render(Graphics g, int x, int y) {
        //Draw here
        //g.setColor(Color.GREEN);
        //g.fillRect((int)getX() + x, (int)getY() + y, (int)bounds.width, (int)bounds.height);
        g.drawImage(img, (int)getX() + x, (int)getY() + y, null);

        super.render(g, x, y);
    }
}
