package graphs;

import graphs.map.MapGraph;
import graphs.matrix.MatrixGraph;

import java.util.*;
import java.util.function.BinaryOperator;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class Algorithms {
    /** Performs breadth-first search of a Graph starting in a vertex
     *
     * @param g Graph instance
     * @param vert vertex that will be the source of the search
     * @return a LinkedList with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {

        if(g == null)
            return null;

        int key = g.key(vert);

        if(key < 0)
            return null;

        boolean[] visited = new boolean[g.numVertices()];
        Arrays.fill(visited, false);
        LinkedList<V> qbfs = new LinkedList<>();
        qbfs.add(vert);
        LinkedList<V> qaux = new LinkedList<>();
        qaux.add(vert);

        visited[key] = true;

        while(!qaux.isEmpty()){
            vert = qaux.removeFirst();
            for(V v : g.adjVertices(vert)){
                int k = g.key(v);
                if(!visited[k]){
                    qbfs.add(v);
                    qaux.add(v);
                    visited[k] = true;
                }
            }
        }
        return qbfs;
    }

    /** Performs depth-first search starting in a vertex
     *
     * @param g Graph instance
     * @param vOrig vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs return LinkedList with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {

        int key = g.key(vOrig);
        if(visited[key])
            return;

        qdfs.add(vOrig);
        visited[key] = true;

        Collection<V> vAdj = g.adjVertices(vOrig);

        for(V vert : vAdj)
            DepthFirstSearch(g, vert, visited, qdfs);
    }

    /** Performs depth-first search starting in a vertex
     *
     * @param g Graph instance
     * @param vert vertex of graph g that will be the source of the search

     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {

        if(g == null)
            return null;

        if(g.key(vert) < 0)
            return null;

        boolean[] visited = new boolean[g.numVertices()];
        Arrays.fill(visited, false);
        LinkedList<V> vertexList = new LinkedList<>();
        DepthFirstSearch(g, vert, visited, vertexList);
        return vertexList;
    }

    /** Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {

        int key = g.key(vOrig);
        path.add(vOrig);
        visited[key] = true;
        for(V v : g.adjVertices(vOrig)){
            int k = g.key(v);
            if(v == vDest){
                path.add(vDest);
                paths.add(new LinkedList<>(path));
                path.removeLast();
            }else{
                if(!visited[k])
                    allPaths(g, v, vDest, visited, path, paths);
            }
        }
        visited[key] = false;
        path.removeLast();
    }

    /** Returns all paths from vOrig to vDest
     *
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
        if(g == null)
            return null;
        if(g.key(vOrig) == -1 || g.key(vDest) == -1)
            return null;

        LinkedList<V> path = new LinkedList<>();
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        allPaths(g, vOrig, vDest, new boolean[g.numVertices()], path, paths);
        return paths;
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of previously visited vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    private static <V, E> void shortestPathDijkstra(Graph<V, E> g, V vOrig,
                                                    Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                    boolean[] visited, ArrayList<V> pathKeys, ArrayList<E> dist) {

        if(g == null)
            return;
        if(!g.validVertex(vOrig))
            return;

        int vOrigKey = g.key(vOrig);

        for(int i=0;i<visited.length;i++) {
            dist.add(null);
            pathKeys.add(null);
        }

        dist.set(vOrigKey, zero);
        pathKeys.set(vOrigKey, vOrig);

        while(vOrigKey != -1){
            visited[vOrigKey] = true;
            vOrig = g.vertex(vOrigKey);
            for(V vAdj : g.adjVertices(vOrig)) {
                Edge<V, E> edge = g.edge(vOrig, vAdj);

                int vAdjKey = g.key(vAdj);
                int comp;
                if (dist.get(vAdjKey) != null) {
                    comp = ce.compare(dist.get(vAdjKey), sum.apply(dist.get(vOrigKey), edge.getWeight()));
                    if (!visited[vAdjKey] && comp > 0) {
                        dist.set(vAdjKey, sum.apply(dist.get(vOrigKey), edge.getWeight()));
                        pathKeys.set(vAdjKey, vOrig);
                    }
                } else{
                    dist.set(vAdjKey, sum.apply(dist.get(vOrigKey), edge.getWeight()));
                    pathKeys.set(vAdjKey, vOrig);
                }
            }
            vOrigKey = getVertMinDist(dist, visited, ce, zero);
        }
    }

    private static <E> int getVertMinDist(ArrayList<E> dist, boolean[] visited, Comparator<E> ce, E zero){
        int v = -1;
        E min = null;
        for(int i=0;i<dist.size();i++){
            if(dist.get(i) == null)
                continue;

            if(min == null && dist.get(i) != zero && !visited[i]){
                min = dist.get(i);
                v = i;
                continue;
            }

            if(min == null)
                continue;
            if(ce.compare(dist.get(i), min) < 0 && !visited[i]) {
                min = dist.get(i);
                v = i;
            }
        }
        return v;
    }



    /** Shortest-path between two vertices
     *
     * @param g graph
     * @param vOrig origin vertex
     * @param vDest destination vertex
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @param zero neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> E shortestPath(Graph<V, E> g, V vOrig, V vDest,
                                        Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                        LinkedList<V> shortPath) {
        if(g == null)
            return null;
        if(g.key(vOrig) < 0 || g.key(vDest) < 0)
            return null;

        int size = g.numVertices();
        boolean[] visited = new boolean[size];
        ArrayList<V> pathKeys = new ArrayList<>(size);
        ArrayList<E> dist = new ArrayList<>(size);

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        getPath(g, vOrig, vDest, pathKeys, shortPath);
        if(shortPath.isEmpty())
            return null;

        return dist.get(g.key(vDest));
    }

    /** Shortest-path between a vertex and all other vertices
     *
     * @param g graph
     * @param vOrig start vertex
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @param zero neutral element of the sum in elements of type E
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig,
                                               Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                               ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {

        if(g == null)
            return false;

        if(!g.validVertex(vOrig))
            return false;

        int size = g.numVertices();
        boolean[] visited = new boolean[size];
        ArrayList<V> pathKeys = new ArrayList<>(size);
        ArrayList<E> dist = new ArrayList<>(size);

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        for(V vDest : g.vertices()){
            dists.add(dist.get(g.key(vDest)));
            LinkedList<V> path = new LinkedList<>();
            getPath(g, vOrig, vDest, pathKeys, path);
            paths.add(path);
        }

        return true;
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest,
                                       ArrayList<V> pathKeys, LinkedList<V> path) {

        if(!g.validVertex(vDest))
            return;

        if(vOrig.equals(vDest)){
            path.add(vOrig);
            return;
        }

        V vertex = vDest;

        do{
            path.addFirst(vertex);
            int keySearch = g.key(vertex);
            vertex = pathKeys.get(keySearch);
            if(vertex == null) {
                path.clear();
                return;
            }
        }while(!vertex.equals(vOrig));
        path.addFirst(vertex);
    }

    /** Calculates the minimum distance graph using Floyd-Warshall
     *
     * @param g initial graph
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @return the minimum distance graph
     */
    public static <V,E> MatrixGraph<V,E> minDistGraph(Graph <V,E> g, Comparator<E> ce, BinaryOperator<E> sum, E zero) {

        MatrixGraph<V, E> matrixGraph = new MatrixGraph<>(g);
        int size = matrixGraph.numVertices();


        for(int k=0;k<size;k++){
            for(int i=0;i<size;i++){
                Edge<V, E> ikEdge = matrixGraph.edge(i, k);
                if(i != k && ikEdge != null)
                    for(int j=0;j<size;j++){
                        Edge<V, E> kjEdge = matrixGraph.edge(k, j);
                        Edge<V, E> ijEdge = matrixGraph.edge(i, j);
                        if(i != j && k != j && kjEdge != null && ijEdge != null){
                            if(ce.compare(sum.apply(kjEdge.getWeight(), ikEdge.getWeight()), ijEdge.getWeight()) < 0)
                                ijEdge.setWeight(sum.apply(kjEdge.getWeight(), ikEdge.getWeight()));
                        }else if(ijEdge == null && kjEdge != null) {
                            if(i == j) {
                                matrixGraph.addEdge(matrixGraph.vertex(i), matrixGraph.vertex(j), zero);
                                continue;
                            }
                            matrixGraph.addEdge(matrixGraph.vertex(i), matrixGraph.vertex(j),
                                    sum.apply(kjEdge.getWeight(), ikEdge.getWeight()));
                        }

                    }
            }
        }
        return matrixGraph;
    }


    public static<V, E> E graphDiameter(Graph <V, E> graph, Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                           LinkedList<V> path){

        if(graph == null)
            return null;

        MatrixGraph<V, E> transitiveClosure = minDistGraph(graph, ce, sum, zero);

        Edge<V, E> max = null;

        for(Edge<V, E> edge : transitiveClosure.edges()) {
            if(max == null)
                max = edge;
            if (ce.compare(max.getWeight(), edge.getWeight()) < 0)
                max = edge;
        }

        V vO = max.getVOrig();
        V vD = max.getVDest();

        shortestPath(graph, vO, vD, ce, sum, zero, path);

        return max.getWeight();
    }

    /**
     * Given an undirected, connected graph with positive edge weights, a minimum spanning tree (MST) is calculated.
     * This algorithm uses Prim's algorithm.
     * @param g graph to calculate.
     * @param zero neutral element of the sum in elements of type E.
     * @param ce comparator between elements of type E.
     * @return minimum spanning tree.
     */
    public static <V, E> MapGraph<V, E> minimumSpanningTree(Graph<V, E> g, E zero,
                                                            Comparator<E> ce){
        if(g == null)
            return null;

        if(g.isDirected())
            return null;

        int size = g.numVertices();

        ArrayList<E> dist = new ArrayList<>(size);
        ArrayList<V> path = new ArrayList<>(size);
        boolean[] visited = new boolean[size];

        for(int i=0;i<size;i++){
            dist.add(null);
            path.add(null);
            visited[i] = false;
        }

        V vOrig = g.vertex(g.numVertices()-1);
        dist.set(g.numVertices()-1, zero);
        path.set(g.numVertices()-1, vOrig);
        int vOrigKey = g.key(vOrig);

        while(vOrigKey != -1){
            visited[vOrigKey] = true;
            vOrig = g.vertex(vOrigKey);
            for(V vAdj : g.adjVertices(vOrig)){
                Edge<V, E> edge = g.edge(vOrig, vAdj);
                int vAdjKey = g.key(vAdj);
                if(dist.get(vAdjKey) != null) {
                    if (!visited[vAdjKey] && ce.compare(dist.get(vAdjKey), edge.getWeight()) > 0) {
                        dist.set(vAdjKey, edge.getWeight());
                        path.set(vAdjKey, vOrig);
                    }
                }else {
                    path.set(vAdjKey, vOrig);
                    dist.set(vAdjKey, edge.getWeight());
                }
            }
            vOrigKey = getVertMinDist(dist, visited, ce, zero);
        }
        MapGraph<V, E> mst = new MapGraph<>(false);

        for(V vertex : g.vertices())
            mst.addVertex(vertex);

        buildMST(mst, path, dist, zero);
        return mst;
    }

    private static <V, E> void buildMST(Graph<V, E> mst, ArrayList<V> path, ArrayList<E> dist, E zero){
        for(int i=0;i<path.size();i++){
            E d = dist.get(i);
            if(d.equals(zero))
                continue;
            V v1 = path.get(i);
            V v2 = mst.vertex(i);
            mst.addEdge(v1, v2, d);
        }
    }
}
