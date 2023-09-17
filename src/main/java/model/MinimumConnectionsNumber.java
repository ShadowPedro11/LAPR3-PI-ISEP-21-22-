package model;

import graphs.Algorithms;
import graphs.Edge;
import graphs.Graph;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.BinaryOperator;

/**
 * @author : Pedro Pereira - 1211131
 **/
public class MinimumConnectionsNumber {

    /**
     * Binary Operator for Double
     */
    BinaryOperator<Double> sum = Double::sum;

    /**
     * Comparator for Double
     */
    Comparator<Double> ce = Double::compareTo;

    /**
     * Graph
     */
    private final Graph<Entity, Double> graph;

    /**
     * Constructor given a graph
     */
    public MinimumConnectionsNumber(Graph<Entity, Double> graph) {
        this.graph = graph;
    }


    /**
     * Check if a graph is connected, using BFS algorithm
     * @return boolean for graph connectivity (true if the graph is connected otherwise false)
     */
    public boolean isConnected(){
        //Verify if the graph is null
        if(graph == null){
            return false;
        }
        LinkedList<Entity> entityLinkedList = Algorithms.BreadthFirstSearch(graph,graph.vertex(0));
        //Verify if the Linked List is null
        if(entityLinkedList==null){
            return false;
        }
        int numConnectedComponents = entityLinkedList.size();
        int numVerticesOfGraph = graph.numVertices();
        return (numConnectedComponents==numVerticesOfGraph);
    }


    /**
     * Calculates the minimum distance graph using Floyd-Warshall
     * @return MinimumConnectionData of a graph (LinkedList<Entity> path;Double diameter;Integer pathConnections)
     * @return LinkedList<Entity> path - Vertices of minimum distance graph
     * @return Double diameter - Weight of minimum distance graph
     * @return Integer pathConnections - Number of connections of minimum distance graph
     */
    public MinimumConnectionData minimumConnectionNumber(){
        LinkedList<Entity> path = new LinkedList<>();
        Graph<Entity,Double> cloneGraph = graph.clone();
        Collection<Edge<Entity, Double>> xx = cloneGraph.edges();
        for (Edge<Entity, Double> c: xx){
            c.setWeight(1.00);
        }

        Double diameter = Algorithms.graphDiameter(cloneGraph,ce,sum,0.0,path);
        //Verify if the path is empty and if diameter is null
        if(diameter==null || path.isEmpty()){
            return null;
        }
        return new MinimumConnectionData(path,diameter,(path.size())-1);
    }

}
