package game.gameObjects;

import utils.ImageLoader;
import game.interfaces.IRenderable;
import utils.Utils;
import game.screens.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Class creating a new randomly generated background
 */
public class Background implements IRenderable {
    private BufferedImage background;

    /**
     * Generates a background image
     * @return The background image
     */
    private BufferedImage generateBackground() {
        int screenWidth = Game.getInstance().getWidth();
        int screenHeight = Game.getInstance().getHeight();

        //Getting the tile size
        Image image0 = ImageLoader.getImage("grass_2_stalks", Background.class);
        int tileWidth = image0.getWidth(null);
        int tileHeight = image0.getHeight(null);

        //Create a new image
        BufferedImage bufferedImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        //Weights for the image generation
        LinkedList<Utils.Pair<String, Integer>> weights = new LinkedList<>();
        weights.add(new Utils.Pair<>("grass_2_stalks", 60));
        weights.add(new Utils.Pair<>("grass_3_stalks", 35));
        weights.add(new Utils.Pair<>("grass_2_stones", 5));

        //The number of tiles for the x and y-axis
        int nbTilesX = (screenWidth / tileWidth) + 1;
        int nbTilesY = (screenHeight / tileHeight) + 1;

        //Generated the background
        for (int iX = 0; iX < nbTilesX; ++iX) {
            for (int iY = 0; iY < nbTilesY; ++iY) {
                String imgName = Utils.getWeightedRandom(weights);
                g2d.drawImage(ImageLoader.getImage(imgName, Background.class), iX * tileWidth, iY * tileHeight, null);
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
