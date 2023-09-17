package model;

import java.util.Collection;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public record TechnicalSheet(Collection<Element> substances, Collection<Element> elements) {
}
