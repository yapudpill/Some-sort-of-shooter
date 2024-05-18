package model.level.scenario;

import model.ingame.entity.EntityConstructor;
import model.ingame.weapon.WeaponConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.EndReachedBehaviour;
import util.Pair;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ScenarioParser {

    private static Pair<Double, IGameContext> parseContextElement(Element contextElement) throws InvalidScenarioException {
        String type = contextElement.getAttribute("type");
        boolean oneshot = switch (type) {
            case "oneshot" -> true;
            case "fixed" -> false;
            default -> throw new InvalidScenarioException("Invalid context type");
        };

        double time;
        try {
            time = Double.parseDouble(contextElement.getAttribute("time"));
        } catch (NumberFormatException e) {
            throw new InvalidScenarioException("Invalid time");
        }

        List<WeaponConstructor> oneShotWeapons = null;
        Map<WeaponConstructor, Double> weaponRates = null;
        Element weaponsNode = (Element) contextElement.getElementsByTagName("weapons").item(0);
        if (oneshot) {
            oneShotWeapons = parseOneShotContextPart(weaponsNode, "weapon", ScenarioElementsParsers::parseWeapon);
        } else {
            weaponRates = parseFixedContextPart(weaponsNode, "weapon", ScenarioElementsParsers::parseWeapon);
        }

        List<EntityConstructor> oneShotEnemies = null;
        Map<EntityConstructor, Double> enemyRates = null;
        Element enemiesNode = (Element) contextElement.getElementsByTagName("enemies").item(0);
        if (oneshot) {
            oneShotEnemies = parseOneShotContextPart(enemiesNode, "enemy", ScenarioElementsParsers::parseEnemy);
        } else {
            enemyRates = parseFixedContextPart(enemiesNode, "enemy", ScenarioElementsParsers::parseEnemy);
        }

        List<EntityConstructor> oneShotMiscs = new ArrayList<>();
        Map<EntityConstructor, Double> miscRates = new HashMap<>();
        Element miscsNode = (Element) contextElement.getElementsByTagName("miscs").item(0);
        if (oneshot) {
            oneShotMiscs = parseOneShotContextPart(miscsNode, "misc", ScenarioElementsParsers::parseMisc);
        } else {
            miscRates = parseFixedContextPart(miscsNode, "misc", ScenarioElementsParsers::parseMisc);
        }

        IGameContext context;
        if (oneshot) {
            context = new IGameContext.OneShotSpawnContext(oneShotWeapons, oneShotEnemies, oneShotMiscs);
        } else {
            context = new IGameContext.FixedSpawnRateContext(weaponRates, enemyRates, miscRates);
        }

        return new Pair<>(time, context);
    }

    private static <T> Map<T, Double> parseFixedContextPart(Element part, String childName, Function<String, T> parser) throws InvalidScenarioException {
        NodeList nodes = part.getElementsByTagName(childName);

        Map<T, Double> rates = new HashMap<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            String name = element.getAttribute("name");

            T parsed = parser.apply(name);
            if (parsed == null) {
                throw new InvalidScenarioException("Invalid " + childName + " name");
            }

            try {
                double rate = Double.parseDouble(element.getAttribute("rate"));
                rates.put(parsed, rate);
            } catch (NumberFormatException e) {
                throw new InvalidScenarioException("Invalid " + childName + " rate");
            }
        }

        return rates;
    }

    private static <T> List<T> parseOneShotContextPart(Element part, String childName, Function<String, T> parser) throws InvalidScenarioException {
        NodeList nodes = part.getElementsByTagName(childName);

        List<T> oneShot = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Element enemy = (Element) nodes.item(i);
            String name = enemy.getAttribute("name");

            T parsed = parser.apply(name);
            if (parsed == null) {
                throw new InvalidScenarioException("Invalid " + childName + " name");
            }
            oneShot.add(parsed);
        }

        return oneShot;
    }

    public static IScenario loadScenario(InputStream stream) throws InvalidScenarioException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(stream);
            Element root = doc.getDocumentElement();
            root.normalize();

            if (!root.getNodeName().equals("scenario")) {
                throw new InvalidScenarioException("Root element must be <scenario>");
            }

            EndReachedBehaviour endReachedBehaviour;
            try {
                endReachedBehaviour = EndReachedBehaviour.valueOf(
                        root.getAttribute("endReachedBehaviour").toUpperCase()
                );
            } catch (IllegalArgumentException e) {
                throw new InvalidScenarioException("Invalid endReachedBehaviour");
            }


            FixedScenario scenario = new FixedScenario(endReachedBehaviour);
            NodeList contextNodes = root.getElementsByTagName("interval");

            for (int i = 0; i < contextNodes.getLength(); i++) {
                Node node = contextNodes.item(i);
                Pair<Double, IGameContext> pair = parseContextElement((Element) node);
                scenario.put(pair.first(), pair.second());
            }

            return scenario;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new InvalidScenarioException(e.getMessage());
        }
    }
}
