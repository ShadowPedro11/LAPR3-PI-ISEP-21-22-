package model;

import data.DataIO;
import graphs.map.MapGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DistributionHubsTest {
    private DataIO dataIOsmall;
    private DataIO dataIObig;
    private final String SMALL_CLIENT_PRODUCERS = "src/resources/grafos/Small/clientes-produtores_small.csv";
    private final String SMALL_DISTANCES = "src/resources/grafos/Small/distancias_small.csv";
    private final String BIG_CLIENT_PRODUCERS = "src/resources/grafos/Big/clientes-produtores_big.csv";
    private final String BIG_DISTANCES = "src/resources/grafos/Big/distancias_big.csv";
    Company cmp1 = new Company("CT14","E1",new Coordinates(38.5243,-8.8926));
    Company cmp2 = new Company("CT11","E2", new Coordinates(39.3167,-7.4167));
    Company cmp3 = new Company("CT5","E3", new Coordinates(39.823,-7.4931));
    Company cmp4 = new Company("CT9","E4", new Coordinates(40.5364,-7.2683));
    Company cmp5 = new Company("CT4","E5",new Coordinates(41.8,-6.75));
    @BeforeEach
    public void setUp() throws FileNotFoundException {
        dataIOsmall = new DataIO();
        dataIOsmall.loadNetworkFile(SMALL_CLIENT_PRODUCERS, SMALL_DISTANCES);
        dataIObig = new DataIO();
        dataIObig.loadNetworkFile(BIG_CLIENT_PRODUCERS, BIG_DISTANCES);
    }
    @Test
    void testDistributionHubs(){
        DistributionHubs dh = new DistributionHubs();
        int n = 3;
        ArrayList<Company> expected = new ArrayList<>();
        expected.add(cmp3);
        expected.add(cmp4);
        expected.add(cmp2);
        dh.defineDistributionHubs(dataIOsmall.getGraph(), n);
        assertEquals(expected.toString(), DistributionHubs.getHubsList().toString());
    }
    @Test
    void testDistributionHubsNullGraph(){
        DistributionHubs dh = new DistributionHubs();
        int n = 3;
        ArrayList<Company> expected = null;

        dh.defineDistributionHubs(new MapGraph<>(true), n);
        assertEquals(expected, DistributionHubs.getHubsList());
    }
    @Test
    void testDistributionHubsGraphNotConnected(){
        DistributionHubs dh = new DistributionHubs();
        int n = 3;
        ArrayList<Company> expected = null;
        MapGraph<Entity, Double> graph = new MapGraph<>(true);
        Client c1 = new Client("client1", "client1", null);
        Client c2 = new Client("client2", "client2", null);
        Producer p1 = new Producer("producer1", "producer1", null);
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addVertex(p1);
        graph.addVertex(cmp1);
        graph.addVertex(cmp2);
        graph.addEdge(c1, c2, 23.2);
        graph.addEdge(c1, p1, 15.0);
        graph.addEdge(cmp1, c2, 28.1);
        graph.addEdge(cmp2, p1, 33.4);
        dh.defineDistributionHubs(graph, n);
        assertEquals(expected, DistributionHubs.getHubsList());
    }

    @Test
    void testCalcAvgDistance() {
        DistributionHubs dh = new DistributionHubs();
        ArrayList<Double> distances = new ArrayList<>();
        distances.add(4.0);
        distances.add(73.0);
        distances.add(22.6);
        distances.add( 52.0);
        distances.add( 81.0);
        distances.add(26.0);
        Double expected = 43.1;
        assertEquals(expected, dh.calcAvgDistance(distances));
    }

    @Test
    void testCalcAvgDistanceEmptyArray() {
        DistributionHubs dh = new DistributionHubs();
        ArrayList<Double> distances = new ArrayList<>();
        Double expected = null;
        assertEquals(expected, dh.calcAvgDistance(distances));
    }

    @Test
    void testDistributionHubsBigFile(){
        DistributionHubs dh = new DistributionHubs();
        int n = 3;
        ArrayList<Company> expected = new ArrayList<>();
        Company cmp49 = new Company("CT146","E49",new Coordinates(40.1125,-8.2469));
        Company cmp71= new Company("CT142","E71",new Coordinates(40.2594,-8.3168));
        Company cmp40 = new Company("CT209","E40",new Coordinates(40.2667,-8.2667));
        expected.add(cmp49);
        expected.add(cmp71);

        expected.add(cmp40);
        dh.defineDistributionHubs(dataIObig.getGraph(), n);
        assertEquals(expected.toString(), DistributionHubs.getHubsList().toString());
    }

    @Test
    void testDistributionHubsNBiggerThanNumberOfCompanies(){
        DistributionHubs dh = new DistributionHubs();
        int n = 10;
        ArrayList<Company> expected = new ArrayList<>();
        expected.add(cmp3);
        expected.add(cmp4);
        expected.add(cmp2);
        expected.add(cmp1);
        expected.add(cmp5);
        dh.defineDistributionHubs(dataIOsmall.getGraph(), n);
        assertEquals(expected.toString(), DistributionHubs.getHubsList().toString());
    }
}