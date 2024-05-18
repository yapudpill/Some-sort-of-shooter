package util;

import java.awt.image.BufferedImage;

public interface ImageLoader {

    BufferedImage load(String path, Class<?> resourceBase);

}
