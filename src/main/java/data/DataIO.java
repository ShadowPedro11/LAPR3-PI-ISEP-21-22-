package data;

import exceptions.MissingNetworkException;
import graphs.Graph;
import graphs.map.MapGraph;
import model.*;
import trees.AVL;
import utils.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static trees.Algorithms.computeIfAbsent;


/**
 * Data input/output.
 * @author : Ricardo Venâncio - 1210828
 **/
public class DataIO {
    /**
     * Graph variable that will contain the loaded graph by file loading.
     */
    private Graph<Entity, Double> graph;
    /**
     * Entity AVL.
     */
    private final AVL<Entity> entityAVL = new AVL<>();
    /**
     * Entities local IDs AVL.
     */
    private final AVL<LocalID> localIDAVL = new AVL<>();
    /**
     * Orders from clients AVL.
     */
    private AVL<DayClient> orderedHumpers;
    /**
     * Provided from producers AVL.
      */
    private AVL<DayProducer> providedHumpers;

    /**
     * Loads orders/provided file into two separate AVL's (orderedHumpers and providedHumpers).
     * @param ordersPath path to the file to load.
     * @return true if the method worked properly, false otherwise.
     * @throws Exception when: the graph is empty throws MissingNetworkException, the ordersPath is null throws
     * NullPointerException and when the path leads to an invalid file throws FileNotFoundException.
     */
    public boolean loadHumpers(String ordersPath) throws Exception{

        orderedHumpers = new AVL<>();
        providedHumpers = new AVL<>();

        File f = Utils.validatePath(ordersPath);

        if(graph == null)
            throw new MissingNetworkException("");

        Scanner sc;

        try{
            sc = new Scanner(f);
            sc.nextLine();

            while(sc.hasNextLine()){

                String line = sc.nextLine();
                String[] words = line.split(",");
                String NAME = words[0].replace("\"", "");

                Entity ENTITY;

                final int DAY = Integer.parseInt(words[1]);

                if(NAME.matches("C[0-9]+")) {
                    ENTITY = entityAVL.find(new Client(null, NAME, null));
                }else if(NAME.matches("E[0-9]+")) {
                    ENTITY = entityAVL.find(new Company(null, NAME, null));
                }else if(NAME.matches("P[0-9]+"))
                    ENTITY = entityAVL.find(new Producer(null, NAME, null));
                else
                    continue;

                if(ENTITY instanceof Producer producer) {
                    //test(providedHumpers, DAY, producer, words,
                    // a -> new DayProducer(DAY), b -> new ProducerPlus(producer));
                    DayProducer dp = new DayProducer(DAY);
                    dp = computeIfAbsent(providedHumpers, dp, d -> new DayProducer(DAY));

                    ProducerPlus producerPlus = new ProducerPlus(producer);
                    producerPlus = computeIfAbsent(dp.getProducers(),
                            producerPlus, e -> new ProducerPlus(producer));

                    if(!loadProducts(words, producerPlus.getProductsAVL()))
                        dp.getProducers().remove(producerPlus);
                }else {
                    //test(orderedHumpers, DAY, ENTITY, words,
                     //a -> new DayClient(DAY), b -> new EntityPlus(ENTITY));
                    DayClient dc = new DayClient(DAY);
                    dc = computeIfAbsent(orderedHumpers, dc, d -> new DayClient(DAY));

                    EntityPlus entityPlus = new EntityPlus(ENTITY);
                    entityPlus = computeIfAbsent(dc.getClients(), entityPlus, e -> new EntityPlus(ENTITY));


                    if(!loadProducts(words, entityPlus.getProductsAVL()))
                        dc.getClients().remove(entityPlus);
                }
            }

        }catch(Exception e){
            System.out.println("O seguinte erro aconteceu: " + e.getMessage());
        }
        return true;
    }

    /**
     * Loads products to an AVL regarding a day and an entity.
     * @param words string array containing a row from the file.
     * @param productsAVL container to store the information from the file.
     * @return true if the entity ordered/provided something, false otherwise.
     */
    private boolean loadProducts(String[] words, AVL<Products> productsAVL){
        boolean flag = false;
        for(int i=2;i<words.length;i++) {
            if(words[i].matches("[1-9]([0-9]+)?(.[0-9]+)?")) {
                flag = true;
                Products products = productsAVL.find(new Products(i - 1, Double.parseDouble(words[i])));
                if(products == null){
                    products = new Products(i-1, Double.parseDouble(words[i]));
                    productsAVL.insert(products);
                }
            }
        }
        return flag;
    }
//    private <E extends Day & Comparable<E>, T> void test(AVL<E> avl, int day, Entity entity, String[] words,
//                                                         Function<?, ? extends E> dayFunction,
//                                                         Function<?, ? extends T> entityFunction){
//
//
//
////        Day d;
////
////        if(entity instanceof Producer){
////            d = new DayProducer(day);
////        }else
////            d = new DayClient(day);
////
////        d = computeIfAbsent(avl, d, d1 -> new DayProducer(day));
////
////        ProducerPlus producerPlus = new ProducerPlus(producer);
////        producerPlus = computeIfAbsent(dp.getProducers(),
////                producerPlus, e -> new ProducerPlus(producer));
////
////        if(!loadProducts(words, producerPlus.getProductsAVL()))
////            dp.getProducers().remove(producerPlus);
//    }


    /**
     * Loads a client/producer/company file and a distance between nodes, creates the network nodes.
     * @param entitiesPath path to entities (clients, producers and companies) file.
     * @param distancesPath path to distances (between nodes) file.
     * @return true if it was successfully loaded, false otherwise.
     * @throws FileNotFoundException if the file was not found or even if it was invalid.
     */
    public boolean loadNetworkFile(String entitiesPath, String distancesPath) throws FileNotFoundException {

        graph = new MapGraph<>(false);

        File clientProducers = Utils.validatePath(entitiesPath);
        File distances = Utils.validatePath(distancesPath);

        Scanner sc;

        try{
            String line;
            String[] words;

            sc = new Scanner(clientProducers);
            sc.nextLine();

            while(sc.hasNextLine()){
                line = sc.nextLine();
                words = line.split(",");

                final String LOCAL_ID = words[0], ENTITY = words[3];
                final double LAT = Double.parseDouble(words[1]), LON = Double.parseDouble(words[2]);

                if(ENTITY.matches("C[0-9]+")) {
                    Client c = new Client(LOCAL_ID, ENTITY, new Coordinates(LAT, LON));
                    graph.addVertex(c);
                    entityAVL.insert(c);
                }else if(ENTITY.matches("E[0-9]+")) {
                    Company c = new Company(LOCAL_ID, ENTITY, new Coordinates(LAT, LON));
                    graph.addVertex(c);
                    entityAVL.insert(c);
                }else if(ENTITY.matches("P[0-9]+")) {
                    Producer p = new Producer(LOCAL_ID, ENTITY, new Coordinates(LAT, LON));
                    graph.addVertex(p);
                    entityAVL.insert(p);
                }else
                    continue;
                localIDAVL.insert(new LocalID(ENTITY, LOCAL_ID));

            }

            sc.close();

            sc = new Scanner(distances);
            sc.nextLine();
            while(sc.hasNextLine()){
                line = sc.nextLine();
                words = line.split(",");

                final String LOCAL_ID_1 = words[0], LOCAL_ID_2 = words[1];
                final double DISTANCE = Double.parseDouble(words[2]);

                Entity entity1 = entityAVL.find(new Client(null,
                        localIDAVL.find(new LocalID(null, LOCAL_ID_1)).name(),
                        null));
                Entity entity2 = entityAVL.find(new Client(null,
                        localIDAVL.find(new LocalID(null, LOCAL_ID_2)).name(),
                        null));

                if(entity1 != null || entity2 != null)
                    graph.addEdge(entity1, entity2, DISTANCE);

            }
            sc.close();
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao carregar os ficheiros!");
            return false;
        }
        Utils.createDotGraph("Rede", graph, null);
        return true;
    }

    /**
     * Returns the network.
     * @return returns the network.
     */
    public Graph<Entity, Double> getGraph() {
        return graph;
    }
    /**
     * Returns the ordered humpers.
     * @return returns the ordered humpers.
     */
    public AVL<DayClient> getOrderedHumpers() {
        return orderedHumpers;
    }

    /**
     * Returns the provided humpers.
     * @return returns the provided humpers.
     */
    public AVL<DayProducer> getProvidedHumpers() {
        return providedHumpers;
    }

}
