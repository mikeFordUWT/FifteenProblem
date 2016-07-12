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
        myRootNode = new PuzzleNode(theRootNode);
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

    public int getMaxFringe() {
        return myMaxFringe;
    }

    public int getExpanded() {
        return myExpanded;
    }

    public int getCreated() {
        return myCreatedNodes;
    }
}
