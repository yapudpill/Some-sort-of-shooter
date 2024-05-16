package gui;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.Image;
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

    public static Image loadImage(String path, Class<?> resourceBase) {
        if (cache.get(path) != null) {
            return cache.get(path);
        }

        URL url = resourceBase.getResource(path);
        if (url == null) {
            throw new ImageNotFoundException(String.format("Image resource %s missing !", path));
        }

        try {
            BufferedImage image = ImageIO.read(url);
            cache.put(path, image);
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load an icon from the resource/gui/laf/icon directory
     */
    public static Icon loadIcon(String name) {
        Image image = loadImage("laf/icon/" + name + ".png", MainFrame.class);
        return new ImageIcon(image);
    }
}