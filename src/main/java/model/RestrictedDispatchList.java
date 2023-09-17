package model;

import data.DataIO;
import graphs.Algorithms;
import graphs.Graph;
import trees.AVL;

import java.util.*;

/**
 * @author : Tiago Oliveira - 1211128
 **/
public class RestrictedDispatchList {
    private final Graph<Entity, Double> graph;
    private final DataIO dataIO;
    AVL<DayClient> orderedHumpers;
    AVL<DayProducer> providedHumpers;

    public RestrictedDispatchList(DataIO dataIO) {
        this.dataIO = dataIO;
        this.graph = dataIO.getGraph();
        this.orderedHumpers = dataIO.getOrderedHumpers();
        this.providedHumpers = dataIO.getProvidedHumpers();
    }

    /**This method receives a certain day to do the dispatch list and the number of closest producers desired. Then, for
     * each client gets its hub and the N closest producers to that hub. This data is then used in the auxiliary methods
     * of DispatchListWithoutRestriction to compute the dispatch information for each client this is stored in the
     * dispatch list.
     *
     * @param day is the day to be analyzed.
     * @param producersNumber producer's number to filter.
     * @return dispatch list with the N closest producers to the clients' hubs.
     */
    public List<Expedition> restrictedDispatchList(int day, int producersNumber) {
        List<Expedition> dispatchList = new ArrayList<>();

        DispatchListWithoutRestriction dlwr = new DispatchListWithoutRestriction(dataIO);

        AVL<EntityPlus> actualDayEntityPlusAVL = new AVL<>();
        AVL<ProducerPlus> actualDayProducerPlusAVL;
        Iterable<EntityPlus> actualDayEntityPlusIterable;
        Iterable<ProducerPlus> actualDayProducerPlusIterable;
        AVL<ProducerPlus> day1 = null;
        AVL<ProducerPlus> day2 = null;

        ClosestDistributionHub cdh = new ClosestDistributionHub(graph);
        Map<Entity, Company> closestHubsMap = cdh.closestHub();

        for (int i = 1; i <= day; i++) {
            Iterable<EntityPlus> clientsList = orderedHumpers.find(new DayClient(i)).getClients().inOrder();
            DayProducer dayProducer = providedHumpers.clone().find(new DayProducer(day));

            for (EntityPlus client : clientsList) {
                Company closestHub = closestHubsMap.get(client.getEntity());
                actualDayEntityPlusAVL.insert(client);
                actualDayProducerPlusAVL = getNClosestProducersToHub(closestHub, producersNumber, dayProducer);
                actualDayEntityPlusIterable = actualDayEntityPlusAVL.inOrder();
                actualDayProducerPlusIterable = actualDayProducerPlusAVL.inOrder();

                if (actualDayProducerPlusAVL.isEmpty()) break;

                if (day == i) {
                    dlwr.expeditionListFullProducts(dispatchList, actualDayEntityPlusIterable, actualDayProducerPlusIterable, day1, day2);
                    dlwr.expeditionListRemainProducts(dispatchList, actualDayEntityPlusIterable, actualDayProducerPlusIterable, day1, day2);

                } else {
                    dlwr.expeditionFull(actualDayEntityPlusIterable, actualDayProducerPlusIterable, day1, day2);
                    dlwr.expeditionRemain(actualDayEntityPlusIterable, actualDayProducerPlusIterable, day1, day2);
                    day2 = day1;
                    day1 = actualDayProducerPlusAVL;

                }
                actualDayEntityPlusAVL.remove(client);
            }
        }
        return dispatchList;
    }


    /**This method receives the closest hub of a certain client for which the closest producers will be determined.
     * Firstly it will get the shortest path between the closest hub and all the vertices in the graph and then only the
     * producers that are the closer are selected.
     *
     * @param closestHub is the closest hub to a certain client from which it will be determined the closest producers.
     * @param producersNumber is the number of producers to determine.
     * @param dayProducer is the day that is being analysed.
     * @return an AVL of ProducerPlus, containing the producer's information, as well as its products' information.
     * */
    public AVL<ProducerPlus> getNClosestProducersToHub(Company closestHub, int producersNumber, DayProducer dayProducer) {
        AVL<ProducerPlus> producers = new AVL<>();

        if (dayProducer == null) return producers;

        Map<Double, Producer> distanceMap = new TreeMap<>(Double::compareTo);
        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<LinkedList<Entity>> paths = new ArrayList<>();
        Algorithms.shortestPaths(graph, closestHub, Double::compare, Double::sum, 0.0, paths, distances);

        int i = 0;
        for (LinkedList<Entity> path : paths) {
            if (path.getLast() instanceof Producer p) distanceMap.put(distances.get(i), p);
            i++;
        }

        for (Producer p : distanceMap.values()) {
            if (producersNumber-- <= 0) break;
            producers.insert(dayProducer.getProducers().find(new ProducerPlus(p)));
        }

        return producers;
    }
}
