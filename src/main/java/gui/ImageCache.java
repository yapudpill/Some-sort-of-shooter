package gui;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageCache {
    private static final Map<String, Image> cache = new HashMap<>();

    public static Image loadImage(String path, Class<?> ressourceBase) throws IOException {
        if (cache.get(path) != null) {
            return cache.get(path);
        } else {
            URL url = ressourceBase.getResource(path);
            if (url == null) throw new IOException(String.format("Image resource %s missing !", path));
            Image image = ImageIO.read(url);
            cache.put(path, image);
            return image;
        }
    }

    public static void clear() {
        cache.clear();
    }

    public static Image loadImage(String path) throws IOException {
        return loadImage(path, ImageCache.class);
    }
}