package statistics;

import graphs.Graph;
import model.*;
import trees.AVL;

import java.util.*;

/**
 * @author : Alexandre Geração - 1211151
 **/
public class StatisticCalculations {

    private List<Expedition> expeditionList;
    private AVL<ProducerPlus> producerPlusAVL;
    private Graph<Entity, Double> graph;

    public StatisticCalculations(List<Expedition> expeditionList, AVL<ProducerPlus> producerPlusAVL, Graph<Entity, Double> map) {
        this.expeditionList = expeditionList;
        this.producerPlusAVL = producerPlusAVL;
        this.graph = map;
    }

    /**
     * The function goes through the expedition list and does a cycle everytime it finds a new client
     * Checks if the linked map contains the entity key
     * It then creates two variables that get the value of quantity ordered and quantity supplied
     * Checks the results of both and increments the value of completelySatisfiedProducts, parciallySatisfiedProducts and nonSatisfiedProducts accordingly
     * If the linked map does not contain the key, does the same cycle but creates new hash sets and variables for the requirements it needs
     * @return returns the map created with all the data for each humper
     */
    public Map<Entity, StatisticsPerHumper> calculateHumperStatistic(){

        Map<Entity, StatisticsPerHumper> statisticsPerHumper = new LinkedHashMap<>();

        for(Expedition e : expeditionList) {
            Entity entity = e.getClient().getEntity();

            if(statisticsPerHumper.containsKey(entity)){

                Integer completelySatisfiedProducts = statisticsPerHumper.get(entity).getCompletelySatisfiedProducts();
                Integer parciallySatisfiedProducts = statisticsPerHumper.get(entity).getParciallySatisfiedProducts();
                Integer nonSatisfiedProducts = statisticsPerHumper.get(entity).getNonSatisfiedProducts();
                HashSet<Producer> distinctProducers = statisticsPerHumper.get(entity).getDistinctProducers();
                double percentageOfSatisfaction = statisticsPerHumper.get(entity).getPercentageOfSatisfaction();
                double productoPedidosQuantidade = statisticsPerHumper.get(entity).getProductoPedidosQuantidade();
                double productoObtidosQuantidade =  statisticsPerHumper.get(entity).getProductoObtidosQuantidade();

                Map<Products,ExpeditionInfo> productsMap = e.getMap();

                for (Map.Entry<Products,ExpeditionInfo> v: productsMap.entrySet()){
                    Producer producer = v.getValue().getProducer();
                    double quantidadePedida = v.getValue().getQuantidadePedida();
                    double quantidadeFornecida = v.getValue().getQuantidadeFornecida();

                    if(quantidadeFornecida==quantidadePedida){
                        completelySatisfiedProducts++;

                        productoObtidosQuantidade +=1;
                        productoPedidosQuantidade +=1;
                    }
                    if(quantidadeFornecida==0.0){
                        nonSatisfiedProducts++;

                        productoObtidosQuantidade +=0;
                        productoPedidosQuantidade +=1;
                    }
                    if(quantidadeFornecida!=0.00 & quantidadeFornecida!= quantidadePedida){
                        parciallySatisfiedProducts++;

                        productoObtidosQuantidade += (quantidadeFornecida/quantidadePedida);
                        productoPedidosQuantidade +=1;
                    }
                    if(!distinctProducers.contains(producer) && producer.getName().equals("NULL")) {
                        distinctProducers.add(producer);
                    }

                }
                statisticsPerHumper.get(entity).setProductoObtidosQuantidade(productoObtidosQuantidade);
                statisticsPerHumper.get(entity).setProductoPedidosQuantidade(productoPedidosQuantidade);

                percentageOfSatisfaction=productoObtidosQuantidade/productoPedidosQuantidade;
                statisticsPerHumper.get(entity).setCompletelySatisfiedProducts(completelySatisfiedProducts);
                statisticsPerHumper.get(entity).setParciallySatisfiedProducts(parciallySatisfiedProducts);
                statisticsPerHumper.get(entity).setNonSatisfiedProducts(nonSatisfiedProducts);
                statisticsPerHumper.get(entity).setPercentageOfSatisfaction(percentageOfSatisfaction);

            }else{
                statisticsPerHumper.put(entity,new StatisticsPerHumper());

                Integer completelySatisfiedProducts = 0;
                Integer parciallySatisfiedProducts = 0;
                Integer nonSatisfiedProducts = 0;
                HashSet<Producer> distinctProducers = new HashSet<>();
                double percentageOfSatisfaction = 0.0;
                double productoPedidosQuantidade = 0.0;
                double productoObtidosQuantidade = 0.0;

                Map<Products,ExpeditionInfo> productsMap = e.getMap();

                for (Map.Entry<Products,ExpeditionInfo> v: productsMap.entrySet()){
                    Producer producer = v.getValue().getProducer();
                    double quantidadePedida = v.getValue().getQuantidadePedida();
                    double quantidadeFornecida = v.getValue().getQuantidadeFornecida();

                    if(quantidadeFornecida==quantidadePedida){
                        completelySatisfiedProducts++;
                        productoObtidosQuantidade +=1;
                        productoPedidosQuantidade +=1;
                    }
                    if(quantidadeFornecida==0.0){
                        nonSatisfiedProducts++;
                        productoObtidosQuantidade +=0;
                        productoPedidosQuantidade +=1;
                    }
                    if(quantidadeFornecida!=0.00 & quantidadeFornecida!= quantidadePedida){
                        parciallySatisfiedProducts++;
                        productoObtidosQuantidade += (quantidadeFornecida/quantidadePedida);
                        productoPedidosQuantidade +=1;
                    }
                    if(!distinctProducers.contains(producer) && !Objects.equals(producer.getName(), "NULL")){
                        distinctProducers.add(producer);
                    }

                }
                percentageOfSatisfaction=productoObtidosQuantidade/productoPedidosQuantidade;

                statisticsPerHumper.get(entity).setProductoObtidosQuantidade(productoObtidosQuantidade);
                statisticsPerHumper.get(entity).setProductoPedidosQuantidade(productoPedidosQuantidade);
                statisticsPerHumper.get(entity).setCompletelySatisfiedProducts(completelySatisfiedProducts);
                statisticsPerHumper.get(entity).setParciallySatisfiedProducts(parciallySatisfiedProducts);
                statisticsPerHumper.get(entity).setNonSatisfiedProducts(nonSatisfiedProducts);
                statisticsPerHumper.get(entity).setDistinctProducers(distinctProducers);
                statisticsPerHumper.get(entity).setPercentageOfSatisfaction(percentageOfSatisfaction);

            }
        }
        return statisticsPerHumper;
    }


    /**
     * The function goes through the expedition list and does a cycle everytime it finds a new client
     * It then creates two variables that get the value of quantity ordered and quantity supplied
     * If both quantities are the same, it increments the number of total satisfied humpers else it increments the number of partial satisfied humpers
     * After it checks if the number of total satisfied humpers is the same as the number of products. If so, sets the number of humpers to 1
     * It also creates a HashSet to check all the different producers that supplied all of its humpers
     * @return returns the map created with all the data for each client
     */
    public Map<Entity, StastisticPerClient> calculateClientStatistic(){

        Map<Entity, StastisticPerClient> stastistics = new LinkedHashMap<>();

        for(Expedition e : expeditionList) {
            Entity client = e.getClient().getEntity();

            StastisticPerClient stastistic = stastistics.get(client);
            if(stastistic == null) {
                stastistic = new StastisticPerClient(0, 0, null);
                stastistics.put(client, stastistic);
            }

            for (Map.Entry<Products, ExpeditionInfo> me : e.getMap().entrySet()) {
                double quantFornecida = me.getValue().getQuantidadeFornecida();
                double quantPedida = me.getValue().getQuantidadePedida();
                Producer p = me.getValue().getProducer();
                if(p.getName().equals("NULL"))
                    continue;

                HashSet<Producer> producers = stastistics.get(client).getDistinctProducers();

                if(producers == null){
                    producers = new HashSet<>();
                    stastistics.get(client).setDistinctProducers(producers);
                }


                if(quantFornecida == quantPedida) {
                    stastistic.incrementTotalSatis();
                    producers.add(me.getValue().getProducer());
                } else {
                    stastistic.incrementParcialSatis();
                    producers.remove(me.getValue().getProducer());
                }
            }

            if((stastistic.getTotalSatis()==e.getClient().getProductsAVL().size()
                    || stastistic.getTotalSatis()==1
            )&& stastistic.getParcialSatis()==0.0 ){
                stastistic.setTotalSatis(1);
                stastistic.setParcialSatis(0);
            } else {
                stastistic.setParcialSatis(1);
                stastistic.setTotalSatis(0);
            }

       }
        return stastistics;
    }


    /**
     * Gets the closest hub to each client using a function previously implemented
     * Goes through the expedition list, sets new variables for quantity delivered and the quantity ordered
     * If both quantities are the same, it increments the number of total satisfied humpers else it increments the number of partial satisfied humpers
     * Checks for each client which hub is he from
     * Uses the clients to check which producers goes to which hub
     * @return returns the map created with all the data for each producer
     */
    public Map<Entity, StatisticPerProducer> calculateProducerStatistic() {


        ClosestDistributionHub closestDistributionHub = new ClosestDistributionHub(this.graph);

        Map<Entity, StatisticPerProducer> statisticPerProducer = new LinkedHashMap<>();
        StatisticPerProducer statistic = null;
        //getHub
       HashMap<Entity,Company> entityCompanyHashMap = closestDistributionHub.closestHub();


        for (Expedition e : expeditionList) {
            Entity client = e.getClient().getEntity();

            Company company = entityCompanyHashMap.get(client);

            for (Map.Entry<Products, ExpeditionInfo> me : e.getMap().entrySet()) {
                double quantDeliv = me.getValue().getQuantidadeFornecida();
                double quantOrder = me.getValue().getQuantidadePedida();
                Producer producer = me.getValue().getProducer();
                if (producer.getName().equals("NULL"))
                    continue;
                statistic = statisticPerProducer.get(producer);
                if (statistic == null) {
                    statistic = new StatisticPerProducer(0, 0, new HashSet<>(), 0, 0,new HashSet<>());
                    statisticPerProducer.put(producer, statistic);
                }

                if (quantDeliv == quantOrder)
                    statistic.incrementTotalDeliv();
                if (quantDeliv > 0 && quantDeliv < quantOrder)
                    statistic.incrementPartialDeliv();

                HashSet<Entity> distinctClients = statistic.getDistinctClients();
                if (distinctClients == null)
                    distinctClients = new HashSet<>();
                distinctClients.add(client);

                StatisticPerProducer atp = statisticPerProducer.get(me.getValue().getProducer());
                atp.getCompanies().add(company);

            }
        }

        int sold = 0;

        for(ProducerPlus pp: producerPlusAVL.inOrder()){
            Iterable<Products> productsIterable = pp.getProductsAVL().inOrder();
            sold = 0;
            for (Products products: productsIterable){
                if(products.getQuantity()==0.0){
                    sold++;
                }
            }
            statistic = statisticPerProducer.get(pp.getProducer());
            statistic.setSoldOut(sold);

        }

        for (Map.Entry<Entity, StatisticPerProducer> map: statisticPerProducer.entrySet()){
            map.getValue().setNumberOfHubs(map.getValue().getCompanies().size());
        }

        return statisticPerProducer;
    }


    /**
     * Gets the closest hub to each client using a function previously implemented
     * Goes through the expedition list and checks for each client which hub is he from
     * Uses the clients to check which producers goes to which hub
     * @return returns the map created with all the data for each hub
     */
    public Map<Company, StatisticPerHub> calculateHubStatistic(){

        Map<Company, StatisticPerHub> stats = new LinkedHashMap<>();

        ClosestDistributionHub closestDistributionHub = new ClosestDistributionHub(this.graph);

        HashMap<Entity,Company> entityCompanyHashMap = closestDistributionHub.closestHub();

        for(Expedition e : expeditionList) {

            Entity client = e.getClient().getEntity();
            Company company = entityCompanyHashMap.get(client);


            StatisticPerHub sph = stats.get(company);
            if(sph==null){
                sph = new StatisticPerHub(new HashSet<>(),new HashSet<>());
                stats.put(company,sph);
            }

            sph.getDistinctClients().add(client);

            for(Map.Entry<Products,ExpeditionInfo> mm: e.getMap().entrySet()){
                Producer producer = mm.getValue().getProducer();
                if(producer.getName().equals("NULL")) {
                    sph.getDistinctProducers().add(producer);
                }

            }
        }

        return stats;
    }
}
