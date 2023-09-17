package ui;

import utils.Utils;
import java.util.ArrayList;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class DistributionManagerUI {

    private static final String CHARACTER = "*";
    private static int TIMES;

    private static final String IMPORT_CLIENT_DATA = "Importar dados de clientes/produtores";
    private static final String VERIFY_GRAPH_PLUS_MINIMUM_CONNECTIONS =
            "Verificar conectividade do grafo carregado + número mínimo de ligações";
    private static final String DISTRIBUTIONS_HUBS = "Hubs de distribuição";
    private static final String CLIENT_CLOSEST_HUB = "Hub de distribuição mais próximo";
    private static final String NETWORK_CONNECTING_ALL_CLIENTS_N_AGRICULUTRAL_PRODUCERS =
            "Rede conectora de todos os clientes e produtores com distância total mínima";

    private static final String IMPORT_HUMPERS_DATA = "Importar dados de cabazes";
    private static final String EXPEDITATION_LIST = "Lista de expedição";
    private static final String RESTRICTED_EXPEDITATION_LIST = "Lista de expedição restrita";
    private static final String DELIVERY_ROUTE = "Percurso de entrega";
    private static final String STATISTIC_CALCULATIONS = "Calcular estatísticas da lista de expedição";


    public int mainMenu() {

        ArrayList<String> list = new ArrayList<>();
        list.add(IMPORT_CLIENT_DATA);
        list.add(IMPORT_HUMPERS_DATA);
        list.add(VERIFY_GRAPH_PLUS_MINIMUM_CONNECTIONS);
        list.add(DISTRIBUTIONS_HUBS);
        list.add(CLIENT_CLOSEST_HUB);
        list.add(NETWORK_CONNECTING_ALL_CLIENTS_N_AGRICULUTRAL_PRODUCERS);
        list.add(EXPEDITATION_LIST);
        list.add(RESTRICTED_EXPEDITATION_LIST);
        list.add(DELIVERY_ROUTE);
        list.add(STATISTIC_CALCULATIONS);

        TIMES = list.stream().map(String::length).max(Integer::compareTo).get() + 5;

        Utils.header(CHARACTER, TIMES, "Menu Gestor de Distribuição");

        Utils.menuList(list);
        Utils.characterRepetition(CHARACTER, TIMES);

        System.out.print("\n\nIntroduza o comando necessário.\n\nIntroduza a opção :: ");
        return Utils.integerInput(0, list.size());
    }
}
