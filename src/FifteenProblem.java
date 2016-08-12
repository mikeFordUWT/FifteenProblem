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


            if(args[1].toUpperCase().equals("BFS") && args.length == 2){ //BREADTH FIRST
                myBoard = new Board(toUp);
                System.out.println(myBoard.BFS());
            } else if(args[1].toUpperCase().equals("DFS") && args.length == 2){ //DEPTH FIRST
                myBoard = new Board(toUp);
                System.out.println(myBoard.DFS());
            } else if(args[1].toUpperCase().equals("DLS") && args.length == 3){ //DEPTH LIMITED
                boolean parse = isParseable(args[2]);
                if(parse){
                    myBoard = new Board(toUp);
                    int arg = Integer.parseInt(args[2]);
                    System.out.println(myBoard.DLS(arg)+ "\n");
                }else{
                    System.out.println("The second argument is not an Integer");
                }

            } else if(args[1].toUpperCase().equals("GBFS") && args.length == 3){ // GREEDY BEST-FIRST
                if(args[2] != null && args[2].equals("h1")){
                    myBoard = new Board(toUp);
                    System.out.println(myBoard.GBFS(1)+ "\n");
                } else if(args[2] != null && args[2].equals("h2")){
                    myBoard = new Board(toUp);
                    System.out.println(myBoard.GBFS(2)+ "\n");

                }else if(args[2] == null){
                    System.out.println("NOT ENOUGH ARGUMENTS\nARGS: [\"initialstate\"] [searchmethod] [options]");
                }
                else {
                    System.out.println("GBFS only takes two heuristics: h1 AND h2");
                }

            } else if(args[1].toUpperCase().equals("ASTAR") && args.length == 3){ //A*
                if(args[2] != null && args[2].equals("h1")){
                    myBoard = new Board(toUp);
                    System.out.println(myBoard.AStar(3)+ "\n");
                } else if(args[2] != null && args[2].equals("h2")){
                    myBoard = new Board(toUp);
                    System.out.println(myBoard.AStar(4) + "\n");
                } else if(args[2] == null){
                    System.out.println("NOT ENOUGH ARGUMENTS\nARGS: [\"initialstate\"] [searchmethod] [options]");
                } else {
                    System.out.println("AStar only takes two heuristics: h1 AND h2");
                }

            }else{
                System.out.println("\nNOT ENOUGH ARGUMENTS\nARGS: [\"initialstate\"] [searchmethod] [options]\n");
            }
        }

        long end = System.currentTimeMillis();

        System.out.println((end-start) + " ms" + "\n");
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




