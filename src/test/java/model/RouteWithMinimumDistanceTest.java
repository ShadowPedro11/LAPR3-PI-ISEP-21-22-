package model;

import data.DataIO;
import graphs.map.MapGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RouteWithMinimumDistanceTest {

    private DataIO dataIoSmall;
    private DispatchListWithoutRestriction dispatchListWithoutRestrictionSmall;
    private RouteWithMinimumDistance routeWithMinimumDistanceSmall;
    private final String SMALL_CLIENT_PRODUCERS = "src/resources/grafos/Small/clientes-produtores_small.csv";
    private final String SMALL_DISTANCES = "src/resources/grafos/Small/distancias_small.csv";
    private final String SMALL_CREEL = "src/resources/grafos/Small/cabazes_small.csv";

    private DataIO dataIoBig;
    private final String BIG_CLIENT_PRODUCERS = "src/resources/grafos/Big/clientes-produtores_big.csv";
    private final String BIG_DISTANCES = "src/resources/grafos/Big/distancias_big.csv";
    private final String BIG_CREEL = "src/resources/grafos/Big/cabazes_big.csv";
    private RouteWithMinimumDistance routeWithMinimumDistanceNull;

    @Before
    public void setUp() throws Exception {
        dataIoSmall = new DataIO();
        dataIoSmall.loadNetworkFile(SMALL_CLIENT_PRODUCERS, SMALL_DISTANCES);
        dataIoSmall.loadHumpers(SMALL_CREEL);
        dispatchListWithoutRestrictionSmall = new DispatchListWithoutRestriction(dataIoSmall);

        dataIoBig = new DataIO();
        dataIoBig.loadNetworkFile(BIG_CLIENT_PRODUCERS, BIG_DISTANCES);
        dataIoBig.loadHumpers(BIG_CREEL);
    }

    @Test
    public void testNull1() {
        routeWithMinimumDistanceNull = new RouteWithMinimumDistance(null, new ArrayList<>(), new HashMap<>(), new HashMap<>());
        ArrayList<Entity> actual = new ArrayList<>();
        routeWithMinimumDistanceNull.minimumDistance(actual);
        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void testNull2() {
        routeWithMinimumDistanceNull = new RouteWithMinimumDistance(new MapGraph<>(false), null, new HashMap<>(), new HashMap<>());
        ArrayList<Entity> actual = new ArrayList<>();
        routeWithMinimumDistanceNull.minimumDistance(actual);
        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void testNull3() {
        routeWithMinimumDistanceNull = new RouteWithMinimumDistance(new MapGraph<>(false), new ArrayList<>(), null, new HashMap<>());
        ArrayList<Entity> actual = new ArrayList<>();
        routeWithMinimumDistanceNull.minimumDistance(actual);
        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void testNull4() {
        routeWithMinimumDistanceNull = new RouteWithMinimumDistance(new MapGraph<>(false), new ArrayList<>(), new HashMap<>(),  null);
        ArrayList<Entity> actual = new ArrayList<>();
        routeWithMinimumDistanceNull.minimumDistance(actual);
        Assert.assertEquals(0, actual.size());
    }
    @Test
    public void testSmall1() {
        List<Expedition> expeditionList = dispatchListWithoutRestrictionSmall.dispatchListWithoutRestriction(2);
        HashMap<Company, List<Expedition>> deliveredInEachHub = new HashMap<>();

        DistributionHubs distributionHubs = new DistributionHubs();
        distributionHubs.defineDistributionHubs(dataIoSmall.getGraph(), 3);
        ClosestDistributionHub closestDistributionHub = new ClosestDistributionHub(dataIoSmall.getGraph());
        routeWithMinimumDistanceSmall = new RouteWithMinimumDistance(dataIoSmall.getGraph(), expeditionList, deliveredInEachHub, closestDistributionHub.closestHub());

        ArrayList<Entity> expected = new ArrayList<>();
        Company entity1 = new Company("CT11","E2", new Coordinates(39.3167,-7.4167));
        Company entity2 = new Company("CT5","E3", new Coordinates(39.823,-7.4931));
        Company entity3 = new Company("CT9","E4", new Coordinates(40.5364,-7.2683));
        Producer entity4 = new Producer("CT6", "P2", new Coordinates(40.2111, -8.4291));
        Producer entity5 = new Producer("CT10", "P3", new Coordinates(39.7444, -8.8072));
        Producer entity6 = new Producer("CT17" ,"P1", new Coordinates(40.6667, -7.9167));
        expected.add(entity1);
        expected.add(entity2);
        expected.add(entity3);
        expected.add(entity4);
        expected.add(entity5);
        expected.add(entity6);

        ArrayList<Entity> actual = new ArrayList<>();
        routeWithMinimumDistanceSmall.minimumDistance(actual);
        System.out.println(routeWithMinimumDistanceSmall.hubMap);
        System.out.println(routeWithMinimumDistanceSmall.producerMap);
        System.out.println(routeWithMinimumDistanceSmall.producerPerHub);
        System.out.println(actual);

        Assert.assertTrue(actual.containsAll(expected));
    }

    @Test
    public void testSmall2() {
        List<Expedition> expeditionList = dispatchListWithoutRestrictionSmall.dispatchListWithoutRestriction(2);
        HashMap<Company, List<Expedition>> deliveredInEachHub = new HashMap<>();

        DistributionHubs distributionHubs = new DistributionHubs();
        distributionHubs.defineDistributionHubs(dataIoSmall.getGraph(), 1);
        ClosestDistributionHub closestDistributionHub = new ClosestDistributionHub(dataIoSmall.getGraph());
        routeWithMinimumDistanceSmall = new RouteWithMinimumDistance(dataIoSmall.getGraph(), expeditionList, deliveredInEachHub, closestDistributionHub.closestHub());

        ArrayList<Entity> expected = new ArrayList<>();
        Company entity2 = new Company("CT5","E3", new Coordinates(39.823,-7.4931));
        Producer entity4 = new Producer("CT6", "P2", new Coordinates(40.2111, -8.4291));
        Producer entity5 = new Producer("CT10", "P3", new Coordinates(39.7444, -8.8072));
        Producer entity6 = new Producer("CT17" ,"P1", new Coordinates(40.6667, -7.9167));
        expected.add(entity2);
        expected.add(entity4);
        expected.add(entity5);
        expected.add(entity6);

        ArrayList<Entity> actual = new ArrayList<>();
        routeWithMinimumDistanceSmall.minimumDistance(actual);
        System.out.println(routeWithMinimumDistanceSmall.hubMap);
        System.out.println(routeWithMinimumDistanceSmall.producerMap);
        System.out.println(routeWithMinimumDistanceSmall.producerPerHub);
        System.out.println(actual);

        Assert.assertTrue(actual.containsAll(expected));
    }

    @Test
    public void testBig1() throws Exception {
        DataIO dataIO = new DataIO();
        dataIO.loadNetworkFile(BIG_CLIENT_PRODUCERS, BIG_DISTANCES);
        dataIO.loadHumpers(BIG_CREEL);
        DistributionHubs distributionHubs = new DistributionHubs();
        distributionHubs.defineDistributionHubs(dataIO.getGraph(), 10);
        ClosestDistributionHub closestDistributionHub = new ClosestDistributionHub(dataIO.getGraph());
        HashMap<Company, List<Expedition>> deliveredInEachHub = new HashMap<>();

        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(dataIO);
        List<Expedition> expeditionList = dispatchListWithoutRestriction.dispatchListWithoutRestriction(1);

        RouteWithMinimumDistance routeWithMinimumDistance = new RouteWithMinimumDistance(dataIO.getGraph(), expeditionList, deliveredInEachHub, closestDistributionHub.closestHub());

        LinkedList<Entity> actual = new LinkedList<>();
        routeWithMinimumDistance.minimumDistance(actual);
        System.out.println(routeWithMinimumDistance.hubMap);
        System.out.println(routeWithMinimumDistance.producerMap);
        System.out.println(routeWithMinimumDistance.producerPerHub);
        System.out.println(actual);

        Assert.assertEquals(actual.get(0), new Company("CT114", "E47", new Coordinates(40.5667,-8.5167)));
        Assert.assertEquals(actual.get(2), new Producer("CT6", "P55", new Coordinates(40.6936,-8.4806)));
        Assert.assertEquals(actual.get(3), new Producer("CT185", "P31", new Coordinates(40.7369, -8.6386)));
        Assert.assertEquals(actual.getLast(), new Company("CT262", "E36", new Coordinates(39.8008,-8.1003)));
    }

    @Test
    public void testBig2() throws Exception {
        DataIO dataIO = new DataIO();
        dataIO.loadNetworkFile(BIG_CLIENT_PRODUCERS, BIG_DISTANCES);
        dataIO.loadHumpers(BIG_CREEL);
        DistributionHubs distributionHubs = new DistributionHubs();
        distributionHubs.defineDistributionHubs(dataIO.getGraph(), 1);
        ClosestDistributionHub closestDistributionHub = new ClosestDistributionHub(dataIO.getGraph());
        HashMap<Company, List<Expedition>> deliveredInEachHub = new HashMap<>();

        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(dataIO);
        List<Expedition> expeditionList = dispatchListWithoutRestriction.dispatchListWithoutRestriction(1);

        RouteWithMinimumDistance routeWithMinimumDistance = new RouteWithMinimumDistance(dataIO.getGraph(), expeditionList, deliveredInEachHub, closestDistributionHub.closestHub());

        LinkedList<Entity> actual = new LinkedList<>();
        routeWithMinimumDistance.minimumDistance(actual);
        System.out.println(routeWithMinimumDistance.hubMap);
        System.out.println(routeWithMinimumDistance.producerMap);
        System.out.println(routeWithMinimumDistance.producerPerHub);
        System.out.println(actual);

        Assert.assertEquals(actual.get(2), new Producer("CT90", "P9", new Coordinates(40.2111,-8.4291)));
        Assert.assertEquals(actual.get(4), new Producer("CT160", "P50", new Coordinates(40.3781,-8.4514)));
        Assert.assertEquals(actual.getLast(), new Company("CT146", "E49", new Coordinates(40.1125,-8.2469)));
    }


}