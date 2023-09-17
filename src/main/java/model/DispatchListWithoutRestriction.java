package model;

import data.DataIO;
import trees.AVL;

import java.util.*;

/**
 * @author : Pedro Pereira - 1211131
 **/

public class DispatchListWithoutRestriction {

    /*
      Estruturas das AVLs
       AVL<DayClient>
        DayClient(int day, AVL<EntityPlus>)
        EntityPlus(Entity entity, AVL<Products>)
        Products(int product, double quantity)

        A primeira AVL vai conter os dias e a esses dias vão estar associadas AVLs de clientes(clientes/empresas) (só vão estar
        presentes as que realmente tiverem encomendas para esse dia) a segunda tem a entidade com uma AVL de
        produtos que vai ter um produto e uma quantidade associada (tal como em cima, apenas os produtos que tenham > 0
        de quantidade é que vão estar presentes).

       AVL<DayProducer>
        DayProducer(int day, AVL<ProducerPlus>)
        ProducerPlus(Producer producer, AVL<Products>)
        Products(int product, double quantity)

        A estrutura é igual à de cima, muda apenas no atributo de DayClient e DayProducer.

        Uma tem as encomendas dos clientes/empresas e a outra, os produtos fornecidos pelos produtores.
     */

    private DataIO dataIO;
    AVL<DayClient> orderedHumpers;
    AVL<DayProducer> providedHumpers;
    public DispatchListWithoutRestriction(DataIO dataIO){
        this.dataIO = dataIO;
        this.orderedHumpers = dataIO.getOrderedHumpers();
        this.providedHumpers = dataIO.getProvidedHumpers();
    }

    /**
     * Generates a list of expeditions for a given day, without restrictions.
     *
     * @param day the number of days for which the expeditions should be generated
     * @return a list of expeditions for the given day
     */
    public List<Expedition> dispatchListWithoutRestriction(Integer day){
        List<Expedition> expeditionList = new ArrayList<>();

        AVL<EntityPlus> actualDayEntityPlusAVL;
        AVL<ProducerPlus> actualDayProducerPlusAVL ;
        Iterable<EntityPlus> actualDayEntityPlusIterable;
        Iterable<ProducerPlus> actualDayProducerPlusIterable;
        AVL<ProducerPlus> day1 = null;
        AVL<ProducerPlus> day2 = null;

        for (int i = 1; i <= day; i++) {
            actualDayEntityPlusAVL = orderedHumpers.clone().find(new DayClient(i)).getClients();
            actualDayProducerPlusAVL = providedHumpers.clone().find(new DayProducer(i)).getProducers();
            actualDayEntityPlusIterable = actualDayEntityPlusAVL.inOrder();
            actualDayProducerPlusIterable = actualDayProducerPlusAVL.inOrder();

            if(day==i){

                makeExpeditionListFullProducts(expeditionList, actualDayEntityPlusIterable, actualDayProducerPlusIterable,day1,day2);
                makeExpeditionListRemainProducts(expeditionList, actualDayEntityPlusIterable, actualDayProducerPlusIterable,day1,day2);


            }else{

                makeExpeditionFull(actualDayEntityPlusIterable, actualDayProducerPlusIterable,day1,day2);
                makeExpeditionRemain(actualDayEntityPlusIterable, actualDayProducerPlusIterable,day1,day2);
                day2=day1;
                day1=actualDayProducerPlusAVL;

            }
        }

        return expeditionList;
    }


    /**
     * Helper method that generates a list of expeditions for clients who have placed orders of products
     * that are available in full from one or more producers, for the current day.
     *
     * @param expeditionList the list of expeditions to be updated
     * @param entityPlusIterable an iterable of EntityPlus objects representing clients
     * @param producerPlusIterable an iterable of ProducerPlus objects representing producers
     * @param day1 an AVL tree of ProducerPlus objects representing the producers available on the previous day
     * @param day2 an AVL tree of ProducerPlus objects representing the producers available on the day before the previous day
     */
    private static void makeExpeditionListFullProducts(List<Expedition> expeditionList, Iterable<EntityPlus> entityPlusIterable,
                                                       Iterable<ProducerPlus> producerPlusIterable,AVL<ProducerPlus> day1,AVL<ProducerPlus> day2) {
        for (EntityPlus ep: entityPlusIterable){
            Iterable<Products> clientProducts = ep.getProductsAVL().inOrder();
            Expedition expedition = new Expedition(ep,new LinkedHashMap<>());
            for (Products cp: clientProducts){
                for (ProducerPlus pp: producerPlusIterable){

                    Products product = pp.getProductsAVL().find(new Products(cp.getProduct(),0.0));
                    Products products1 = null;
                    Products products2 = null;
                    if(day1 != null && day1.find(pp) != null && day1.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0)) != null) {
                         products1 = day1.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0));
                    }
                    if(day2 != null && day2.find(pp) != null && day2.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0)) != null) {
                         products2 = day2.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0));
                    }

                    if(product!=null && (products1==null) && (products2==null) && (product.getQuantity()>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){
                        double initQuantity = product.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = initQuantity-quantity;
                        product.setQuantity(newQuantity);
                        cp.setQuantity(0.0);
                        expedition.getMap().put(product,new ExpeditionInfo(pp.getProducer(),quantity,quantity));
                        break;
                    }
                    if(product!=null && (products1!=null) && (products2==null) && ((product.getQuantity() + products1.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){
                        double productQuantity = product.getQuantity();
                        double product1Quantity = products1.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = 0.0;

                        if(product1Quantity>=quantity){
                            newQuantity = product1Quantity-quantity;
                            products1.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                            expedition.getMap().put(product,new ExpeditionInfo(pp.getProducer(),quantity,quantity));
                        }else{
                            double quantitySave=quantity;
                            quantity = quantity-product1Quantity;
                            products1.setQuantity(0.0);
                            newQuantity = productQuantity-quantity;
                            product.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                            expedition.getMap().put(product,new ExpeditionInfo(pp.getProducer(),quantitySave,quantitySave));
                        }
                        break;
                    }
                    if(product!=null && (products1==null) && (products2!=null) && ((product.getQuantity() + products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){
                        double productQuantity = product.getQuantity();
                        double product2Quantity = products2.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = 0.0;

                        if(product2Quantity>=quantity){
                            newQuantity = product2Quantity-quantity;
                            products2.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                            expedition.getMap().put(product,new ExpeditionInfo(pp.getProducer(),quantity,quantity));
                        }else{
                            double quantitySave=quantity;
                            quantity = quantity-product2Quantity;
                            products2.setQuantity(0.0);
                            newQuantity = productQuantity-quantity;
                            product.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                            expedition.getMap().put(product,new ExpeditionInfo(pp.getProducer(),quantitySave,quantitySave));
                        }
                        break;
                    }
                    if(product!=null && (products1!=null) && (products2!=null) && ((product.getQuantity() + products1.getQuantity() + products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){

                        double productQuantity = product.getQuantity();
                        double product1Quantity = products1.getQuantity();
                        double product2Quantity = products2.getQuantity();

                        double quantity = cp.getQuantity();
                        double newQuantity = 0.0;
                        double quantitySave=quantity;

                        if(product2Quantity>=quantity) {
                            newQuantity = product2Quantity - quantity;
                            products2.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                            expedition.getMap().put(product, new ExpeditionInfo(pp.getProducer(), quantitySave, quantitySave));

                        }else if(product2Quantity+product1Quantity>=quantity){
                            quantity = quantity - product2Quantity;
                            products2.setQuantity(0.0);
                            newQuantity = product1Quantity-quantity;
                            products1.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                            expedition.getMap().put(product,new ExpeditionInfo(pp.getProducer(),quantitySave,quantitySave));
                        }else{
                            quantity = quantity-product2Quantity;
                            products2.setQuantity(0.0);
                            quantity = quantity-product1Quantity;
                            products1.setQuantity(0.0);
                            newQuantity = productQuantity-quantity;
                            product.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                            expedition.getMap().put(product,new ExpeditionInfo(pp.getProducer(),quantitySave,quantitySave));
                        }
                        break;
                    }
                    if(product==null && (products1!=null) && (products2==null) && (( products1.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){
                        double initQuantity = products1.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = initQuantity-quantity;
                        products1.setQuantity(newQuantity);
                        cp.setQuantity(0.0);
                        expedition.getMap().put(products1,new ExpeditionInfo(pp.getProducer(),quantity,quantity));
                        break;
                    }
                    if(product==null && (products1==null) && (products2!=null) && ((products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0)){
                        double initQuantity = products2.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = initQuantity-quantity;
                        products2.setQuantity(newQuantity);
                        cp.setQuantity(0.0);
                        expedition.getMap().put(products2,new ExpeditionInfo(pp.getProducer(),quantity,quantity));
                        break;
                    }
                    if(product==null && (products1!=null) && (products2!=null) && (( products1.getQuantity() + products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){
                        double product1Quantity = products1.getQuantity();
                        double product2Quantity = products2.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = 0.0;

                        if(product2Quantity>=quantity){
                            newQuantity = product2Quantity-quantity;
                            products2.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                            expedition.getMap().put(products1,new ExpeditionInfo(pp.getProducer(),quantity,quantity));
                        }else{
                            double saveQuantity = quantity;
                            quantity = quantity-product2Quantity;
                            products2.setQuantity(0.0);
                            newQuantity = product1Quantity-quantity;
                            products1.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                            expedition.getMap().put(products1,new ExpeditionInfo(pp.getProducer(),saveQuantity,saveQuantity));
                        }
                        break;
                    }
                }

            }
            expeditionList.add(expedition);
        }
    }

    /**
     * Helper method that updates the available quantities of products for the current day, for clients
     * who have placed orders for products that are available in full from one or more producers.
     *
     * @param entityPlusIterable an iterable of EntityPlus objects representing clients
     * @param producerPlusIterable an iterable of ProducerPlus objects representing producers
     * @param day1 an AVL tree of ProducerPlus objects representing the producers available on the previous day
     * @param day2 an AVL tree of ProducerPlus objects representing the producers available on the day before the previous day
     */
    private static void makeExpeditionFull(Iterable<EntityPlus> entityPlusIterable,
                                                       Iterable<ProducerPlus> producerPlusIterable,AVL<ProducerPlus> day1,AVL<ProducerPlus> day2) {
        for (EntityPlus ep: entityPlusIterable){
            Iterable<Products> clientProducts = ep.getProductsAVL().inOrder();

            for (Products cp: clientProducts){
                for (ProducerPlus pp: producerPlusIterable){
                    Products product = pp.getProductsAVL().find(new Products(cp.getProduct(),0.0));
                    Products products1 = null;
                    Products products2 = null;

                    if(day1 != null && day1.find(pp) != null && day1.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0)) != null) {
                        products1 = day1.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0));
                    }
                    if(day2 != null && day2.find(pp) != null && day2.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0)) != null) {
                        products2 = day2.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0));
                    }

                    if(product!=null && (products1==null) && (products2==null) && (product.getQuantity()>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){
                        double initQuantity = product.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = initQuantity-quantity;
                        product.setQuantity(newQuantity);
                        cp.setQuantity(0.0);
                        break;
                    }
                    if(product!=null && (products1!=null) && (products2==null) && ((product.getQuantity() + products1.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){
                        double productQuantity = product.getQuantity();
                        double product1Quantity = products1.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = 0.0;

                        if(product1Quantity>=quantity){
                            newQuantity = product1Quantity-quantity;
                            products1.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                        }else{
                            quantity = quantity-product1Quantity;
                            products1.setQuantity(0.0);
                            newQuantity = productQuantity-quantity;
                            product.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                        }
                        break;
                    }
                    if(product!=null && (products1==null) && (products2!=null) && ((product.getQuantity() + products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){
                        double productQuantity = product.getQuantity();
                        double product2Quantity = products2.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = 0.0;

                        if(product2Quantity>=quantity){
                            newQuantity = product2Quantity-quantity;
                            products2.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                        }else{
                            quantity = quantity-product2Quantity;
                            products2.setQuantity(0.0);
                            newQuantity = productQuantity-quantity;
                            product.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                        }
                        break;
                    }
                    if(product!=null && (products1!=null) && (products2!=null) && ((product.getQuantity() + products1.getQuantity() + products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){

                        double productQuantity = product.getQuantity();
                        double product1Quantity = products1.getQuantity();
                        double product2Quantity = products2.getQuantity();

                        double quantity = cp.getQuantity();
                        double newQuantity = 0.0;

                        if(product2Quantity>=quantity) {
                            newQuantity = product2Quantity - quantity;
                            products2.setQuantity(newQuantity);
                            cp.setQuantity(0.0);


                        }else if(product2Quantity+product1Quantity>=quantity){
                            quantity = quantity - product2Quantity;
                            products2.setQuantity(0.0);
                            newQuantity = product1Quantity-quantity;
                            products1.setQuantity(newQuantity);
                            cp.setQuantity(0.0);

                        }else{
                            quantity = quantity-product2Quantity;
                            products2.setQuantity(0.0);
                            quantity = quantity-product1Quantity;
                            products1.setQuantity(0.0);
                            newQuantity = productQuantity-quantity;
                            product.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                        }
                        break;
                    }
                    if(product==null && (products1!=null) && (products2==null) && (( products1.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){
                        double initQuantity = products1.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = initQuantity-quantity;
                        products1.setQuantity(newQuantity);
                        cp.setQuantity(0.0);
                        break;
                    }
                    if(product==null && (products1==null) && (products2!=null) && ((products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0)){
                        double initQuantity = products2.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = initQuantity-quantity;
                        products2.setQuantity(newQuantity);
                        cp.setQuantity(0.0);
                        break;
                    }
                    if(product==null && (products1!=null) && (products2!=null) && (( products1.getQuantity() + products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0) ){
                        double product1Quantity = products1.getQuantity();
                        double product2Quantity = products2.getQuantity();
                        double quantity = cp.getQuantity();
                        double newQuantity = 0.0;

                        if(product2Quantity>=quantity){
                            newQuantity = product2Quantity-quantity;
                            products2.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                        }else{
                            quantity = quantity-product2Quantity;
                            products2.setQuantity(0.0);
                            newQuantity = product1Quantity-quantity;
                            products1.setQuantity(newQuantity);
                            cp.setQuantity(0.0);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * Helper method that generates a list of expeditions for clients who have placed orders for products
     * that are not available in full from any producer, for the current day.
     *
     * @param expeditionList the list of expeditions to be updated
     * @param entityPlusIterable an iterable of EntityPlus objects representing clients
     * @param producerPlusIterable an iterable of ProducerPlus objects representing producers
     * @param day1 an AVL tree of ProducerPlus objects representing the producers available on the previous day
     * @param day2 an AVL tree of ProducerPlus objects representing the producers available on the day before the previous day
     */
    private static void makeExpeditionListRemainProducts(List<Expedition> expeditionList, Iterable<EntityPlus> entityPlusIterable,
                                                         Iterable<ProducerPlus> producerPlusIterable,AVL<ProducerPlus> day1,AVL<ProducerPlus> day2) {
        for (EntityPlus ep: entityPlusIterable){
            Iterable<Products> clientProducts = ep.getProductsAVL().inOrder();
            Expedition expedition = new Expedition(ep,new LinkedHashMap<>());
            for (Products cp: clientProducts){
                double before = cp.getQuantity();
                if(before!=0.0){
                    for (ProducerPlus pp: producerPlusIterable) {
                        Products product = pp.getProductsAVL().find(new Products(cp.getProduct(), 0.0));
                        Products products1 = null;
                        Products products2 = null;

                        if(day1 != null && day1.find(pp) != null && day1.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0)) != null) {
                            products1 = day1.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0));
                        }
                        if(day2 != null && day2.find(pp) != null && day2.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0)) != null) {
                            products2 = day2.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0));
                        }

                        if (product != null && products1 ==null && products2==null && (product.getQuantity() > 0.0)) {
                            double initQuantity = product.getQuantity();
                            double quantity = cp.getQuantity();
                            double newQuantity = quantity-initQuantity;
                            cp.setQuantity(newQuantity);
                            product.setQuantity(0.0);
                            expedition.getMap().put(product,new ExpeditionInfo(pp.getProducer(),quantity,initQuantity));
                            break;
                        }
                        if (product != null && products1 != null && products2 == null && (product.getQuantity() + products1.getQuantity() > 0.0)) {
                            double productQuantity = product.getQuantity();
                            double product1Quantity = products1.getQuantity();
                            double quantity = cp.getQuantity();

                            double newQuantity = quantity-productQuantity-product1Quantity;

                            cp.setQuantity(newQuantity);
                            product.setQuantity(0.0);
                            products1.setQuantity(0.0);
                            expedition.getMap().put(product,new ExpeditionInfo(pp.getProducer(),quantity,productQuantity+product1Quantity));
                            break;
                        }
                        if (product != null && products1 == null && products2 != null && (product.getQuantity() + products2.getQuantity() > 0.0)) {
                            double productQuantity = product.getQuantity();
                            double product2Quantity = products2.getQuantity();
                            double quantity = cp.getQuantity();

                            double newQuantity = quantity-productQuantity-product2Quantity;

                            cp.setQuantity(newQuantity);
                            product.setQuantity(0.0);
                            products2.setQuantity(0.0);
                            expedition.getMap().put(product,new ExpeditionInfo(pp.getProducer(),quantity,productQuantity+product2Quantity));
                            break;
                        }
                        if (product != null && products1 != null && products2 != null && (product.getQuantity() + products1.getQuantity() + products2.getQuantity() > 0.0)) {
                            double productQuantity = product.getQuantity();
                            double product1Quantity = products1.getQuantity();
                            double product2Quantity = products2.getQuantity();
                            double quantity = cp.getQuantity();
                            double newQuantity = quantity-productQuantity-product1Quantity-product2Quantity;

                            cp.setQuantity(newQuantity);
                            product.setQuantity(0.0);
                            products1.setQuantity(0.0);
                            products2.setQuantity(0.0);
                            expedition.getMap().put(product,new ExpeditionInfo(pp.getProducer(),quantity,productQuantity+product1Quantity+product2Quantity));
                            break;
                        }
                        if(product==null && (products1!=null) && (products2==null) && (( products1.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0)){
                            double initQuantity = products1.getQuantity();
                            double quantity = cp.getQuantity();
                            double newQuantity = quantity-initQuantity;
                            cp.setQuantity(newQuantity);
                            products1.setQuantity(0.0);
                            expedition.getMap().put(products1,new ExpeditionInfo(pp.getProducer(),quantity,initQuantity));
                            break;
                        }
                        if(product==null && (products1==null) && (products2!=null) && ((products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0)){
                            double initQuantity = products2.getQuantity();
                            double quantity = cp.getQuantity();
                            double newQuantity = quantity-initQuantity;
                            cp.setQuantity(newQuantity);
                            products2.setQuantity(0.0);
                            expedition.getMap().put(products2,new ExpeditionInfo(pp.getProducer(),quantity,initQuantity));
                            break;
                        }
                        if(product==null && (products1!=null) && (products2!=null) && (( products1.getQuantity() + products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0)){
                            double productQuantity = products2.getQuantity();
                            double product1Quantity = products1.getQuantity();
                            double quantity = cp.getQuantity();

                            double newQuantity = quantity-productQuantity-product1Quantity;

                            cp.setQuantity(newQuantity);
                            products2.setQuantity(0.0);
                            products1.setQuantity(0.0);
                            expedition.getMap().put(products1,new ExpeditionInfo(pp.getProducer(),quantity,productQuantity+product1Quantity));
                            break;
                        }
                    }
                    if(before == cp.getQuantity()){
                        expedition.getMap().put(cp,new ExpeditionInfo(new Producer("NULL","NULL",null),cp.getQuantity(),0));
                    }
                }
            }
            expeditionList.add(expedition);
        }
    }

    /**
     * Make an expedition of the remaining products that couldn't be completed in the previous expedition.
     * For each EntityPlus in the given Iterable of EntityPlus, it gets all its products and for each product,
     * it iterates through the given Iterable of ProducerPlus and checks if the product is available.
     * If the product is available, it reduces the quantity of the product in the producer by the quantity of the product in the EntityPlus.
     * If the product is not available in the producer, it continues with the next producer.
     * If the product is not available in any of the producers, it continues with the next product of the EntityPlus.
     *
     * @param entityPlusIterable an Iterable of EntityPlus which represent the clients.
     * @param producerPlusIterable an Iterable of ProducerPlus which represent the producers.
     * @param day1 an AVL of ProducerPlus which represents the producers available on day 1.
     * @param day2 an AVL of ProducerPlus which represents the producers available on day 2.
     */
    private static void makeExpeditionRemain(Iterable<EntityPlus> entityPlusIterable,
                                                         Iterable<ProducerPlus> producerPlusIterable,AVL<ProducerPlus> day1,AVL<ProducerPlus> day2) {
        for (EntityPlus ep: entityPlusIterable){
            Iterable<Products> clientProducts = ep.getProductsAVL().inOrder();

            for (Products cp: clientProducts){
                double before = cp.getQuantity();
                if(before!=0.0){
                    for (ProducerPlus pp: producerPlusIterable) {
                        Products product = pp.getProductsAVL().find(new Products(cp.getProduct(), 0.0));
                        Products products1 = null;
                        Products products2 = null;

                        if(day1 != null && day1.find(pp) != null && day1.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0)) != null) {
                            products1 = day1.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0));
                        }
                        if(day2 != null && day2.find(pp) != null && day2.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0)) != null) {
                            products2 = day2.find(pp).getProductsAVL().find(new Products(cp.getProduct(), 0.0));
                        }

                        if (product != null && products1 ==null && products2==null && (product.getQuantity() > 0.0)) {
                            double initQuantity = product.getQuantity();
                            double quantity = cp.getQuantity();
                            double newQuantity = quantity-initQuantity;
                            cp.setQuantity(newQuantity);
                            product.setQuantity(0.0);
                            break;
                        }
                        if (product != null && products1 != null && products2 == null && (product.getQuantity() + products1.getQuantity() > 0.0)) {
                            double productQuantity = product.getQuantity();
                            double product1Quantity = products1.getQuantity();
                            double quantity = cp.getQuantity();

                            double newQuantity = quantity-productQuantity-product1Quantity;

                            cp.setQuantity(newQuantity);
                            product.setQuantity(0.0);
                            products1.setQuantity(0.0);
                            break;
                        }
                        if (product != null && products1 == null && products2 != null && (product.getQuantity() + products2.getQuantity() > 0.0)) {
                            double productQuantity = product.getQuantity();
                            double product2Quantity = products2.getQuantity();
                            double quantity = cp.getQuantity();

                            double newQuantity = quantity-productQuantity-product2Quantity;

                            cp.setQuantity(newQuantity);
                            product.setQuantity(0.0);
                            products2.setQuantity(0.0);
                            break;
                        }
                        if (product != null && products1 != null && products2 != null && (product.getQuantity() + products1.getQuantity() + products2.getQuantity() > 0.0)) {
                            double productQuantity = product.getQuantity();
                            double product1Quantity = products1.getQuantity();
                            double product2Quantity = products2.getQuantity();
                            double quantity = cp.getQuantity();
                            double newQuantity = quantity-productQuantity-product1Quantity-product2Quantity;

                            cp.setQuantity(newQuantity);
                            product.setQuantity(0.0);
                            products1.setQuantity(0.0);
                            products2.setQuantity(0.0);
                            break;
                        }
                        if(product==null && (products1!=null) && (products2==null) && (( products1.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0)){
                            double initQuantity = products1.getQuantity();
                            double quantity = cp.getQuantity();
                            double newQuantity = quantity-initQuantity;
                            cp.setQuantity(newQuantity);
                            products1.setQuantity(0.0);
                            break;
                        }
                        if(product==null && (products1==null) && (products2!=null) && ((products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0)){
                            double initQuantity = products2.getQuantity();
                            double quantity = cp.getQuantity();
                            double newQuantity = quantity-initQuantity;
                            cp.setQuantity(newQuantity);
                            products2.setQuantity(0.0);
                            break;
                        }
                        if(product==null && (products1!=null) && (products2!=null) && (( products1.getQuantity() + products2.getQuantity())>=cp.getQuantity()) && (cp.getQuantity()>0.0)){
                            double productQuantity = products2.getQuantity();
                            double product1Quantity = products1.getQuantity();
                            double quantity = cp.getQuantity();

                            double newQuantity = quantity-productQuantity-product1Quantity;

                            cp.setQuantity(newQuantity);
                            products2.setQuantity(0.0);
                            products1.setQuantity(0.0);
                            break;
                        }
                    }
                }
            }
        }
    }


    /**
     * Public method that calls a the private method
     *
     * @param expeditionList the list of expeditions to be updated
     * @param entityPlusIterable an iterable of EntityPlus objects representing clients
     * @param producerPlusIterable an iterable of ProducerPlus objects representing producers
     * @param day1 an AVL tree of ProducerPlus objects representing the producers available on the previous day
     * @param day2 an AVL tree of ProducerPlus objects representing the producers available on the day before the previous day
     */
    public void expeditionListFullProducts(List<Expedition> expeditionList, Iterable<EntityPlus> entityPlusIterable,
                                               Iterable<ProducerPlus> producerPlusIterable,AVL<ProducerPlus> day1,AVL<ProducerPlus> day2){
        makeExpeditionListFullProducts(expeditionList,entityPlusIterable,producerPlusIterable,day1,day2);
    }

    /**
     * Public method that calls a the private method
     *
     * @param entityPlusIterable an iterable of EntityPlus objects representing clients
     * @param producerPlusIterable an iterable of ProducerPlus objects representing producers
     * @param day1 an AVL tree of ProducerPlus objects representing the producers available on the previous day
     * @param day2 an AVL tree of ProducerPlus objects representing the producers available on the day before the previous day
     */
    public void expeditionFull(Iterable<EntityPlus> entityPlusIterable,
                                   Iterable<ProducerPlus> producerPlusIterable,AVL<ProducerPlus> day1,AVL<ProducerPlus> day2){
        makeExpeditionFull(entityPlusIterable,producerPlusIterable,day1,day2);
    }

    /**
     * Public method that calls a the private method
     *
     * @param expeditionList the list of expeditions to be updated
     * @param entityPlusIterable an iterable of EntityPlus objects representing clients
     * @param producerPlusIterable an iterable of ProducerPlus objects representing producers
     * @param day1 an AVL tree of ProducerPlus objects representing the producers available on the previous day
     * @param day2 an AVL tree of ProducerPlus objects representing the producers available on the day before the previous day
     */
    public void expeditionListRemainProducts(List<Expedition> expeditionList, Iterable<EntityPlus> entityPlusIterable,
                                                         Iterable<ProducerPlus> producerPlusIterable,AVL<ProducerPlus> day1,AVL<ProducerPlus> day2){
        makeExpeditionListRemainProducts(expeditionList,entityPlusIterable,producerPlusIterable,day1,day2);
    }

    /**
     * Public method that calls a the private method
     *
     * @param entityPlusIterable an Iterable of EntityPlus which represent the clients.
     * @param producerPlusIterable an Iterable of ProducerPlus which represent the producers.
     * @param day1 an AVL of ProducerPlus which represents the producers available on day 1.
     * @param day2 an AVL of ProducerPlus which represents the producers available on day 2.
     */
    public void expeditionRemain(Iterable<EntityPlus> entityPlusIterable,
                                             Iterable<ProducerPlus> producerPlusIterable,AVL<ProducerPlus> day1,AVL<ProducerPlus> day2){
        makeExpeditionRemain(entityPlusIterable,producerPlusIterable,day1,day2);
    }

    /**
     * Returns the orderedHumpers field.
     * @return the orderedHumpers field.
     */
    public AVL<DayClient> getOrderedHumpers() {
        return orderedHumpers;
    }

    /**
     * Returns the providedHumpers field.
     * @return the providedHumpers field.
     */
    public AVL<DayProducer> getProvidedHumpers() {
        return providedHumpers;
    }




    public static void printExpeditionList(List<Expedition> expeditionList) {
        for(Expedition s: expeditionList){
            System.out.println(s);
        }
    }
    public static void printProducers(Iterable<ProducerPlus> producerPlusIterable) {
        for (ProducerPlus c: producerPlusIterable){
            System.out.println(c.getProducer().getName());

            Iterable<Products>  productsIterable = c.getProductsAVL().inOrder();
            for (Products d: productsIterable){
                System.out.print("Product:"+d.getProduct() + " Quantidade:" + d.getQuantity() + " || " );
            }
            System.out.println();
        }
    }
    public static void printEntity(Iterable<EntityPlus> entityPlusIterable) {

        for (EntityPlus c: entityPlusIterable){
            System.out.println(c.getEntity().getName());

            Iterable<Products> productIterable = c.getProductsAVL().inOrder();
            for (Products d: productIterable){
                System.out.print("Product:"+d.getProduct() + " Quantidade:" + d.getQuantity() + " || " );
            }
            System.out.println();
        }
    }
    public static void printSobrasDia(Iterable<ProducerPlus> producerPlusIterable) {
        for (ProducerPlus pp: producerPlusIterable){
            Iterable<Products> productsIterable = pp.getProductsAVL().inOrder();
            for (Products p: productsIterable){
                if(p.getQuantity()!=0.0){
                    System.out.println(" Producer:" + pp.getProducer().getName() +" Product:" + p.getProduct()
                            + " Quantidade:" +p.getQuantity());
                }
            }
        }
    }
    public List<String> sobrasDia(AVL<ProducerPlus> avl) {
        List<String> list = new ArrayList<>();
        Iterable<ProducerPlus> producerPlusIterable = avl.inOrder();
        for (ProducerPlus pp: producerPlusIterable){
            Iterable<Products> productsIterable = pp.getProductsAVL().inOrder();
            for (Products p: productsIterable){
                if(p.getQuantity()!=0.0){
                    list.add("Producer:" + pp.getProducer().getName() +" Product:" + p.getProduct()
                            + " Quantidade:" +p.getQuantity());

                    System.out.println(" Producer:" + pp.getProducer().getName() +" Product:" + p.getProduct()
                            + " Quantidade:" +p.getQuantity());
                }
            }
        }
        return list;
    }


}
