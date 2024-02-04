package main.java.model.ingame.weapon;

import main.java.model.ingame.MapComponentModel;

public abstract class ProjectileModel extends MapComponentModel {

    // note to whoever is gonna work on this : make sure to add a zero paramater constructor to serve as a supplier for the factory

    public ProjectileModel(double x, double y) {
        super(x, y);
        //TODO Auto-generated constructor stub
    }
    
}
