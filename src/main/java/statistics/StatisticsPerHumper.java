package statistics;

import model.Producer;

import java.util.HashSet;

public class StatisticsPerHumper {

    private Integer completelySatisfiedProducts;
    private Integer parciallySatisfiedProducts;
    private Integer nonSatisfiedProducts;
    private HashSet<Producer> distinctProducers;
    private double percentageOfSatisfaction;
    private double productoPedidosQuantidade;
    private double productoObtidosQuantidade;

    public StatisticsPerHumper() {
        this.completelySatisfiedProducts = 0;
        this.parciallySatisfiedProducts = 0;
        this.nonSatisfiedProducts = 0;
        this.distinctProducers = null;
        this.percentageOfSatisfaction = 0;
        this.productoPedidosQuantidade =0;
        this.productoObtidosQuantidade =0;
    }

    public Integer getCompletelySatisfiedProducts() {
        return completelySatisfiedProducts;
    }

    public void incrementCompletelySatisfiedProducts() {
        this.completelySatisfiedProducts++;
    }

    public void incrementSatisfactionPercentage(){

    }

    public Integer getParciallySatisfiedProducts() {
        return parciallySatisfiedProducts;
    }

    public void incrementParciallySatisfiedProducts() {
        this.parciallySatisfiedProducts++;
    }

    public Integer getNonSatisfiedProducts() {
        return nonSatisfiedProducts;
    }

    public void incrementNonSatisfiedProducts() {
        this.nonSatisfiedProducts++;
    }

    public HashSet<Producer> getDistinctProducers() {
        return distinctProducers;
    }

    public void setDistinctProducers(HashSet<Producer> distinctProducers) {
        this.distinctProducers = distinctProducers;
    }

    public double getPercentageOfSatisfaction() {
        return percentageOfSatisfaction;
    }

    public void setPercentageOfSatisfaction(double percentageOfSatisfaction) {
        this.percentageOfSatisfaction = percentageOfSatisfaction;
    }

    public void setCompletelySatisfiedProducts(Integer completelySatisfiedProducts) {
        this.completelySatisfiedProducts = completelySatisfiedProducts;
    }

    public void setParciallySatisfiedProducts(Integer parciallySatisfiedProducts) {
        this.parciallySatisfiedProducts = parciallySatisfiedProducts;
    }

    public void setNonSatisfiedProducts(Integer nonSatisfiedProducts) {
        this.nonSatisfiedProducts = nonSatisfiedProducts;
    }

    public double getProductoPedidosQuantidade() {
        return productoPedidosQuantidade;
    }

    public double getProductoObtidosQuantidade() {
        return productoObtidosQuantidade;
    }

    public void setProductoPedidosQuantidade(double productoPedidosQuantidade) {
        this.productoPedidosQuantidade = productoPedidosQuantidade;
    }

    public void setProductoObtidosQuantidade(double productoObtidosQuantidade) {
        this.productoObtidosQuantidade = productoObtidosQuantidade;
    }
}
