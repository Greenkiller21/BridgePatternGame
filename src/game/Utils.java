package game;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Stream;

public class Utils {
    public static final String FONT_NAME = "blomberg";

    private static Random rdm = new Random();

    public static Random getRandom() {
        return rdm;
    }

    public static <T> T[] mergeArray(T[] arr1, T[] arr2) {
        final T[] finalArr = (T[]) Array.newInstance(arr1.getClass().getComponentType(), arr1.length + arr2.length);

        System.arraycopy(arr1, 0, finalArr, 0, arr1.length);
        System.arraycopy(arr2, 0, finalArr, arr1.length, arr2.length);

        return finalArr;
    }

    public static Point2D.Double normalize(Point2D.Double point) {
        double length = Math.sqrt(point.x * point.x + point.y * point.y);

        return new Point2D.Double(point.x / length, point.y / length);
    }

    public static void drawCenteredString(Graphics g, String str, int x, int y, int screenW, int screenH) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString(str, screenW - x - getStringWidth(g, str) / 2, screenH - y + getStringHeight(g, str) / 2);
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
        private T t;
        private U u;

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

    public static int getRandomWithWeight(LinkedList<Pair<Integer, Integer>> weights) {
        int totalWeights = weights.stream().mapToInt(Pair::getSecond).sum();
        int num = getRandom().nextInt(0, totalWeights);

        int current = 0;

        for (Pair<Integer, Integer> pair : weights) {
            current += pair.getSecond();
            if (num < current) {
                return pair.getFirst();
            }
        }

        throw new RuntimeException("NOT POSSIBLE");
    }
}
