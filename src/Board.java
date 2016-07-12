import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mike on 7/6/16.
 */
public class Board {

    private final ArrayList<String> WIN_STATES =
            new ArrayList<>(Arrays.asList("123456789ABCDEF ", "123456789ABCDFE "));
    private char[][] myBoard;
    private PuzzleTree myTree;
    private Map<Character, Integer> blankPosition;

    public Board(String startState){
        myBoard = new char[4][4];
        int k = 0;
        for(int i=0; i< 4; i++){
            for(int j = 0; j <4; j++){
                myBoard[i][j] = startState.charAt(k);
                k++;
            }
        }

        myTree = new PuzzleTree(myBoard);
        System.out.println(myTree.getRoot().checkState());
        blankPosition = getBlank(myBoard);
        System.out.println("Current Blank Location: ROW: " + blankPosition.get('i')
                + " COLUMN: " + blankPosition.get('j'));
    }


    public char[][] getMyBoard() {
        return myBoard;
    }

    //TODO
    public String BFS(){
        StringBuilder toReturn = new StringBuilder();

        return toReturn.toString();
    }

    //TODO
    public String DFS(){
        StringBuilder toReturn = new StringBuilder();

        return toReturn.toString();
    }

    //TODO
    public String GBFS(){
        StringBuilder toReturn = new StringBuilder();

        return toReturn.toString();
    }


    public String toString(){
        return myTree.getDepth() + ", " + myTree.getCreated() + ", " + myTree.getExpanded() + ", " + myTree.getMaxFringe();

    }

    private Boolean winState(String stateToCheck){
        return WIN_STATES.contains(stateToCheck);
    }

    private Map getBlank(char[][] currentBoard){

        Map<Character, Integer> cooridinates = new HashMap<>();

        for(int i = 0;i< currentBoard.length; i++){
            for(int j=0; j<currentBoard.length; j++){
                if(currentBoard[i][j] == ' '){
                    cooridinates.put('i', i);
                    cooridinates.put('j', j);
                    break;
                }
            }
        }

        return cooridinates;
    }
}
