package util;

import gui.ImageCache;

import java.awt.image.BufferedImage;

public class RegularImageLoader implements ImageLoader{

    @Override
    public BufferedImage load(String path, Class<?> resourceBase) {
        return ImageCache.loadImage(path, resourceBase);
    }
}
