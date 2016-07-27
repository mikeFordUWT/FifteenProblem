import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mike on 7/6/16.
 */
public class PuzzleTree {
    private int myDepth;
    private int myExpanded;
    private int myCreatedNodes;
    private int myMaxFringe;
    private PuzzleNode myRootNode;
    ArrayList<PuzzleNode> created;

    public PuzzleTree(){
        myDepth = 0;
        myExpanded = 0;
        myCreatedNodes = 0;
        myMaxFringe = 0;
        created = new ArrayList<>();
        myRootNode = null;
    }

    public PuzzleTree(PuzzleNode theRootNode){
        myRootNode = theRootNode;
        myDepth = 0;
        myExpanded = 0;
        myCreatedNodes = 1;
        myMaxFringe = 0;
        created = new ArrayList<>();
    }

    public PuzzleNode getRoot() {
        return myRootNode;
    }

    public void setRoot(PuzzleNode theRoot){
        myRootNode = theRoot;
    }

    public int getDepth() {
        return myDepth;
    }

    public void setDepth(int theDepth) {
        myDepth = theDepth;
    }

    public void incrementDepth(){
        myDepth++;
    }

    public int getMaxFringe() {
        return myMaxFringe;
    }

    public void incrementMaxFringe(){
        myMaxFringe++;
    }

    public int getExpanded() {
        return myExpanded;
    }

    public void incrementExpanded(){
        myExpanded++;
    }

    public int getCreated() {
        return myCreatedNodes;
    }

    public void incrementCreated(){
        myCreatedNodes++;
    }

    public void backtrack(PuzzleNode goalState){
        PuzzleNode current = goalState;
        while(current.getParent() != null){
            System.out.println(current.checkState());
            current = current.getParent();
        }

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
