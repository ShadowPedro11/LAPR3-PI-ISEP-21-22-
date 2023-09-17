package statistics;

import model.Company;
import model.Entity;
import java.util.HashSet;

public class StatisticPerProducer {

    private Integer totalDeliv;
    private Integer partialDeliv;
    private HashSet<Entity> distinctClients;
    private Integer soldOut;
    private Integer numberOfHubs;
    private HashSet<Company> companies;

    public StatisticPerProducer(Integer totalDeliv, Integer partialDeliv, HashSet<Entity> distinctClients, Integer soldOut, Integer numberOfHubs,HashSet<Company> companies) {
        this.totalDeliv = totalDeliv;
        this.partialDeliv = partialDeliv;
        this.distinctClients = distinctClients;
        this.soldOut = soldOut;
        this.numberOfHubs = numberOfHubs;
        this.companies = companies;
    }

    public Integer getTotalDeliv() {
        return totalDeliv;
    }

    public Integer getPartialDeliv() {
        return partialDeliv;
    }

    public HashSet<Entity> getDistinctClients() {
        return distinctClients;
    }

    public Integer getSoldOut() {
        return soldOut;
    }

    public void setTotalDeliv(Integer totalDeliv) {
        this.totalDeliv = totalDeliv;
    }

    public void setPartialDeliv(Integer partialDeliv) {
        this.partialDeliv = partialDeliv;
    }

    public void setDistinctClients(HashSet<Entity> distinctClients) {
        this.distinctClients = distinctClients;
    }

    public void setSoldOut(Integer soldOut) {
        this.soldOut = soldOut;
    }

    public void setNumberOfHubs(Integer numberOfHubs) {
        this.numberOfHubs = numberOfHubs;
    }

    public void incrementTotalDeliv(){
        totalDeliv++;
    }

    public void incrementPartialDeliv(){
        partialDeliv++;
    }
    public void incrementHubs(){
        numberOfHubs++;
    }

    public Integer getNumberOfHubs() {
        return numberOfHubs;
    }

    public HashSet<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(HashSet<Company> companies) {
        this.companies = companies;
    }
}
