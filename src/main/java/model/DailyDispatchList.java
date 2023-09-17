package model;

import trees.AVL;

import java.util.List;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class DailyDispatchList {
    /*
      Estruturas das AVLs

       AVL<DayClient>
        DayClient(int day, AVL<EntityPlus>)
        EntityPlus(Entity entity, AVL<Products>)
        Products(int product, double quantity)

        A primeira AVL vai conter os dias e a esses dias vão estar associadas AVLs de clientes(clientes/empresas) (só vão estar
        presentes as que realmente tiverem encomendas para esse dia) a segunda tem a entidade com uma AVL de
        produtos que vai ter um produto e uma quantidade associada (tal como em cima, apenas os produtos que tenham > 0
        de quantidade é que vão estar presentes).

       AVL<DayProducer>
        DayProducer(int day, AVL<ProducerPlus>)
        ProducerPlus(Producer producer, AVL<Products>)
        Products(int product, double quantity)

        A estrutura é igual à de cima, muda apenas no atributo de DayClient e DayProducer.

        Uma tem as encomendas dos clientes/empresas e a outra, os produtos fornecidos pelos produtores.

     */

    public void dailyDispatchList(AVL<DayClient> orders, AVL<DayProducer> provided){
    }
}
