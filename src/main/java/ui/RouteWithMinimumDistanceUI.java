package ui;

import data.DataIO;
import model.*;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class RouteWithMinimumDistanceUI {
    RouteWithMinimumDistance routeWithMinimumDistance;

    public boolean exportRestrictedList(DataIO dataIO, List<Expedition> expeditionList, Map<Entity, Company> closestHubs){

        Map<Company, List<Expedition>> deliveredInEachHub = new HashMap<>();

        routeWithMinimumDistance = new RouteWithMinimumDistance(dataIO.getGraph(), expeditionList,
                deliveredInEachHub, closestHubs);

        ArrayList<Entity> list = new ArrayList<>();
        routeWithMinimumDistance.minimumDistance(list);
        System.out.print("Nome para guardar o ficheiro :: ");
        Utils.createDotGraph(Utils.stringInput(), dataIO.getGraph(), list);
        return true;
    }
}
