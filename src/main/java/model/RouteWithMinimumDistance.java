package model;

import graphs.Algorithms;
import graphs.Graph;

import java.util.*;

public class RouteWithMinimumDistance {

    public Graph<Entity, Double> graph;
    public List<Expedition> expeditionList;
    public Map<Company, List<Expedition>> deliveredInEachHub;
    public HashMap<Company, Set<Producer>> producerPerHub;
    public HashMap<Company, Boolean> hubMap;
    public HashMap<Producer, Boolean> producerMap;
    public Map<Entity, Company> clientHub;

    public RouteWithMinimumDistance(Graph<Entity, Double> map, List<Expedition> expeditionList, Map<Company, List<Expedition>> deliveredInEachHub, Map<Entity, Company> clientHub) {
        this.graph = map;
        this.expeditionList = expeditionList;
        this.deliveredInEachHub = deliveredInEachHub;
        this.clientHub = clientHub;
        this.hubMap = new HashMap<>();
        this.producerMap = new HashMap<>();
        this.producerPerHub = new HashMap<>();
        generateHubMapAndProducerMap();
    }

    /**
     * Will prepare @hubMap, @producerPerHub, @producerMap for the minimumDistance method
     */
    private void generateHubMapAndProducerMap() {
        if (this.expeditionList == null || this.expeditionList.size() == 0 || this.graph == null || this.clientHub == null) return;

        for (Expedition e : this.expeditionList) {
            this.hubMap.put(this.clientHub.get(e.getClient().getEntity()), false);
            this.deliveredInEachHub.put(this.clientHub.get(e.getClient().getEntity()), new ArrayList<>());
            this.producerPerHub.put(this.clientHub.get(e.getClient().getEntity()), new HashSet<>());

            this.deliveredInEachHub.get(this.clientHub.get(e.getClient().getEntity())).add(e);

            for (Map.Entry<Products, ExpeditionInfo> entry : e.getMap().entrySet()) {
                ExpeditionInfo value = entry.getValue();
                if (!value.getProducer().getName().equals("NULL")) {
                    this.producerMap.put(value.getProducer(), false);
                }
            }
        }

        for (Expedition e : this.expeditionList) {
            for (Map.Entry<Products, ExpeditionInfo> entry : e.getMap().entrySet()) {
                ExpeditionInfo value = entry.getValue();
                if (!value.getProducer().getName().equals("NULL")) {
                    this.producerPerHub.get(this.clientHub.get(e.getClient().getEntity())).add(value.getProducer());
                }
            }
        }
    }


    /**
     * Will find the shortest path to collect the orders from the producers and deliver them to the hubs of the respective clients
     * @param route which will be filled with the shortest path.
     */
    public void minimumDistance(List<Entity> route) {
        if (this.graph == null || this.expeditionList == null || this.expeditionList.size() == 0 || this.deliveredInEachHub == null || route == null) return;
        Entity actualPos = DistributionHubs.getHubsList().getLast();
        route.add(actualPos);

        ArrayList<LinkedList<Entity>> pathsFromActual = new ArrayList<>();
        ArrayList<Double> minDistance = new ArrayList<>();
        LinkedList<Entity> resultPath = new LinkedList<>();
        ArrayList<Producer> producersVisited = new ArrayList<>();

        int numberOfProducersAndHubs = this.producerMap.keySet().size() + this.hubMap.keySet().size();
        while (numberOfProducersAndHubs > 0) {
            if (!Algorithms.shortestPaths(this.graph, actualPos, Double::compare, Double::sum, 0.0, pathsFromActual, minDistance)) return;
            actualPos = closestPointOfInterest(pathsFromActual, minDistance, producersVisited, resultPath);
            if (actualPos == null) return;

            route.addAll(resultPath);

            pathsFromActual.clear();
            minDistance.clear();
            resultPath.clear();
            numberOfProducersAndHubs--;
        }
    }

    /**
     * Will find the shortest path to the next producer or hub
     * @param pathsFromActual shortest path to every point from the actual point
     * @param minDistance the size of pathsFromActual paths
     * @param producersVisited a list with all visited producers
     * @param resultPath the shortest path
     * @return the entity where the selected path ends, return null if there is a case where it doesn't find a producer or a hub.
     */
    public Entity closestPointOfInterest(List<LinkedList<Entity>> pathsFromActual, List<Double> minDistance, List<Producer> producersVisited, LinkedList<Entity> resultPath) {
        if (minDistance == null || pathsFromActual == null) return null;
        double distance = Collections.min(minDistance);
        int indexDistance = minDistance.indexOf(distance);

        minDistance.remove(indexDistance);
        pathsFromActual.remove(indexDistance);


        while (pathsFromActual.size() != 0) {
            distance = Collections.min(minDistance);
            indexDistance = minDistance.indexOf(distance);

            if (pathsFromActual.get(indexDistance).getLast() instanceof Company company && this.producerPerHub.containsKey(company) &&
                    !this.hubMap.get(company) && producersVisited.size() != 0 && new HashSet<>(producersVisited).containsAll(this.producerPerHub.get(company))) {

                this.hubMap.put(company, true);
                pathsFromActual.get(indexDistance).removeFirst();
                resultPath.addAll(pathsFromActual.get(indexDistance));
                return pathsFromActual.get(indexDistance).getLast();
            }

            if (pathsFromActual.get(indexDistance).getLast() instanceof Producer producer && this.producerMap.containsKey(producer) && !this.producerMap.get(producer)) {
                this.producerMap.put(producer, true);
                pathsFromActual.get(indexDistance).removeFirst();
                resultPath.addAll(pathsFromActual.get(indexDistance));
                producersVisited.add(producer);
                return pathsFromActual.get(indexDistance).getLast();
            }

            minDistance.remove(indexDistance);
            pathsFromActual.remove(indexDistance);
        }
        return null;
    }
}
