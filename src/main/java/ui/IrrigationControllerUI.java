package ui;

import model.IrrigationController;
import utils.Utils;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class IrrigationControllerUI {

    private final IrrigationController ic = new IrrigationController();

    public boolean irrigationControllerLoad() throws FileNotFoundException {
        System.out.print("Caminho para o ficheiro :: ");
        String path = Utils.stringInput();
        return ic.importFile(path);
    }

    public void getIrrigationControllerInformation(){
        LocalDateTime dateTime;

        System.out.println( "Introduza a Hora [0-23]: " );
        int hours = Utils.integerInput(0, 23);

        System.out.println( "Introduza os minutos [0-59]: " );
        int minutes = Utils.integerInput(0, 59);

        System.out.println( "Introduza o dia do mês [0-30]: " );
        int day = Utils.integerInput(0, 30);

        System.out.println( "Introduza o mês do ano [0-12]: " );
        int month = Utils.integerInput(0, 12);

        System.out.println( "Introduza o Ano [1950-"+LocalDateTime.now().getYear()+"]: " );
        int year = Utils.integerInput(1950, LocalDateTime.now().getYear());

        dateTime=LocalDateTime.of(year,month,day,hours,minutes,0);

        ic.isWatering(dateTime);

    }
}

