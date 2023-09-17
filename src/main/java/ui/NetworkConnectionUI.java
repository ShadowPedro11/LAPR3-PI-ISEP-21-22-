package ui;

import graphs.Graph;
import graphs.map.MapGraph;
import model.Entity;
import model.NetworkConnection;
import utils.Utils;

/**
 * @author : Ricardo Venâncio - 1210828
 * @author : Alexandre Geração - 1211151
 **/
public class NetworkConnectionUI {

    private final NetworkConnection networkConnection = new NetworkConnection();
    public boolean networkConnection(Graph<Entity, Double> graph){
        MapGraph<Entity,Double> mapGraph = networkConnection.arrangeNetwork(graph);
        System.out.println("Rede: " + mapGraph.toString());

        System.out.println("\n");
        System.out.println("Distância total mínima: " + networkConnection.calcSum(mapGraph));
        Utils.createDotGraph("RedeConectoraMinima", mapGraph, null);
        return true;
    }
}
