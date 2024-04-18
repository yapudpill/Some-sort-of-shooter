package gui.animations;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.TimeIntervalMappings.EndReachedBehaviour;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AnimationCache {
    public static class AnimationNotFoundException extends RuntimeException {
        public AnimationNotFoundException(String message) {
            super(message);
        }
    }

    private static final Map<String, Animation> animationCache = new HashMap<>();
    private static final Map<String, AnimationGroup> animationCollectionCache = new HashMap<>();


    public static class InvalidAnimationException extends RuntimeException {
        public InvalidAnimationException(String message) {
            super(message);
        }
    }

    public static Animation loadAnimation(String path, Class<?> resourceBase) {
        DocumentBuilder builder = null;

        URL url = resourceBase.getResource(path);
        if (url == null) throw new AnimationNotFoundException(path);
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc = null;
        try {
            doc = builder.parse(resourceBase.getResourceAsStream(path));
        } catch (SAXException|IOException e) {
            throw new InvalidAnimationException(e.getMessage());
        }
        doc.getDocumentElement().normalize();

        EndReachedBehaviour endReachedBehaviour;
        try {
            endReachedBehaviour = EndReachedBehaviour.valueOf(doc.getDocumentElement().getAttribute("endReachedBehaviour"));
        } catch (IllegalArgumentException e) {
            throw new InvalidAnimationException("Invalid endReachedBehaviour");
        }
        Animation animation = new Animation(endReachedBehaviour, doc.getDocumentElement().getAttribute("id"));
        animationCache.put(path, animation);
        NodeList frames = doc.getDocumentElement().getElementsByTagName("frame");
        for (int i = 0; i < frames.getLength(); i++) {
            int time;
            try {
                time = Integer.parseInt(frames.item(i).getAttributes().getNamedItem("time").getNodeValue());
            } catch (ClassCastException | NumberFormatException e) {
                throw new InvalidAnimationException("Invalid animation frame");
            }
            animation.addNextAssociation(time, frames.item(i).getAttributes().getNamedItem("path").getNodeValue());
        }

        return animation;
    }
}
