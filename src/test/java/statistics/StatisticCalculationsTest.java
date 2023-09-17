package statistics;

import data.DataIO;
import model.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

class StatisticCalculationsTest {

    private final String TEST_CLIENT_PRODUCERS = "src/test/test_files/clientes_produtores_empresas_small.csv";
    private final String TEST_DISTANCES = "src/test/test_files/distancias_small.csv";
    private final String TEST_CREEL = "src/test/test_files/cabazes_small.csv";
    private final DataIO testFile = new DataIO();
    private final String SMALL_CLIENT_PRODUCERS = "src/resources/grafos/Small/clientes-produtores_small.csv";
    private final String SMALL_DISTANCES = "src/resources/grafos/Small/distancias_small.csv";
    private final String SMALL_CREEL = "src/resources/grafos/Small/cabazes_small.csv";
    private final DataIO smallFile = new DataIO();
    private final String BIG_CLIENT_PRODUCERS = "src/resources/grafos/Big/clientes-produtores_big.csv";
    private final String BIG_DISTANCES = "src/resources/grafos/Big/distancias_big.csv";
    private final String BIG_CREEL = "src/resources/grafos/Big/cabazes_big.csv";
    private final DataIO bigFile = new DataIO();
    private StatisticCalculations day1;
    private StatisticCalculations day2;
    private StatisticCalculations day3;
    private DispatchListWithoutRestriction dLWRSmall;
    private DispatchListWithoutRestriction dLWRBig;
    private DispatchListWithoutRestriction dLWRTest;
    private DistributionHubs distributionHubs;



    @BeforeEach
    void setUp() throws Exception {
        smallFile.loadNetworkFile(SMALL_CLIENT_PRODUCERS,SMALL_DISTANCES);
        smallFile.loadHumpers(SMALL_CREEL);
        testFile.loadNetworkFile(TEST_CLIENT_PRODUCERS,TEST_DISTANCES);
        testFile.loadHumpers(TEST_CREEL);
        bigFile.loadNetworkFile(BIG_CLIENT_PRODUCERS,BIG_DISTANCES);
        bigFile.loadHumpers(BIG_CREEL);
        dLWRSmall = new DispatchListWithoutRestriction(smallFile);
        dLWRBig = new DispatchListWithoutRestriction(bigFile);
        dLWRTest = new DispatchListWithoutRestriction(testFile);
        day1 = new StatisticCalculations(dLWRSmall.dispatchListWithoutRestriction(1),smallFile.getProvidedHumpers().find(new DayProducer(1)).getProducers(), smallFile.getGraph());
        day2 = new StatisticCalculations(dLWRBig.dispatchListWithoutRestriction(4),bigFile.getProvidedHumpers().find(new DayProducer(4)).getProducers(), bigFile.getGraph());
        day3 = new StatisticCalculations(dLWRTest.dispatchListWithoutRestriction(3),testFile.getProvidedHumpers().find(new DayProducer(3)).getProducers(), testFile.getGraph());
        distributionHubs = new DistributionHubs();
    }

    @Test
    void testCalculateHumpersSmallFile() {
        distributionHubs.defineDistributionHubs(smallFile.getGraph(), 2);
        StatisticCalculations statisticCalculations = day1;
        Map<Entity,StatisticsPerHumper> mapTest = statisticCalculations.calculateHumperStatistic();

        Map<Entity,StatisticsPerHumper> mapCreated = new HashMap<>();


        Client c1 = new Client("CT1", "C1", new Coordinates(40.6389,-8.6553));
        Client c6 = new Client("CT12", "C6", new Coordinates(41.1495,-8.6108));
        Company e4 = new Company("CT9", "E4", new Coordinates(40.5364,-7.2683));

        mapCreated.put(c1, new StatisticsPerHumper());
        mapCreated.put(c6, new StatisticsPerHumper());
        mapCreated.put(e4, new StatisticsPerHumper());
        mapCreated.get(c1).setNonSatisfiedProducts(0);
        mapCreated.get(c1).setParciallySatisfiedProducts(0);
        mapCreated.get(c1).setCompletelySatisfiedProducts(3);
        mapCreated.get(c1).setPercentageOfSatisfaction(1);

        mapCreated.get(c6).setNonSatisfiedProducts(0);
        mapCreated.get(c6).setParciallySatisfiedProducts(3);
        mapCreated.get(c6).setCompletelySatisfiedProducts(0);
        mapCreated.get(c6).setPercentageOfSatisfaction(0.3051);

        mapCreated.get(e4).setNonSatisfiedProducts(3);
        mapCreated.get(e4).setParciallySatisfiedProducts(0);
        mapCreated.get(e4).setCompletelySatisfiedProducts(2);
        mapCreated.get(e4).setPercentageOfSatisfaction(0.4);


        Assert.assertEquals(mapTest.get(c1).getCompletelySatisfiedProducts(),mapCreated.get(c1).getCompletelySatisfiedProducts());
        Assert.assertEquals(mapTest.get(c6).getParciallySatisfiedProducts(),mapCreated.get(c6).getParciallySatisfiedProducts());
    }

    @Test
    void testCalculateClientsSmallFile() {
        distributionHubs.defineDistributionHubs(smallFile.getGraph(), 2);
        StatisticCalculations statisticCalculations = day1;
        Map<Entity,StastisticPerClient> mapTest = statisticCalculations.calculateClientStatistic();

        Map<Entity,StastisticPerClient> mapCreated = new HashMap<>();


        Client c1 = new Client("CT1", "C1", new Coordinates(40.6389,-8.6553));
        Client c6 = new Client("CT12", "C6", new Coordinates(41.1495,-8.6108));
        Company e4 = new Company("CT9", "E4", new Coordinates(40.5364,-7.2683));

        mapCreated.put(c1, new StastisticPerClient(1,0,new HashSet<>()));
        mapCreated.put(c6, new StastisticPerClient(0,1,new HashSet<>()));
        mapCreated.put(e4, new StastisticPerClient(0,1,new HashSet<>()));

        Assert.assertEquals(mapTest.get(c1).getTotalSatis(),mapCreated.get(c1).getTotalSatis());
        Assert.assertEquals(mapTest.get(c6).getParcialSatis(),mapCreated.get(c6).getParcialSatis());


    }

    @Test
    void testCalculateProducerSmallFile() {
        distributionHubs.defineDistributionHubs(smallFile.getGraph(), 2);
        StatisticCalculations statisticCalculations = day1;
        Map<Entity,StatisticPerProducer> mapTest = statisticCalculations.calculateProducerStatistic();

        Map<Entity,StatisticPerProducer> mapCreated = new HashMap<>();

        Producer p2 = new Producer("CT6", "P2", new Coordinates(40.2111, -8.4291));
        Producer p3 = new Producer("CT10", "P3", new Coordinates(39.7444, -8.8072));

        mapCreated.put(p2, new StatisticPerProducer(4,4,new HashSet<>(),5, 2,new HashSet<>()));
        mapCreated.put(p3, new StatisticPerProducer(5,7,new HashSet<>(),7, 2,new HashSet<>()));

        Assert.assertEquals(mapTest.get(p2).getTotalDeliv(),mapCreated.get(p2).getTotalDeliv());
        Assert.assertEquals(mapTest.get(p3).getSoldOut(),mapCreated.get(p3).getSoldOut());


    }


    @Test
    void testCalculateHumpersBigFile() {
        distributionHubs.defineDistributionHubs(bigFile.getGraph(), 3);

        StatisticCalculations statisticCalculations = day2;
        Map<Entity,StatisticsPerHumper> mapTest = statisticCalculations.calculateHumperStatistic();

        Map<Entity,StatisticsPerHumper> mapCreated = new HashMap<>();


        Client c1 = new Client("CT166", "C1", new Coordinates(40.1,-8.3333));
        Client c6 = new Client("CT203", "C6", new Coordinates(41.2833,-8.3833));

        mapCreated.put(c1, new StatisticsPerHumper());
        mapCreated.put(c6, new StatisticsPerHumper());
        mapCreated.get(c1).setNonSatisfiedProducts(0);
        mapCreated.get(c1).setParciallySatisfiedProducts(1);
        mapCreated.get(c1).setCompletelySatisfiedProducts(8);
        mapCreated.get(c1).setPercentageOfSatisfaction(0.8944);
        mapCreated.get(c1).setProductoObtidosQuantidade(0.0);
        mapCreated.get(c1).setProductoPedidosQuantidade(0.0);
        mapCreated.get(c1).setDistinctProducers(new HashSet<>());

        mapCreated.get(c6).setNonSatisfiedProducts(3);
        mapCreated.get(c6).setParciallySatisfiedProducts(0);
        mapCreated.get(c6).setCompletelySatisfiedProducts(0);
        mapCreated.get(c6).setPercentageOfSatisfaction(0.0);
        mapCreated.get(c6).setProductoObtidosQuantidade(0.0);
        mapCreated.get(c6).setProductoPedidosQuantidade(0.0);
        mapCreated.get(c6).setDistinctProducers(new HashSet<>());

        Assert.assertEquals(mapTest.get(c1).getCompletelySatisfiedProducts(),mapCreated.get(c1).getCompletelySatisfiedProducts());
        Assert.assertEquals(mapTest.get(c6).getParciallySatisfiedProducts(),mapCreated.get(c6).getParciallySatisfiedProducts());


    }

    @Test
    void testCalculateClientsTestFile() {
        distributionHubs.defineDistributionHubs(testFile.getGraph(), 1);
        StatisticCalculations statisticCalculations = day3;
        Map<Entity,StastisticPerClient> mapTest = statisticCalculations.calculateClientStatistic();

        Map<Entity,StastisticPerClient> mapCreated = new HashMap<>();


        Client c1 = new Client("CT1", "C1", new Coordinates(40.6389,-8.6553));
        Company e2 = new Company("CT11", "E2", new Coordinates(39.3167,-7.4167));

        mapCreated.put(c1, new StastisticPerClient(0,0,new HashSet<>()));
        mapCreated.put(e2, new StastisticPerClient(1,1,new HashSet<>()));

        Assert.assertEquals(mapTest.get(c1).getTotalSatis(),mapCreated.get(c1).getTotalSatis());
        Assert.assertEquals(mapTest.get(e2).getParcialSatis(),mapCreated.get(e2).getParcialSatis());
    }

    @Test
    void testCalculateProducerTestFile() {
        distributionHubs.defineDistributionHubs(testFile.getGraph(), 1);
        StatisticCalculations statisticCalculations = day3;
        Map<Entity,StatisticPerProducer> mapTest = statisticCalculations.calculateProducerStatistic();

        Map<Entity,StatisticPerProducer> mapCreated = new HashMap<>();

        Producer p2 = new Producer("CT6", "P2", new Coordinates(40.2111, -8.4291));
        Producer p3 = new Producer("CT10", "P3", new Coordinates(39.7444, -8.8072));

        mapCreated.put(p2, new StatisticPerProducer(2,4,new HashSet<>(),5, 2,new HashSet<>()));
        mapCreated.put(p3, new StatisticPerProducer(5,7,new HashSet<>(),0, 2,new HashSet<>()));

        Assert.assertEquals(mapTest.get(p2).getTotalDeliv(),mapCreated.get(p2).getTotalDeliv());
        Assert.assertEquals(mapTest.get(p3).getSoldOut(),mapCreated.get(p3).getSoldOut());
    }




}