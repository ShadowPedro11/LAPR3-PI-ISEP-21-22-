package model;

import graphs.Algorithms;
import graphs.Graph;

import java.util.*;

/**
 * @author : Tiago Oliveira - 1211128
 **/
public class DistributionHubs {
    /**
     * Is the list with all the hubs in the distribution network
     */
    private static LinkedList<Company> hubsList;

    /**
     * @return the list with all the hubs in the distribution network
     */
    public static LinkedList<Company> getHubsList() {
        return hubsList;
    }

    /**
     * This method receives as parameters the graph for which the hubs will be defined and the number of hubs required.
     * If the given graph is null or isn't connected, the hubsList will be null. If it is connected, for each company,
     * calculates the average distance between the specified company and all the clients/producers. Then it puts the
     * nearest N companies in the hubsList.
     *
     * @param graph is the graph to be used to define the hubs
     * @param n     is the number of hubs to be defined (the N companies closest to all the points of the network)
     */
    public void defineDistributionHubs(Graph<Entity, Double> graph, Integer n) {
        if (graph.edges().isEmpty() || graph.vertices().isEmpty()) {
            hubsList = null;
            return;
        }
        MinimumConnectionsNumber mcn = new MinimumConnectionsNumber(graph);
        if (!mcn.isConnected()) {
            hubsList = null;
            return;
        }
        hubsList = new LinkedList<>();
        Map<Double, Company> avgDistanceMap = new TreeMap<>(Double::compareTo);
        for (Entity e : graph.vertices()) {
            if (e instanceof Company c) {
                ArrayList<Double> distances = new ArrayList<>();
                Algorithms.shortestPaths(graph, e, Double::compare, Double::sum, 0.0, new ArrayList<>(), distances);
                avgDistanceMap.put(calcAvgDistance(distances), c);
            }
        }
        for (Company c : avgDistanceMap.values()) {
            if (n-- <= 0) break;
            hubsList.add(c);
        }
    }


    /**
     * This method calculates the average distance between a certain company and all the clients/producers.
     *
     * @param distances is the list with the distances (weights of the edges) between
     *                  a certain company and all the clients/producers.
     * @return the average distance between a certain company and all the clients/producers or null if the given list is empty.
     */
    public Double calcAvgDistance(ArrayList<Double> distances) {
        if (distances.isEmpty()) return null;
        Double sum = 0.0;
        for (Double distance : distances) {
            sum += distance;
        }
        return sum / distances.size();
    }

}
