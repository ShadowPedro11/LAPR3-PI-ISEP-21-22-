package model;

import java.util.List;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class AgriculturalInstallation {

    private String name;
    private List<Building> buildings;
    private List<AgriculuturalPlot> plots;

    public AgriculturalInstallation(String name, List<Building> buildings, List<AgriculuturalPlot> plots) {
        this.name = name;
        this.buildings = buildings;
        this.plots = plots;
    }

    public String getName() {
        return name;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<AgriculuturalPlot> getPlots() {
        return plots;
    }
}
