package ui;

import graphs.Graph;
import model.ClosestDistributionHub;
import model.Company;
import model.Entity;

import java.util.Map;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class ClosestDistributionHubUI {
    public Map<Entity, Company> closestDistributionHub(Graph<Entity, Double> graph){
        ClosestDistributionHub closestDistributionHub = new ClosestDistributionHub(graph);

        Map<Entity, Company> closestHubs = closestDistributionHub.closestHub();

        for(Map.Entry<Entity, Company> client : closestHubs.entrySet())
            System.out.println(client.getKey().getName() + " -> " + client.getValue().getName());

        return closestHubs;
    }
}
