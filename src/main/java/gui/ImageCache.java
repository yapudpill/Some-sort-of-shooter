package gui;

import javax.imageio.ImageIO;

import java.awt.Color;
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

    public static BufferedImage loadNegativeImage(String path, Class<?> resourceBase) {
        if (cache.get(path + "_inv") != null) {
            return cache.get(path + "_inv");
        }
        BufferedImage img = loadImage(path, resourceBase);

        //inversion
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int pixel = img.getRGB(x, y);
                Color col = new Color(pixel, true);
                col = new Color(
                    255 - col.getRed(),
                    255 - col.getGreen(),
                    255 - col.getBlue()
                );
                img.setRGB(x, y, col.getRGB());
            }
        }

        cache.put(path + "_inv", img);
        return img;
    }

    public static BufferedImage loadImage(String path, Class<?> resourceBase) {
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
}