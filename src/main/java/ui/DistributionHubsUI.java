package ui;

import graphs.Graph;
import model.Company;
import model.DistributionHubs;
import model.Entity;
import utils.Utils;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class DistributionHubsUI {

    DistributionHubs distributionHubs = new DistributionHubs();

    public boolean distributionHubs(Graph<Entity, Double> graph){
        System.out.print("Número de empresas a procurar :: ");
        int topN = Utils.integerInput(1, graph.numVertices());
        if(topN <= 0) return false;

        if (DistributionHubs.getHubsList() != null){
            int sizeHubsList = DistributionHubs.getHubsList().size();
            if (topN <= sizeHubsList){
                System.out.println("Top " + topN + " empresas mais próximas de todos os pontos de rede.");
                for (int i = 0; i < sizeHubsList - topN; i++) {
                    DistributionHubs.getHubsList().removeLast();
                }
                for(Company c : DistributionHubs.getHubsList()) System.out.println(c.toString());

            }else {
                distributionHubs.defineDistributionHubs(graph, topN);
                System.out.println("Top " + topN + " empresas mais próximas de todos os pontos de rede.");
                for(Company c : DistributionHubs.getHubsList()) System.out.println(c.toString());
            }
        }else {
            distributionHubs.defineDistributionHubs(graph, topN);
            System.out.println("Top " + topN + " empresas mais próximas de todos os pontos de rede.");
            for(Company c : DistributionHubs.getHubsList()) System.out.println(c.toString());
        }

        return true;
    }
}
