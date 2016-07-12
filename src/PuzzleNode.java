import java.util.ArrayList;

/**
 * Created by Mike on 7/6/16.
 */
public class PuzzleNode {
    private char[][] myData;
    private ArrayList<char[][]> myKids;
    private int myDepth;

    public PuzzleNode(char[][] theData){
        myData = theData;
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

    public String toString(){
        StringBuilder toReturn = new StringBuilder();
        for(int i = 0; i < myData.length; i++){
            for(int j = 0; j <myData.length; j++){
                toReturn.append(myData[i][j]);
            }
        }
        return toReturn.toString();
    }
}
