package model;

public class ExpeditionInfo {

    private Producer producer;
    private double quantidadePedida;
    private double quantidadeFornecida;

    public ExpeditionInfo(Producer producer, double quantidadePedida, double quantidadeFornecida) {
        this.producer = producer;
        this.quantidadePedida = quantidadePedida;
        this.quantidadeFornecida = quantidadeFornecida;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public double getQuantidadePedida() {
        return quantidadePedida;
    }

    public void setQuantidadePedida(double quantidadePedida) {
        this.quantidadePedida = quantidadePedida;
    }

    public double getQuantidadeFornecida() {
        return quantidadeFornecida;
    }

    public void setQuantidadeFornecida(double quantidadeFornecida) {
        this.quantidadeFornecida = quantidadeFornecida;
    }
}
