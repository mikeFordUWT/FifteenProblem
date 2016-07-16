import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mike on 7/6/16.
 */
public class FifteenProblem {
    final static ArrayList<Character> LETTERS =
            new ArrayList<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', ' '));
    final static ArrayList<String> SEARCHES =
            new ArrayList<>(Arrays.asList("BFS", "DFS", "GBFS", "AStar", "DLS", "ID"));


    public static void main (String[] args) {
        /*For testing manual input because I'm lazy*/
        args = new String[2];
        args[0] = "123456789ABC DEF";
//        args[0] = "123456789ABCDEF ";
        args[1] = "BFS";
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

            if(!SEARCHES.contains(args[1])){
                System.out.println("That isn't a searching algorithm I know, my creator is small minded\n" +
                        "Please choose one of the following: " + SEARCHES.toString());
                return;
            }
            myBoard = new Board(toUp);

            if(args[1].equals("BFS")){ //BREADTH FIRST
                String result = myBoard.BFS();
                System.out.println(result);
            } else if(args[1].equals("DFS")){ //DEPTH FIRST

            } else if(args[1].equals("DLS")){ //DEPTH LIMITED

            } else if(args[1].equals("GBFS")){ // GREEDY BEST-FIRST

            } else if(args[1].equals("AStar")){ //A*

            } else if(args[1].equals("ID")){ //Iterative Deepening

            }
//
//            System.out.println("\nRESULTS:");
//            System.out.println(myBoard.toString());
        }
    }
}
