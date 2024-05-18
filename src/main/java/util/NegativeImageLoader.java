package util;

import java.awt.image.BufferedImage;

import gui.ImageCache;

public class NegativeImageLoader implements ImageLoader{

    @Override
    public BufferedImage load(String path, Class<?> resourceBase) {
        return ImageCache.loadNegativeImage(path, resourceBase);
    }
}
