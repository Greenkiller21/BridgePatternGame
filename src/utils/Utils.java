package utils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Random;

public class Utils {
    public static final String FONT_NAME = "blomberg";

    private static Random rdm = new Random();

    public static Random getRandom() {
        return rdm;
    }

    public static Point2D.Double normalize(Point2D.Double point) {
        double length = Math.sqrt(point.x * point.x + point.y * point.y);

        return new Point2D.Double(point.x / length, point.y / length);
    }

    public static int getStringWidth(Graphics g, String str) {
        Graphics2D g2d = (Graphics2D) g;
        return (int)getStringBounds(g2d, str).getWidth();
    }

    public static int getStringHeight(Graphics g, String str) {
        Graphics2D g2d = (Graphics2D) g;
        return (int)getStringBounds(g2d, str).getHeight();
    }

    /**
     * https://stackoverflow.com/a/12495108
     */
    private static Rectangle getStringBounds(Graphics2D g2, String str) {
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        return gv.getPixelBounds(null, 0, 0);
    }

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
