package model;

import data.DataIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trees.AVL;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RestrictedDispatchListTest {
    //test small file
    //test big file
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

    @BeforeEach
    void setUp() throws Exception {
        testFile.loadNetworkFile(TEST_CLIENT_PRODUCERS, TEST_DISTANCES);
        testFile.loadHumpers(TEST_CREEL);

        smallFile.loadNetworkFile(SMALL_CLIENT_PRODUCERS, SMALL_DISTANCES);
        smallFile.loadHumpers(SMALL_CREEL);

        bigFile.loadNetworkFile(BIG_CLIENT_PRODUCERS, BIG_DISTANCES);
        bigFile.loadHumpers(BIG_CREEL);
    }
    @Test
    void restrictedDispatchListTestFileInvalidDay() {
        RestrictedDispatchList rdl = new RestrictedDispatchList(testFile);
        DistributionHubs dh = new DistributionHubs();
        dh.defineDistributionHubs(testFile.getGraph(), 2);

        int day = 0;
        int producersNumber = 2;

        List<Expedition> expected = new ArrayList<>();

        List<Expedition> actual = rdl.restrictedDispatchList(day, producersNumber);

        assertEquals(expected.toString(), actual.toString());
    }
    @Test
    void restrictedDispatchListTestFileInvalidProducersNumber() {
        RestrictedDispatchList rdl = new RestrictedDispatchList(testFile);
        DistributionHubs dh = new DistributionHubs();
        dh.defineDistributionHubs(testFile.getGraph(), 2);

        int day = 1;
        int producersNumber = 0;

        List<Expedition> expected = new ArrayList<>();

        List<Expedition> actual = rdl.restrictedDispatchList(day, producersNumber);

        assertEquals(expected.toString(), actual.toString());
    }
    @Test
    void restrictedDispatchListTestFile() {
        RestrictedDispatchList rdl = new RestrictedDispatchList(testFile);
        DistributionHubs dh = new DistributionHubs();
        dh.defineDistributionHubs(testFile.getGraph(), 2);

        int day = 1;
        int producersNumber = 2;

        List<Expedition> expected = new ArrayList<>();

        Producer p2 = new Producer("CT6", "P2", new Coordinates(40.2111, -8.4291));
        Producer p3 = new Producer("CT10", "P3", new Coordinates(39.7444, -8.8072));

        Iterable<EntityPlus> entityPlusIterable = testFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().inOrder();
        for (EntityPlus ep : entityPlusIterable) {
            Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
            Expedition expedition = new Expedition(ep, map);

            if (ep.getEntity().getName().equals("C1")) {
                map.put(new Products(6, 0.0), new ExpeditionInfo(p2, 2.0, 2.0));
                map.put(new Products(11, 0.0), new ExpeditionInfo(p3, 2.5, 2.5));

                expected.add(expedition);
            }
        }

        for (EntityPlus ep : entityPlusIterable) {
            Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
            Expedition expedition = new Expedition(ep, map);

            if (ep.getEntity().getName().equals("C1")) {
                map.put(new Products(5, 0.0), new ExpeditionInfo(p2, 5.0, 4.0));
                expected.add(expedition);
            } else if (ep.getEntity().getName().equals("E2")) {
                map.put(new Products(2, 0.0), new ExpeditionInfo(p2, 6.0, 6.0));
                map.put(new Products(7, 0.0), new ExpeditionInfo(p3, 7.5, 7.5));
                map.put(new Products(9, 0.0), new ExpeditionInfo(p3, 2.5, 2.5));

                expected.add(expedition);
            }
        }

        for (EntityPlus ep : entityPlusIterable) {
            Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
            Expedition expedition = new Expedition(ep, map);

            if (ep.getEntity().getName().equals("E2")) {
                map.put(new Products(1, 0.0), new ExpeditionInfo(p2, 9.0, 7.5));
                map.put(new Products(3, 0.0), new ExpeditionInfo(p2, 9.0, 1.5));
                map.put(new Products(5, 0.0), new ExpeditionInfo(p3, 6.0, 1.5));
                map.put(new Products(6, 0.0), new ExpeditionInfo(p2, 8.5, 0.5));
                map.put(new Products(10, 0.0), new ExpeditionInfo(p3, 4.5, 4.0));
                map.put(new Products(11, 0.0), new ExpeditionInfo(p3, 3.0, 0.5));

                expected.add(expedition);
            } else if (ep.getEntity().getName().equals("E3")) {
                expected.add(expedition);
            }
        }

        for (EntityPlus ep : entityPlusIterable) {
            Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
            Expedition expedition = new Expedition(ep, map);

            if (ep.getEntity().getName().equals("E3")) {
                map.put(new Products(6, 0.0), new ExpeditionInfo(p3, 10.0, 8.0));
                map.put(new Products(9, 0.0), new ExpeditionInfo(p2, 5.0, 1.0));
                map.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 5.5, 0.0));

                expected.add(expedition);
            }
        }

        List<Expedition> actual = rdl.restrictedDispatchList(day, producersNumber);

        assertEquals(expected.size(), actual.size());

        //System.out.println("Expected: " + expected.find(p).getProducer().toString());
        //System.out.println("Actual: " + actual.find(p).getProducer().toString());
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void restrictedDispatchListSmallFile() {
        RestrictedDispatchList rdl = new RestrictedDispatchList(smallFile);
        DistributionHubs dh = new DistributionHubs();
        dh.defineDistributionHubs(smallFile.getGraph(), 2);

        int day = 1;
        int producersNumber = 2;

        List<Expedition> expected = new ArrayList<>();

        Producer p2 = new Producer("CT6", "P2", new Coordinates(40.2111, -8.4291));
        Producer p1 = new Producer("CT17", "P1", new Coordinates(40.6667, -7.9167));

        Iterable<EntityPlus> entityPlusIterable = smallFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().inOrder();
        for (EntityPlus ep : entityPlusIterable) {
            Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
            Expedition expedition = new Expedition(ep, map);

            if (ep.getEntity().getName().equals("C1")) {
                map.put(new Products(5, 0.0), new ExpeditionInfo(p1, 5.0, 5.0));
                map.put(new Products(6, 0.0), new ExpeditionInfo(p2, 2.0, 2.0));

                expected.add(expedition);

                Map<Products, ExpeditionInfo> map2 = new LinkedHashMap<>();
                Expedition expedition2 = new Expedition(ep, map2);
                map2.put(new Products(11, 0.0), new ExpeditionInfo(p1, 2.5, 1.0));
                expected.add(expedition2);

            } else if (ep.getEntity().getName().equals("C2")) {
                map.put(new Products(2, 0.0), new ExpeditionInfo(p1, 5.5, 5.5));
                map.put(new Products(3, 0.0), new ExpeditionInfo(p1, 4.5, 4.5));
                map.put(new Products(5, 0.0), new ExpeditionInfo(p2, 4.0, 4.0));
                map.put(new Products(9, 0.0), new ExpeditionInfo(p1, 1.0, 1.0));
                map.put(new Products(10, 0.0), new ExpeditionInfo(p1, 9.0, 9.0));

                expected.add(expedition);

                Map<Products, ExpeditionInfo> map2 = new LinkedHashMap<>();
                Expedition expedition2 = new Expedition(ep, map2);
                map2.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 10.0, 0.0));
                expected.add(expedition2);

                Map<Products, ExpeditionInfo> map3 = new LinkedHashMap<>();
                Expedition expedition3 = new Expedition(ep, map3);
                expected.add(expedition3);

            } else if (ep.getEntity().getName().equals("C3")) {
                map.put(new Products(1, 0.0), new ExpeditionInfo(p2, 10.0, 7.5));
                map.put(new Products(5, 0.0), new ExpeditionInfo(p1, 9.0, 1.0));
                map.put(new Products(6, 0.0), new ExpeditionInfo(p2, 2.5, 0.5));
                map.put(new Products(9, 0.0), new ExpeditionInfo(p1, 4.5, 2.5));

                expected.add(expedition);

                Map<Products, ExpeditionInfo> map2 = new LinkedHashMap<>();
                Expedition expedition2 = new Expedition(ep, map2);
                expected.add(expedition2);

            } else if (ep.getEntity().getName().equals("C6")) {
                map.put(new Products(3, 0.0), new ExpeditionInfo(p1, 8.5, 4.5));
                map.put(new Products(10, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 9.0, 0.0));
                map.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 9.5, 0.0));

                expected.add(expedition);

                Map<Products, ExpeditionInfo> map2 = new LinkedHashMap<>();
                Expedition expedition2 = new Expedition(ep, map2);
                expected.add(expedition2);

            } else if (ep.getEntity().getName().equals("C8")) {
                map.put(new Products(6, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 2.5, 0.0));
                map.put(new Products(9, 0.0), new ExpeditionInfo(p2, 7.5, 1.0));
                map.put(new Products(12, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 6.0, 0.0));

                expected.add(expedition);

                Map<Products, ExpeditionInfo> map2 = new LinkedHashMap<>();
                Expedition expedition2 = new Expedition(ep, map2);
                expected.add(expedition2);

            } else if (ep.getEntity().getName().equals("C9")) {
                map.put(new Products(1, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 7.0, 0.0));
                map.put(new Products(9, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 3.0, 0.0));

                expected.add(expedition);

            } else if (ep.getEntity().getName().equals("E2")) {
                map.put(new Products(2, 0.0), new ExpeditionInfo(p2, 6.0, 6.0));
                map.put(new Products(7, 0.0), new ExpeditionInfo(p1, 7.5, 7.5));

                expected.add(expedition);

                Map<Products, ExpeditionInfo> map2 = new LinkedHashMap<>();
                Expedition expedition2 = new Expedition(ep, map2);
                map2.put(new Products(1, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 9.0, 0.0));
                map2.put(new Products(3, 0.0), new ExpeditionInfo(p2, 9.0, 1.5));
                map2.put(new Products(5, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 6.0, 0.0));
                map2.put(new Products(6, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.5, 0.0));
                map2.put(new Products(9, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 2.5, 0.0));
                map2.put(new Products(10, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 4.5, 0.0));
                map2.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 3.0, 0.0));

                expected.add(expedition2);

                Map<Products, ExpeditionInfo> map3 = new LinkedHashMap<>();
                Expedition expedition3 = new Expedition(ep, map3);
                expected.add(expedition3);

            } else if (ep.getEntity().getName().equals("E3")) {
                map.put(new Products(6, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 10.0, 0.0));
                map.put(new Products(9, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 5.0, 0.0));
                map.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 5.5, 0.0));

                expected.add(expedition);

                Map<Products, ExpeditionInfo> map2 = new LinkedHashMap<>();
                Expedition expedition2 = new Expedition(ep, map2);
                expected.add(expedition2);

            } else if (ep.getEntity().getName().equals("E4")) {
                map.put(new Products(1, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 6.5, 0.0));
                map.put(new Products(3, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 6.5, 0.0));
                map.put(new Products(5, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 1.0, 0.0));
                map.put(new Products(10, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 1.0, 0.0));
                map.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 5.5, 0.0));

                expected.add(expedition);
            } else if (ep.getEntity().getName().equals("E5")) {
                map.put(new Products(2, 0.0), new ExpeditionInfo(p1, 1.5, 1.5));

                expected.add(expedition);

                Map<Products, ExpeditionInfo> map2 = new LinkedHashMap<>();
                Expedition expedition2 = new Expedition(ep, map2);

                map2.put(new Products(6, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.0, 0.0));
                map2.put(new Products(7, 0.0), new ExpeditionInfo(p1, 9.5, 1.0));
                map2.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 6.0, 0.0));

                expected.add(expedition2);
            }
        }

        List<Expedition> actual = rdl.restrictedDispatchList(day, producersNumber);

        assertEquals(expected.size(), actual.size());

        //System.out.println("Expected: " + expected.find(p).getProducer().toString());
        //System.out.println("Actual: " + actual.find(p).getProducer().toString());
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void restrictedDispatchListBigFile() {
        RestrictedDispatchList rdl = new RestrictedDispatchList(bigFile);
        DistributionHubs dh = new DistributionHubs();
        dh.defineDistributionHubs(bigFile.getGraph(), 2);

        int day = 1;
        int producersNumber = 2;

        Producer p9 = new Producer("CT90", "P9", new Coordinates(40.2111, -8.4291));
        Producer p4 = new Producer("CT120", "P4", new Coordinates(39.9019, -8.275));

        //========Cliente1========//
        Client c1 = new Client("CT166", "C1", new Coordinates(40.1, -8.3333));
        EntityPlus c1plus = bigFile.getOrderedHumpers().clone().find(new DayClient(day)).getClients().find(new EntityPlus(c1));

        Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
        Expedition expedition1 = new Expedition(c1plus, map);
        map.put(new Products(5, 0.0), new ExpeditionInfo(p9, 3.0, 3.0));
        map.put(new Products(16, 0.0), new ExpeditionInfo(p9, 2.0, 2.0));

        Map<Products, ExpeditionInfo> map2 = new LinkedHashMap<>();
        Expedition expedition2 = new Expedition(c1plus, map2);
        map2.put(new Products(3, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 4.5, 0.0));
        map2.put(new Products(8, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 9.5, 0.0));
        map2.put(new Products(12, 0.0), new ExpeditionInfo(p9, 2.5, 2.0));
        map2.put(new Products(14, 0.0), new ExpeditionInfo(p4, 10.0, 7.0));
        map2.put(new Products(18, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 5.0, 0.0));

        //========Cliente77========//
        Client c77 = new Client("CT40", "C77", new Coordinates(41.1167, -7.6833));
        EntityPlus c77plus = bigFile.getOrderedHumpers().clone().find(new DayClient(day)).getClients().find(new EntityPlus(c77));

        Map<Products, ExpeditionInfo> map3 = new LinkedHashMap<>();
        Expedition expedition3 = new Expedition(c77plus, map3);
        map3.put(new Products(3, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 6.0, 0.0));
        map3.put(new Products(6, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 10.0, 0.0));
        map3.put(new Products(9, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 5.5, 0.0));
        map3.put(new Products(10, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 7.0, 0.0));
        map3.put(new Products(15, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 5.0, 0.0));
        map3.put(new Products(16, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 1.5, 0.0));
        map3.put(new Products(17, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 9.5, 0.0));
        map3.put(new Products(19, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 5.0, 0.0));
        map3.put(new Products(20, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 8.0, 0.0));

        //========Empresa90========//
        Client e90 = new Client("CT159", "E90", new Coordinates(41.2077, -8.6674));

        EntityPlus e90plus = bigFile.getOrderedHumpers().clone().find(new DayClient(day)).getClients().find(new EntityPlus(e90));

        Map<Products, ExpeditionInfo> map4 = new LinkedHashMap<>();
        Expedition expedition4 = new Expedition(e90plus, map4);

        map4.put(new Products(1, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 1.0, 0.0));
        map4.put(new Products(4, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 5.0, 0.0));
        map4.put(new Products(6, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 3.5, 0.0));
        map4.put(new Products(11, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 3.0, 0.0));
        map4.put(new Products(12, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 4.0, 0.0));
        map4.put(new Products(15, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 6.5, 0.0));
        map4.put(new Products(16, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 7.0, 0.0));
        map4.put(new Products(18, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 7.5, 0.0));
        map4.put(new Products(19, 0.0),
                new ExpeditionInfo(new Producer("", "NULL", null), 4.5, 0.0));

        List<Expedition> actual = rdl.restrictedDispatchList(day, producersNumber);

        //System.out.println("Expected: " + expedition1.getClient().toString());
        //System.out.println("Actual: " + actual.find(p).getProducer().toString());
        //System.out.println(actual.size());
        assertEquals(expedition1.toString(), actual.get(0).toString());
        assertEquals(expedition2.toString(), actual.get(1).toString());
        assertEquals(expedition3.toString(), actual.get(189).toString());
        assertEquals(expedition4.toString(), actual.get(325).toString());

    }

    @Test
    void getNClosestProducersToHubTestFile() {
        RestrictedDispatchList rdl = new RestrictedDispatchList(testFile);

        Client client = new Client("CT1", "C1", new Coordinates(40.6389, -8.6553));

        DistributionHubs dh = new DistributionHubs();
        dh.defineDistributionHubs(testFile.getGraph(), 2);

        ClosestDistributionHub cdh = new ClosestDistributionHub(testFile.getGraph());
        Company closestHub = cdh.closestHub().get(client);


        int day = 1;
        int producersNumber = 2;

        DayProducer dayProducer = testFile.getProvidedHumpers().find(new DayProducer(day));

        AVL<ProducerPlus> expected = new AVL<>();
        expected.insert(new ProducerPlus(new Producer("CT6", "P2", new Coordinates(40.2111, -8.4291))));
        expected.insert(new ProducerPlus(new Producer("CT10", "P3", new Coordinates(39.7444, -8.8072))));
        //expected.insert(new ProducerPlus(new Producer("CT17", "P1", new Coordinates(40.6667, -7.9167))));

        AVL<ProducerPlus> actual = rdl.getNClosestProducersToHub(closestHub, producersNumber, dayProducer);


        assertEquals(expected.size(), actual.size());
        for (ProducerPlus p : actual.inOrder()) {
            //System.out.println("Expected: " + expected.find(p).getProducer().toString());
            //System.out.println("Actual: " + actual.find(p).getProducer().toString());
            assertEquals(expected.find(p).getProducer(), p.getProducer());
        }
    }

    @Test
    void getNClosestProducersToHubTestFileInvalidDay() {
        RestrictedDispatchList rdl = new RestrictedDispatchList(testFile);

        Client client = new Client("CT1", "C1", new Coordinates(40.6389, -8.6553));

        DistributionHubs dh = new DistributionHubs();
        dh.defineDistributionHubs(testFile.getGraph(), 2);

        ClosestDistributionHub cdh = new ClosestDistributionHub(testFile.getGraph());
        Company closestHub = cdh.closestHub().get(client);

        int day = 0;
        int producersNumber = 2;

        DayProducer dayProducer = testFile.getProvidedHumpers().find(new DayProducer(day));

        AVL<ProducerPlus> expected = new AVL<>();

        AVL<ProducerPlus> actual = rdl.getNClosestProducersToHub(closestHub, producersNumber, dayProducer);

        assertEquals(expected.isEmpty(), actual.isEmpty());
    }

    @Test
    void getNClosestProducersToHubTestFileInvalidClient() {
        RestrictedDispatchList rdl = new RestrictedDispatchList(testFile);

        Client client = new Client("CT100", "C6", new Coordinates(40.6389, -8.6553));

        DistributionHubs dh = new DistributionHubs();
        dh.defineDistributionHubs(testFile.getGraph(), 2);

        ClosestDistributionHub cdh = new ClosestDistributionHub(testFile.getGraph());
        Company closestHub = cdh.closestHub().get(client);

        int day = 1;
        int producersNumber = 2;

        DayProducer dayProducer = testFile.getProvidedHumpers().find(new DayProducer(day));

        AVL<ProducerPlus> expected = new AVL<>();

        AVL<ProducerPlus> actual = rdl.getNClosestProducersToHub(closestHub, producersNumber, dayProducer);

        assertEquals(expected.isEmpty(), actual.isEmpty());
    }

    @Test
    void getNClosestProducersToHubSmallFile() {
        RestrictedDispatchList rdl = new RestrictedDispatchList(smallFile);

        Client client = new Client("CT1", "C1", new Coordinates(40.6389, -8.6553));

        DistributionHubs dh = new DistributionHubs();
        dh.defineDistributionHubs(smallFile.getGraph(), 2);

        ClosestDistributionHub cdh = new ClosestDistributionHub(smallFile.getGraph());
        Company closestHub = cdh.closestHub().get(client);

        int day = 1;
        int producersNumber = 2;

        DayProducer dayProducer = smallFile.getProvidedHumpers().find(new DayProducer(day));

        AVL<ProducerPlus> expected = new AVL<>();
        expected.insert(new ProducerPlus(new Producer("CT6", "P2", new Coordinates(40.2111, -8.4291))));
        //expected.insert(new ProducerPlus(new Producer("CT10", "P3", new Coordinates(39.7444, -8.8072))));
        expected.insert(new ProducerPlus(new Producer("CT17", "P1", new Coordinates(40.6667, -7.9167))));

        AVL<ProducerPlus> actual = rdl.getNClosestProducersToHub(closestHub, producersNumber, dayProducer);

        assertEquals(expected.size(), actual.size());
        for (ProducerPlus p : actual.inOrder()) {
            //System.out.println("Expected: " + expected.find(p).getProducer().toString());
            //System.out.println("Actual: " + actual.find(p).getProducer().toString());
            assertEquals(expected.find(p).getProducer(), p.getProducer());
        }
    }

    @Test
    void getNClosestProducersToHubBigFile() {
        RestrictedDispatchList rdl = new RestrictedDispatchList(bigFile);

        Client client = new Client("CT43", "C125", new Coordinates(39.1167, -7.2833));

        DistributionHubs dh = new DistributionHubs();
        dh.defineDistributionHubs(bigFile.getGraph(), 2);

        ClosestDistributionHub cdh = new ClosestDistributionHub(bigFile.getGraph());
        Company closestHub = cdh.closestHub().get(client);



        int day = 1;
        int producersNumber = 2;
        DayProducer dayProducer = bigFile.getProvidedHumpers().find(new DayProducer(day));

        AVL<ProducerPlus> expected = new AVL<>();

        expected.insert(new ProducerPlus(new Producer("CT90", "P9", new Coordinates(40.2111, -8.4291))));
        expected.insert(new ProducerPlus(new Producer("CT120", "P4", new Coordinates(39.9019, -8.275))));

        //expected.insert(new ProducerPlus(new Producer("CT17", "P1", new Coordinates(40.6667, -7.9167))));

        AVL<ProducerPlus> actual = rdl.getNClosestProducersToHub(closestHub, producersNumber, dayProducer);

        assertEquals(expected.size(), actual.size());
        for (ProducerPlus p : actual.inOrder()) {
            //System.out.println("Expected: " + expected.find(p).getProducer().toString());
            //System.out.println("Actual: " + actual.find(p).getProducer().toString());
            assertEquals(expected.find(p).getProducer(), p.getProducer());
        }
    }
}