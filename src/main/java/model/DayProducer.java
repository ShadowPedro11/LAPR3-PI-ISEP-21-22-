package model;

import trees.AVL;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class DayProducer extends Day implements Comparable<DayProducer>{

    private AVL<ProducerPlus> producers;

    public DayProducer(int day) {
        super(day);
        producers = new AVL<>();
    }

    public AVL<ProducerPlus> getProducers() {
        return producers;
    }

    @Override
    public int compareTo(DayProducer o) {
        return Integer.compare(this.getDay(), o.getDay());
    }
}
