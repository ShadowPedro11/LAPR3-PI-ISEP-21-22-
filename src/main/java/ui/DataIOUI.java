package ui;

import data.DataIO;
import utils.Utils;

import java.io.FileNotFoundException;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class DataIOUI {
    private DataIO dataIO;
    public boolean importData() throws FileNotFoundException {

        dataIO = new DataIO();

        System.out.print("\n\nCaminho para importar os clientes/produtores :: ");
        String path1 = Utils.stringInput();
        System.out.print("\n\nCaminho para importar as distâncias :: ");
        String path2 = Utils.stringInput();

        return dataIO.loadNetworkFile(path1, path2);
    }

    public DataIO getDataIO() {
        return dataIO;
    }
}
