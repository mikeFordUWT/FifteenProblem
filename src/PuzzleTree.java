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

    /*
        Helper method for building the tree from the root node.
     */
    private void buildTree(PuzzleNode theNode){
        PuzzleNode current  = theNode;
        created.add(current);
        Map<Character, Integer> coor = getBlank(current.getData());
        int i = coor.get('i');
        int j = coor.get('j');
        boolean complete = false;
//        while(!complete){
            char[][] tempData = current.getData();
            int depth = current.getDepth() + 1;
            int lastIndex = tempData.length - 1;
            if(current.getDepth() > myDepth){
                myDepth = current.getDepth();
            }

            //Check right move
            if(j != lastIndex){
                tempData = makeCopy(current.getData());
                PuzzleNode right = new PuzzleNode(moveRight(tempData), depth);
                if(!created.contains(right)){

                    created.add(right);
                    current.addChild(right);
                }
            }

            //Check down move
            if(i != lastIndex){
                tempData = makeCopy(current.getData());
                PuzzleNode down = new PuzzleNode(moveDown(tempData), depth);
                if(!created.contains(down)){
                    created.add(down);
                    current.addChild(down);
                }
            }

            //Check left move
            if(j != 0){
                tempData = makeCopy(current.getData());
                PuzzleNode left = new PuzzleNode(moveLeft(tempData), depth);
                if(!created.contains(left)){
                    created.add(left);
                    current.addChild(left);
                }
            }

            //Check up move
            if(i != 0){
                tempData = makeCopy(current.getData());
                PuzzleNode up = new PuzzleNode(moveUp(tempData), depth);
                if(!created.contains(up)){
                    created.add(up);
                    current.addChild(up);
                }
            }
        System.out.println(created.size());
            if(current.leafNode()){
                //TODO
                System.out.println("LEAF!!!");
            } else {
                ArrayList<PuzzleNode> kids = current.getMyKids();
                for(int k = 0; k < kids.size(); k++ ){

                    buildTree(kids.get(k));
                }
            }


//        }

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

    /*
        Helper method to move blank tile up.
     */
    private char[][] moveUp(char[][] boardState){
        char[][] toReturn = boardState;
        Map<Character, Integer> blankSpot = getBlank(toReturn);
        int i = blankSpot.get('i');
        int j = blankSpot.get('j');
        char blank = toReturn[i][j];
        char swap = toReturn[i-1][j];
        toReturn[i-1][j] = blank;
        toReturn[i][j] = swap;
        return toReturn;
    }

    /*
        Helper method to move blank tile down.
     */
    private char[][] moveDown(char[][] boardState){
        char[][] toReturn = boardState;
        Map<Character, Integer> blankSpot = getBlank(toReturn);
        int i = blankSpot.get('i');
        int j = blankSpot.get('j');
        char blank = toReturn[i][j];
        char swap = toReturn[i+1][j];
        toReturn[i+1][j] = blank;
        toReturn[i][j] = swap;

        return toReturn;
    }

    /*
        Helper method to move blank tile right.
     */
    private char[][] moveRight(char[][] boardState){
        char[][] toReturn = boardState;
        Map<Character, Integer> blankSpot = getBlank(toReturn);
        int i = blankSpot.get('i');
        int j = blankSpot.get('j');
        if(j>= 3){
            System.out.println("I: " + i + " | J: " + j);
        }
        char blank = toReturn[i][j];
        char swap = toReturn[i][j+1];
        toReturn[i][j] = swap;
        toReturn[i][j+1] = blank;
        return toReturn;
    }

    /*
        Helper method to move blank tile left.
     */
    private char[][] moveLeft(char[][] boardState){
        char[][] toReturn = boardState;
        Map<Character, Integer> blankSpot = getBlank(toReturn);
        int i = blankSpot.get('i');
        int j = blankSpot.get('j');
        char blank = toReturn[i][j];
        char swap = toReturn[i][j-1];
        toReturn[i][j] = swap;
        toReturn[i][j-1] = blank;
        return toReturn;
    }

    /*
        Helper method for creating a copy of a 2d array
     */
    private char[][] makeCopy(char[][] toCopy){
        char[][] newCopy = new char[toCopy.length][toCopy.length];
        for(int i = 0; i < toCopy.length; i++){
            for(int j = 0; j<toCopy.length; j++){
                newCopy[i][j] = toCopy[i][j];
            }
        }
        return newCopy;
    }

}
