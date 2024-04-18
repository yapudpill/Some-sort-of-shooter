package util;


public record Pair<T, U>(T first, U second) {

    public boolean equals(T f, U s) {
        return first.equals(f) && second.equals(s);
    }
}