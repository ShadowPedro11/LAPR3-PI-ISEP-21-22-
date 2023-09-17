package model;
import data.DataIO;
import graphs.Graph;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
 * @author : Pedro Pereira - 1211131
 **/

class MinimumConnectionsNumberTest {
    private final DataIO bigFile = new DataIO();
    private final DataIO smallFile = new DataIO();
    private final DataIO testFile = new DataIO();
    private final DataIO testFile2 = new DataIO();
    private final String bigPathCP = "src/resources/grafos/Big/clientes-produtores_big.csv";
    private final String bigPathD = "src/resources/grafos/Big/distancias_big.csv";
    private final String smallPathCP = "src/resources/grafos/Small/clientes-produtores_small.csv";
    private final String smallPathD = "src/resources/grafos/Small/distancias_small.csv";
    private final String testPathCP = "src/resources/grafos/UnconnectedGraphTest/clientes-produtores_small.csv";
    private final String testPathD = "src/resources/grafos/UnconnectedGraphTest/distancias_small.csv";
    private final String testPath2CP = "src/resources/grafos/ConnectedGraphTest/clientes-produtores_small.csv";
    private final String testPath2D = "src/resources/grafos/ConnectedGraphTest/distancias_small.csv";

    @BeforeEach
    void setUp() throws FileNotFoundException {
        smallFile.loadNetworkFile(smallPathCP,smallPathD);
        bigFile.loadNetworkFile(bigPathCP,bigPathD);
        testFile.loadNetworkFile(testPathCP,testPathD);
        testFile2.loadNetworkFile(testPath2CP,testPath2D);
    }

    @Test
    public void checkConnectionSmallGraph() {
        MinimumConnectionsNumber small = new MinimumConnectionsNumber(smallFile.getGraph());
        boolean actual = small.isConnected();
        Assert.assertTrue(actual);
    }

    @Test
    public void checkConnectionBigGraph() {
        MinimumConnectionsNumber big = new MinimumConnectionsNumber(bigFile.getGraph());
        boolean actual = big.isConnected();
        Assert.assertTrue(actual);
    }

    @Test
    public void checkConnectionUnconnectedGraph() {
        MinimumConnectionsNumber test = new MinimumConnectionsNumber(testFile.getGraph());
        boolean actual = test.isConnected();
        Assert.assertFalse(actual);
    }
    @Test
    public void checkConnectionNullGraph() {
        Graph<Entity,Double> nullGraph = null;
        MinimumConnectionsNumber test = new MinimumConnectionsNumber(nullGraph);
        boolean actual = test.isConnected();
        Assert.assertFalse(actual);
    }

    @Test
    public void checkConnectionTestGraph() {
        MinimumConnectionsNumber big = new MinimumConnectionsNumber(testFile2.getGraph());
        boolean actual = big.isConnected();
        Assert.assertTrue(actual);
    }

    @Test
    public void checkMinimumConnectionNumberSmallGraph() {
        MinimumConnectionsNumber small = new MinimumConnectionsNumber(smallFile.getGraph());
        MinimumConnectionData actual = small.minimumConnectionNumber();
        //C3-->C5-->E4-->E3-->E2-->C2-->C8
        Client C3 = new Client("CT15","C3",new Coordinates(41.7,-8.8333));
        Client C5 = new Client("CT210","C5",new Coordinates(41.2,-8.2833));
        Company E4 = new Company("CT8","E4",new Coordinates(37.0161,-7.935));
        Company E3 = new Company("CT12","E3",new Coordinates(41.1495,-8.6108));
        Company E2 = new Company("CT1","E2",new Coordinates(40.6389,-8.6553));
        Client C2 = new Client("CT10","C2",new Coordinates(39.7444,-8.8072));
        Client C8 = new Client("CT13","C8",new Coordinates(39.2369,-8.685));
        LinkedList<Entity> pathExpected = new LinkedList<>();
        pathExpected.add(C3);
        pathExpected.add(C5);
        pathExpected.add(E4);
        pathExpected.add(E3);
        pathExpected.add(E2);
        pathExpected.add(C2);
        pathExpected.add(C8);
        Double diameterExpected = 6.0;
        Integer pathConnectionsExpected = 6;


        Assert.assertEquals(actual.getPathConnections(),pathConnectionsExpected);
        Assert.assertEquals(actual.getPath().toString(),pathExpected.toString());
        Assert.assertEquals(actual.getDiameter(),diameterExpected);

    }

    @Test
    public void checkMinimumConnectionNumberBigGraph() {
        MinimumConnectionsNumber big = new MinimumConnectionsNumber(bigFile.getGraph());
        MinimumConnectionData actual = big.minimumConnectionNumber();
        //P6-->P43-->C19-->C115-->C128-->E20-->E58-->C171-->C151-->C118-->P3-->C124-->C172-->C106-->C92-->E2-->C113-->E35-->P11-->C121-->C108-->E1-->C120-->C66-->C123-->C43-->E31-->C15-->C26
        Producer P6 = new Producer("CT228","P6",new Coordinates(41.5667,-8.2667));
        Producer P43= new Producer("CT228","P43",new Coordinates(41.5667,-8.2667));
        Client C19 = new Client("CT78","C19",new Coordinates(41.063,-8.2647));
        Client C115 = new Client("CT78","C115",new Coordinates(41.063,-8.2647));
        Client C128 = new Client("CT78","C128",new Coordinates(41.063,-8.2647));
        Company E20 = new Company("CT34","E20",new Coordinates(41.85,-8.4167));
        Company E58 = new Company("CT34","E58",new Coordinates(41.85,-8.4167));
        Client C171 = new Client("CT34","C171",new Coordinates(41.85,-8.4167));
        Client C151 = new Client("CT180","C151",new Coordinates(38.9333,-8.15));
        Client C118 = new Client("CT222","C118",new Coordinates(39.25,-8.0167));
        Producer P3 = new Producer("CT126","P3",new Coordinates(39.465,-7.9369));
        Client C124 = new Client("CT34","C124",new Coordinates(41.85,-8.4167));
        Client C172 = new Client("CT34","C172",new Coordinates(41.85,-8.4167));
        Client C106 = new Client("CT34","C106",new Coordinates(41.85,-8.4167));
        Client C92 = new Client("CT34","C92",new Coordinates(41.85,-8.4167));
        Company E2 = new Company("CT34","E2",new Coordinates(41.85,-8.4167));
        Client C113 = new Client("CT34","C113",new Coordinates(41.85,-8.4167));
        Company E35 = new Company("CT34","E35",new Coordinates(41.85,-8.4167));
        Producer P11 = new Producer("CT34","P11",new Coordinates(41.85,-8.4167));
        Client C121 = new Client("CT34","C121",new Coordinates(41.85,-8.4167));
        Client C108 = new Client("CT34","C108",new Coordinates(41.85,-8.4167));
        Company E1 = new Company("CT34","E1",new Coordinates(41.85,-8.4167));
        Client C120 = new Client("CT34","C120",new Coordinates(41.85,-8.4167));
        Client C66 = new Client("CT34","C66",new Coordinates(41.85,-8.4167));
        Client C123 = new Client("CT34","C123",new Coordinates(41.85,-8.4167));
        Client C43 = new Client("CT276","C43",new Coordinates(41.7167,-8.3));
        Company E31 = new Company("CT34","E31",new Coordinates(41.85,-8.4167));
        Client C15 = new Client("CT34","C15",new Coordinates(41.85,-8.4167));
        Client C26 = new Client("CT34","C26",new Coordinates(41.85,-8.4167));
        LinkedList<Entity> pathExpected = new LinkedList<>();
        pathExpected.add(P6);
        pathExpected.add(P43);
        pathExpected.add(C19);
        pathExpected.add(C115);
        pathExpected.add(C128);
        pathExpected.add(E20);
        pathExpected.add(E58);
        pathExpected.add(C171);
        pathExpected.add(C151);
        pathExpected.add(C118);
        pathExpected.add(P3);
        pathExpected.add(C124);
        pathExpected.add(C172);
        pathExpected.add(C106);
        pathExpected.add(C92);
        pathExpected.add(E2);
        pathExpected.add(C113);
        pathExpected.add(E35);
        pathExpected.add(P11);
        pathExpected.add(C121);
        pathExpected.add(C108);
        pathExpected.add(E1);
        pathExpected.add(C120);
        pathExpected.add(C66);
        pathExpected.add(C123);
        pathExpected.add(C43);
        pathExpected.add(E31);
        pathExpected.add(C15);
        pathExpected.add(C26);
        Double diameterExpected = 28.0;
        Integer pathConnectionsExpected = 28;


        Assert.assertEquals(actual.getPathConnections(),pathConnectionsExpected);
        Assert.assertEquals(actual.getPath().toString(),pathExpected.toString());
        Assert.assertEquals(actual.getDiameter(),diameterExpected);

    }

    @Test
    public void checkMinimumConnectionNumberTestGraph() {
        MinimumConnectionsNumber test = new MinimumConnectionsNumber(testFile2.getGraph());
        MinimumConnectionData actual = test.minimumConnectionNumber();
        Client C1 = new Client("CT1","C1",new Coordinates(40.6389,-8.6553));
        Client C2 = new Client("CT2","C2",new Coordinates(38.0333,-7.8833));
        Client C4 = new Client("CT15","C4",new Coordinates(41.7,-8.8333));
        Client C6 = new Client("CT12","C6",new Coordinates(41.1495,-8.6108));
        Client C7 = new Client("CT7","C7",new Coordinates(37.0161,-7.935));
        Client C9 = new Client("CT13","C9",new Coordinates(37.0161,-7.935));

        LinkedList<Entity> pathExpected = new LinkedList<>();
        //C1-->C6-->C4-->C2-->C7-->C9
        pathExpected.add(C1);
        pathExpected.add(C6);
        pathExpected.add(C4);
        pathExpected.add(C2);
        pathExpected.add(C7);
        pathExpected.add(C9);

        Double diameterExpected = 5.0;
        Integer pathConnectionsExpected = 5;


        Assert.assertEquals(actual.getPathConnections(),pathConnectionsExpected);
        Assert.assertEquals(actual.getPath().toString(),pathExpected.toString());
        Assert.assertEquals(actual.getDiameter(),diameterExpected);

    }


}