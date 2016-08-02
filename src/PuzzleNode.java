import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A PuzzleNode class that implement comparable for use in a PuzzleTree for a tile puzzle.
 */
public class PuzzleNode implements Comparable<PuzzleNode>{
    private final char BLANK = ' ';
    private final ArrayList<String> WIN_STATES =
            new ArrayList<>(Arrays.asList("123456789ABCDEF ", "123456789ABCDFE "));

    private char[][] myData;
    private ArrayList<PuzzleNode> myKids;
    private PuzzleNode myParent;
    private int myDepth;
    private int myMisplacedTiles;
    private int myTotalMovesToWin;
    private int myHeuristic;
    private int myPathCost;

    private int myAStarValue;

    /**
     * Constructor that just takes a 2d matrix of chars and the depth of the node.
     *
     * @param theData the matrix of chars that represent tiles in the puzzle
     * @param theDepth the Integer representation of the node's depth in the tree
     */
    public PuzzleNode(char[][] theData, int theDepth, PuzzleNode theParent){
        myData = theData;
        myDepth = theDepth;
        myKids = new ArrayList<>();
        myMisplacedTiles = misplacedTiles();
        myTotalMovesToWin = movesToWin();
        myParent = theParent;
    }

    /**
     * A public constructor that takes 3 parameters.
     *
     * @param theData the matrix of chars that represent tiles in the puzzle
     * @param theDepth the Integer representation if the node's depth in the tree
     * @param theHeuristic the heuristic to use for choosing a move
     */
    public PuzzleNode(char[][] theData, int theDepth, int theHeuristic, PuzzleNode theParent){
        myData = theData;
        myDepth = theDepth;
        myHeuristic = theHeuristic;
        myKids = new ArrayList<>();
        myMisplacedTiles = misplacedTiles();
        myTotalMovesToWin = movesToWin();
        myParent = theParent;

        //Check which heuristic to use
        if(theHeuristic == 1){//GBFS h1
            myPathCost = myMisplacedTiles;
        } else if(theHeuristic == 2){// GBFS h2
            myPathCost = myTotalMovesToWin;
        } else if(theHeuristic == 3){
            myPathCost = myMisplacedTiles + this.rowsOutOfPlace();
        } else if(theHeuristic == 4){
            myPathCost = myTotalMovesToWin + this.rowsOutOfPlace();
        }

    }

    /**
     * Add a node to this node's list of children nodes.
     *
     * @param theNode the node to be added
     */
    public void addChild(PuzzleNode theNode){
        myKids.add(theNode);

    }

    /**
     * Get the depth of the node.
     *
     * @return Integer representation of the node's depth in the tree
     */
    public int getDepth(){
        return myDepth;
    }

    /**
     * Get the children of the node.
     *
     * @return A List of children nodes
     */
    public ArrayList<PuzzleNode> getMyKids() {
        return myKids;
    }

    /**
     * Gets the 2d array representation of the tiles in the puzzle node.
     *
     * @return 2d array of tiles place in puzzle
     */
    public char[][] getData(){
        return myData;
    }

    /**
     * Check the current position of tiles in the puzzle.
     *
     * @return a String that represents the state of the puzzle
     */
    public String checkState(){
        StringBuilder toBuild = new StringBuilder();

        for(int i = 0; i < myData.length; i++){
            for(int j = 0; j < myData.length; j++){
                if(j == 0){
                    toBuild.append("[" + myData[i][j] +", ");
                }else if(j == myData.length-1){
                    toBuild.append(myData[i][j] + "]\n");
                }else{
                    toBuild.append(myData[i][j] + ", ");
                }
            }
        }
        return toBuild.toString();
    }

    /**
     * Get the heuristic of the node for which tile to choose.
     *
     * @return Integer representing the node's heuristic
     */
    public int getHeuristic(){
        return myHeuristic;
    }

    /**
     * Gets the parent of the node, for tracing the path.
     *
     * @return PuzzleNode that is the node's parent
     */
    public PuzzleNode getParent(){
        return myParent;
    }

    /**
     * Sets the parent of the node.
     *
     * @param theParent the node's parent
     */
    public void setParent(PuzzleNode theParent){
        myParent = theParent;
    }

    /*
    How many tiles are misplaced in this puzzle state
     */
    private int misplacedTiles(){
        char[][] win1 = {
                {'1', '2', '3', '4'},
                {'5', '6', '7', '8'},
                {'9', 'A', 'B', 'C'},
                {'D', 'E', 'F', ' '}};
        char[][] win2 = {
                {'1', '2', '3', '4'},
                {'5', '6', '7', '8'},
                {'9', 'A', 'B', 'C'},
                {'D', 'F', 'E', ' '}};

        int toReturn = 0;
        for(int i = 0; i < myData.length; i++){
            for(int j = 0; j < myData.length; j++){
                if(myData[i][j] != win1[i][j] && myData[i][j] != win2[i][j]){
                    toReturn++;
                }
            }
        }

        return toReturn;
    }

    /*
    A helper method that find the coordinates of a tile and returns them in an ArrayList.
     */
    private ArrayList<Integer> findTile(char theTile){
        ArrayList<Integer> toReturn = new ArrayList<>();
        for(int i = 0; i<myData.length; i++){
            for(int j = 0; j< myData.length; j++){
                if(myData[i][j] == theTile){
                    toReturn.add(i);
                    toReturn.add(j);
                    break;
                }
            }
        }

        return toReturn;
    }

    /*
    How many moves until the puzzle is in a win state.
     */
    private int movesToWin(){
        int toReturn = 0;

        char[][] win1 = {
                {'1', '2', '3', '4'},
                {'5', '6', '7', '8'},
                {'9', 'A', 'B', 'C'},
                {'D', 'E', 'F', ' '}};

        HashMap<Character, ArrayList<Integer>> coords = new HashMap<>();
        //Put win positions into a HashMap
        for(int i = 0; i <win1.length; i++){
            for(int j = 0; j <win1.length; j++){
                ArrayList<Integer> place = new ArrayList<>();
                place.add(i);
                place.add(j);
                char tile = win1[i][j];
                coords.put(tile,place);
            }
        }

        //loop over current state to see manhattan distance of each tile
        for(int i = 0; i < myData.length; i++){
            for(int j = 0; j < myData.length; j++){
                int row = this.getRow(myData[i][j]);
                int column = this.getColumn(myData[i][j]);
                char tile = myData[i][j];
                if(tile != win1[i][j] && tile!=BLANK){

                    int rowDiff = Math.abs(coords.get(tile).get(0) - row);
                    int colDiff = Math.abs(coords.get(tile).get(1) - column);

                    toReturn += rowDiff + colDiff;

                }

            }
        }




        return toReturn;
    }

    /**
     * Get the amount of misplaced tiles.
     *
     * @return Integer of the misplaced tiles
     */
    public int getMisplacedTiles(){
        return  myMisplacedTiles;
    }

    /**
     * Getter for how many moves total to a win state
     *
     * @return total moves away from win
     */
    public int getTotalMovesToWin(){
        return myTotalMovesToWin;
    }

    /**
     * Checks if a node is in a win state or not.
     * @return true if in win state or false if not in win state
     */
    public boolean winState(){
        return WIN_STATES.contains(this.toString());
    }


    public int getAStarValue(){
        return myAStarValue;
    }

    public void setAStarValue(int theValue){
        myAStarValue = theValue;
    }

    public int getPathCost(){
        return myPathCost;
    }

    public void setPathCost(int newCost){
        myPathCost = newCost;
    }
    /**
     * Get the number of tiles out of place.
     *
     * @return integer representing the tiles not in there proper row
     */
    public int rowsOutOfPlace(){

        char[][] win1 = {
                {'1', '2', '3', '4'},
                {'5', '6', '7', '8'},
                {'9', 'A', 'B', 'C'},
                {'D', 'E', 'F', ' '}};

        HashMap<Character, ArrayList<Integer>> coords = new HashMap<>();
        //Put win positions into a HashMap
        for(int i = 0; i <win1.length; i++){
            for(int j = 0; j <win1.length; j++){
                ArrayList<Integer> place = new ArrayList<>();
                place.add(i);
                place.add(j);
                char tile = win1[i][j];
                coords.put(tile,place);
            }
        }


        int count = 0;
        for(int i = 0; i < myData.length; i++){
            for(int j = 0; j < myData.length; j++){
                int row = this.getRow(myData[i][j]);
                int column = this.getColumn(myData[i][j]);
                char tile = myData[i][j];
                if(tile != win1[i][j] && tile!=BLANK){

                    boolean rowDiff = Math.abs(coords.get(tile).get(0) - row) != 0;
                    boolean colDiff = Math.abs(coords.get(tile).get(1) - column) != 0;

                    if(rowDiff){count++;}
                    if(colDiff){count++;}

                }
            }
        }

        return count;
    }

    /**
     * Gets a string of the puzzle state.
     *
     * @return String representing the puzzle state
     */
    public String toString(){
        StringBuilder toReturn = new StringBuilder();
        for(int i = 0; i < myData.length; i++){
            for(int j = 0; j <myData.length; j++){
                toReturn.append(myData[i][j]);
            }
        }
        return toReturn.toString();
    }

    /**
     * Method for checking if a node is a leaf node.
     *
     * @return true if node has no children
     */
    public boolean leafNode(){
        return myKids.size() == 0;
    }

    public int getRow( char tile){
        for(int row = 0; row < myData.length; row++){
            for(int column = 0; column < myData.length; column++){
                if(myData[row][column] == tile){
                    return row;
                }
            }
        }

        return -1;
    }


    public int getColumn(char tile){
        for(int row = 0; row < myData.length; row++){
            for(int column = 0; column < myData.length; column++){
                if(myData[row][column] == tile){
                    return column;
                }
            }
        }

        return -1;
    }

    /**
     * A method that checks equality of nodes by checking their toStrings
     * @return true if the two nodes are equal
     */
    @Override
    public boolean equals(Object theOther){
        return this.toString().equals(theOther.toString());
    }

    /**
     * Compare method that checks the heuristic to use for comparison.
     *
     * @param o the other node to compare
     * @return -1 if this node is less than o, 0 if they are equal and 1 if this node is greater than o
     */
    @Override
    public int compareTo(PuzzleNode o) {
        int compare;

        if(myPathCost > o.getPathCost()){
            compare = 1;
        } else if(myPathCost < o.getPathCost()){
            compare = -1;
        } else {
            compare = 0;
        }
        return compare;
    }
}
