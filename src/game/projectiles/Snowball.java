package game.projectiles;

import game.ImageLoader;
import game.gameObjects.GameObject;

import java.awt.*;

public class Snowball extends Projectile {
    private static final Image image = ImageLoader.getImage("all", Snowball.class);

    public Snowball(double x, double y, GameObject creator) {
        super(x, y, creator);
        bounds.width = image.getWidth(null);
        bounds.height = image.getHeight(null);
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(image, (int)getX() + x, (int)getY() + y, null);
    }

    @Override
    public int getDamage() {
        return 10;
    }

    @Override
    public int maxDistance() {
        return 100;
    }
}
