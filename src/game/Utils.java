package game;

import java.lang.reflect.Array;
import java.util.stream.Stream;

public class Utils {
    public static <T> T[] mergeArray(T[] arr1, T[] arr2) {
        final T[] finalArr = (T[]) Array.newInstance(arr1.getClass().getComponentType(), arr1.length + arr2.length);

        System.arraycopy(arr1, 0, finalArr, 0, arr1.length);
        System.arraycopy(arr2, 0, finalArr, arr1.length, arr2.length);

        return finalArr;
    }
}
