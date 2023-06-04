package game;

@FunctionalInterface
public interface ThreeParametersFunction<T, U, V, W> {
    public W apply(T t, U u, V v);
}
