import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mike on 7/6/16.
 */
public class PuzzleNode {
    private final ArrayList<String> WIN_STATES =
            new ArrayList<>(Arrays.asList("123456789ABCDEF ", "123456789ABCDFE "));

    private char[][] myData;
    private ArrayList<char[][]> myKids;
    private int myDepth;
    private int myMisplacedTiles;
    private int myTotalMovesToWin;



    public PuzzleNode(char[][] theData){
        myData = theData;
        myKids = new ArrayList<>();
        myMisplacedTiles = 0;
        myTotalMovesToWin = 0;
    }

    public void addChild(char[][] theNode){
        myKids.add(theNode);

    }
    public ArrayList<char[][]> getMyKids() {
        return myKids;
    }


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
     *TODO
     * @return
     */
    public int getMisplacedTiles(){
        return  myMisplacedTiles;
    }

    /**
     *  Setter for misplaced tiles
     * @param newTotal
     */
    public void setMisplacedTiles(int newTotal){
        myMisplacedTiles = newTotal;
    }

    /**
     * Getter for how many moves total to a win state
     * @return total moves away from win
     */
    public int getTotalMovesToWin(){
        return myTotalMovesToWin;
    }

    /**
     *
     * @param newTotal
     */
    public void setTotalMovesToWin(int newTotal){
        myTotalMovesToWin = newTotal;
    }

    /**
     * Checks if a node is in a win state or not.
     * @return true if in win state or false if not in win state
     */
    public boolean winState(){
        return WIN_STATES.contains(this.toString());
    }

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
     * A method that checks equality of nodes by checking their toStrings
     * @return
     */
    public boolean equals(PuzzleNode theOther){
        return this.toString().equals(theOther.toString());
    }
}
