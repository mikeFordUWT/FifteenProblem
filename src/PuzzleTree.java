import java.util.*;

/**
 * A Tree data structure for use with PuzzleNodes
 *
 * @author Michael Ford
 * TCSS 435
 * Summer 2016
 */
public class PuzzleTree {
    private int myDepth;
    private PuzzleNode myRootNode;
    ArrayList<PuzzleNode> created;

    /**
     * Parameterized constructor.
     *
     * @param theRootNode the root of the tree
     */
    public PuzzleTree(PuzzleNode theRootNode){
        myRootNode = theRootNode;
        myDepth = 0;
        created = new ArrayList<>();
    }

    /**
     * Retrieve the root of the tree.
     *
     * @return the root of the tree
     */
    public PuzzleNode getRoot() {
        return myRootNode;
    }

    /**
     * Set the root of the tree.
     *
     * @param theRoot the new root of the tree
     */
    public void setRoot(PuzzleNode theRoot){
        myRootNode = theRoot;
    }

    /**
     * Retrieve the current depth of the entire tree.
     *
     * @return the tree's overall depth
     */
    public int getDepth() {
        return myDepth;
    }

    /**
     * Set the depth of the tree.
     *
     * @param theDepth the new depth of the tree.
     */
    public void setDepth(int theDepth) {
        myDepth = theDepth;
    }

    /**
     * Retrieve the path from the root to the goal state.
     * @param goalState the goal node
     * @return a list in the order from root to goal
     */
    public ArrayList<PuzzleNode> findPathFromGoal(PuzzleNode goalState){
        ArrayList<PuzzleNode> path = new ArrayList<>();
        PuzzleNode current = goalState;
        path.add(current);
        while(current.getParent() != null){
            System.out.println(current.checkState());
            current = current.getParent();
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }
}
