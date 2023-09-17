package ui;

import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class AgriculturalManagerUI {

    private static final String WATTER_CONTROLLER = "Criar controlador de rega";
    private static final String WATTER_CONTROLLER_INFORMATION = "Informações sobre o controlador de rega";
    private static int TIMES;

    private static final String CHARACTER = "*";

    public int mainMenu(){

        List<String> items = new ArrayList<>();

        items.add(WATTER_CONTROLLER);
        items.add(WATTER_CONTROLLER_INFORMATION);

        TIMES = items.stream().map(String::length).max(Integer::compareTo).get() + 5;

        Utils.header(CHARACTER, TIMES, "Menu Gestor Agrícola");

        Utils.menuList(items);
        Utils.characterRepetition(CHARACTER, TIMES);

        System.out.print("\n\nIntroduza o comando necessário.\n\nIntroduza a opção :: ");
        return Utils.integerInput(0, items.size());
    }
}
