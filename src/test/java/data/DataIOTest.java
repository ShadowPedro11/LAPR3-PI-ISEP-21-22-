package data;

import exceptions.MissingNetworkException;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class DataIOTest {

    private DataIO dataEntity1;
    private DataIO dataEntity2;
    private DataIO dataEntity3;
    private DataIO dataC;

    private String path1;
    private String path2;

    private final String SMALL_CLIENT_PRODUCERS = "src/resources/grafos/Small/clientes-produtores_small.csv";
    private final String SMALL_DISTANCES = "src/resources/grafos/Small/distancias_small.csv";
    private final String BIG_CLIENT_PRODUCERS = "src/resources/grafos/Big/clientes-produtores_big.csv";
    private final String BIG_DISTANCES = "src/resources/grafos/Big/distancias_big.csv";
    private final String CUSTOM_CLIENT_PRODUCERS = "src/resources/grafos/Custom/clientes-produtores.csv";
    private final String CUSTOM_DISTANCES = "src/resources/grafos/Custom/distancias.csv";

    //------------------------------------------------------------------------------------//

    private final String BIG_CREEL = "src/resources/grafos/Big/cabazes_big.csv";
    private final String SMALL_CREEL = "src/resources/grafos/Small/cabazes_small.csv";
    //private final String CUSTOM_CREEL = "";

    @Before
    public void setUp() throws Exception {
        dataEntity1 = new DataIO();
        dataEntity1.loadNetworkFile(SMALL_CLIENT_PRODUCERS, SMALL_DISTANCES);
        dataEntity1.loadHumpers(SMALL_CREEL);

        dataEntity2 = new DataIO();
        dataEntity2.loadNetworkFile(BIG_CLIENT_PRODUCERS, BIG_DISTANCES);
        dataEntity2.loadHumpers(BIG_CREEL);

        dataC = new DataIO();
        dataC.loadNetworkFile(CUSTOM_CLIENT_PRODUCERS, CUSTOM_DISTANCES);
        //dataC.loadOrders(CUSTOM_CREEL);
    }

    @Test
    public void loadInvalidFile() {

        path1 = "invalidPath";
        path2 = "invalidPath2";

        dataEntity3 = new DataIO();

        Assert.assertThrows(FileNotFoundException.class, () -> dataEntity3.loadNetworkFile(path1, path2));

        Assert.assertThrows(FileNotFoundException.class, () -> dataEntity3.loadNetworkFile(path1, SMALL_DISTANCES));

        Assert.assertThrows(FileNotFoundException.class, () -> dataEntity3.loadNetworkFile(SMALL_CLIENT_PRODUCERS, path2));

        Assert.assertThrows(FileNotFoundException.class, () -> dataEntity3.loadHumpers(path1));

        Assert.assertThrows(FileNotFoundException.class, () -> dataEntity3.loadHumpers(path2));

    }

    @Test
    public void loadNullFile(){

        path1 = null;
        path2 = null;

        dataEntity3 = new DataIO();

        Assert.assertThrows(NullPointerException.class, () -> dataEntity3.loadNetworkFile(path1, path2));

        Assert.assertThrows(NullPointerException.class, () -> dataEntity3.loadNetworkFile(path1, SMALL_DISTANCES));

        Assert.assertThrows(NullPointerException.class, () -> dataEntity3.loadNetworkFile(SMALL_CLIENT_PRODUCERS, path2));

        Assert.assertThrows(NullPointerException.class, () -> dataEntity3.loadHumpers(path1));


    }

    @Test
    public void loadValidFile() throws Exception {

        dataEntity3 = new DataIO();

        Assert.assertTrue(dataEntity3.loadNetworkFile(SMALL_CLIENT_PRODUCERS, SMALL_DISTANCES));
        Assert.assertTrue(dataEntity3.loadHumpers(SMALL_CREEL));

        Assert.assertTrue(dataEntity3.loadNetworkFile(BIG_CLIENT_PRODUCERS, BIG_DISTANCES));
        Assert.assertTrue(dataEntity3.loadHumpers(BIG_CREEL));

        Assert.assertTrue(dataEntity3.loadNetworkFile(CUSTOM_CLIENT_PRODUCERS, CUSTOM_DISTANCES));
        //Assert.assertTrue(dataEntity3.loadOrders(CUSTOM_CREEL));

    }

    @Test
    public void assertDataIsCorrectlyLoaded(){

        ArrayList<Entity> vertices = dataEntity1.getGraph().vertices();
        Set<Producer> producers = new HashSet<>();
        Set<Client> clients = new HashSet<>();
        Set<Company> companies = new HashSet<>();
        countAVLEntities(dataEntity1.getOrderedHumpers().preOrder(), dataEntity1.getProvidedHumpers().inOrder(),
                producers, clients, companies);

        // Producer
        int exp = 3;
        int resEntity = countEntities(Producer.class, vertices);
        //int resOrder = countEntities(Producer.class, keys);
        int resOrder = producers.size();

        Assert.assertEquals(exp, resEntity);
        Assert.assertEquals(exp, resOrder);

        // Client
        exp = 9;
        resEntity = countEntities(Client.class, vertices);
        //resOrder = countEntities(Client.class, keys);
        resOrder = clients.size();

        Assert.assertEquals(exp, resEntity);
        Assert.assertEquals(exp, resOrder);

        // Company
        exp = 5;
        resEntity = countEntities(Company.class, vertices);
        //resOrder = countEntities(Company.class, keys);
        resOrder = companies.size();

        Assert.assertEquals(exp, resEntity);
        Assert.assertEquals(exp, resOrder);

        
        vertices = dataEntity2.getGraph().vertices();
        //keys = dataEntity2.getOrders().keySet();
        countAVLEntities(dataEntity2.getOrderedHumpers().preOrder(), dataEntity2.getProvidedHumpers().inOrder(),
                producers, clients, companies);

        // Producer
        exp = 60;
        resEntity = countEntities(Producer.class, vertices);
        //resOrder = countEntities(Producer.class, keys);
        resOrder = producers.size();

        Assert.assertEquals(exp, resEntity);
        Assert.assertEquals(exp, resOrder);

        // Client
        exp = 173;
        resEntity = countEntities(Client.class, vertices);
        //resOrder = countEntities(Client.class, keys);
        resOrder = clients.size();

        Assert.assertEquals(exp, resEntity);
        Assert.assertEquals(exp, resOrder);

        // Company
        exp = 90;
        resEntity = countEntities(Company.class, vertices);
        //resOrder = countEntities(Company.class, keys);
        resOrder = companies.size();

        Assert.assertEquals(exp, resEntity);
        Assert.assertEquals(exp, resOrder);

    }

    private void countAVLEntities(Iterable<DayClient> orders, Iterable<DayProducer> provided, Set<Producer> producers,
                                  Set<Client> clients, Set<Company> companies){
        producers.clear();
        clients.clear();
        companies.clear();

        for(DayClient dc : orders){
            for(EntityPlus ep : dc.getClients().inOrder()){
                if(ep.getEntity() instanceof Company c)
                    companies.add(c);
                else if(ep.getEntity() instanceof Client c)
                    clients.add(c);
            }
        }

        for(DayProducer dp : provided){
            for(ProducerPlus pp : dp.getProducers().inOrder())
                producers.add(pp.getProducer());
        }
    }
    @Test
    public void assertVertexNEdgesNumber(){

        int vertexQuantityExp = 17;
        int edgesQuantityExp = 66;

        Set<Entity> entityList = new HashSet<>();
        getHumpersEntitiesLoaded(entityList, dataEntity1);
        Assert.assertEquals(vertexQuantityExp, entityList.size());
        entityList.clear();

        Map<Integer, Integer> exp = new HashMap<>();
        exp.put(vertexQuantityExp, edgesQuantityExp);

        int vertexQuantityRes = dataEntity1.getGraph().numVertices();
        int edgesQuantityRes = dataEntity1.getGraph().numEdges();

        Map<Integer, Integer> res = new HashMap<>();
        res.put(vertexQuantityRes, edgesQuantityRes);

        Assert.assertEquals(exp, res);


        vertexQuantityExp = 323;
        edgesQuantityExp = 1566;

        getHumpersEntitiesLoaded(entityList, dataEntity2);
        Assert.assertEquals(vertexQuantityExp, entityList.size());
        entityList.clear();

        exp.put(vertexQuantityExp, edgesQuantityExp);

        vertexQuantityRes = dataEntity2.getGraph().numVertices();
        edgesQuantityRes = dataEntity2.getGraph().numEdges();

        res.put(vertexQuantityRes, edgesQuantityRes);

        Assert.assertEquals(exp, res);

        vertexQuantityExp = 11;
        edgesQuantityExp = 24;

        exp.put(vertexQuantityExp, edgesQuantityExp);

        vertexQuantityRes = dataC.getGraph().numVertices();
        edgesQuantityRes = dataC.getGraph().numEdges();

        res.put(vertexQuantityRes, edgesQuantityRes);

        Assert.assertEquals(exp, res);

    }

    private void getHumpersEntitiesLoaded(Collection<Entity> collection, DataIO dataIO){
        for(DayClient day : dataIO.getOrderedHumpers().inOrder())
            for(EntityPlus entity : day.getClients().inOrder())
                collection.add(entity.getEntity());

        for(DayProducer day : dataIO.getProvidedHumpers().inOrder())
            for(ProducerPlus producer : day.getProducers().inOrder())
                collection.add(producer.getProducer());

    }

    @Test
    public void assertLoadOrdersDoesntWorkWithoutLoadNetwork() throws Exception {

        dataEntity3 = new DataIO();
        Assert.assertThrows(MissingNetworkException.class, () -> dataEntity3.loadHumpers(SMALL_CREEL));

        dataEntity3.loadNetworkFile(SMALL_CLIENT_PRODUCERS, SMALL_DISTANCES);
        boolean res = dataEntity3.loadHumpers(SMALL_CREEL);
        Assert.assertTrue(res);

    }

    private static <E> int countEntities(Class<E> classType, Iterable<Entity> vertices){
        int sum=0;
        for(Entity e : vertices)
            if(e.getClass().equals(classType))
                sum++;
        return sum;
    }


}
