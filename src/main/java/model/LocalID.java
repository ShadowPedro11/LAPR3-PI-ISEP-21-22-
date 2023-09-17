package model;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public record LocalID(String name, String localID) implements Comparable<LocalID> {
    @Override
    public int compareTo(LocalID o) {
        return this.localID.compareTo(o.localID);
    }
}
