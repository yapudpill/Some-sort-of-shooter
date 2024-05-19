package util;

import java.awt.Image;

/**
 * Interface for loading images.
 */
@FunctionalInterface
public interface ImageLoader {
    Image load(String path, Class<?> resourceBase);
}
