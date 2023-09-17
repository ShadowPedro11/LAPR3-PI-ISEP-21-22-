package model;

import trees.AVL;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class ProducerPlus implements Comparable<ProducerPlus>{

    private Producer producer;
    private AVL<Products> productsAVL;

    public ProducerPlus(Producer producer) {
        this.producer = producer;
        productsAVL = new AVL<>();
    }

    public Producer getProducer() {
        return producer;
    }

    public AVL<Products> getProductsAVL() {
        return productsAVL;
    }

    @Override
    public int compareTo(ProducerPlus o) {
        return this.producer.getName().compareTo(o.producer.getName());
    }
}
