package statistics;

import model.Producer;

import java.util.HashSet;

public class StastisticPerClient {

    private Integer totalSatis;
    private Integer parcialSatis;
    private HashSet<Producer> distinctProducers;


    public StastisticPerClient(Integer totalSatis, Integer parcialSatis, HashSet<Producer> distinctProducers) {
        this.totalSatis = totalSatis;
        this.parcialSatis = parcialSatis;
        this.distinctProducers = distinctProducers;
    }

    public Integer getTotalSatis() {
        return totalSatis;
    }

    public Integer getParcialSatis() {
        return parcialSatis;
    }

    public HashSet<Producer> getDistinctProducers() {
        return distinctProducers;
    }



    public void incrementTotalSatis() {
        this.totalSatis++;
    }

    public void incrementParcialSatis() {
        this.parcialSatis++;
    }

    public void setDistinctProducers(HashSet<Producer> distinctProducers) {
        this.distinctProducers = distinctProducers;
    }

    public void setTotalSatis(Integer totalSatis) {
        this.totalSatis = totalSatis;
    }

    public void setParcialSatis(Integer parcialSatis) {
        this.parcialSatis = parcialSatis;
    }
}
