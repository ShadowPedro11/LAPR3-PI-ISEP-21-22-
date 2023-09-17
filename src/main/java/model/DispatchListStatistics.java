package model;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class DispatchListStatistics {
    /*
      Estrutura da AVL<Day>

        Day(int day, AVL<EntityPlus>)
        EntityPlus(Entity entity, AVL<Products>)
        Product(int product, double quantity)

        A primeira AVL vai conter os dias e a esses dias vão estar associadas AVLs de clientes/produtores (só vão estar
        presentes as que realmente tiverem encomendas/produções para esse dia) a segunda tem a entidade com uma AVL de
        produtos que vai ter um produto e uma quantidade associada (tal como em cima, apenas os produtos que tenham > 0
        de quantidade é que vão estar presentes).

     */

    public void dispatchListStatistics(){

    }
}
