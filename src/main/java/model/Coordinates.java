package model;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public record Coordinates(double latitude, double longitude) {
    @Override
    public int hashCode() {
        return (int)(latitude * longitude);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
    }
}