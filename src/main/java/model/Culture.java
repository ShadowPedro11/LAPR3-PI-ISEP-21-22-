package model;

import java.util.Collection;

/**
 *
 * @param vegetalSpecie Vegetal specie used in agriculture to be consumed by humans/animals (e.g., flowers, fruits,
 *                     cereals, horticultural, fodder).
 * @param permanentCulture A culture could be permanent type (e.g., tree fruits like pear trees or apple trees)
 *                         or temporary (e.g., horticultural or fodder)
 * @param produtionFactors Production factors are every product applied on the ground/plants, in order to nutrure it,
 *                        previne diseases, fix imbalances, fights against pests/diseases. They can be classified as
 *                         fertilizers, manure, correctives orphytopharmaceuticals products.
 * @author : Ricardo Venâncio - 1210828
 */
public record Culture(String vegetalSpecie, boolean permanentCulture, Collection<Product> produtionFactors) {
}
