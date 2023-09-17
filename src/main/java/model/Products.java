package model;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class Products implements Comparable<Products> {
    int product;
    double quantity;

    public Products(int product, double quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(Products o) {
        return Integer.compare(this.product, o.product);
    }

    @Override
    public String toString() {
        return "Products{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
