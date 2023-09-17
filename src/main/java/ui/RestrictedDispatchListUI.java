package ui;

import data.DataIO;
import model.Expedition;
import model.RestrictedDispatchList;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class RestrictedDispatchListUI {

    RestrictedDispatchList restrictedDispatchList;

    public List<Expedition> exportRestrictedList(DataIO dataIO){
        restrictedDispatchList = new RestrictedDispatchList(dataIO);
        System.out.print("\n\nDia para gerar a lista de exportação :: ");
        int day = Utils.integerInput(0, Integer.MAX_VALUE);
        System.out.print("\n\nNúmero de produtores mais próximos do hub de entrega do cliente :: " );
        int n = Utils.integerInput(0, Integer.MAX_VALUE);
        List<Expedition> expeditionList = restrictedDispatchList.restrictedDispatchList(day, n);
        for(Expedition s : expeditionList)
            System.out.println(s);
        return expeditionList;
    }
}
