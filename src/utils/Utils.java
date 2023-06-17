package utils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Random;

public class Utils {
    /**
     * The name of the font used
     */
    public static final String FONT_NAME = "blomberg";

    /**
     * The random object
     */
    private static final Random rdm = new Random();

    /**
     * Returns the random object
     * @return The random object
     */
    public static Random getRandom() {
        return rdm;
    }

    /**
     * Normalizes a 2D point
     * @param point The 2D point
     * @return The normalized 2D points
     */
    public static Point2D.Double normalize(Point2D.Double point) {
        double length = Math.sqrt(point.x * point.x + point.y * point.y);

        return new Point2D.Double(point.x / length, point.y / length);
    }

    /**
     * Returns the string width
     * @param g The graphics object used to draw the string
     * @param str The string to draw
     * @return The width of the string
     */
    public static int getStringWidth(Graphics g, String str) {
        Graphics2D g2d = (Graphics2D) g;
        return (int)getStringBounds(g2d, str).getWidth();
    }

    /**
     * Returns the string height
     * @param g The graphics object used to draw the string
     * @param str The string to draw
     * @return The height of the string
     */
    public static int getStringHeight(Graphics g, String str) {
        Graphics2D g2d = (Graphics2D) g;
        return (int)getStringBounds(g2d, str).getHeight();
    }

    /**
     * Computes the bounds of a string
     * Found here : https://stackoverflow.com/a/12495108
     * @param g2 The graphics used to draw the string
     * @param str The string
     * @return The bounds of the string
     */
    private static Rectangle getStringBounds(Graphics2D g2, String str) {
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        return gv.getPixelBounds(null, 0, 0);
    }

    /**
     * Represent a pair
     * @param <T> The first type of the pair
     * @param <U> The second type of the pair
     */
    public static class Pair<T, U> {
        private final T t;
        private final U u;

        public Pair(T t, U u) {
            this.t = t;
            this.u = u;
        }

        public T getFirst() {
            return t;
        }

        public U getSecond() {
            return u;
        }
    }

    /**
     * Executes a weighted random
     * @param weights The weights
     * @return The random value
     * @param <T> The type of the values to be chosen
     */
    public static <T> T getWeightedRandom(LinkedList<Pair<T, Integer>> weights) {
        if (weights.isEmpty()) {
            throw new RuntimeException("The weights list cannot be empty !");
        }

        int totalWeights = weights.stream().mapToInt(Pair::getSecond).sum();
        int num = getRandom().nextInt(0, totalWeights);

        int current = 0;

        for (Pair<T, Integer> pair : weights) {
            current += pair.getSecond();
            if (num < current) {
                return pair.getFirst();
            }
        }

        return null; //Not possible to reach here
    }
}
