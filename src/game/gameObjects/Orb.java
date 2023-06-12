package game.gameObjects;

import game.characters.Character;
import utils.ImageLoader;

import java.awt.*;

public abstract class Orb extends GameObject {
    private Image image;

    public Orb(double x, double y) {
        super(x, y);

        updateBounds();
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.Orb;
    }

    @Override
    public void onCollide(GameObject other) {
        switch (other.getType()) {
            case Character -> {
                Character character = (Character) other;
                doActionOnCollide(character);
                destroy();
            }
        }
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getImage(), x + (int)getX(), y + (int)getY(), null);
    }

    @Override
    public void tick() {

    }

    private void updateBounds() {
        bounds.width = getImage().getWidth(null);
        bounds.height = getImage().getHeight(null);
    }

    private Image getImage() {
        if (image == null) {
            image = ImageLoader.getImage(this.getClass().getSimpleName().toLowerCase(), Orb.class);
        }
        return image;
    }

    public abstract void doActionOnCollide(Character c);
}
