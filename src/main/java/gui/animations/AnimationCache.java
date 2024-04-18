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

    private static final Map<URL, Animation> animationCache = new HashMap<>();
    private static final Map<URL, AnimationGroup> animationGroupCache = new HashMap<>();


    public static class InvalidAnimationException extends RuntimeException {
        public InvalidAnimationException(String message) {
            super(message);
        }
    }

    public static Animation loadAnimation(String path, Class<?> resourceBase) {
        URL url = resourceBase.getResource(path);
        if (animationCache.containsKey(url)) return animationCache.get(url);
        if (url == null) throw new AnimationNotFoundException(path);

        DocumentBuilder builder;


        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc;
        try {
            doc = builder.parse(resourceBase.getResourceAsStream(path));
        } catch (SAXException|IOException e) {
            throw new InvalidAnimationException(e.getMessage());
        }
        doc.getDocumentElement().normalize();

        if (!doc.getDocumentElement().getNodeName().equals("animation")) throw new InvalidAnimationException("Invalid animation");

        EndReachedBehaviour endReachedBehaviour;
        try {
            endReachedBehaviour = EndReachedBehaviour.valueOf(doc.getDocumentElement().getAttribute("endReachedBehaviour"));
        } catch (IllegalArgumentException e) {
            throw new InvalidAnimationException("Invalid endReachedBehaviour");
        }
        Animation animation = new Animation(endReachedBehaviour, doc.getDocumentElement().getAttribute("id"));
        animationCache.put(url, animation);
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

    public static AnimationGroup loadAnimationGroup(String path, Class<?> resourceBase) {
        URL url = resourceBase.getResource(path);
        if (animationGroupCache.containsKey(url)) return animationGroupCache.get(url);
        if (url == null) throw new AnimationNotFoundException(path);

        DocumentBuilder builder;

        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc;
        try {
            doc = builder.parse(resourceBase.getResourceAsStream(path));
        } catch (SAXException|IOException e) {
            throw new InvalidAnimationException(e.getMessage());
        }
        doc.getDocumentElement().normalize();
        if (!doc.getDocumentElement().getNodeName().equals("animationGroup")) throw new InvalidAnimationException("Invalid animation group");

        String defaultAnimationId = doc.getDocumentElement().getAttribute("default");
        AnimationGroup animationGroup = new AnimationGroup(defaultAnimationId, resourceBase);
        animationGroupCache.put(url, animationGroup);
        NodeList animations = doc.getDocumentElement().getElementsByTagName("animation");
        for (int i = 0; i < animations.getLength(); i++) {
            Animation animation = loadAnimation(animations.item(i).getAttributes().getNamedItem("path").getNodeValue(), resourceBase);
            animationGroup.put(animations.item(i).getAttributes().getNamedItem("id").getNodeValue(), animation);
        }

        return animationGroup;
    }
}
