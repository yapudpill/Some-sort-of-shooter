package model.ingame;

public abstract class MapComponentModel {
    protected Coordinates pos;

    public MapComponentModel(Coordinates pos) {
        if (pos == null)
            throw new IllegalArgumentException("Position cannot be null");
        this.pos = pos;
    }

    public Coordinates getPos() {
        return pos;
    }

    public void setPos(Coordinates pos) {
        this.pos = pos;
    }
}
