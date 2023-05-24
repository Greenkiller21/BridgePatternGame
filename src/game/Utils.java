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

    public static Point2D.Double normalize(Point point) {
        double length = Math.sqrt(point.x * point.x + point.y * point.y);

        return new Point2D.Double(point.x / length, point.y / length);
    }
}
