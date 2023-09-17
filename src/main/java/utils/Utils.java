package utils;

import graphs.Graph;
import model.Client;
import model.Company;
import model.Producer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class Utils {

    public static void header(String character, int characterTimes, String headerTitle){
        characterRepetition(character, characterTimes);
        System.out.println();
        for(int i=0;i<characterTimes/3;i++)
            System.out.print(" ");
        System.out.print("# " + headerTitle + " # \n");
        characterRepetition(character, characterTimes);
        System.out.println("\n");
    }

    public static void characterRepetition(String character, int characterTimes){
        for(int i=0;i<characterTimes;i++){
            System.out.print(character);
        }
    }

    public static void menuList(List<String> itemsList){
        for(int i=0;i<itemsList.size();i++){
            System.out.println((i+1) + ". " + itemsList.get(i));
        }
        System.out.println("\n0. Exit");
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }catch (Exception e){
            System.out.println();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
    }

    public static int integerInput(int min, int max){
        Scanner sc = new Scanner(System.in);

        String i;
        int tries = 3;
        do{
            i = sc.next();
            if(i.matches("[0-9]+")) {
                int x = Integer.parseInt(i);
                if (x >= min && x <= max)
                    break;
            }
            System.out.print("\nErro, digite novamente ("+tries+") :: ");
        }while(tries-- > 0);

        //sc.close();

        if(tries <= 1){
            System.out.println("\n\nNúmero máximo de tentativas excedido!");
            return -1;
        }
        
        return Integer.parseInt(i);
    }

    /**
     * Reads text input.
     * @return the string that was read.
     */
    public static String stringInput(){
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        //sc.close();
        return s;
    }

    public static void pressEnterToContinue() {
        System.out.print("\n\n# Clicar no Enter para prosseguir #");
        try
        {
            System.in.read();
        }
        catch(Exception ignored)
        {}
    }

    /**
     * Verifies if a path is null and if it leads to a file or not.
     * @param path to analyze.
     * @return a file if it is valid.
     * @throws FileNotFoundException when the path doesn't lead to any file.
     * @throws NullPointerException if path is null.
     */

    public static File validatePath(String path) throws FileNotFoundException {
        if(path == null)
            throw new NullPointerException();
        File f = new File(path);
        Scanner sc = new Scanner(f);
        sc.close();
        return f;
    }


    /**
     * Creates a graphic visualization of the created graph.
     * @param fileName file to save the .dot file.
     * @param graph graph to implement.
     * @param path this can be either null, or a valid Collection. If this contains a valid path with vertices, a path
     *            will be marked, otherwise (if it's null) is going to be ignored.
     * @param <V> Vertices.
     * @param <E> Edges.
     */
    public static <V, E> void createDotGraph(String fileName, Graph<V, E> graph, List<V> path){

        boolean flag = !(path == null);
        if(graph == null)
            return;

        if(!fileName.matches("[a-zA-Z0-9]+")) {
            System.out.println("Nome inválido");
            return;
        }

        File f = new File("docs/"+fileName+".gv");
        try {
            if (f.createNewFile())
                System.out.println("Ficheiro guardado em: \"" + f.getAbsolutePath() + "\"");
        }catch (Exception e){
            return;
        }


        int incrementor = 0;
        try{
            FileWriter fw = new FileWriter(f);
            fw.write("""
                    graph entitiesnetwork{
                    graph [rankdir = LR]
                    node [shape = circle,
                          style = filled,
                          color = grey,      splines="line"]""");

            StringBuilder prods = new StringBuilder("\nnode [fillcolor = red]");
            StringBuilder clients = new StringBuilder("\nnode [fillcolor = green]");
            StringBuilder companies = new StringBuilder("\nnode [fillcolor = orange]");

            StringBuilder edges = new StringBuilder("\nedge [color = grey]\n");

            Map<V, String> vertices = new HashMap<>();

            for(V v1 : graph.vertices()){
                if(v1 instanceof Producer p)
                    prods.append("\n").append(p.getName());
                else if(v1 instanceof Client c)
                    clients.append("\n").append(c.getName());
                else if(v1 instanceof Company c)
                    companies.append("\n").append(c.getName());
                vertices.put(v1, v1.toString());
                for(V v2 : graph.adjVertices(v1)){
                    if(vertices.get(v2) != null)
                        continue;
                    edges.append(v1).append(" -- ").
                            append(v2.toString()).append("[label=").
                            append(graph.edge(v1, v2).getWeight()).append("]\n");
                }
            }

            if(flag){
                for(int i=0;i<path.size();i++){
                    if(i == path.size()-1)
                        break;
                    edges.append(path.get(i).toString()).append(" -- ").
                            append(path.get(i + 1)).
                            append("[label=\" >> ").
                            append(++incrementor).
                            append("\" color=blue]\n");
                }
            }
            fw.append(prods.toString()).append(String.valueOf(clients)).append(String.valueOf(companies));
            fw.append(edges).append("}");
            fw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
