package ui;

import utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class LogInUI {

    private final String LOG_IN = "Iniciar sessão";

    private final File USERS = new File("src/resources/users");

    public int mainLog(){
        ArrayList<String> list = new ArrayList<>();
        list.add(LOG_IN);

        final int TIMES = list.stream().map(String::length).max(Integer::compareTo).get() + LOG_IN.length()*3;

        Utils.header("~", TIMES, "Inicio de sessão");
        Utils.menuList(list);
        Utils.characterRepetition("~", TIMES);

        System.out.print("\n\nIntroduza o comando necessário.\n\nIntroduza a opção :: ");
        return Utils.integerInput(0, list.size());
    }

    public String logIn(){
        System.out.print("Nome de utilizador :: ");
        String usr = Utils.stringInput();
        System.out.print("Password :: ");
        String pwd = Utils.stringInput();
        return checkUser(usr, pwd);
    }

    private String checkUser(String usr, String pwd){
        Scanner sc = null;
        try {
            sc = new Scanner(USERS);
            sc.nextLine();
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] words = line.split(",");
                final String USER = words[0].trim(), PASSWORD = words[1].trim(), USER_TYPE = words[2].trim();
                if(usr.equalsIgnoreCase(USER) && pwd.equalsIgnoreCase(PASSWORD)){
                    sc.close();
                    return USER_TYPE;
                }
            }
        }catch(Exception e){
            sc.close();
            return null;
        }
        sc.close();
        return null;
    }


}
