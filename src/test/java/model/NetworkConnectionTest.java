package model;

import data.DataIO;
import graphs.map.MapGraph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Alexandre Geração - 1211151
 **/

class NetworkConnectionTest {

    private final DataIO bigFile = new DataIO();
    private final DataIO smallFile = new DataIO();

    private final String bigPathCP = "src/resources/grafos/Big/clientes-produtores_big.csv";
    private final String bigPathD = "src/resources/grafos/Big/distancias_big.csv";
    private final String smallPathCP = "src/resources/grafos/Small/clientes-produtores_small.csv";
    private final String smallPathD = "src/resources/grafos/Small/distancias_small.csv";
    private NetworkConnection nc = new NetworkConnection();

    @Test
    public void testNull() {
        assertEquals(-1,nc.calcSum(null));
    }

    @Test
    public void assertDirectGraphDoesntWork() {
        MapGraph<Entity,Double> mapGraph = new MapGraph<>(true);
        Client c1 = new Client("teste","teste",null);
        Client c2 = new Client("teste2","teste2",null);
        mapGraph.addVertex(c1);
        mapGraph.addVertex(c2);
        mapGraph.addEdge(c1,c2,20.0);

        assertNull(nc.arrangeNetwork(mapGraph));
    }

    @Test
    public void assertNullGraphDoesntWork(){
        assertNull(nc.arrangeNetwork(null));
    }

    @Test
    public void calcSumSmallFile() {
        try {
            smallFile.loadNetworkFile(smallPathCP,smallPathD);
        } catch (Exception e){

        }

        int result = nc.calcSum((MapGraph<Entity, Double>) smallFile.getGraph());
        int expected = 3225580;
        assertEquals(expected,result);
    }

    @Test
    public void calcSumBigFile() {
        try {
            bigFile.loadNetworkFile(bigPathCP,bigPathD);
        } catch (Exception e){

        }

        int result = nc.calcSum((MapGraph<Entity, Double>) bigFile.getGraph());
        int expected = 14908212;
        assertEquals(expected,result);
    }

    @Test
    public void calcSumCustomFile() {
        MapGraph<Entity, Double> g = new MapGraph<>(false);

        Client c1 = new Client("teste","teste",null);
        Client c2 = new Client("teste2","teste2",null);
        Client c3 = new Client("teste3","teste3",null);
        Client c4 = new Client("teste4","teste4",null);
        Client c5 = new Client("teste5","teste5",null);
        Client c6 = new Client("teste6","teste6",null);
        Client c7 = new Client("teste7","teste7",null);
        g.addVertex(c1);
        g.addVertex(c2);
        g.addVertex(c3);
        g.addVertex(c4);
        g.addVertex(c5);
        g.addVertex(c6);
        g.addVertex(c7);

        g.addEdge(c1, c2, 2.0);
        g.addEdge(c1, c3, 4.0);
        g.addEdge(c1, c4, 1.0);

        g.addEdge(c2, c4, 3.0);
        g.addEdge(c2, c5, 10.0);

        g.addEdge(c3, c4, 2.0);
        g.addEdge(c3, c6, 5.0);

        g.addEdge(c4, c5, 7.0);
        g.addEdge(c4, c6, 8.0);
        g.addEdge(c4, c7, 4.0);

        g.addEdge(c5, c7, 6.0);

        g.addEdge(c6, c7, 1.0);


        int result = nc.calcSum(g);
        int expected = 53;
        assertEquals(expected,result);
    }

    @Test
    public void assertNetworkCustomFile(){
        MapGraph<Entity, Double> g = new MapGraph<>(false);
        MapGraph<Entity, Double> expected = new MapGraph<>(false);

        Client c1 = new Client("teste","teste",null);
        Client c2 = new Client("teste2","teste2",null);
        Client c3 = new Client("teste3","teste3",null);
        Client c4 = new Client("teste4","teste4",null);
        Client c5 = new Client("teste5","teste5",null);
        Client c6 = new Client("teste6","teste6",null);
        Client c7 = new Client("teste7","teste7",null);
        g.addVertex(c1);
        g.addVertex(c2);
        g.addVertex(c3);
        g.addVertex(c4);
        g.addVertex(c5);
        g.addVertex(c6);
        g.addVertex(c7);

        g.addEdge(c1, c2, 2.0);
        g.addEdge(c1, c3, 4.0);
        g.addEdge(c1, c4, 1.0);

        g.addEdge(c2, c4, 3.0);
        g.addEdge(c2, c5, 10.0);

        g.addEdge(c3, c4, 2.0);
        g.addEdge(c3, c6, 5.0);

        g.addEdge(c4, c5, 7.0);
        g.addEdge(c4, c6, 8.0);
        g.addEdge(c4, c7, 4.0);

        g.addEdge(c5, c7, 6.0);

        g.addEdge(c6, c7, 1.0);

        expected.addVertex(c1);
        expected.addVertex(c2);
        expected.addVertex(c3);
        expected.addVertex(c4);
        expected.addVertex(c5);
        expected.addVertex(c6);
        expected.addVertex(c7);

        expected.addEdge(c1, c2, 2.0);
        expected.addEdge(c1, c4, 1.0);

        expected.addEdge(c3, c4, 2.0);

        expected.addEdge(c4, c7, 4.0);

        expected.addEdge(c5, c7, 6.0);

        expected.addEdge(c6, c7, 1.0);

        MapGraph<Entity,Double> result = nc.arrangeNetwork(g);

        assertEquals(expected,result);
    }

}