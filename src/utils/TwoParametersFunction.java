package utils;

@FunctionalInterface
public interface TwoParametersFunction<T, U, V> {
    V apply(T t, U u);
}
