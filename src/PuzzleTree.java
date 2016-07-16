/**
 * Created by Mike on 7/6/16.
 */
public class PuzzleTree {
    private int myDepth;
    private int myExpanded;
    private int myCreatedNodes;
    private int myMaxFringe;
    private PuzzleNode myRootNode;

    public PuzzleTree(char[][] theRootNode){
        myRootNode = new PuzzleNode(theRootNode, 0);
        myDepth = 0;
        myExpanded = 0;
        myCreatedNodes = 1;
        myMaxFringe = 0;
    }

    public PuzzleNode getRoot() {
        return myRootNode;
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
}
