package util;

public enum EndReachedBehaviour {

    /** Values beyond last interval are not defined */
    FINITE,

    /** Values beyond last interval are the last value */
    INFINITE,

    /** Loop over the values */
    LOOPING
}
