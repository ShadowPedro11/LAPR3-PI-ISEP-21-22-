package model;

import data.DataIO;
import graphs.map.MapGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;


public class ClosestDistributionHubTest {

    private DataIO dataIO1;
    private ClosestDistributionHub closestDistributionHub1;
    private ClosestDistributionHub closestDistributionHub2;
    private DistributionHubs distributionHubsNull;
    private DistributionHubs distributionHubs1;
    private DistributionHubs distributionHubs2;
    private Entity entity1;
    private Company entity2;
    private Entity entity3;
    private Entity entity4;
    private Company entity5;
    private Entity entity6;

    private final String SMALL_CLIENT_PRODUCERS = "src/test/test_files/clientes_produtores_empresas_small.csv";
    private final String SMALL_DISTANCES = "src/test/test_files/distancias_small.csv";
    private final String BIG_CLIENT_PRODUCERS = "src/resources/grafos/Big/clientes-produtores_big.csv";
    private final String BIG_DISTANCES = "src/resources/grafos/Big/distancias_big.csv";

    @Before
    public void setUp() throws FileNotFoundException {
        dataIO1 = new DataIO();
        dataIO1.loadNetworkFile(SMALL_CLIENT_PRODUCERS, SMALL_DISTANCES);
        closestDistributionHub1 = new ClosestDistributionHub(new MapGraph<>(false));
        closestDistributionHub2 = new ClosestDistributionHub(dataIO1.getGraph());

        distributionHubsNull = new DistributionHubs();
        distributionHubs1 = new DistributionHubs();
        distributionHubs2 = new DistributionHubs();

        entity1 = new Client("CT1", "C1", new Coordinates(40.6389, -8.6553));
        entity2 = new Company("CT5", "E3", new Coordinates(39.823, -7.4931));
        entity3 = new Producer("CT6", "P2", new Coordinates(40.2111, -8.4291));
        entity4 = new Producer("CT10", "P3", new Coordinates(39.7444, -8.8072));
        entity5 = new Company("CT11", "E2", new Coordinates(39.3167, -7.4167));
        entity6 = new Producer("CT17" ,"P1", new Coordinates(40.6667, -7.9167));
    }

    @Test
    public void testNull() {
        Assert.assertTrue(closestDistributionHub1.closestHub().isEmpty());
    }
    @Test
    public void testNoData() {
        HashMap<Entity, Company> expected = new HashMap<>();
        HashMap<Entity, Company> actual = closestDistributionHub1.closestHub();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testSmallOneHub() {
        distributionHubs1.defineDistributionHubs(dataIO1.getGraph(), 1);

        HashMap<Entity, Company> expected = new HashMap<>();
        expected.put(entity4, entity2);
        expected.put(entity6, entity2);
        expected.put(entity3, entity2);
        expected.put(entity5, entity2);
        expected.put(entity2, entity2);
        expected.put(entity1, entity2);
        HashMap<Entity, Company> actual = closestDistributionHub2.closestHub();

        ArrayList<Entity> listExpectedKeys = new ArrayList<>(expected.keySet());
        listExpectedKeys.sort(Comparator.comparing(Entity::getLocalID));
        ArrayList<Entity> listActualKeys = new ArrayList<>(actual.keySet());
        listActualKeys.sort(Comparator.comparing(Entity::getLocalID));

        ArrayList<Entity> listExpectedValues = new ArrayList<>(expected.values());
        listExpectedValues.sort(Comparator.comparing(Entity::getLocalID));
        ArrayList<Entity> listActualValues = new ArrayList<>(actual.values());
        listActualValues.sort(Comparator.comparing(Entity::getLocalID));

        Assert.assertEquals(listExpectedKeys.toString(), listActualKeys.toString());
        Assert.assertEquals(listExpectedValues.toString(), listActualValues.toString());
    }

    @Test
    public void testSmallTwoHubs() {
        distributionHubs2.defineDistributionHubs(dataIO1.getGraph(), 2);

        HashMap<Entity, Company> expected = new HashMap<>();
        expected.put(entity1, entity2);
        expected.put(entity2, entity2);
        expected.put(entity3, entity2);
        expected.put(entity4, entity2);
        expected.put(entity5, entity5);
        expected.put(entity6, entity2);
        HashMap<Entity, Company> actual = closestDistributionHub2.closestHub();

        ArrayList<Entity> listExpectedKeys = new ArrayList<>(expected.keySet());
        listExpectedKeys.sort(Comparator.comparing(Entity::getLocalID));
        ArrayList<Entity> listActualKeys = new ArrayList<>(actual.keySet());
        listActualKeys.sort(Comparator.comparing(Entity::getLocalID));

        ArrayList<Entity> listExpectedValues = new ArrayList<>(expected.values());
        listExpectedValues.sort(Comparator.comparing(Entity::getLocalID));
        ArrayList<Entity> listActualValues = new ArrayList<>(actual.values());
        listActualValues.sort(Comparator.comparing(Entity::getLocalID));

        Assert.assertEquals(listExpectedKeys.toString(), listActualKeys.toString());
        Assert.assertEquals(listExpectedValues.toString(), listActualValues.toString());
    }
}