package util;

import java.awt.image.BufferedImage;

/**
 * Interface for loading images.
 */
public interface ImageLoader {
    BufferedImage load(String path, Class<?> resourceBase);
}
