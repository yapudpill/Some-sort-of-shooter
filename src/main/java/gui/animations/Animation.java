package gui.animations;

import gui.ImageCache;
import util.TimeIntervalMappings;
import util.TimeIntervalMappingsCursor;

import java.awt.image.BufferedImage;

public class Animation {
    private final TimeIntervalMappings<String> imageNamesMappings;
    private final TimeIntervalMappingsCursor<String> cursor;

    public Animation(TimeIntervalMappings<String> imageNamesMappings) {
        this.imageNamesMappings = imageNamesMappings;
        cursor = new TimeIntervalMappingsCursor<>(imageNamesMappings);
    }

    public String getCurrentImageName() {
        return cursor.getCurrentValue();
    }

    public BufferedImage getCurrentImage() {
        return ImageCache.loadImage(getCurrentImageName());
    }

    public void loadImages() {
        for (String imageName : imageNamesMappings.values()) {
            ImageCache.loadImage(imageName);
        }
    }
}
