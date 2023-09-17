package model;

import utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class IrrigationController {

    private IrrigationTime irrigationTime;

    private List<IrrigationProperties> properties;

    public IrrigationController() {
    }

    public boolean importFile(String path) throws FileNotFoundException {

        File f = Utils.validatePath(path);
        Scanner sc;
        properties = new ArrayList<>();

        try {
            sc = new Scanner(f);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] words = line.split(",");
                //trocar esta verificação
                if (words.length < 3) {
                    String[] hours = line.split(":|,");
                    List<LocalTime> timeList = new ArrayList<>();
                    for(int i=0;i<hours.length;i+=2){
                        timeList.add(LocalTime.of(Integer.parseInt(hours[i]),Integer.parseInt(hours[i+1]),0));
                    }
                    irrigationTime = new IrrigationTime(timeList, LocalDate.ofYearDay(2000,1));
                } else {
                    properties.add(new IrrigationProperties(words[0],Long.parseLong(words[1]), words[2]));
                }
            }
        }catch(Exception e){
            System.out.println("Ocorreu um erro ao carregar os ficheiros!");
        }

        return true;
    }

    private boolean isDataLoaded(){
        return irrigationTime != null && properties != null;
    }

    public void isWatering(LocalDateTime dateTime){
        if(!isDataLoaded())
            System.out.println("Data is not loaded");
        //verificar se o sistema está em funcionamento naquele momento
        if(systemIsOn(dateTime.toLocalTime())==true){
            String sector = actualSector(dateTime.toLocalTime());
            for (IrrigationProperties property:properties) {
                if(property.portion().equals(sector)){

                    long minutesToFinish=minutesToFinish(dateTime.toLocalTime(),sector);

                    if(property.frequency().equals("t"))
                        System.out.println("O Sistema esta a regar o setor "+sector+" e faltam "+minutesToFinish+" para terminar a rega");
                    if(property.frequency().equals("p")){
                        if(dateTime.toLocalDate().getDayOfMonth()% 2 == 0){
                            System.out.println("O Sistema esta a regar o setor "+sector+" e faltam "+minutesToFinish+" para terminar a rega");
                        }
                        System.out.println("O Sistema não esta em funcionamento nesse dia a esta hora1");
                    }
                    if(property.frequency().equals("i")){

                        if(dateTime.getDayOfMonth()% 2 == 0)
                            System.out.println("O Sistema não esta em funcionamento nesse dia a esta hora2");

                        System.out.println("O Sistema esta a regar o setor "+sector+" e faltam "+minutesToFinish+" para terminar a rega");

                    }
                }
            }
        }
    }



    /**
     * Função que dada um parametro time devolve o setor que o sistema de rega está a regar, sabendo que o sistema de rega está a funcionar
     * @param time
     * @return
     */
    public String actualSector(LocalTime time){

        for(LocalTime cicle:irrigationTime.cycles()) {
            long sum=0;
            for(IrrigationProperties property:properties){
                if( time.isBefore(cicle.plus(sum,ChronoUnit.MINUTES).plus(property.duration(),ChronoUnit.MINUTES)) && time.isAfter(cicle.plus(sum,ChronoUnit.MINUTES))){
                    return property.portion();
                }
            sum+=property.duration();
            }

        }
        return "Nenhum sector esta a ser regado";
    }

    private long minutesToFinish(LocalTime t, String sector){
        for (IrrigationProperties property:properties){
            if(property.portion().equals(sector)){
                for (LocalTime cycle:irrigationTime.cycles()){

                    LocalTime endCycle=cycle.plus(getTotalTimeWatering(),ChronoUnit.MINUTES);

                    if(t.isAfter(cycle) && t.isBefore(endCycle)){

                        return ChronoUnit.MINUTES.between(cycle, t);
                    }
                }
            }
        }
        return 0;
    }



    /**
     * Função que retorna em minutos o total de tempo de cada ciclo de rega
     * @return tempo em minutos da duração de um ciclo
     */
    private long getTotalTimeWatering(){
        long sum=0;
        for (IrrigationProperties property:properties) {
            sum+=property.duration();
        }
        return sum;
    }

    /**
     * MEtodo que verifica se o sistema está a funcionar num determinado instante de acordo com os horarios dos ciclos de rega fornecidos
     * @param t intante a verificar
     * @return true se estiver ligado, falso se estiver desligado
     */
    public boolean systemIsOn(LocalTime t){
        for (LocalTime time:this.irrigationTime.cycles()) {
            if(t.isBefore(time.plus(getTotalTimeWatering(), ChronoUnit.MINUTES))){
                return true;
            }
        }
        return false;
    }



}
