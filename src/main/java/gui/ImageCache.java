package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A cache for images, to avoid loading the same image multiple times and doubles as a helper for image loading.
 */
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

    public static Image loadNegativeImage(String path, Class<?> resourceBase) {
        if (cache.get(path + "_inv") != null) {
            return cache.get(path + "_inv");
        }

        BufferedImage img = copyImage(loadImage(path, resourceBase));
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int pixel = img.getRGB(x, y);
                Color col = new Color(pixel, true);
                col = new Color(
                    255 - col.getRed(),
                    255 - col.getGreen(),
                    255 - col.getBlue(),
                    col.getAlpha()
                );
                img.setRGB(x, y, col.getRGB());
            }
        }

        cache.put(path + "_inv", img);
        return img;
    }

    private static BufferedImage copyImage(Image source) {
        BufferedImage b = new BufferedImage(source.getWidth(null), source.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    /**
     * Load an icon from the resource/gui/laf/icon directory
     */
    public static Icon loadIcon(String name) {
        Image image = loadImage("laf/icon/" + name + ".png", MainFrame.class);
        return new ImageIcon(image);
    }
}