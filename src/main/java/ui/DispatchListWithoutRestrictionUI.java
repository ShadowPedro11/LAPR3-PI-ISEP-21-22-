package ui;


import data.DataIO;
import model.DispatchListWithoutRestriction;
import model.Expedition;
import utils.Utils;

import java.util.List;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class DispatchListWithoutRestrictionUI {

    DispatchListWithoutRestriction dispatchList;

    public List<Expedition> exportList(DataIO dataIO){

        dispatchList = new DispatchListWithoutRestriction(dataIO);
        System.out.print("\n\nDia para gerar a lista de exportação :: ");
        int day = Utils.integerInput(0, Integer.MAX_VALUE);
        List<Expedition> expeditionList = dispatchList.dispatchListWithoutRestriction(day);
        for(Expedition s : expeditionList)
            System.out.println(s);
        return expeditionList;
    }
}
