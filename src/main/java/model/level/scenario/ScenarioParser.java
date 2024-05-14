package model.level.scenario;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.ingame.entity.IEnemy;
import model.ingame.entity.IEntity;
import model.ingame.weapon.WeaponModel;
import util.EndReachedBehaviour;
import util.Pair;

public class ScenarioParser {
    private static Pair<Double, IGameContext> parseContextElement(Element contextElement) throws InvalidScenarioException {
        IGameContext context;

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

        Element weaponsNode = (Element) contextElement.getElementsByTagName("weapons").item(0);
        NodeList weaponNodes = weaponsNode.getElementsByTagName("weapon");

        Map<Double, WeaponModel.IWeaponFactory> weaponRates = new HashMap<>();
        List<WeaponModel.IWeaponFactory> oneShotWeapons = new ArrayList<>();

        for (int i = 0; i < weaponNodes.getLength(); i++) {
            Element weaponElement = (Element) weaponNodes.item(i);
            String name = weaponElement.getAttribute("name");
            WeaponModel.IWeaponFactory weaponFactory = WeaponParser.parseWeapon(name);
            if (weaponFactory == null) {
                throw new InvalidScenarioException("Invalid weapon name");
            }
            double rate = 0;
            try {
                if (!oneshot) rate = Double.parseDouble(weaponElement.getAttribute("rate"));
            } catch (NumberFormatException e) {
                throw new InvalidScenarioException("Invalid weapon rate");
            }
            if (oneshot) {
                oneShotWeapons.add(weaponFactory);
            } else {
                weaponRates.put(rate, weaponFactory);
            }
        }


        Element enemiesNode = (Element) contextElement.getElementsByTagName("enemies").item(0);
        NodeList enemyNodes = enemiesNode.getElementsByTagName("enemy");

        Map<Double, IEnemy.IEnemyFactory> enemyRates = new HashMap<>();
        List<IEnemy.IEnemyFactory> oneShotEnemies = new ArrayList<>();

        for (int i = 0; i < enemyNodes.getLength(); i++) {
            Element enemyElement = (Element) enemyNodes.item(i);
            String name = enemyElement.getAttribute("name");
            IEnemy.IEnemyFactory enemyFactory = EnemyParser.getEnemyFactory(name);
            if (enemyFactory == null) {
                throw new InvalidScenarioException("Invalid enemy name");
            }
            double rate = 0;
            try {
                if (!oneshot) rate = Double.parseDouble(enemyElement.getAttribute("rate"));
            } catch (NumberFormatException e) {
                throw new InvalidScenarioException("Invalid enemy rate");
            }
            if (oneshot) {
                oneShotEnemies.add(enemyFactory);
            } else {
                enemyRates.put(rate, enemyFactory);
            }
        }

        Element miscsNode = (Element) contextElement.getElementsByTagName("miscs").item(0);
        NodeList miscNodes = miscsNode.getElementsByTagName("misc");

        Map<Double, IEntity.IEntityFactory> miscRates = new HashMap<>();
        List<IEntity.IEntityFactory> oneShotMiscs = new ArrayList<>();


        for (int i = 0; i < miscNodes.getLength(); i++) {
            Element miscNode = (Element) miscNodes.item(i);
            String name = miscNode.getAttribute("name");
            IEntity.IEntityFactory miscEntityFactory = MiscEntityParser.parseMiscEntity(name);
            if (miscEntityFactory == null) {
                throw new InvalidScenarioException("Invalid entity name");
            }
            double rate = 0;
            try {
                if (!oneshot) rate = Double.parseDouble(miscNode.getAttribute("rate"));
            } catch (NumberFormatException e) {
                throw new InvalidScenarioException("Invalid entity rate");
            }
            if (oneshot) {
                oneShotMiscs.add(miscEntityFactory);
            } else {
                miscRates.put(rate, miscEntityFactory);
            }
        }

        if (oneshot) {
            context = new IGameContext.OneShotSpawnContext(oneShotWeapons, oneShotEnemies, oneShotMiscs);
        } else {
            context = new IGameContext.FixedSpawnRateContext(weaponRates, enemyRates, miscRates);
        }


        return new Pair<>(time, context);
    }

    public static Scenario loadScenario(InputStream stream) throws InvalidScenarioException {
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


            Scenario scenario = new Scenario(endReachedBehaviour);
            NodeList contextNodes = root.getElementsByTagName("interval");

            for (int i = 0; i < contextNodes.getLength(); i++) {
                Node interval_node = contextNodes.item(i);
                Pair<Double, IGameContext> pair = parseContextElement((Element) interval_node);
                scenario.put(pair.first(), pair.second());
            }

            return scenario;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
