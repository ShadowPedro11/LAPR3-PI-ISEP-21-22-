package model;

import trees.AVL;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class EntityPlus implements Comparable<EntityPlus> {

    private Entity entity;
    private AVL<Products> productsAVL;

    public EntityPlus(Entity entity) {
        this.entity = entity;
        productsAVL = new AVL<>();
    }

    public Entity getEntity() {
        return entity;
    }

    public AVL<Products> getProductsAVL() {
        return productsAVL;
    }

    @Override
    public int compareTo(EntityPlus o) {
        return this.entity.getName().compareTo(o.entity.getName());
    }

    @Override
    public String toString() {
        return "EntityPlus{" +
                "entity=" + entity +
                ", productsAVL=" + productsAVL.inOrder() +
                '}'+"\n";
    }
}
