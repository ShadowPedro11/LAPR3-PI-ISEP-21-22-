package model;

import graphs.Algorithms;
import graphs.Edge;
import graphs.Graph;
import graphs.map.MapGraph;

/**
 * @author : Alexandre Geração - 1211151
 **/

public class NetworkConnection {

    /**
     * Organizes the graph in a way top make the smallest path to connect all vertex
     * @param graph original graph that is received to be organized
     * @return the organized graph
     */
    public MapGraph<Entity, Double> arrangeNetwork(Graph<Entity, Double> graph) {
        return Algorithms.minimumSpanningTree(graph, 0.0, Double::compare);
    }

    /**
     * Uses the weight of the edges of the smallest path and sums them to create the weight/distance of the smallest path
     * @param mapGraph receives the map graph with the smallest path
     * @return the weight of the smallest path to connect all vertex
     */
    public int calcSum(MapGraph<Entity,Double> mapGraph){

        if (mapGraph == null){
            return -1;
        }

        int sum = 0;
        for (Edge<Entity,Double> edge:mapGraph.edges()) {
            sum += edge.getWeight();
        }
        sum = sum/2;
        return sum;
    }

}
