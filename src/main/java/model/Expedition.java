package model;

import java.util.Map;
import java.util.Objects;

public class Expedition  {

    private EntityPlus client;
    private Map<Products,ExpeditionInfo> map;

    public Expedition(EntityPlus client, Map<Products, ExpeditionInfo> map) {
        this.client = client;
        this.map = map;
    }

    public EntityPlus getClient() {
        return client;
    }

    public void setClient(EntityPlus client) {
        this.client = client;
    }

    public Map<Products, ExpeditionInfo> getMap() {
        return map;
    }

    public void setMap(Map<Products, ExpeditionInfo> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        String a = "";
        for (Map.Entry<Products,ExpeditionInfo> entry : map.entrySet()) {
            a += "Client:" + client.getEntity().getName() + " Product:" + entry.getKey().getProduct() +
                    " Producer:" + entry.getValue().getProducer().getName()
            + " Quantity ordered:" + entry.getValue().getQuantidadePedida() +
                    " Quantity supplied:" + entry.getValue().getQuantidadeFornecida() + "\n" ;
        }
        return a;
    }
}
