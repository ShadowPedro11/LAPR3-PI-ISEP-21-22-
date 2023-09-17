package graphs.map;

import graphs.Algorithms;
import graphs.Graph;
import graphs.matrix.MatrixGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class MapAlgorithmsTest {
    final Graph<String, Integer> completeMap = new MapGraph<>(false);
    Graph<String, Integer> incompleteMap = new MapGraph<>(false);

    public MapAlgorithmsTest() {
    }

    @BeforeEach
    public void setUp() {

        completeMap.addVertex("Porto");
        completeMap.addVertex("Braga");
        completeMap.addVertex("Vila Real");
        completeMap.addVertex("Aveiro");
        completeMap.addVertex("Coimbra");
        completeMap.addVertex("Leiria");

        completeMap.addVertex("Viseu");
        completeMap.addVertex("Guarda");
        completeMap.addVertex("Castelo Branco");
        completeMap.addVertex("Lisboa");
        completeMap.addVertex("Faro");

        completeMap.addEdge("Porto", "Aveiro", 75);
        completeMap.addEdge("Porto", "Braga", 60);
        completeMap.addEdge("Porto", "Vila Real", 100);
        completeMap.addEdge("Viseu", "Guarda", 75);
        completeMap.addEdge("Guarda", "Castelo Branco", 100);
        completeMap.addEdge("Aveiro", "Coimbra", 60);
        completeMap.addEdge("Coimbra", "Lisboa", 200);
        completeMap.addEdge("Coimbra", "Leiria", 80);
        completeMap.addEdge("Aveiro", "Leiria", 120);
        completeMap.addEdge("Leiria", "Lisboa", 150);

        incompleteMap = completeMap.clone();

        completeMap.addEdge("Aveiro", "Viseu", 85);
        completeMap.addEdge("Leiria", "Castelo Branco", 170);
        completeMap.addEdge("Lisboa", "Faro", 280);
    }

    private void checkContentEquals(List<String> l1, List<String> l2, String msg) {
        Collections.sort(l1);
        Collections.sort(l2);
        assertEquals(l1, l2, msg);
    }

    /**
     * Test of BreadthFirstSearch method, of class Algorithms.
     */
    @Test
    public void testBreadthFirstSearch() {
        System.out.println("Test BreadthFirstSearch");

        Assertions.assertNull(Algorithms.BreadthFirstSearch(completeMap, "LX"), "Should be null if vertex does not exist");

        LinkedList<String> path = Algorithms.BreadthFirstSearch(incompleteMap, "Faro");

        assertEquals(1, path.size(), "Should be just one");

        assertEquals("Faro", path.peekFirst());

        path = Algorithms.BreadthFirstSearch(incompleteMap, "Porto");
        assertEquals(7, path.size(), "Should give seven vertices");

        assertEquals("Porto", path.removeFirst(), "BreathFirst Porto");

        LinkedList<String> expected = new LinkedList<>(Arrays.asList("Aveiro", "Braga", "Vila Real"));
        checkContentEquals(expected, path.subList(0, 3), "BreathFirst Porto");

        expected = new LinkedList<>(Arrays.asList("Coimbra", "Leiria"));
        checkContentEquals(expected, path.subList(3, 5), "BreathFirst Porto");

        expected = new LinkedList<>(Arrays.asList("Lisboa"));
        checkContentEquals(expected, path.subList(5, 6), "BreathFirst Porto");

        path = Algorithms.BreadthFirstSearch(incompleteMap, "Viseu");
        expected = new LinkedList<>(Arrays.asList("Viseu", "Guarda", "Castelo Branco"));
        assertEquals(expected, path, "BreathFirst Viseu");
    }

    /**
     * Test of DepthFirstSearch method, of class Algorithms.
     */
    @Test
    public void testDepthFirstSearch() {
        System.out.println("Test of DepthFirstSearch");

        assertNull(Algorithms.DepthFirstSearch(completeMap, "LX"), "Should be null if vertex does not exist");

        LinkedList<String> path = Algorithms.DepthFirstSearch(incompleteMap, "Faro");
        assertEquals(1, path.size(), "Should be just one");

        assertEquals("Faro", path.peekFirst());

        path = Algorithms.BreadthFirstSearch(incompleteMap, "Porto");
        assertEquals(7, path.size(), "Should give seven vertices");

        assertEquals("Porto", path.removeFirst(), "DepthFirst Porto");
        assertTrue(new LinkedList<>(Arrays.asList("Aveiro", "Braga", "Vila Real")).contains(path.removeFirst()), "DepthFirst Porto");

        path = Algorithms.BreadthFirstSearch(incompleteMap, "Viseu");
        List<String> expected = new LinkedList<>(Arrays.asList("Viseu", "Guarda", "Castelo Branco"));
        assertEquals(expected, path, "DepthFirst Viseu");
    }

    /**
     * Test of shortestPath method, of class Algorithms.
     */
    @Test
    public void testShortestPath() {
        System.out.println("Test of shortest path");

        LinkedList<String> shortPath = new LinkedList<>();

        Integer lenPath = Algorithms.shortestPath(completeMap, "Porto", "LX", Integer::compare, Integer::sum, 0, shortPath);
        assertNull(lenPath, "Length path should be null if vertex does not exist");
        assertEquals(0, shortPath.size(), "Shortest Path does not exist");

        shortPath.clear();
        lenPath = Algorithms.shortestPath(incompleteMap, "Porto", "Faro", Integer::compare, Integer::sum, 0, shortPath);
        assertNull(lenPath, "Length path should be null if vertex does not exist");
        assertEquals(0, shortPath.size(), "Shortest Path does not exist");

        shortPath.clear();
        lenPath = Algorithms.shortestPath(completeMap, "Porto", "Porto", Integer::compare, Integer::sum, 0, shortPath);
        assertEquals(0, lenPath, "Length path should be 0 if vertices are the same");
        assertEquals(Arrays.asList("Porto"), shortPath, "Shortest Path only contains Porto");

        shortPath.clear();
        lenPath = Algorithms.shortestPath(incompleteMap, "Porto", "Lisboa", Integer::compare, Integer::sum, 0, shortPath);
        assertEquals(335, lenPath, "Length path should be 0 if vertices are the same");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), shortPath, "Shortest Path Porto - Lisboa");

        shortPath.clear();
        lenPath = Algorithms.shortestPath(incompleteMap, "Braga", "Leiria", Integer::compare, Integer::sum, 0, shortPath);
        assertEquals(255, lenPath, "Length path should be 0 if vertices are the same");
        assertEquals(Arrays.asList("Braga", "Porto", "Aveiro", "Leiria"), shortPath, "Shortest Path Braga - Leiria");

        shortPath.clear();
        lenPath = Algorithms.shortestPath(completeMap, "Porto", "Castelo Branco", Integer::compare, Integer::sum, 0, shortPath);
        assertEquals(335, lenPath, "Length path should be 0 if vertices are the same");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Viseu", "Guarda", "Castelo Branco"), shortPath, "Shortest Path Porto - Castelo Branco");

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco
        //should change shortest path between Porto and Castelo Branco

        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.addEdge("Leiria", "Castelo Branco", 170);

        shortPath.clear();
        lenPath = Algorithms.shortestPath(completeMap, "Porto", "Castelo Branco", Integer::compare, Integer::sum, 0, shortPath);
        assertEquals(365, lenPath, "Length path should be 0 if vertices are the same");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Leiria", "Castelo Branco"), shortPath, "Shortest Path Porto - Castelo Branco");
    }

    /**
     * Test of shortestPaths method, of class Algorithms.
     */
    @Test
    public void testShortestPaths() {
        System.out.println("Test of shortest path");

        ArrayList<LinkedList<String>> paths = new ArrayList<>();
        ArrayList<Integer> dists = new ArrayList<>();

        Algorithms.shortestPaths(completeMap, "Porto", Integer::compare, Integer::sum, 0, paths, dists);

        assertEquals(paths.size(), dists.size(), "There should be as many paths as sizes");
        assertEquals(completeMap.numVertices(), paths.size(), "There should be a path to every vertex");
        assertEquals(Arrays.asList("Porto"), paths.get(completeMap.key("Porto")), "Number of nodes should be 1 if source and vertex are the same");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.key("Lisboa")), "Path to Lisbon");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Viseu", "Guarda", "Castelo Branco"), paths.get(completeMap.key("Castelo Branco")), "Path to Castelo Branco");
        assertEquals(335, dists.get(completeMap.key("Castelo Branco")), "Path between Porto and Castelo Branco should be 335 Km");

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco
        //should change shortest path between Porto and Castelo Branco
        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.addEdge("Leiria", "Castelo Branco", 170);
        paths.clear();
        dists.clear();
        Algorithms.shortestPaths(completeMap, "Porto", Integer::compare, Integer::sum, 0, paths, dists);
        assertEquals(365, dists.get(completeMap.key("Castelo Branco")), "Path between Porto and Castelo Branco should now be 365 Km");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Leiria", "Castelo Branco"), paths.get(completeMap.key("Castelo Branco")), "Path to Castelo Branco");

        paths.clear();
        dists.clear();
        Algorithms.shortestPaths(incompleteMap, "Porto", Integer::compare, Integer::sum, 0, paths, dists);
        assertNull(dists.get(completeMap.key("Faro")), "Length path should be null if there is no path");
        assertEquals(335, dists.get(completeMap.key("Lisboa")), "Path between Porto and Lisboa should be 335 Km");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.key("Lisboa")), "Path to Lisboa");

        paths.clear();
        dists.clear();
        Algorithms.shortestPaths(incompleteMap, "Braga", Integer::compare, Integer::sum, 0, paths, dists);
        assertEquals(255, dists.get(completeMap.key("Leiria")), "Path between Braga and Leiria should be 255 Km");
        assertEquals(Arrays.asList("Braga", "Porto", "Aveiro", "Leiria"), paths.get(completeMap.key("Leiria")), "Path to Leiria");
    }

    @Test
    public void testDijskstra(){
        Graph<Integer, Integer> g = new MapGraph<>(true);
        for(int i=1;i<=5;i++)
            g.addVertex(i);
        g.addEdge(1, 2, 4);
        g.addEdge(1, 5, 1);
        g.addEdge(2, 3, 1);
        g.addEdge(5, 2, 1);
        g.addEdge(5, 4, 3);
        g.addEdge(3, 5, 5);
        g.addEdge(3, 4, 2);

        ArrayList<LinkedList<Integer>> paths = new ArrayList<>();
        ArrayList<Integer> dists = new ArrayList<>();

        Algorithms.shortestPaths(g, 1, Integer::compareTo, Integer::sum, 0, paths, dists);
    }

    /**
     * Test minimum distance graph using Floyd-Warshall.
     */
    @Test
    public void testminDistGraph() {
        Graph<Integer, Integer> res = new MapGraph<>(false);

        res.addVertex(1);
        res.addVertex(2);
        res.addVertex(3);
        res.addVertex(4);
        res.addVertex(5);
        res.addVertex(6);

        res.addEdge(1, 4, 10);
        res.addEdge(1, 3, 5);
        res.addEdge(1, 2, 7);

        res.addEdge(3, 2, 4);
        res.addEdge(2, 5, 2);
        res.addEdge(5, 6, 4);

        final Graph<Integer, Integer> exp = new MatrixGraph<>(false);

        exp.addVertex(1);
        exp.addVertex(2);
        exp.addVertex(3);
        exp.addVertex(4);
        exp.addVertex(5);
        exp.addVertex(6);

        exp.addEdge(1, 4, 10);
        exp.addEdge(1, 3, 5);
        exp.addEdge(1, 2, 7);

        exp.addEdge(3, 2, 4);
        exp.addEdge(2, 5, 2);
        exp.addEdge(5, 6, 4);

        exp.addEdge(1, 5, 9);
        exp.addEdge(1, 6, 13);

        exp.addEdge(2, 4, 17);
        exp.addEdge(2, 6, 6);

        exp.addEdge(3, 4, 15);
        exp.addEdge(3, 5, 6);
        exp.addEdge(3, 6, 10);

        exp.addEdge(4, 5, 19);
        exp.addEdge(4, 6, 23);

        exp.addEdge(1, 1, 0);
        exp.addEdge(2, 2, 0);
        exp.addEdge(3, 3, 0);
        exp.addEdge(4, 4, 0);
        exp.addEdge(5, 5, 0);
        exp.addEdge(6, 6, 0);

        res = Algorithms.minDistGraph(res, Integer::compare, Integer::sum, 0);

        assertEquals(exp, res);

    }

    @Test
    public void testMST(){
        final Graph<String, Integer> instance = new MapGraph<>(false);
        instance.addVertex("A");
        instance.addVertex("B");
        instance.addVertex("C");
        instance.addVertex("D");
        instance.addVertex("E");
        instance.addVertex("F");
        instance.addVertex("G");

        Graph<String, Integer> expected = instance.clone();

        instance.addEdge("A", "B", 2);
        instance.addEdge("A", "C", 4);
        instance.addEdge("A", "D", 1);

        instance.addEdge("B", "D", 3);
        instance.addEdge("B", "E", 10);

        instance.addEdge("C", "D", 2);
        instance.addEdge("C", "F", 5);

        instance.addEdge("D", "E", 7);
        instance.addEdge("D", "F", 8);
        instance.addEdge("D", "G", 4);

        instance.addEdge("E", "G", 6);

        instance.addEdge("F", "G", 1);

        MapGraph<String, Integer> result = Algorithms.minimumSpanningTree(instance, 0, Integer::compareTo);
        expected.addEdge("A", "B", 2);
        expected.addEdge("A", "D", 1);
        expected.addEdge("C", "D", 2);
        expected.addEdge("D", "G", 4);
        expected.addEdge("F", "G", 1);
        expected.addEdge("G", "E", 6);

        assertEquals(expected, result);
    }

    @Test
    public void testGraphDiameter(){

        LinkedList<String> resPath = new LinkedList<>();

        int resDist = Algorithms.graphDiameter(completeMap, Integer::compareTo, Integer::sum, 0, resPath);

        LinkedList<String> expPath = new LinkedList<>();

        expPath.add("Vila Real");
        expPath.add("Porto");
        expPath.add("Aveiro");
        expPath.add("Coimbra");
        expPath.add("Lisboa");
        expPath.add("Faro");

        int expDist = 715;

        assertEquals(expPath, resPath);
        assertEquals(expDist, resDist);

    }

}
