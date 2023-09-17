package model;

import trees.AVL;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class DayClient extends Day implements Comparable<DayClient>{

    private AVL<EntityPlus> clients;

    public DayClient(int day) {
        super(day);
        clients = new AVL<>();
    }

    public AVL<EntityPlus> getClients() {
        return clients;
    }

    @Override
    public int compareTo(DayClient o) {
        return Integer.compare(this.getDay(), o.getDay());
    }
}
