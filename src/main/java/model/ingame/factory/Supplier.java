package model.ingame.factory;

/*
 * Used to create default instances of a given class
 */
public interface Supplier<T> {
    T get();
}
