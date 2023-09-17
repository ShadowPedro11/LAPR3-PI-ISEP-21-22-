package graphs.matrix;

import graphs.Algorithms;
import graphs.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class MatrixAlgorithmsTest {
    final Graph<String,Integer> completeMap = new MatrixGraph<>(false);
    Graph<String,Integer> incompleteMap = new MatrixGraph<>(false);

    public MatrixAlgorithmsTest() {
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

        path = Algorithms.DepthFirstSearch(incompleteMap, "Porto");
        assertEquals(7, path.size(), "Should give seven vertices");

        assertEquals("Porto", path.removeFirst(), "DepthFirst Porto");
        assertTrue(new LinkedList<>(Arrays.asList("Aveiro", "Braga", "Vila Real")).contains(path.removeFirst()), "DepthFirst Porto");

        path = Algorithms.DepthFirstSearch(incompleteMap, "Viseu");
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

    /**
     * Test minimum distance graph using Floyd-Warshall.
     */
    @Test
    public void testMinDistGraph() {

        Graph<Integer, Integer> instance = new MatrixGraph<>(false);

        instance.addVertex(1);
        instance.addVertex(2);
        instance.addVertex(3);
        instance.addVertex(4);
        instance.addVertex(5);
        instance.addVertex(6);

        instance.addEdge(1, 2, 7);
        instance.addEdge(1, 3, 5);
        instance.addEdge(1, 4, 10);
        instance.addEdge(2, 3, 4);
        instance.addEdge(2, 5, 2);
        instance.addEdge(5, 6, 4);


        MatrixGraph<Integer, Integer> result = Algorithms.minDistGraph(instance, Integer::compare, Integer::sum, 0);
        MatrixGraph<Integer, Integer> expected = new MatrixGraph<>(instance);
        expected.addEdge(1, 1, 0);
        expected.addEdge(1, 5, 9);
        expected.addEdge(1, 6, 13);

        expected.addEdge(2, 2, 0);
        expected.addEdge(2, 4, 17);
        expected.addEdge(2, 6, 6);

        expected.addEdge(3, 3, 0);
        expected.addEdge(3, 4, 15);
        expected.addEdge(3, 5, 6);
        expected.addEdge(3, 6, 10);

        expected.addEdge(4, 4, 0);
        expected.addEdge(4, 5, 19);
        expected.addEdge(4, 6, 23);

        expected.addEdge(5, 5, 0);

        expected.addEdge(6, 6, 0);

        assertEquals(expected, result);

    }

    @Test
    public void testAllPaths(){
        System.out.println("Test all paths");
        ArrayList<LinkedList<String>> allPaths =  Algorithms.allPaths(completeMap, "Porto", "Lisboa");
        for(LinkedList<String> path : allPaths) {
            System.out.println();
            for (String s : path)
                System.out.print(s + "->");
        }

    }
}
