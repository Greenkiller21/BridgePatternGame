package game.gameObjects;

import game.interfaces.IRenderable;
import game.Utils;
import game.screens.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Background implements IRenderable {
    static Image[] images = new Image[3];

    static {
        try {
            images[0] = ImageIO.read(new File("assets/floor/grass_2_stalks.png"));
            images[1] = ImageIO.read(new File("assets/floor/grass_3_stalks.png"));
            images[2] = ImageIO.read(new File("assets/floor/grass_2_stones.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage background;

    private BufferedImage generateBackground() {
        int screenWidth = Game.getInstance().getWidth();
        int screenHeight = Game.getInstance().getHeight();

        int tileWidth = images[0].getWidth(null);
        int tileHeight = images[0].getHeight(null);

        BufferedImage bufferedImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        for (int iX = 0; iX < screenWidth / tileWidth; ++iX) {
            for (int iY = 0; iY < screenHeight / tileHeight; ++iY) {
                LinkedList<Utils.Pair<Integer, Integer>> weights = new LinkedList<>();
                weights.add(new Utils.Pair<>(0, 60));
                weights.add(new Utils.Pair<>(1, 35));
                weights.add(new Utils.Pair<>(2, 5));

                int imgNum = Utils.getWeightedRandom(weights);

                g2d.drawImage(images[imgNum], iX * tileWidth, iY * tileHeight, null);
            }
        }

        g2d.dispose();

        return bufferedImage;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        if (background == null) {
            background = generateBackground();
        }

        g.drawImage(background, x, y, null);
    }
}
