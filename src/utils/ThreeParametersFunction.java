package utils;

@FunctionalInterface
public interface ThreeParametersFunction<T, U, V, W> {
    W apply(T t, U u, V v);
}
