import exceptions.MissingNetworkException;
import model.Company;
import model.DistributionHubs;
import model.Entity;
import model.Expedition;
import ui.*;
import utils.Utils;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final LogInUI logIn = new LogInUI();
    private static final DistributionManagerUI dmui = new DistributionManagerUI();
    private static final AgriculturalManagerUI amui = new AgriculturalManagerUI();

    private static List<Expedition> expeditionList = new ArrayList<>();

    private static Map<Entity, Company> closestHubs = new HashMap<>();

    public static void main(String[] args){

        int mainChoice;
        boolean flag, flagLogIn = true;
        DataIOUI diui = null;
        HumpersDataIOUI hdioui = null;

        while(flagLogIn){
            flag = true;
            int logInChoice = logIn.mainLog();
            switch (logInChoice){
                case 0 -> flagLogIn = false;
                case 1 ->{
                    Utils.clearConsole();
                    String userType = logIn.logIn();
                    if(userType == null) {
                        System.out.println("\nInício de sessão inválido!");
                        Utils.pressEnterToContinue();
                        Utils.clearConsole();
                        continue;
                    }
                    switch (userType){
                        case "agricultural_manager"->{
                            boolean file = false;
                            IrrigationControllerUI icui = new IrrigationControllerUI();
                            while(flag) {
                                Utils.clearConsole();
                                int choice = amui.mainMenu();
                                System.out.println("\n");
                                try {
                                    switch (choice) {

                                        case 0 -> flag = false;
                                        case 1 -> {
                                            if (!icui.irrigationControllerLoad())
                                                System.out.println("Ocorreu um erro ao tentar ler o ficheiro!");
                                            else {
                                                System.out.println("O ficheiro foi lido com sucesso!");
                                                file = true;
                                            }
                                            Utils.pressEnterToContinue();
                                        }

                                        case 2 ->{
                                            if(!file) {
                                                System.out.println("Não existe um controlador de rega!");
                                                break;
                                            }
                                            icui.getIrrigationControllerInformation();
                                            Utils.pressEnterToContinue();
                                        }

                                        default -> System.out.println("\n\nOcorreu um erro!");
                                    }

                                }catch(FileNotFoundException fileNotFoundException){
                                    System.out.println("O ficheiro não foi encontrado!");
                                }catch (Exception exception){
                                    System.out.println("Ocorreu um erro inesperado!");
                                }
                            }
                        }

                        case "driver", "client" ->{
                            noUIAvailable();
                            Utils.pressEnterToContinue();
                        }

                        case "distribution_manager"->{
                            while(flag) {
                                Utils.clearConsole();
                                mainChoice = dmui.mainMenu();
                                System.out.println("\n");

                                try {
                                    switch (mainChoice) {
                                        case 0 -> flag = false;
                                        case 1 -> {
                                            diui = new DataIOUI();
                                            if (!diui.importData()) {
                                                errorMessage("Ocorreu um erro ao carregar os ficheiros!");
                                                diui = null;
                                            }
                                            else {
                                                System.out.println("Os dados foram carregados com sucesso!");
                                                if(DistributionHubs.getHubsList() != null)
                                                    DistributionHubs.getHubsList().clear();
                                            }
                                            Utils.pressEnterToContinue();
                                        }
                                        case 2 ->{
                                            hdioui = new HumpersDataIOUI();
                                            if(!hdioui.importData(diui))
                                                errorMessage("Ocorreu um erro ao carregar o ficheiro dos cabazes!");
                                            else
                                                System.out.println("Os cabazes foram carregados com sucesso!");

                                            Utils.pressEnterToContinue();
                                        }
                                        case 3 -> {
                                            if(!dataImported(diui))
                                                break;
                                            MinimumConnectionsNumberUI mcnui = new MinimumConnectionsNumberUI();
                                            if (!mcnui.minimumConnections(diui.getDataIO().getGraph()))
                                                errorMessage("Ocorreu um erro ao pesquisar o número mínimo de ligações" +
                                                        " necessário" + "para qualquer cliente/produtor conseguir" +
                                                        " contatar qualquer outro.");
                                            Utils.pressEnterToContinue();
                                        }
                                        case 4 -> {
                                            if(!dataImported(diui))
                                                break;
                                            DistributionHubsUI dhui = new DistributionHubsUI();
                                            if (!dhui.distributionHubs(diui.getDataIO().getGraph()))
                                                errorMessage("Ocorreu um erro ao pesquisar pelas N \" +\n" +
                                                        "\"empresas mais próximas de todos os pontos da rede");
                                            Utils.pressEnterToContinue();
                                        }
                                        case 5 -> {
                                            if(!dataImported(diui) && DistributionHubs.getHubsList() == null)
                                                break;
                                            ClosestDistributionHubUI cdh = new ClosestDistributionHubUI();
                                            if ((closestHubs = cdh.closestDistributionHub(diui.getDataIO().getGraph())) == null)
                                                errorMessage("Ocorreu um erro ao determinar o hub mais próximo!");
                                            Utils.pressEnterToContinue();
                                        }
                                        case 6 -> {
                                            if(!dataImported(diui))
                                                break;
                                            NetworkConnectionUI ncui = new NetworkConnectionUI();
                                            if (!ncui.networkConnection(diui.getDataIO().getGraph()))
                                                errorMessage("Ocorreu um erro ao pesquisar pela distância total" +
                                                        " mínima conectora de todos os produtores/clientes!");
                                            Utils.pressEnterToContinue();
                                        }

                                        case 7 -> {
                                            if(!dataImported(diui) && !dataImported(hdioui))
                                                break;
                                            DispatchListWithoutRestrictionUI dlwrui =
                                                    new DispatchListWithoutRestrictionUI();

                                            if((expeditionList = dlwrui.exportList(diui.getDataIO())) == null)
                                                errorMessage("Ocorreu um erro ao gerar a lista de expedição!");
                                            Utils.pressEnterToContinue();
                                        }

                                        case 8 -> {
                                            if(!dataImported(diui) && !dataImported(hdioui))
                                                break;
                                            if(DistributionHubs.getHubsList() == null)
                                                throw new Exception();
                                            if(DistributionHubs.getHubsList().isEmpty())
                                                throw new Exception();
                                            RestrictedDispatchListUI rdlui =
                                                    new RestrictedDispatchListUI();

                                            if((expeditionList = rdlui.exportRestrictedList(diui.getDataIO())) == null)
                                                errorMessage("Ocorreu um erro ao gerar a lista de expedição com" +
                                                        " restrições!");
                                            Utils.pressEnterToContinue();

                                        }

                                        case 9 -> {
                                            if(!dataImported(diui) && !dataImported(hdioui) && expeditionList == null
                                                    && closestHubs == null)
                                                break;
                                            RouteWithMinimumDistanceUI rwmdui = new RouteWithMinimumDistanceUI();
                                            if(expeditionList.isEmpty())
                                                break;
                                            if(!rwmdui.exportRestrictedList(diui.getDataIO(), expeditionList, closestHubs))
                                                errorMessage("Ocorreu um erro ao gerar o percurso de entrega!");
                                            Utils.pressEnterToContinue();
                                        }

                                        case 10 -> {
                                            if(!dataImported(diui) && !dataImported(hdioui) && DistributionHubs.getHubsList() == null)
                                                break;
                                            StatisticCalculationsUI stcui = new StatisticCalculationsUI();
                                            if(!stcui.statisticCalculations(diui.getDataIO()))
                                                errorMessage("Ocorreu um erro ao gerar a lista de expedição com" +
                                                        " restrições!");
                                            Utils.pressEnterToContinue();

                                        }


                                        default -> errorMessage("\n\nOcorreu um erro!");
                                    }
                                }catch(FileNotFoundException fileNotFoundException){
                                    errorMessage("\n\nO ficheiro não foi encontrado!");
                                    diui = null;
                                }catch(MissingNetworkException missingNetworkException){
                                    errorMessage("\n\nOs ficheiros de clientes/produtores e distâncias não foram carregados");
                                }catch (Exception exception){
                                    errorMessage("Um erro inesperado aconteceu!");
                                }
                            }
                        }

                        default -> System.out.println("Um erro inesperado ocorreu!");
                    }
                }
            }
        }



    }


    private static boolean dataImported(Object o){
        if (o == null) {
            errorMessage("Ainda não foram carregados dados!");
            return false;
        }
        return true;
    }

    private static void errorMessage(String errorMessage){
        System.out.println(errorMessage);
        Utils.pressEnterToContinue();
    }

    private static void noUIAvailable(){
        System.out.println("\nEste utilizador ainda não tem uma interface disponível!");
        Utils.pressEnterToContinue();
    }
}
