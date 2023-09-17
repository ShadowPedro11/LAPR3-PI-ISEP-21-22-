package model;

import graphs.Algorithms;
import graphs.Graph;

import java.util.*;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class ClosestDistributionHub {

    private final Graph<Entity, Double> graph;

    public ClosestDistributionHub(Graph<Entity, Double> graph) {
        this.graph = graph;
    }

    /**
     * This method will search for a list containing all hubs of the graph
     * then for every entity will compare the distance between that entity and hub
     * and save in the map the entity e with the hub with the shortest distance
     * @return A map containing the closest hub (value) for every entity (key).
     */
    public HashMap<Entity, Company> closestHubOld() {
        LinkedList<Company> hubs = DistributionHubs.getHubsList();
        if (hubs.size() == 0) return null;

        HashMap<Entity, Company> closetsHubToClient = new HashMap<>();

        for (Entity e : this.graph.vertices()) {
            Company closestCompany = null;
            double shortestEdge = Double.MAX_VALUE;

            for (Company c : hubs) {
                Double weightShortestPath = Algorithms.shortestPath(this.graph, e, c, Double::compare, Double::sum, 0.0, new LinkedList<>());

                if (weightShortestPath != null && weightShortestPath < shortestEdge) {
                    closestCompany = c;
                    shortestEdge = weightShortestPath;
                }
            }
            closetsHubToClient.put(e, closestCompany);
        }

        return closetsHubToClient;
    }

    public HashMap<Entity, Company> closestHub() {
        LinkedList<Company> hubs = DistributionHubs.getHubsList();
        if (hubs.size() == 0) return null;
        HashMap<Entity, Company> closetsHubToClient = new HashMap<>();

        ArrayList<LinkedList<Entity>> pathsFromActual = new ArrayList<>();
        ArrayList<Double> minDistance = new ArrayList<>();

        double distance = 0;
        int index = 0;

        for (Entity e : this.graph.vertices()) {
            Algorithms.shortestPaths(this.graph, e, Double::compare, Double::sum, 0.0, pathsFromActual, minDistance);

            while (pathsFromActual.size() != 0) {
                distance = Collections.min(minDistance);
                index = minDistance.indexOf(distance);

                if (pathsFromActual.get(index).getLast() instanceof Company company && hubs.contains(company)) {
                    closetsHubToClient.put(e, company);
                    break;
                }

                minDistance.remove(index);
                pathsFromActual.remove(index);
            }


            pathsFromActual.clear();
            minDistance.clear();
        }
        return closetsHubToClient;
    }
}
