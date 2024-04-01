package gui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    public static class ImageNotFoundException extends RuntimeException {
        public ImageNotFoundException(String message) {
            super(message);
        }
    }
    private static final Map<String, BufferedImage> cache = new HashMap<>();

    public static BufferedImage loadImage(String path, Class<?> ressourceBase) {
        if (cache.get(path) != null) {
            return cache.get(path);
        } else {
            URL url = ressourceBase.getResource(path);
            if (url == null) throw new ImageNotFoundException(String.format("Image resource %s missing !", path));
            BufferedImage image = null;
            try {
                image = ImageIO.read(url);
                cache.put(path, image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return image;
        }
    }

    public static void clear() {
        cache.clear();
    }

    public static BufferedImage loadImage(String path) {
        return loadImage(path, ImageCache.class);
    }
}