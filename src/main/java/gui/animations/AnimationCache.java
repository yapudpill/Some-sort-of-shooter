package gui.animations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.EndReachedBehaviour;

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

    public static class InvalidAnimationException extends RuntimeException {
        public InvalidAnimationException(String message) {
            super(message);
        }
    }

    private static final Map<URL, AnimationGroup> animationGroupCache = new HashMap<>();

    private static Animation parseAnimationElement(Element animationElement) {
        try {
            EndReachedBehaviour endReachedBehaviour = EndReachedBehaviour.valueOf(
                animationElement.getAttributes().getNamedItem("endReachedBehaviour").getNodeValue()
            );

            Animation animation = new Animation(
                endReachedBehaviour,
                animationElement.getAttributes().getNamedItem("id").getNodeValue()
            );

            NodeList frames = animationElement.getElementsByTagName("frame");

            for (int i = 0; i < frames.getLength(); i++) {
                NamedNodeMap frameAttributes = frames.item(i).getAttributes();
                double time = Double.parseDouble(frameAttributes.getNamedItem("time").getNodeValue());
                animation.put(time, frameAttributes.getNamedItem("path").getNodeValue());
            }

            return animation;

        } catch (ClassCastException | NumberFormatException e) {
            throw new InvalidAnimationException("Invalid animation frame");
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidAnimationException("Invalid endReachedBehaviour");
        }
    }

    public static AnimationGroup loadAnimationGroup(String path, Class<?> resourceBase) {
        URL url = resourceBase.getResource(path);
        if (animationGroupCache.containsKey(url)) {
            return animationGroupCache.get(url);
        }
        if (url == null) {
            throw new AnimationNotFoundException(path);
        }

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(resourceBase.getResourceAsStream(path));
            Element root = doc.getDocumentElement();
            root.normalize();

            if (!root.getNodeName().equals("animationGroup")) {
                throw new InvalidAnimationException("Invalid animation group");
            }

            String defaultId = root.getAttribute("default");
            AnimationGroup group = new AnimationGroup(defaultId);
            animationGroupCache.put(url, group);

            NodeList nodes = root.getElementsByTagName("animation");

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                Animation animation = parseAnimationElement((Element) node);
                group.put(node.getAttributes().getNamedItem("id").getNodeValue(), animation);
            }

            group.preloadAnimations(resourceBase);

            return group;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
