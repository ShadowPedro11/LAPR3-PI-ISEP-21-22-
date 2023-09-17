package ui;

import graphs.Graph;
import model.Entity;
import model.MinimumConnectionData;
import model.MinimumConnectionsNumber;

import java.util.LinkedList;

/**
 * @author : Pedro Pereira - 1211131
 **/
public class MinimumConnectionsNumberUI {
    public boolean minimumConnections(Graph<Entity, Double> graph){

        MinimumConnectionsNumber minimumConnectionsNumber = new MinimumConnectionsNumber(graph);
        boolean conexo = minimumConnectionsNumber.isConnected();
        System.out.println("Rede conectada : " + ((conexo) ? "Sim" : "Não"));
        if(conexo) {
            MinimumConnectionData minimumConnections = minimumConnectionsNumber.minimumConnectionNumber();
            showMinimConnection(minimumConnections);
        }else {
            System.out.println("Número mínimo de conexões: Erro Grafo não é conexo");
        }
        return true;
    }

    private void showMinimConnection(MinimumConnectionData minimumConnectionData){

        LinkedList<Entity> entityLinkedList = minimumConnectionData.getPath();
        Double diameter = minimumConnectionData.getDiameter();
        Integer pathConnections = minimumConnectionData.getPathConnections();

        for (int i = 0; i < entityLinkedList.size(); i++) {
            if(i!=entityLinkedList.size()-1){
                System.out.print(entityLinkedList.get(i).getName()+ "-->");
            }else {
                System.out.println(entityLinkedList.get(i).getName());
            }
        }
        System.out.println("Número mínimo de conexões: " + pathConnections);
        System.out.println("Valor do número mínimo de conexões: " + diameter);
    }
}
