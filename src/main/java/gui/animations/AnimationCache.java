package gui.animations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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

    private static final Map<URL, AnimationGroup> animationGroupCache = new HashMap<>();

    public static class InvalidAnimationException extends RuntimeException {
        public InvalidAnimationException(String message) {
            super(message);
        }
    }

    private static Animation parseAnimationElement(Element animationElement) throws InvalidAnimationException {
        EndReachedBehaviour endReachedBehaviour;
        try {
            endReachedBehaviour = EndReachedBehaviour.valueOf(animationElement.getAttributes().getNamedItem("endReachedBehaviour").getNodeValue());
        } catch (IllegalArgumentException|NullPointerException e) {
            throw new InvalidAnimationException("Invalid endReachedBehaviour");
        }
        Animation animation = new Animation(endReachedBehaviour, animationElement.getAttributes().getNamedItem("id").getNodeValue());

        NodeList frames = animationElement.getElementsByTagName("frame");

        for (int i = 0; i < frames.getLength(); i++) {
            double time;
            try {
                time = Double.parseDouble(frames.item(i).getAttributes().getNamedItem("time").getNodeValue());
            } catch (ClassCastException | NumberFormatException e) {
                throw new InvalidAnimationException("Invalid animation frame");
            }
            animation.addNextAssociation(time, frames.item(i).getAttributes().getNamedItem("path").getNodeValue());
        }
        return animation;
    }

    public static AnimationGroup loadAnimationGroup(String path, Class<?> resourceBase) throws InvalidAnimationException {
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
        NodeList animations_nodes = doc.getDocumentElement().getElementsByTagName("animation");
        for (int i = 0; i < animations_nodes.getLength(); i++) {
            Node animation_node = animations_nodes.item(i);
            Animation animation = parseAnimationElement((Element) animation_node);
            animationGroup.put(animations_nodes.item(i).getAttributes().getNamedItem("id").getNodeValue(), animation);
        }

        return animationGroup;
    }
}
