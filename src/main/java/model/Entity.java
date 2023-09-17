package model;

/**
 * @author : Ricardo Venâncio - 1210828
 *
 **/
public abstract class Entity implements Comparable<Entity> {
    private final String localID;
    private final String name;
    private final Coordinates local;

    public Entity(String localID, String name, Coordinates local) {
        this.localID = localID;
        this.name = name;
        this.local = local;
    }

    public String getName() {
        return name;
    }

    public Coordinates getLocal() {
        return local;
    }

    public String getLocalID() {
        return localID;
    }


    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(Entity e) {
        return this.getName().compareTo(e.getName());
    }

    @Override
    public int hashCode() {
        return getLocalID().hashCode() * getName().hashCode();
    }

}
