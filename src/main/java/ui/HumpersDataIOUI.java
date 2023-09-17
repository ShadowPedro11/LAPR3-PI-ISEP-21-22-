package ui;

import exceptions.MissingNetworkException;
import utils.Utils;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class HumpersDataIOUI {

        public boolean importData(DataIOUI dataIOUI) throws Exception {

            if(dataIOUI == null)
                throw new MissingNetworkException("");

            System.out.print("\n\nCaminho para importar os cabazes :: ");
            String path1 = Utils.stringInput();

            return dataIOUI.getDataIO().loadHumpers(path1);
        }

}
