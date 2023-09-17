package model;

import java.util.LinkedList;
import java.util.Objects;
/**
 * @author : Pedro Pereira - 1211131
 **/
public class MinimumConnectionData {
    LinkedList<Entity> path;
    Double diameter;
    Integer pathConnections;

    public MinimumConnectionData(LinkedList<Entity> path, Double diameter, Integer pathConnections) {
        this.path = path;
        this.diameter = diameter;
        this.pathConnections = pathConnections;
    }

    public LinkedList<Entity> getPath() {
        return path;
    }

    public Double getDiameter() {
        return diameter;
    }

    public Integer getPathConnections() {
        return pathConnections;
    }

}
