package model;

import data.DataIO;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trees.AVL;

import java.io.FileNotFoundException;
import java.util.*;




import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Pedro Pereira - 1211131
 **/
class DispatchListWithoutRestrictionTest {
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
     smallFile.loadNetworkFile(SMALL_CLIENT_PRODUCERS,SMALL_DISTANCES);
     smallFile.loadHumpers(SMALL_CREEL);
     testFile.loadNetworkFile(TEST_CLIENT_PRODUCERS,TEST_DISTANCES);
     testFile.loadHumpers(TEST_CREEL);
     bigFile.loadNetworkFile(BIG_CLIENT_PRODUCERS,BIG_DISTANCES);
     bigFile.loadHumpers(BIG_CREEL);
    }


    //Teste
    @Test
    void dispatchListWithoutRestrictionTestFileDay1() throws FileNotFoundException {
        List<Expedition> actual;
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(testFile);
        actual = dispatchListWithoutRestriction.dispatchListWithoutRestriction(1);

        Iterable<EntityPlus> entityPlusIterable = testFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().inOrder();
        List<Expedition> expected = new ArrayList<>();

        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap <>();
            Expedition expedition = new Expedition(ep,map);

            if(ep.getEntity().getName().equals("C1")){
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),5.0,5.0));
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),2.0,2.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),2.5,2.5));



            }else if(ep.getEntity().getName().equals("E2")){

                map.put(new Products(2,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),6.0,6.0));
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),9.0,9.0));
                map.put(new Products(7,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),7.5,7.5));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),2.5,2.5));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),4.5,4.5));

            }else if(ep.getEntity().getName().equals("E3")){

            }
            expected.add(expedition);

        }
        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap<>();
            Expedition expedition = new Expedition(ep,map);

            if(ep.getEntity().getName().equals("C1")){

            }else if(ep.getEntity().getName().equals("E2")){
                map.put( new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),9.0,7.5));
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),6.0,1.0));
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),8.5,0.5));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),3.0,1.0));

            }else if(ep.getEntity().getName().equals("E3")){
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),10.0,8.0));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),5.0,1.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),5.5,0.5));
            }
            expected.add(expedition);
        }

        Assert.assertEquals(expected.size(),actual.size());
        Assert.assertEquals(expected.toString(),actual.toString());
        dispatchListWithoutRestriction.printExpeditionList(actual);
        }

    @Test
    void dispatchListWithoutRestrictionTestFileDay2() throws FileNotFoundException {
        List<Expedition> actual;
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(testFile);
        actual = dispatchListWithoutRestriction.dispatchListWithoutRestriction(2);

        Iterable<EntityPlus> entityPlusIterable = testFile.getOrderedHumpers().clone().find(new DayClient(2)).getClients().inOrder();
        List<Expedition> expected = new ArrayList<>();

        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap <>();
            Expedition expedition = new Expedition(ep,map);

            if(ep.getEntity().getName().equals("C1")){
                map.put(new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),4.5,4.5));
                map.put(new Products(2,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),6.0,6.0));
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),3.5,3.5));
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),4.0,4.0));
                map.put(new Products(7,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),9.0,9.0));
                map.put(new Products(8,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),3.0,3.0));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),5.5,5.5));


            }else if(ep.getEntity().getName().equals("E2")){
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),2.0,2.0));

            }else if(ep.getEntity().getName().equals("E3")){

            }
            expected.add(expedition);

        }
        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap<>();
            Expedition expedition = new Expedition(ep,map);
            if(ep.getEntity().getName().equals("C1")){
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),1.5,0.0));

            }else if(ep.getEntity().getName().equals("E2")){
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),9.5,0.5));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),9.0,0.0));
                map.put(new Products(12,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),9.0,2.5));

            }else if(ep.getEntity().getName().equals("E3")){

            }
            expected.add(expedition);
        }

        Assert.assertEquals(expected.size(),actual.size());
        Assert.assertEquals(expected.toString(),actual.toString());
        dispatchListWithoutRestriction.printExpeditionList(actual);
    }

    @Test
    void dispatchListWithoutRestrictionTestFileDay3() throws FileNotFoundException {
        List<Expedition> actual;
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(testFile);
        actual = dispatchListWithoutRestriction.dispatchListWithoutRestriction(3);

        Iterable<EntityPlus> entityPlusIterable = testFile.getOrderedHumpers().clone().find(new DayClient(3)).getClients().inOrder();
        List<Expedition> expected = new ArrayList<>();

        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap <>();
            Expedition expedition = new Expedition(ep,map);

            if(ep.getEntity().getName().equals("C1")){
                map.put(new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),8.0,8.0));
                map.put(new Products(4,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),2.0,2.0));
                map.put(new Products(7,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),9.5,9.5));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),2.0,2.0));
                map.put(new Products(12,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),6.0,6.0));

            }else if(ep.getEntity().getName().equals("E2")){
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),2.5,2.5));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),5.5,5.5));

            }else if(ep.getEntity().getName().equals("E3")){

            }
            expected.add(expedition);

        }
        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap<>();
            Expedition expedition = new Expedition(ep,map);
            if(ep.getEntity().getName().equals("C1")){
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),9.5,6.0));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),9.5,3.0));

            }else if(ep.getEntity().getName().equals("E2")){
                map.put(new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),8.0,6.0));
                map.put(new Products(12,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),3.5,2.5));
            }else if(ep.getEntity().getName().equals("E3")){

            }
            expected.add(expedition);
        }

        Assert.assertEquals(expected.size(),actual.size());
        Assert.assertEquals(expected.toString(),actual.toString());
        dispatchListWithoutRestriction.printExpeditionList(actual);
    }

    @Test
    void sobrasDiaTestFile() {
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(testFile);
        dispatchListWithoutRestriction.dispatchListWithoutRestriction(1);
        AVL<ProducerPlus> avl= dispatchListWithoutRestriction.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers();
        List<String> actual = dispatchListWithoutRestriction.sobrasDia(avl);
        List<String> expected = new ArrayList<>();
        expected.add("Producer:P1 Product:2 Quantidade:1.5");
        expected.add("Producer:P1 Product:4 Quantidade:2.0");
        expected.add("Producer:P1 Product:7 Quantidade:1.0");
        expected.add("Producer:P1 Product:8 Quantidade:3.0");
        expected.add("Producer:P1 Product:10 Quantidade:4.5");
        expected.add("Producer:P2 Product:2 Quantidade:6.5");
        expected.add("Producer:P2 Product:3 Quantidade:1.5");
        expected.add("Producer:P2 Product:4 Quantidade:7.0");
        expected.add("Producer:P2 Product:5 Quantidade:4.0");
        expected.add("Producer:P2 Product:7 Quantidade:4.5");
        expected.add("Producer:P2 Product:8 Quantidade:3.5");
        expected.add("Producer:P2 Product:9 Quantidade:1.0");
        expected.add("Producer:P3 Product:1 Quantidade:2.5");
        expected.add("Producer:P3 Product:2 Quantidade:2.0");
        expected.add("Producer:P3 Product:5 Quantidade:1.5");
        expected.add("Producer:P3 Product:7 Quantidade:9.0");
        expected.add("Producer:P3 Product:8 Quantidade:6.0");
        expected.add("Producer:P3 Product:9 Quantidade:3.5");
        expected.add("Producer:P3 Product:10 Quantidade:4.0");
        expected.add("Producer:P3 Product:12 Quantidade:2.5");
        Assert.assertEquals(expected,actual);
    }

    @Test
    void expeditionListFullProductsTestFile() {
        List<Expedition> actual = new ArrayList<>();
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(testFile);

        Iterable<EntityPlus> entityPlusIterable = testFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().inOrder();
        Iterable<ProducerPlus> producerPlusIterable = testFile.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers().inOrder();

        dispatchListWithoutRestriction.expeditionListFullProducts(actual,entityPlusIterable,producerPlusIterable,null,null);

        List<Expedition> expected = new ArrayList<>();

        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap <>();
            Expedition expedition = new Expedition(ep,map);

            if(ep.getEntity().getName().equals("C1")){
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),5.0,5.0));
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),2.0,2.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),2.5,2.5));



            }else if(ep.getEntity().getName().equals("E2")){

                map.put(new Products(2,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),6.0,6.0));
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),9.0,9.0));
                map.put(new Products(7,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),7.5,7.5));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),2.5,2.5));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),4.5,4.5));

            }else if(ep.getEntity().getName().equals("E3")){

            }
            expected.add(expedition);

        }

        Assert.assertEquals(expected.size(),actual.size());
        Assert.assertEquals(expected.toString(),actual.toString());

    }

    @Test
    void expeditionFullTestFile() {
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(testFile);

        AVL<EntityPlus> actualEntityPlusAVL = testFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().clone();
        AVL<ProducerPlus> actualProducerPlusAVL = testFile.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers().clone();

        Iterable<EntityPlus> actualEntityPlusIterable =actualEntityPlusAVL.inOrder();
        Iterable<ProducerPlus> actualProducerPlusIterable =actualProducerPlusAVL.inOrder();

        dispatchListWithoutRestriction.expeditionFull(actualEntityPlusIterable,actualProducerPlusIterable,null,null);

        Iterable<Products> actual1 = actualEntityPlusAVL.find(new EntityPlus(new Client("","C1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual2 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E2",null))).getProductsAVL().inOrder();
        Iterable<Products> actual3 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E3",null))).getProductsAVL().inOrder();

        Iterable<Products> actual4 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual5 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P2",null))).getProductsAVL().inOrder();
        Iterable<Products> actual6 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P3",null))).getProductsAVL().inOrder();

        List<Products> expected1 = new ArrayList<>();
        expected1.add(new Products(5,0.0));
        expected1.add(new Products(6,0.0));
        expected1.add(new Products(11,0.0));
        List<Products> expected2 = new ArrayList<>();
        expected2.add(new Products(1,9.0));
        expected2.add(new Products(2,0.0));
        expected2.add(new Products(3,0.0));
        expected2.add(new Products(5,6.0));
        expected2.add(new Products(6,8.5));
        expected2.add(new Products(7,0.0));
        expected2.add(new Products(9,0.0));
        expected2.add(new Products(10,0.0));
        expected2.add(new Products(11,3.0));
        List<Products> expected3 = new ArrayList<>();
        expected3.add(new Products(6,10.0));
        expected3.add(new Products(9,5.0));
        expected3.add(new Products(11,5.5));


        List<Products> expected4 = new ArrayList<>();
        expected4.add(new Products(2,1.5));
        expected4.add(new Products(3,0.0));
        expected4.add(new Products(4,2.0));
        expected4.add(new Products(5,1.0));
        expected4.add(new Products(7,1.0));
        expected4.add(new Products(8,3.0));
        expected4.add(new Products(9,1.0));
        expected4.add(new Products(10,4.5));
        expected4.add(new Products(11,1.0));

        List<Products> expected5 = new ArrayList<>();
        expected5.add(new Products(1,7.5));
        expected5.add(new Products(2,6.5));
        expected5.add(new Products(3,1.5));
        expected5.add(new Products(4,7.0));
        expected5.add(new Products(5,4.0));
        expected5.add(new Products(6,0.5));
        expected5.add(new Products(7,4.5));
        expected5.add(new Products(8,3.5));
        expected5.add(new Products(9,1.0));

        List<Products> expected6 = new ArrayList<>();
        expected6.add(new Products(1,2.5));
        expected6.add(new Products(2,2.0));
        expected6.add(new Products(5,1.5));
        expected6.add(new Products(6,8.0));
        expected6.add(new Products(7,9.0));
        expected6.add(new Products(8,6.0));
        expected6.add(new Products(9,3.5));
        expected6.add(new Products(10,4.0));
        expected6.add(new Products(11,0.5));
        expected6.add(new Products(12,2.5));

        assertEquals(actual1.toString(),expected1.toString());
        assertEquals(actual2.toString(),expected2.toString());
        assertEquals(actual3.toString(),expected3.toString());
        assertEquals(actual4.toString(),expected4.toString());
        assertEquals(actual5.toString(),expected5.toString());
        assertEquals(actual6.toString(),expected6.toString());
    }

    @Test
    void expeditionListRemainProductsTestFile() {
        List<Expedition> actual = new ArrayList<>();
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(testFile);

        Iterable<EntityPlus> entityPlusIterable = testFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().inOrder();
        Iterable<ProducerPlus> producerPlusIterable = testFile.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers().inOrder();
        dispatchListWithoutRestriction.expeditionFull(entityPlusIterable,producerPlusIterable,null,null);
        dispatchListWithoutRestriction.expeditionListRemainProducts(actual,entityPlusIterable,producerPlusIterable,null,null);

        List<Expedition> expected = new ArrayList<>();

        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap<>();
            Expedition expedition = new Expedition(ep,map);

            if(ep.getEntity().getName().equals("C1")){

            }else if(ep.getEntity().getName().equals("E2")){
                map.put( new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),9.0,7.5));
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),6.0,1.0));
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),8.5,0.5));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),3.0,1.0));

            }else if(ep.getEntity().getName().equals("E3")){
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),10.0,8.0));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),5.0,1.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),5.5,0.5));
            }
            expected.add(expedition);
        }

        Assert.assertEquals(expected.size(),actual.size());
        Assert.assertEquals(expected.toString(),actual.toString());
    }

    @Test
    void expeditionRemainTestFile() {
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(testFile);

        AVL<EntityPlus> actualEntityPlusAVL = testFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().clone();
        AVL<ProducerPlus> actualProducerPlusAVL = testFile.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers().clone();

        Iterable<EntityPlus> actualEntityPlusIterable =actualEntityPlusAVL.inOrder();
        Iterable<ProducerPlus> actualProducerPlusIterable =actualProducerPlusAVL.inOrder();

        dispatchListWithoutRestriction.expeditionFull(actualEntityPlusIterable,actualProducerPlusIterable,null,null);
        dispatchListWithoutRestriction.expeditionRemain(actualEntityPlusIterable,actualProducerPlusIterable,null,null);

        Iterable<Products> actual1 = actualEntityPlusAVL.find(new EntityPlus(new Client("","C1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual2 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E2",null))).getProductsAVL().inOrder();
        Iterable<Products> actual3 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E3",null))).getProductsAVL().inOrder();

        Iterable<Products> actual4 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual5 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P2",null))).getProductsAVL().inOrder();
        Iterable<Products> actual6 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P3",null))).getProductsAVL().inOrder();


        List<Products> expected1 = new ArrayList<>();
        expected1.add(new Products(5,0.0));
        expected1.add(new Products(6,0.0));
        expected1.add(new Products(11,0.0));
        List<Products> expected2 = new ArrayList<>();
        expected2.add(new Products(1,1.5));
        expected2.add(new Products(2,0.0));
        expected2.add(new Products(3,0.0));
        expected2.add(new Products(5,5.0));
        expected2.add(new Products(6,8.0));
        expected2.add(new Products(7,0.0));
        expected2.add(new Products(9,0.0));
        expected2.add(new Products(10,0.0));
        expected2.add(new Products(11,2.0));
        List<Products> expected3 = new ArrayList<>();
        expected3.add(new Products(6,2.0));
        expected3.add(new Products(9,4.0));
        expected3.add(new Products(11,5.0));


        List<Products> expected4 = new ArrayList<>();
        expected4.add(new Products(2,1.5));
        expected4.add(new Products(3,0.0));
        expected4.add(new Products(4,2.0));
        expected4.add(new Products(5,0.0));
        expected4.add(new Products(7,1.0));
        expected4.add(new Products(8,3.0));
        expected4.add(new Products(9,0.0));
        expected4.add(new Products(10,4.5));
        expected4.add(new Products(11,0.0));

        List<Products> expected5 = new ArrayList<>();
        expected5.add(new Products(1,0.0));
        expected5.add(new Products(2,6.5));
        expected5.add(new Products(3,1.5));
        expected5.add(new Products(4,7.0));
        expected5.add(new Products(5,4.0));
        expected5.add(new Products(6,0.0));
        expected5.add(new Products(7,4.5));
        expected5.add(new Products(8,3.5));
        expected5.add(new Products(9,1.0));

        List<Products> expected6 = new ArrayList<>();
        expected6.add(new Products(1,2.5));
        expected6.add(new Products(2,2.0));
        expected6.add(new Products(5,1.5));
        expected6.add(new Products(6,0.0));
        expected6.add(new Products(7,9.0));
        expected6.add(new Products(8,6.0));
        expected6.add(new Products(9,3.5));
        expected6.add(new Products(10,4.0));
        expected6.add(new Products(11,0.0));
        expected6.add(new Products(12,2.5));

        assertEquals(actual1.toString(),expected1.toString());
        assertEquals(actual2.toString(),expected2.toString());
        assertEquals(actual3.toString(),expected3.toString());
        assertEquals(actual4.toString(),expected4.toString());
        assertEquals(actual5.toString(),expected5.toString());
        assertEquals(actual6.toString(),expected6.toString());

    }


    //Small
    @Test
    void dispatchListWithoutRestrictionSmallFile() throws FileNotFoundException {
        List<Expedition> actual;
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(smallFile);
        actual = dispatchListWithoutRestriction.dispatchListWithoutRestriction(1);

        Iterable<EntityPlus> entityPlusIterable = smallFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().inOrder();
        List<Expedition> expected = new ArrayList<>();

        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap <>();
            Expedition expedition = new Expedition(ep,map);

            if(ep.getEntity().getName().equals("C1")){
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),5.0,5.0));
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),2.0,2.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),2.5,2.5));

            }else if(ep.getEntity().getName().equals("C2")){
                map.put(new Products(2,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),5.5,5.5));
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),4.5,4.5));
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),4.0,4.0));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),1.0,1.0));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),9.0,9.0));


            }else if(ep.getEntity().getName().equals("C3")){
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),2.5,2.5));

            }else if(ep.getEntity().getName().equals("C8")){
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),2.5,2.5));

            }else if(ep.getEntity().getName().equals("C9")){
                map.put(new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),7.0,7.0));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),3.0,3.0));

            }else if(ep.getEntity().getName().equals("E2")){
                map.put(new Products(2,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),6.0,6.0));
                map.put(new Products(7,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),7.5,7.5));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),2.5,2.5));

            }else if(ep.getEntity().getName().equals("E4")){
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),1.0,1.0));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),1.0,1.0));

            }else if(ep.getEntity().getName().equals("E5")){
                map.put(new Products(2,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),1.5,1.5));

            }
            expected.add(expedition);

        }
        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap<>();
            Expedition expedition = new Expedition(ep,map);

            if(ep.getEntity().getName().equals("C2")){
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),10.0,1.0));

            }else if(ep.getEntity().getName().equals("C3")){
                map.put(new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),10.0,0.5));
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),9.0,1.5));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),4.5,1.0));

            }else if(ep.getEntity().getName().equals("C6")){
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),8.5,4.5));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),9.0,3.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),9.5,0.5));


            }else if(ep.getEntity().getName().equals("C8")){
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),7.5,0.5));
                map.put(new Products(12,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),6.0,2.5));

            }else if(ep.getEntity().getName().equals("E2")){
                map.put(new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),9.0,2.5));
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),9.0,1.5));
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),6.0,0.0));
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),8.5,0.5));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),4.5,0.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),3.0,0.0));


            }else if(ep.getEntity().getName().equals("E3")){
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),10.0,3.0));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),5.0,0.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),5.5,0.0));


            }else if(ep.getEntity().getName().equals("E4")){
                map.put(new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),6.5,0.0));
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),6.5,0.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),5.5,0.0));


            }else if(ep.getEntity().getName().equals("E5")){
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),8.0,0.0));
                map.put(new Products(7,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),9.5,1.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),6.0,0.0));


            }
            expected.add(expedition);
        }

        Assert.assertEquals(expected.size(),actual.size());
        Assert.assertEquals(expected.toString(),actual.toString());
        dispatchListWithoutRestriction.printExpeditionList(actual);
    }
    @Test
    void sobrasDiaSmallFile() {
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(smallFile);
        dispatchListWithoutRestriction.dispatchListWithoutRestriction(1);
        AVL<ProducerPlus> avl= dispatchListWithoutRestriction.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers();
        List<String> actual = dispatchListWithoutRestriction.sobrasDia(avl);
        List<String> expected = new ArrayList<>();
        expected.add("Producer:P1 Product:2 Quantidade:0.5");
        expected.add("Producer:P1 Product:4 Quantidade:2.0");
        expected.add("Producer:P1 Product:8 Quantidade:3.0");
        expected.add("Producer:P2 Product:2 Quantidade:0.5");
        expected.add("Producer:P2 Product:4 Quantidade:7.0");
        expected.add("Producer:P2 Product:7 Quantidade:4.5");
        expected.add("Producer:P2 Product:8 Quantidade:3.5");
        expected.add("Producer:P3 Product:2 Quantidade:2.0");
        expected.add("Producer:P3 Product:7 Quantidade:9.0");
        expected.add("Producer:P3 Product:8 Quantidade:6.0");
        Assert.assertEquals(expected,actual);
    }
    @Test
    void expeditionListFullProductsSmallFile() {
        List<Expedition> actual = new ArrayList<>();
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(smallFile);

        Iterable<EntityPlus> entityPlusIterable = smallFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().inOrder();
        Iterable<ProducerPlus> producerPlusIterable = smallFile.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers().inOrder();

        dispatchListWithoutRestriction.expeditionListFullProducts(actual,entityPlusIterable,producerPlusIterable,null,null);

        List<Expedition> expected = new ArrayList<>();

        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap <>();
            Expedition expedition = new Expedition(ep,map);

            if(ep.getEntity().getName().equals("C1")){
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),5.0,5.0));
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),2.0,2.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),2.5,2.5));

            }else if(ep.getEntity().getName().equals("C2")){
                map.put(new Products(2,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),5.5,5.5));
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),4.5,4.5));
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),4.0,4.0));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),1.0,1.0));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),9.0,9.0));


            }else if(ep.getEntity().getName().equals("C3")){
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),2.5,2.5));

            }else if(ep.getEntity().getName().equals("C8")){
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),2.5,2.5));

            }else if(ep.getEntity().getName().equals("C9")){
                map.put(new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),7.0,7.0));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),3.0,3.0));

            }else if(ep.getEntity().getName().equals("E2")){
                map.put(new Products(2,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),6.0,6.0));
                map.put(new Products(7,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),7.5,7.5));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),2.5,2.5));

            }else if(ep.getEntity().getName().equals("E4")){
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),1.0,1.0));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),1.0,1.0));

            }else if(ep.getEntity().getName().equals("E5")){
                map.put(new Products(2,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),1.5,1.5));

            }
            expected.add(expedition);

        }


        Assert.assertEquals(expected.size(),actual.size());
        Assert.assertEquals(expected.toString(),actual.toString());
    }

    @Test
    void expeditionFullSmallFile() {

        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(smallFile);

        AVL<EntityPlus> actualEntityPlusAVL = smallFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().clone();
        AVL<ProducerPlus> actualProducerPlusAVL = smallFile.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers().clone();

        Iterable<EntityPlus> actualEntityPlusIterable =actualEntityPlusAVL.inOrder();
        Iterable<ProducerPlus> actualProducerPlusIterable =actualProducerPlusAVL.inOrder();

        dispatchListWithoutRestriction.expeditionFull(actualEntityPlusIterable,actualProducerPlusIterable,null,null);

        Iterable<Products> actual1 = actualEntityPlusAVL.find(new EntityPlus(new Client("","C1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual2 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E2",null))).getProductsAVL().inOrder();
        Iterable<Products> actual3 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E3",null))).getProductsAVL().inOrder();

        Iterable<Products> actual4 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual5 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P2",null))).getProductsAVL().inOrder();
        Iterable<Products> actual6 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P3",null))).getProductsAVL().inOrder();


        List<Products> expected1 = new ArrayList<>();
        expected1.add(new Products(5,0.0));
        expected1.add(new Products(6,0.0));
        expected1.add(new Products(11,0.0));
        List<Products> expected2 = new ArrayList<>();
        expected2.add(new Products(1,9.0));
        expected2.add(new Products(2,0.0));
        expected2.add(new Products(3,9.0));
        expected2.add(new Products(5,6.0));
        expected2.add(new Products(6,8.5));
        expected2.add(new Products(7,0.0));
        expected2.add(new Products(9,0.0));
        expected2.add(new Products(10,4.5));
        expected2.add(new Products(11,3.0));
        List<Products> expected3 = new ArrayList<>();
        expected3.add(new Products(6,10.0));
        expected3.add(new Products(9,5.0));
        expected3.add(new Products(11,5.5));


        List<Products> expected4 = new ArrayList<>();
        expected4.add(new Products(2,0.5));
        expected4.add(new Products(3,4.5));
        expected4.add(new Products(4,2.0));
        expected4.add(new Products(5,0.0));
        expected4.add(new Products(7,1.0));
        expected4.add(new Products(8,3.0));
        expected4.add(new Products(9,0.0));
        expected4.add(new Products(10,0.0));
        expected4.add(new Products(11,1.0));

        List<Products> expected5 = new ArrayList<>();
        expected5.add(new Products(1,0.5));
        expected5.add(new Products(2,0.5));
        expected5.add(new Products(3,1.5));
        expected5.add(new Products(4,7.0));
        expected5.add(new Products(5,0.0));
        expected5.add(new Products(6,0.5));
        expected5.add(new Products(7,4.5));
        expected5.add(new Products(8,3.5));
        expected5.add(new Products(9,1.0));

        List<Products> expected6 = new ArrayList<>();
        expected6.add(new Products(1,2.5));
        expected6.add(new Products(2,2.0));
        expected6.add(new Products(5,1.5));
        expected6.add(new Products(6,3.0));
        expected6.add(new Products(7,9.0));
        expected6.add(new Products(8,6.0));
        expected6.add(new Products(9,0.5));
        expected6.add(new Products(10,3.0));
        expected6.add(new Products(11,0.5));
        expected6.add(new Products(12,2.5));

        assertEquals(actual1.toString(),expected1.toString());
        assertEquals(actual2.toString(),expected2.toString());
        assertEquals(actual3.toString(),expected3.toString());
        assertEquals(actual4.toString(),expected4.toString());
        assertEquals(actual5.toString(),expected5.toString());
        assertEquals(actual6.toString(),expected6.toString());

    }

    @Test
    void expeditionListRemainProductsSmallFile() {
        List<Expedition> actual = new ArrayList<>();
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(smallFile);

        Iterable<EntityPlus> entityPlusIterable = smallFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().inOrder();
        Iterable<ProducerPlus> producerPlusIterable = smallFile.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers().inOrder();
        dispatchListWithoutRestriction.expeditionFull(entityPlusIterable,producerPlusIterable,null,null);
        dispatchListWithoutRestriction.expeditionListRemainProducts(actual,entityPlusIterable,producerPlusIterable,null,null);

        List<Expedition> expected = new ArrayList<>();

        for (EntityPlus ep: entityPlusIterable){
            Map<Products,ExpeditionInfo> map = new LinkedHashMap<>();
            Expedition expedition = new Expedition(ep,map);

            if(ep.getEntity().getName().equals("C2")){
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),10.0,1.0));

            }else if(ep.getEntity().getName().equals("C3")){
                map.put(new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),10.0,0.5));
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),9.0,1.5));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),4.5,1.0));

            }else if(ep.getEntity().getName().equals("C6")){
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),8.5,4.5));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),9.0,3.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),9.5,0.5));


            }else if(ep.getEntity().getName().equals("C8")){
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),7.5,0.5));
                map.put(new Products(12,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),6.0,2.5));

            }else if(ep.getEntity().getName().equals("E2")){
                map.put(new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),9.0,2.5));
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),9.0,1.5));
                map.put(new Products(5,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),6.0,0.0));
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P2",null),8.5,0.5));
                map.put(new Products(10,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),4.5,0.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),3.0,0.0));


            }else if(ep.getEntity().getName().equals("E3")){
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","P3",null),10.0,3.0));
                map.put(new Products(9,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),5.0,0.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),5.5,0.0));


            }else if(ep.getEntity().getName().equals("E4")){
                map.put(new Products(1,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),6.5,0.0));
                map.put(new Products(3,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),6.5,0.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),5.5,0.0));


            }else if(ep.getEntity().getName().equals("E5")){
                map.put(new Products(6,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),8.0,0.0));
                map.put(new Products(7,0.0),
                        new ExpeditionInfo(new Producer("","P1",null),9.5,1.0));
                map.put(new Products(11,0.0),
                        new ExpeditionInfo(new Producer("","NULL",null),6.0,0.0));


            }
            expected.add(expedition);
        }

        Assert.assertEquals(expected.size(),actual.size());
        Assert.assertEquals(expected.toString(),actual.toString());
    }

    @Test
    void expeditionRemainSmallFile() {
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(smallFile);

        AVL<EntityPlus> actualEntityPlusAVL = smallFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().clone();
        AVL<ProducerPlus> actualProducerPlusAVL = smallFile.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers().clone();

        Iterable<EntityPlus> actualEntityPlusIterable =actualEntityPlusAVL.inOrder();
        Iterable<ProducerPlus> actualProducerPlusIterable =actualProducerPlusAVL.inOrder();

        dispatchListWithoutRestriction.expeditionFull(actualEntityPlusIterable,actualProducerPlusIterable,null,null);
        dispatchListWithoutRestriction.expeditionRemain(actualEntityPlusIterable,actualProducerPlusIterable,null,null);

        Iterable<Products> actual1 = actualEntityPlusAVL.find(new EntityPlus(new Client("","C1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual2 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E2",null))).getProductsAVL().inOrder();
        Iterable<Products> actual3 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E3",null))).getProductsAVL().inOrder();

        Iterable<Products> actual4 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual5 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P2",null))).getProductsAVL().inOrder();
        Iterable<Products> actual6 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P3",null))).getProductsAVL().inOrder();

        List<Products> expected1 = new ArrayList<>();
        expected1.add(new Products(5,0.0));
        expected1.add(new Products(6,0.0));
        expected1.add(new Products(11,0.0));
        List<Products> expected2 = new ArrayList<>();
        expected2.add(new Products(1,6.5));
        expected2.add(new Products(2,0.0));
        expected2.add(new Products(3,7.5));
        expected2.add(new Products(5,6.0));
        expected2.add(new Products(6,8.0));
        expected2.add(new Products(7,0.0));
        expected2.add(new Products(9,0.0));
        expected2.add(new Products(10,4.5));
        expected2.add(new Products(11,3.0));
        List<Products> expected3 = new ArrayList<>();
        expected3.add(new Products(6,7.0));
        expected3.add(new Products(9,5.0));
        expected3.add(new Products(11,5.5));


        List<Products> expected4 = new ArrayList<>();
        expected4.add(new Products(2,0.5));
        expected4.add(new Products(3,0.0));
        expected4.add(new Products(4,2.0));
        expected4.add(new Products(5,0.0));
        expected4.add(new Products(7,0.0));
        expected4.add(new Products(8,3.0));
        expected4.add(new Products(9,0.0));
        expected4.add(new Products(10,0.0));
        expected4.add(new Products(11,0.0));

        List<Products> expected5 = new ArrayList<>();
        expected5.add(new Products(1,0.0));
        expected5.add(new Products(2,0.5));
        expected5.add(new Products(3,0.0));
        expected5.add(new Products(4,7.0));
        expected5.add(new Products(5,0.0));
        expected5.add(new Products(6,0.0));
        expected5.add(new Products(7,4.5));
        expected5.add(new Products(8,3.5));
        expected5.add(new Products(9,0.0));

        List<Products> expected6 = new ArrayList<>();
        expected6.add(new Products(1,0.0));
        expected6.add(new Products(2,2.0));
        expected6.add(new Products(5,0.0));
        expected6.add(new Products(6,0.0));
        expected6.add(new Products(7,9.0));
        expected6.add(new Products(8,6.0));
        expected6.add(new Products(9,0.0));
        expected6.add(new Products(10,0.0));
        expected6.add(new Products(11,0.0));
        expected6.add(new Products(12,0.0));

        assertEquals(actual1.toString(),expected1.toString());
        assertEquals(actual2.toString(),expected2.toString());
        assertEquals(actual3.toString(),expected3.toString());
        assertEquals(actual4.toString(),expected4.toString());
        assertEquals(actual5.toString(),expected5.toString());
        assertEquals(actual6.toString(),expected6.toString());

    }

    //Big
    @Test
    void dispatchListWithoutRestrictionBigFile() throws FileNotFoundException {
        List<Expedition> actual;
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(bigFile);
        actual = dispatchListWithoutRestriction.dispatchListWithoutRestriction(5);

        Iterable<EntityPlus> entityPlusIterable = bigFile.getOrderedHumpers().clone().find(new DayClient(5)).getClients().inOrder();

        Expedition expedition1 = null;
        Expedition expedition2 = null;
        Expedition expedition3 = null;
        Expedition expedition4 = null;
        Expedition expedition5 = null;
        Expedition expedition6 = null;
        Expedition expedition7 = null;
        Expedition expedition8 = null;
        Expedition expedition9 = null;
        Expedition expedition10 = null;

        for (EntityPlus ep: entityPlusIterable) {

            if (ep.getEntity().getName().equals("C10")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition1 = new Expedition(ep, map);
                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "P49", null), 8.0, 8.0));
                map.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "P50", null), 8.0, 8.0));

            }else if (ep.getEntity().getName().equals("C110")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition2 = new Expedition(ep, map);
                map.put(new Products(4, 0.0),
                        new ExpeditionInfo(new Producer("", "P35", null), 8.5, 8.5));
                map.put(new Products(10, 0.0),
                        new ExpeditionInfo(new Producer("", "P17", null), 3.0, 3.0));

            }else if (ep.getEntity().getName().equals("C121")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition3 = new Expedition(ep, map);

                map.put(new Products(1, 0.0),
                        new ExpeditionInfo(new Producer("", "P38", null), 9.5, 9.5));
                map.put(new Products(3, 0.0),
                        new ExpeditionInfo(new Producer("", "P53", null), 7.5, 7.5));
                map.put(new Products(4, 0.0),
                        new ExpeditionInfo(new Producer("", "P44", null), 3.0, 3.0));
                map.put(new Products(5, 0.0),
                        new ExpeditionInfo(new Producer("", "P53", null), 5.5, 5.5));
                map.put(new Products(7, 0.0),
                        new ExpeditionInfo(new Producer("", "P25", null), 7.0, 7.0));
                map.put(new Products(14, 0.0),
                        new ExpeditionInfo(new Producer("", "P38", null), 5.5, 5.5));
                map.put(new Products(19, 0.0),
                        new ExpeditionInfo(new Producer("", "P1", null), 3.0, 3.0));
                map.put(new Products(20, 0.0),
                        new ExpeditionInfo(new Producer("", "P48", null), 5.0, 5.0));


            }else if (ep.getEntity().getName().equals("C13")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition4 = new Expedition(ep, map);

                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "P36", null), 3.0, 3.0));
                map.put(new Products(12, 0.0),
                        new ExpeditionInfo(new Producer("", "P24", null), 2.0, 2.0));
                map.put(new Products(18, 0.0),
                        new ExpeditionInfo(new Producer("", "P1", null), 2.0, 2.0));



            }else if (ep.getEntity().getName().equals("C104")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition5 = new Expedition(ep, map);

                map.put(new Products(4, 0.0),
                        new ExpeditionInfo(new Producer("", "P24", null), 6.5, 6.5));
                map.put(new Products(15, 0.0),
                        new ExpeditionInfo(new Producer("", "P36", null), 7.0, 7.0));
                map.put(new Products(18, 0.0),
                        new ExpeditionInfo(new Producer("", "P34", null), 7.5, 7.5));


            }

        }

        for (EntityPlus ep: entityPlusIterable) {

            if (ep.getEntity().getName().equals("E59")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition6 = new Expedition(ep, map);
                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 1.5, 0.0));
                map.put(new Products(4, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 2.5, 0.0));
                map.put(new Products(18, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 7.0, 0.0));
                map.put(new Products(20, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 10.0, 0.0));

            }else if (ep.getEntity().getName().equals("E65")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition7 = new Expedition(ep, map);
                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 1.5, 0.0));
                map.put(new Products(8, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 1.5, 0.0));

            }else if (ep.getEntity().getName().equals("E72")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition8 = new Expedition(ep, map);
                map.put(new Products(1, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 6.5, 0.0));
                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 9.5, 0.0));
                map.put(new Products(3, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 2.5, 0.0));
                map.put(new Products(5, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 9.0, 0.0));
                map.put(new Products(6, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 9.0, 0.0));
                map.put(new Products(8, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 3.0, 0.0));
                map.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 7.0, 0.0));
                map.put(new Products(20, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 4.0, 0.0));


            }else if (ep.getEntity().getName().equals("E79")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition9 = new Expedition(ep, map);
                map.put(new Products(1, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 1.5, 0.0));
                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.5, 0.0));
                map.put(new Products(7, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 4.0, 0.0));
                map.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.0, 0.0));
                map.put(new Products(12, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.0, 0.0));
                map.put(new Products(13, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.0, 0.0));
                map.put(new Products(18, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 7.0, 0.0));
                map.put(new Products(19, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 4.5, 0.0));


            }else if (ep.getEntity().getName().equals("E85")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition10 = new Expedition(ep, map);
                map.put(new Products(6, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.5, 0.0));
                map.put(new Products(19, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 2.5, 0.0));


            }

        }


        Assert.assertEquals(actual.get(1).toString(),expedition1.toString());
        Assert.assertEquals(actual.get(5).toString(),expedition5.toString());
        Assert.assertEquals(actual.get(10).toString(),expedition2.toString());
        Assert.assertEquals(actual.get(15).toString(),expedition3.toString());
        Assert.assertEquals(actual.get(20).toString(),expedition4.toString());
        Assert.assertEquals(actual.get(350).toString(),expedition6.toString());
        Assert.assertEquals(actual.get(355).toString(),expedition7.toString());
        Assert.assertEquals(actual.get(360).toString(),expedition8.toString());
        Assert.assertEquals(actual.get(365).toString(),expedition9.toString());
        Assert.assertEquals(actual.get(370).toString(),expedition10.toString());
        dispatchListWithoutRestriction.printExpeditionList(actual);
    }
    @Test
    void sobrasDiaBigFile() {
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(bigFile);
        dispatchListWithoutRestriction.dispatchListWithoutRestriction(1);
        AVL<ProducerPlus> avl= dispatchListWithoutRestriction.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers();
        List<String> actual = dispatchListWithoutRestriction.sobrasDia(avl);
        List<String> expected = new ArrayList<>();

        Assert.assertEquals(expected,actual);
    }

    @Test
    void expeditionListFullProductsBigFile() {

        List<Expedition> actual = new ArrayList<>();
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(bigFile);

        Iterable<EntityPlus> entityPlusIterable = bigFile.getOrderedHumpers().clone().find(new DayClient(5)).getClients().inOrder();
        Iterable<ProducerPlus> producerPlusIterable = bigFile.getProvidedHumpers().clone().find(new DayProducer(5)).getProducers().inOrder();

        dispatchListWithoutRestriction.expeditionListFullProducts(actual,entityPlusIterable,producerPlusIterable,null,null);


        Expedition expedition1 = null;
        Expedition expedition2 = null;
        Expedition expedition3 = null;
        Expedition expedition4 = null;
        Expedition expedition5 = null;
        for (EntityPlus ep: entityPlusIterable) {

            if (ep.getEntity().getName().equals("C10")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition1 = new Expedition(ep, map);
                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "P49", null), 8.0, 8.0));
                map.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "P50", null), 8.0, 8.0));

            }else if (ep.getEntity().getName().equals("C110")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition2 = new Expedition(ep, map);
                map.put(new Products(4, 0.0),
                        new ExpeditionInfo(new Producer("", "P35", null), 8.5, 8.5));
                map.put(new Products(10, 0.0),
                        new ExpeditionInfo(new Producer("", "P17", null), 3.0, 3.0));

            }else if (ep.getEntity().getName().equals("C121")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition3 = new Expedition(ep, map);

                map.put(new Products(1, 0.0),
                        new ExpeditionInfo(new Producer("", "P38", null), 9.5, 9.5));
                map.put(new Products(3, 0.0),
                        new ExpeditionInfo(new Producer("", "P53", null), 7.5, 7.5));
                map.put(new Products(4, 0.0),
                        new ExpeditionInfo(new Producer("", "P44", null), 3.0, 3.0));
                map.put(new Products(5, 0.0),
                        new ExpeditionInfo(new Producer("", "P53", null), 5.5, 5.5));
                map.put(new Products(7, 0.0),
                        new ExpeditionInfo(new Producer("", "P25", null), 7.0, 7.0));
                map.put(new Products(14, 0.0),
                        new ExpeditionInfo(new Producer("", "P38", null), 5.5, 5.5));
                map.put(new Products(19, 0.0),
                        new ExpeditionInfo(new Producer("", "P1", null), 3.0, 3.0));
                map.put(new Products(20, 0.0),
                        new ExpeditionInfo(new Producer("", "P48", null), 5.0, 5.0));


            }else if (ep.getEntity().getName().equals("C13")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition4 = new Expedition(ep, map);

                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "P36", null), 3.0, 3.0));
                map.put(new Products(12, 0.0),
                        new ExpeditionInfo(new Producer("", "P24", null), 2.0, 2.0));
                map.put(new Products(18, 0.0),
                        new ExpeditionInfo(new Producer("", "P1", null), 2.0, 2.0));



            }else if (ep.getEntity().getName().equals("C104")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition5 = new Expedition(ep, map);

                map.put(new Products(4, 0.0),
                        new ExpeditionInfo(new Producer("", "P24", null), 6.5, 6.5));
                map.put(new Products(15, 0.0),
                        new ExpeditionInfo(new Producer("", "P36", null), 7.0, 7.0));
                map.put(new Products(18, 0.0),
                        new ExpeditionInfo(new Producer("", "P34", null), 7.5, 7.5));


            }

        }

        Assert.assertEquals(actual.get(1).toString(),expedition1.toString());
        Assert.assertEquals(actual.get(5).toString(),expedition5.toString());
        Assert.assertEquals(actual.get(10).toString(),expedition2.toString());
        Assert.assertEquals(actual.get(15).toString(),expedition3.toString());
        Assert.assertEquals(actual.get(20).toString(),expedition4.toString());
    }

    @Test
    void expeditionFullBigFile() {

        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(bigFile);

        AVL<EntityPlus> actualEntityPlusAVL = bigFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().clone();
        AVL<ProducerPlus> actualProducerPlusAVL = bigFile.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers().clone();

        Iterable<EntityPlus> actualEntityPlusIterable =actualEntityPlusAVL.inOrder();
        Iterable<ProducerPlus> actualProducerPlusIterable =actualProducerPlusAVL.inOrder();

        dispatchListWithoutRestriction.expeditionFull(actualEntityPlusIterable,actualProducerPlusIterable,null,null);

        Iterable<Products> actual1 = actualEntityPlusAVL.find(new EntityPlus(new Client("","C1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual2 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E2",null))).getProductsAVL().inOrder();
        Iterable<Products> actual3 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E3",null))).getProductsAVL().inOrder();

        Iterable<Products> actual4 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual5 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P4",null))).getProductsAVL().inOrder();
        Iterable<Products> actual6 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P3",null))).getProductsAVL().inOrder();


        List<Products> expected1 = new ArrayList<>();
        expected1.add(new Products(3,0.0));
        expected1.add(new Products(5,0.0));
        expected1.add(new Products(8,0.0));
        expected1.add(new Products(12,0.0));
        expected1.add(new Products(14,0.0));
        expected1.add(new Products(16,0.0));
        expected1.add(new Products(18,0.0));
        List<Products> expected2 = new ArrayList<>();
        expected2.add(new Products(2,8.0));
        expected2.add(new Products(15,7.0));
        expected2.add(new Products(17,5.0));
        expected2.add(new Products(20,4.5));
        List<Products> expected3 = new ArrayList<>();
        expected3.add(new Products(3,9.5));
        expected3.add(new Products(8,5.5));
        expected3.add(new Products(11,3.0));
        expected3.add(new Products(19,8.5));
        expected3.add(new Products(20,9.0));


        List<Products> expected4 = new ArrayList<>();
        expected4.add(new Products(2,0.0));
        expected4.add(new Products(16,0.5));
        expected4.add(new Products(19,0.5));


        List<Products> expected5 = new ArrayList<>();
        expected5.add(new Products(14,0.5));
        expected5.add(new Products(17,0.5));


        List<Products> expected6 = new ArrayList<>();
        expected6.add(new Products(2,0.5));
        expected6.add(new Products(8,1.0));
        expected6.add(new Products(9,1.0));
        expected6.add(new Products(15,0.0));
        expected6.add(new Products(17,0.0));


        assertEquals(actual1.toString(),expected1.toString());
        assertEquals(actual2.toString(),expected2.toString());
        assertEquals(actual3.toString(),expected3.toString());
        assertEquals(actual4.toString(),expected4.toString());
        assertEquals(actual5.toString(),expected5.toString());
        assertEquals(actual6.toString(),expected6.toString());

    }

    @Test
    void expeditionListRemainProductsBigFile() {
        List<Expedition> actual = new ArrayList<>();
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(bigFile);

        Iterable<EntityPlus> entityPlusIterable = bigFile.getOrderedHumpers().clone().find(new DayClient(5)).getClients().inOrder();
        Iterable<ProducerPlus> producerPlusIterable = bigFile.getProvidedHumpers().clone().find(new DayProducer(5)).getProducers().inOrder();
        dispatchListWithoutRestriction.expeditionFull(entityPlusIterable,producerPlusIterable,null,null);
        dispatchListWithoutRestriction.expeditionListRemainProducts(actual,entityPlusIterable,producerPlusIterable,null,null);

        Expedition expedition6 = null;
        Expedition expedition7 = null;
        Expedition expedition8 = null;
        Expedition expedition9 = null;
        Expedition expedition10 = null;

        for (EntityPlus ep: entityPlusIterable) {

            if (ep.getEntity().getName().equals("E59")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition6 = new Expedition(ep, map);
                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 1.5, 0.0));
                map.put(new Products(4, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 2.5, 0.0));
                map.put(new Products(18, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 7.0, 0.0));
                map.put(new Products(20, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 10.0, 0.0));

            }else if (ep.getEntity().getName().equals("E65")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition7 = new Expedition(ep, map);
                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 1.5, 0.0));
                map.put(new Products(8, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 1.5, 0.0));

            }else if (ep.getEntity().getName().equals("E72")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition8 = new Expedition(ep, map);
                map.put(new Products(1, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 6.5, 0.0));
                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 9.5, 0.0));
                map.put(new Products(3, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 2.5, 0.0));
                map.put(new Products(5, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 9.0, 0.0));
                map.put(new Products(6, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 9.0, 0.0));
                map.put(new Products(8, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 3.0, 0.0));
                map.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 7.0, 0.0));
                map.put(new Products(20, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 4.0, 0.0));


            }else if (ep.getEntity().getName().equals("E79")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition9 = new Expedition(ep, map);
                map.put(new Products(1, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 1.5, 0.0));
                map.put(new Products(2, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.5, 0.0));
                map.put(new Products(7, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 4.0, 0.0));
                map.put(new Products(11, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.0, 0.0));
                map.put(new Products(12, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.0, 0.0));
                map.put(new Products(13, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.0, 0.0));
                map.put(new Products(18, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 7.0, 0.0));
                map.put(new Products(19, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 4.5, 0.0));


            }else if (ep.getEntity().getName().equals("E85")) {
                Map<Products, ExpeditionInfo> map = new LinkedHashMap<>();
                expedition10 = new Expedition(ep, map);
                map.put(new Products(6, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 8.5, 0.0));
                map.put(new Products(19, 0.0),
                        new ExpeditionInfo(new Producer("", "NULL", null), 2.5, 0.0));


            }

        }


        Assert.assertEquals(actual.get(163).toString(),expedition6.toString());
        Assert.assertEquals(actual.get(168).toString(),expedition7.toString());
        Assert.assertEquals(actual.get(173).toString(),expedition8.toString());
        Assert.assertEquals(actual.get(178).toString(),expedition9.toString());
        Assert.assertEquals(actual.get(183).toString(),expedition10.toString());

    }

    @Test
    void expeditionRemainBigFile() {
        DispatchListWithoutRestriction dispatchListWithoutRestriction = new DispatchListWithoutRestriction(bigFile);

        AVL<EntityPlus> actualEntityPlusAVL = bigFile.getOrderedHumpers().clone().find(new DayClient(1)).getClients().clone();
        AVL<ProducerPlus> actualProducerPlusAVL = bigFile.getProvidedHumpers().clone().find(new DayProducer(1)).getProducers().clone();

        Iterable<EntityPlus> actualEntityPlusIterable =actualEntityPlusAVL.inOrder();
        Iterable<ProducerPlus> actualProducerPlusIterable =actualProducerPlusAVL.inOrder();

        dispatchListWithoutRestriction.expeditionFull(actualEntityPlusIterable,actualProducerPlusIterable,null,null);
        dispatchListWithoutRestriction.expeditionRemain(actualEntityPlusIterable,actualProducerPlusIterable,null,null);

        Iterable<Products> actual1 = actualEntityPlusAVL.find(new EntityPlus(new Client("","C1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual2 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E2",null))).getProductsAVL().inOrder();
        Iterable<Products> actual3 = actualEntityPlusAVL.find(new EntityPlus(new Client("","E3",null))).getProductsAVL().inOrder();

        Iterable<Products> actual4 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P1",null))).getProductsAVL().inOrder();
        Iterable<Products> actual5 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P4",null))).getProductsAVL().inOrder();
        Iterable<Products> actual6 = actualProducerPlusAVL.find(new ProducerPlus(new Producer("","P3",null))).getProductsAVL().inOrder();

        List<Products> expected1 = new ArrayList<>();
        expected1.add(new Products(3,0.0));
        expected1.add(new Products(5,0.0));
        expected1.add(new Products(8,0.0));
        expected1.add(new Products(12,0.0));
        expected1.add(new Products(14,0.0));
        expected1.add(new Products(16,0.0));
        expected1.add(new Products(18,0.0));
        List<Products> expected2 = new ArrayList<>();
        expected2.add(new Products(2,8.0));
        expected2.add(new Products(15,7.0));
        expected2.add(new Products(17,5.0));
        expected2.add(new Products(20,4.5));
        List<Products> expected3 = new ArrayList<>();
        expected3.add(new Products(3,9.5));
        expected3.add(new Products(8,5.5));
        expected3.add(new Products(11,3.0));
        expected3.add(new Products(19,8.5));
        expected3.add(new Products(20,9.0));


        List<Products> expected4 = new ArrayList<>();
        expected4.add(new Products(2,0.0));
        expected4.add(new Products(16,0.0));
        expected4.add(new Products(19,0.0));


        List<Products> expected5 = new ArrayList<>();
        expected5.add(new Products(14,0.0));
        expected5.add(new Products(17,0.0));


        List<Products> expected6 = new ArrayList<>();
        expected6.add(new Products(2,0.0));
        expected6.add(new Products(8,0.0));
        expected6.add(new Products(9,0.0));
        expected6.add(new Products(15,0.0));
        expected6.add(new Products(17,0.0));

        assertEquals(actual1.toString(),expected1.toString());
        assertEquals(actual2.toString(),expected2.toString());
        assertEquals(actual3.toString(),expected3.toString());
        assertEquals(actual4.toString(),expected4.toString());
        assertEquals(actual5.toString(),expected5.toString());
        assertEquals(actual6.toString(),expected6.toString());

    }

}