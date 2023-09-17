package ui;


import data.DataIO;
import model.*;
import statistics.*;
import utils.Utils;

import java.util.*;

/**
 * @author : Alexandre Geração - 1211151
 **/
public class StatisticCalculationsUI {

    private static final String PER_HUBBER = "Por Cabaz";
    private static final String PER_CLIENT = "Por Cliente";
    private static final String PER_PRODUCER = "Por Produtor";
    private static final String PER_HUB = "Por Hub";


    private final StatisticCalculations statisticCalculations = new StatisticCalculations(null,null, null);

    public boolean statisticCalculations(DataIO dataIO){
        int func = -1;
        boolean flag = true;

        System.out.print("\n\nDia para gerar a lista de exportação :: ");
        int day = Utils.integerInput(0, Integer.MAX_VALUE);


        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(dataIO);
        List<Expedition> expeditionList = dispatchListWithoutRestriction.dispatchListWithoutRestriction(day);

        StatisticCalculations statisticCalculations = new StatisticCalculations(expeditionList,dataIO.getProvidedHumpers().find(new DayProducer(day)).getProducers(), dataIO.getGraph());

        while (flag){
            func=menu();
            switch (func) {
                case 0 -> flag = false;

                case 1 -> {
                    Map<Entity, StatisticsPerHumper> map = statisticCalculations.calculateHumperStatistic();

                    System.out.println();
                    for(Map.Entry<Entity, StatisticsPerHumper> me : map.entrySet()){
                        System.out.print("Cabaz " + me.getKey() + " -> Totalmente Satisfeitos: " +
                                me.getValue().getCompletelySatisfiedProducts() +
                                " | Parcialmente Satisfeitos: " + me.getValue().getParciallySatisfiedProducts() +
                                " | Não Satisfeitos: " + me.getValue().getNonSatisfiedProducts() +
                                " | Nº de Produtores que Forneceram Cabaz: " + me.getValue().getDistinctProducers().size());
                        System.out.printf(" | Percentagem de satisfação do Cabaz: %.2f%% \n", (me.getValue().getPercentageOfSatisfaction()*100));
                    }
                    System.out.println();
                }
                case 2 ->{
                    Map<Entity, StastisticPerClient> stastistics = statisticCalculations.calculateClientStatistic();
                    System.out.println();
                    for(Map.Entry<Entity, StastisticPerClient> m : stastistics.entrySet())
                        System.out.println("Client: " + m.getKey() + " -> Produtores Distintos Que Forneceram Todos os Cabazes: " +
                                m.getValue().getDistinctProducers().size() + " | Totalmente Sastisfeito : "
                                + m.getValue().getTotalSatis() + " | Parcialmente Satisfeito : " + m.getValue().getParcialSatis());

                    System.out.println();
                }
                case 3 -> {
                    Map<Entity, StatisticPerProducer> statisticPerProducer = statisticCalculations.calculateProducerStatistic();
                    System.out.println();
                    for(Map.Entry<Entity, StatisticPerProducer> m : statisticPerProducer.entrySet())
                        System.out.println("Produtor: " + m.getKey() + " -> Clientes Fornecidos: " + m.getValue().getDistinctClients().size() +
                                " | Totalmente Fornecidos: " + m.getValue().getTotalDeliv() + " | Parcialmente Fornecidos: " + m.getValue().getPartialDeliv() +
                                " | nº de Produtos Totalmente Esgotados: " + m.getValue().getSoldOut() + " | Hubs Fornecidos: " + m.getValue().getNumberOfHubs());
                    System.out.println();

                }
                case 4 -> {
                    Map<Company, StatisticPerHub> stats = statisticCalculations.calculateHubStatistic();
                    System.out.println();
                    for(Map.Entry<Company, StatisticPerHub> me : stats.entrySet()){
                        System.out.println("Hub " + me.getKey() + " -> Clientes Distintos: " +
                                me.getValue().getDistinctClients().size() + " | Produtores Distintos: " +
                                me.getValue().getDistinctProducers().size());
                    }
                    System.out.println();
                }
            }
        }
        return true;
    }

    private int menu(){

        ArrayList<String> list = new ArrayList<>();
        list.add(PER_HUBBER);
        list.add(PER_CLIENT);
        list.add(PER_PRODUCER);
        list.add(PER_HUB);


        Utils.menuList(list);

        return Utils.integerInput(0, list.size());
    }

}
