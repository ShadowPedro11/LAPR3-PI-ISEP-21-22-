package statistics;

import model.Entity;
import model.Producer;
import java.util.HashSet;

public class StatisticPerHub {

    private HashSet<Entity> distinctClients;
    private HashSet<Producer> distinctProducers;

    public StatisticPerHub(HashSet<Entity> distinctClients,HashSet<Producer> distinctProducers){
        this.distinctClients = distinctClients;
        this.distinctProducers = distinctProducers;
    }

    public HashSet<Entity> getDistinctClients() {
        return distinctClients;
    }

    public HashSet<Producer> getDistinctProducers() {
        return distinctProducers;
    }

    public void setDistinctClients(HashSet<Entity> distinctClients) {
        this.distinctClients = distinctClients;
    }

    public void setDistinctProducers(HashSet<Producer> distinctProducers) {
        this.distinctProducers = distinctProducers;
    }
}
