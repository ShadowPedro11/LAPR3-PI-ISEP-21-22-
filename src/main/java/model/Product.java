package model;

/**
 *
 * @param comercialName Product's comercial name.
 * @param formulation Product might be either liquid, grainy or dust.
 * @param technicalSheet Product's technical sheet (substances on it and the respective quantities).
 * @author : Ricardo Venâncio - 1210828
 * */
public record Product(String comercialName, Formulation formulation, TechnicalSheet technicalSheet){
}
