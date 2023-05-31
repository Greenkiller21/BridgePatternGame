package game;

import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.stream.Stream;

public class Utils {
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
        g2d.drawString(str, screenW - x - getStringWidth(g, str) / 2, screenH - y - getStringHeight(g, str) / 2);
    }

    public static int getStringWidth(Graphics g, String str) {
        Graphics2D g2d = (Graphics2D) g;
        return (int)g.getFont().getStringBounds(str, g2d.getFontRenderContext()).getWidth();
    }

    public static int getStringHeight(Graphics g, String str) {
        Graphics2D g2d = (Graphics2D) g;
        return (int)g.getFont().getStringBounds(str, g2d.getFontRenderContext()).getHeight();
    }
}
