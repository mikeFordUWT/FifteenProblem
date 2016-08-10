import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

/**
 * Created by Mike on 7/6/16.
 */
public class FifteenProblem {
    final static ArrayList<Character> LETTERS =
            new ArrayList<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', ' '));
    final static ArrayList<String> SEARCHES =
            new ArrayList<>(Arrays.asList("BFS", "DFS", "GBFS", "ASTAR", "DLS"));


    public static void main (String[] args) {
        long start = System.currentTimeMillis();
        /*For testing manual input because I'm lazy*/
//        args = new String[3];
//        args[0] = "162453B7EA C9D8F";
//        args[1] = "AStar";
//        args[1] = "GBFS";
////        args[1] = "BFS";
////        args[1] = "DLS";
//        args[2] = "h2";
//        args[2] = "12";
        Board myBoard;


        if(args.length > 3){
            System.out.println("TOO MANY ARGUMENTS\nARGS: [\"initialstate\"] [searchmethod] [options]");
        } else if(args.length < 2){
            System.out.println("NOT ENOUGH ARGUMENTS\nARGS: [\"initialstate\"] [searchmethod] [options]");
        } else {
            String toUp = args[0].toUpperCase();
            if(toUp.length()!= 16){
                System.out.println("The start state must be 16 characters long and contain one instance of: " + LETTERS.toString());
                return;
            }
            for(int i = 0; i< toUp.length(); i++){
                if(!LETTERS.contains(toUp.charAt(i))){
                    System.out.println("First argument can only contain the characters: " + LETTERS.toString());
                    return;
                }
            }

            if(!SEARCHES.contains(args[1].toUpperCase())){
                System.out.println("That isn't a searching algorithm I know, my creator is small minded\n" +
                        "Please choose one of the following: " + SEARCHES.toString());
                return;
            }


            if(args[1].toUpperCase().equals("BFS")){ //BREADTH FIRST
                myBoard = new Board(toUp);
                System.out.println(myBoard.BFS());
            } else if(args[1].toUpperCase().equals("DFS")){ //DEPTH FIRST
                myBoard = new Board(toUp);
                System.out.println(myBoard.DFS());
            } else if(args[1].toUpperCase().equals("DLS")){ //DEPTH LIMITED
                boolean parse = isParseable(args[2]);
                if(parse){
                    myBoard = new Board(toUp);
                    int arg = Integer.parseInt(args[2]);
                    System.out.println(myBoard.DLS(arg));
                }else{
                    System.out.println("The second argument is not an Integer");
                }

            } else if(args[1].toUpperCase().equals("GBFS")){ // GREEDY BEST-FIRST
                int heuristic;
                if(args[2] != null && args[2].equals("h1")){
                    heuristic = 1;
                    myBoard = new Board(toUp);
                    System.out.println(myBoard.GBFS(1));
                } else if(args[2] != null && args[2].equals("h2")){
                    heuristic = 2;
                    myBoard = new Board(toUp);
                    System.out.println(myBoard.GBFS(2));

                } else {
                    System.out.println("GBFS only takes two heuristics: h1 AND h2");
                }

            } else if(args[1].toUpperCase().equals("ASTAR")){ //A*
                if(args[2] != null && args[2].equals("h1")){
                    int heuristic = 3;
                    myBoard = new Board(toUp);
                    System.out.println(myBoard.AStar(heuristic));
                } else if(args[2] != null && args[2].equals("h2")){
                    int heuristic = 4;
                    myBoard = new Board(toUp);
                    System.out.println(myBoard.AStar(heuristic));
                } else {
                    System.out.println("AStar only takes two heuristics: h1 AND h2");
                }

            }
//
//            System.out.println("\nRESULTS:");
//            System.out.println(myBoard.toString());
        }

        long end = System.currentTimeMillis();

        System.out.println((end-start)/10);
    }

    static boolean isParseable(String toParse){
        boolean p = true;
        try{
            Integer.parseInt(toParse);
        }catch (NumberFormatException e){
            p = false;
        }
        return p;
    }
}




