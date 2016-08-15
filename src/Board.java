
import java.util.*;

/**
 * Board class that handles all algorithms.
 *
 * @author Michael Ford
 * TCSS 435
 * Summer 2016
 */
public class Board {
    private final char BLANK = ' ';
    private char[][] myBoard;

    /**
     * Constructor for when no heuristic is needed.
     *
     * @param startState String of the start state of the tree
     */
    public Board(String startState){
        myBoard = new char[4][4];
        int k = 0;
        for(int i=0; i< 4; i++){
            for(int j = 0; j <4; j++){
                myBoard[i][j] = startState.charAt(k);
                k++;
            }
        }
    }

    /**
     * Runs a Breadth First Search of the Tree.
     *
     * @return String with results
     */
    public String BFS(){
        Queue<PuzzleNode> fringe = new LinkedList<>();
        LinkedList<PuzzleNode> created = new LinkedList<>(); //store the string
        ArrayList<PuzzleNode> visited = new ArrayList<>();

        PuzzleNode current = new PuzzleNode(myBoard, 0, null);
        PuzzleTree tree = new PuzzleTree(current);
        created.add(current);

        fringe.offer(current);
        int fringeSize = fringe.size();
        fringe.poll();
        int row = current.getRow(BLANK);
        int column = current.getColumn(BLANK);
        int createdNodes = 1;
        int expanded = 1;


        boolean win = false;
        while(!win){//until a win state is found

            if(current.winState()){
                win = true;
            }

            if(current.getDepth()>tree.getDepth()){
                tree.setDepth(current.getDepth());
            }

            expanded++;
            visited.add(current);

            ArrayList<PuzzleNode> moves = movesNoH(current, row, column);
            for(int i = 0; i < moves.size(); i++){
                if(!created.contains(moves.get(i))){
                    created.add(moves.get(i));
                    if(!visited.contains(moves.get(i))){
                        current.addChild(moves.get(i));
                        fringe.offer(moves.get(i));
                        createdNodes++;
                    }
                }
            }

            if(fringe.size() > fringeSize){
                fringeSize = fringe.size();
            }

            if(!fringe.isEmpty() && !win){
                current = fringe.poll();
                row = current.getRow(BLANK);
                column = current.getColumn(BLANK);
            }
        }
        PuzzleNode pathNode = current;
        ArrayList<PuzzleNode> path = current.findPathFromGoal();
        //uncomment for printing out path
//        for(int i = 0; i<path.size(); i++){
//            System.out.println(i+1);
//            System.out.println(path.get(i).checkState());
//        }



        return "PATH LENGTH: " + path.size()+"\n" + tree.getDepth() + " "+ createdNodes + " " + expanded + " "+ fringeSize;
    }

    //TODO
    /**
     * Runs a Depth First search on the tree.
     *
     * @return String with results from the search
     */
    public String DFS(){
        Stack<PuzzleNode> fringe = new Stack<>();
        LinkedList<PuzzleNode> created = new LinkedList<>(); //store the string
        ArrayList<PuzzleNode> visited = new ArrayList<>();

        PuzzleNode root = new PuzzleNode(myBoard, 0, null);
        PuzzleTree tree = new PuzzleTree(root);
        PuzzleNode current = root;
        created.add(current);

        fringe.push(current);
        int fringeSize = fringe.size();
//        fringe.pop();
        int createdNodes = 1;
        int expanded = 0;
        int row;
        int column;
        boolean win = false;
        while(!win && !fringe.isEmpty()){
            current = fringe.pop();
            row = current.getRow(BLANK);
            column = current.getColumn(BLANK);
            if(current.winState()){
                win = true;
            }else{
                expanded++;
            }

            if(current.getDepth() > tree.getDepth()){
                tree.setDepth(current.getDepth());
            }

            expanded++;
            visited.add(current);

            ArrayList<PuzzleNode> moves = movesDFS(current, row, column);
            for(int i = 0; i < moves.size(); i++){
                if(!created.contains(moves.get(i))){
                    created.add(moves.get(i));
                    if(!visited.contains(moves.get(i))){
                        current.addChild(moves.get(i));
                        fringe.push(moves.get(i));
                        createdNodes++;
                    }
                }
            }

            if(fringe.size() > fringeSize){
                fringeSize = fringe.size();
            }
        }

        ArrayList<PuzzleNode> path = current.findPathFromGoal();
        return "PATH SIZE: "+ path.size()+ "\n"+ tree.getDepth() + " "+ createdNodes + " " + expanded + " "+ fringeSize;
    }

    /**
     * A Depth Limited Search
     *
     * @param theDepth how deep can the tree go
     * @return the information of the search if a result is found and -1 if goal not found
     */
    public String DLS(int theDepth){
        String toReturn;
        Stack<PuzzleNode> fringe = new Stack<>();
        LinkedList<PuzzleNode> created = new LinkedList<>(); //store the string
        ArrayList<PuzzleNode> visited = new ArrayList<>();

        PuzzleNode root = new PuzzleNode(myBoard, 0, 0, null);
        PuzzleTree tree = new PuzzleTree(root);
        PuzzleNode current = root;
        created.add(current);
        PuzzleNode goal = current;
        fringe.push(current);

        int fringeSize = fringe.size();
        int row = current.getRow(BLANK);
        int column = current.getColumn(BLANK);
        boolean win = false;
        int bad = 0;
        int expanded = 0;
        int createdNodes = 1;
        while(!win){
            if(current.winState()){
                win = true;
                goal = current;
            }else{
                expanded++;
            }

            if(current.getDepth() > tree.getDepth()){
                tree.setDepth(current.getDepth());
            }

            visited.add(current);
            ArrayList<PuzzleNode> moves = movesDFS(current, row, column);

            createdNodes += moves.size();
            for(int i = 0; i< moves.size(); i++){
                if(!created.contains(moves.get(i))){
                    created.add(moves.get(i));
                    if(!visited.contains(moves.get(i)) && moves.get(i).getDepth()<= theDepth){
                        current.addChild(moves.get(i));
                        fringe.push(moves.get(i));
                        createdNodes++;
                    }
                }
            }

            if(fringe.size() > fringeSize){
                fringeSize = fringe.size();
            }

            if(fringe.isEmpty()){
                bad = -1;
                win = true;
            }else{
                current = fringe.pop();
                row = current.getRow(BLANK);
                column = current.getColumn(BLANK);
            }
        }

        if(bad == -1){
            toReturn = "-1";
        }else{
            current = goal;
            ArrayList<PuzzleNode> path = current.findPathFromGoal();
            //uncomment for printing out path
//        for(int i = 0; i<path.size(); i++){
//            System.out.println(i+1);
//            System.out.println(path.get(i).checkState());
//        }

            System.out.println("PATH LENGTH: " + path.size());

            toReturn = tree.getDepth() + " "+ createdNodes + " " + expanded + " "+ fringeSize;
        }
        return toReturn;
    }


    /**
     * A Greedy Best First Search
     *
     * @param theHeuristic the heuristic to use
     * @return the results of the search
     */
    public String GBFS(int theHeuristic){
        int heuristic = theHeuristic;
        ArrayList<PuzzleNode> created = new ArrayList<>();
        ArrayList<PuzzleNode> visited = new ArrayList<>();
        PriorityQueue<PuzzleNode> fringe = new PriorityQueue<>();

        int createdNodes = 0;
        int expanded = 0;

        PuzzleNode current = new PuzzleNode(myBoard, 0, heuristic, null);
        PuzzleNode goal = current;
        PuzzleTree tree = new PuzzleTree(current);
        created.add(current);
        createdNodes++;
        int fringeSize = 1;
        int row = current.getRow(BLANK);
        int column = current.getColumn(BLANK);
        boolean win = false;
        while(!win){
            if(current.winState()){
                win = true;
                goal = current;
            }else{
                expanded++;
            }

            if(current.getDepth() > tree.getDepth()){
                tree.setDepth(current.getDepth());
            }

            visited.add(current);

            ArrayList<PuzzleNode> moves = movesWithH(current, row, column);
            if(!win){
                createdNodes += moves.size();
            }

            for(int i = 0; i < moves.size(); i++){
                if(!visited.contains(moves.get(i))){
                    created.add(moves.get(i));
                    current.addChild(moves.get(i));
                    fringe.offer(moves.get(i));
                }
            }

            if(fringe.size() > fringeSize){
                fringeSize = fringe.size();
            }

            if(!fringe.isEmpty()){
                current = fringe.poll();
                row = current.getRow(BLANK);
                column = current.getColumn(BLANK);
            }
        }

        current = goal;
        ArrayList<PuzzleNode> path = current.findPathFromGoal();
        //uncomment for printing out path
//        for(int i = 0; i<path.size(); i++){
//            System.out.println(i+1);
//            System.out.println(path.get(i).checkState());
//        }
        System.out.println("PATH LENGTH: " + path.size());
        return tree.getDepth() + " "+ createdNodes + " " + expanded + " "+ fringeSize;
    }

    /**
     * Runs the AStar algorithm.
     * @return A string with the results of the search algorithm
     */

    public String AStar(int theHeuristic){
        /*
            Data Structures
         */
        PriorityQueue<PuzzleNode> open = new PriorityQueue<>();
        ArrayList<PuzzleNode> closed = new ArrayList<>();
        HashMap<String, Integer> pathCosts = new HashMap<>();

        //create the first node
        PuzzleNode root = new PuzzleNode(myBoard, 0, theHeuristic, null);
        //add the node to map of costs
        pathCosts.put(root.toString(), root.getPathCost());

        //counter for amount of nodes
        int createdNodes = 1;

        //A tree to build from the root node
        PuzzleTree tree = new PuzzleTree(root);

        //place first node in the open Priority Queue
        open.offer(root);

        //counter to track how big the fringe will get
        int maxFringe = open.size();

        //A node used for back tracking.
        PuzzleNode goal = root;

        //Counter to track the amount of nodes that are expanded
        int expanded = 0;

        //used to find the row and column of the blank tile
        int row;
        int column;

        //boolean to keep the loop going
        boolean win = false;

        //always updating the current node
        PuzzleNode current;

        while(!win && !open.isEmpty()){
            current = open.poll();
            closed.add(current);

            row = current.getRow(BLANK);
            column = current.getColumn(BLANK);

            if(current.winState()){
                win = true;
                goal = current;
            }else{
                expanded++;
            }
            if(current.getDepth() > tree.getDepth()){
                tree.setDepth(current.getDepth());
            }

            ArrayList<PuzzleNode> neighbors = movesWithH(current, row, column);
            if(!win){
                createdNodes += neighbors.size();
            }


            for(int i = 0; i < neighbors.size(); i++){
                PuzzleNode neighbor = neighbors.get(i);
                if(!closed.contains(neighbor)){
                    int newPath = neighbor.getPathCost() + current.getPathCost();
                    int oldPath = Integer.MIN_VALUE;
                    if(pathCosts.containsKey(neighbor.toString())){
                        oldPath = pathCosts.get(neighbor.toString());
                    }

                    boolean inOpen = open.contains(neighbor);

                    if((newPath < oldPath) || !inOpen){
                        neighbor.setPathCost(newPath);
                        pathCosts.put(neighbor.toString(), newPath);
                        neighbor.setParent(current);
                        if(!inOpen){
                            open.offer(neighbor);
                        }else{
                            boolean remove = open.remove(neighbor);
                            open.offer(neighbor);
                        }
                    }
                }
            }

            if(open.size() > maxFringe){
                maxFringe = open.size();
            }

        }

        current = goal;
        ArrayList<PuzzleNode> path = current.findPathFromGoal();
        //uncomment for printing out path
//        for(int i = 0; i<path.size(); i++){
//            System.out.println(i+1);
//            System.out.println(path.get(i).checkState());
//        }

        System.out.println("PATH LENGTH: " + path.size());

        return tree.getDepth() + " "+ createdNodes + " " + expanded + " "+ maxFringe;
    }


    /*
    Helper method to figure out where the blank tile is.
     */
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

    /*
        A helper method for DFS.
     */
    private ArrayList<PuzzleNode> movesDFS(PuzzleNode current, int i, int j){
        ArrayList<PuzzleNode> neighbors = new ArrayList<>();
        //Handle top edge case
        if(i != 0){//visit up first
            char[][] data = makeCopy(current.getData());
            PuzzleNode up = new PuzzleNode(moveUp(data), current.getDepth() + 1, current);
            neighbors.add(up);
        }

        //Handle left edge case
        if(j != 0){
            char[][] data = makeCopy(current.getData());
            PuzzleNode left = new PuzzleNode(moveLeft(data), current.getDepth()+ 1, current);
            neighbors.add(left);
        }

        //Handle bottom edge case
        if(i != myBoard.length - 1){
            char[][] data = makeCopy(current.getData());
            PuzzleNode down = new PuzzleNode(moveDown(data), current.getDepth() + 1, current);
            neighbors.add(down);
        }

        //Handle right edge case
        if(j != myBoard.length - 1){
            char[][] data = makeCopy(current.getData());
            PuzzleNode right = new PuzzleNode(moveRight(data), current.getDepth() + 1, current);
            neighbors.add(right);
        }

        return neighbors;
    }

    /*
    Helper method that returns a collection of possible moves
     */
    private ArrayList<PuzzleNode> movesNoH(PuzzleNode current, int i, int j){

        ArrayList<PuzzleNode> ns = new ArrayList<>();
        //Handle right edge case
        if(j != myBoard.length - 1){
            char[][] data = makeCopy(current.getData());
            PuzzleNode right = new PuzzleNode(moveRight(data), current.getDepth() + 1, current);
            ns.add(right);
        }

        //Handle bottom edge case
        if(i != myBoard.length - 1){
            char[][] data = makeCopy(current.getData());
            PuzzleNode down = new PuzzleNode(moveDown(data), current.getDepth() + 1, current);
            ns.add(down);
        }

        //Handle left edge case
        if(j != 0){
            char[][] data = makeCopy(current.getData());
            PuzzleNode left = new PuzzleNode(moveLeft(data), current.getDepth()+ 1, current);
            ns.add(left);
        }

        //Handle top edge case
        if(i != 0){
            char[][] data = makeCopy(current.getData());
            PuzzleNode up = new PuzzleNode(moveUp(data), current.getDepth() + 1, current);
            ns.add(up);
        }
        return ns;
    }

    private int getRow(char[][] state, char tile){
        for(int row = 0; row < state.length; row++){
            for(int column = 0; column < state.length; column++){
                if(state[row][column] == tile){
                    return row;
                }
            }
        }
        return -1;
    }

    private int getColumn(char[][] state, char tile){
        for(int row = 0; row < state.length; row++){
            for(int column = 0; column < state.length; column++){
                if(state[row][column] == tile){
                    return column;
                }
            }
        }
        return -1;
    }

    /*
        Helper method that find the moves of a node that has a heuristic
     */
    private ArrayList<PuzzleNode> movesWithH(PuzzleNode current, int i, int j){

        ArrayList<PuzzleNode> ns = new ArrayList<>();
        //Handle right edge case
        if(j != myBoard.length - 1){
            char[][] data = makeCopy(current.getData());
            PuzzleNode right = new PuzzleNode(moveRight(data), current.getDepth() + 1, current.getHeuristic(), current);
            ns.add(right);
        }

        //Handle bottom edge case
        if(i != myBoard.length - 1){
            char[][] data = makeCopy(current.getData());
            PuzzleNode down = new PuzzleNode(moveDown(data), current.getDepth() + 1, current.getHeuristic(), current);
            down.setParent(current);
            ns.add(down);
        }

        //Handle left edge case
        if(j != 0){
            char[][] data = makeCopy(current.getData());
            PuzzleNode left = new PuzzleNode(moveLeft(data), current.getDepth()+ 1, current.getHeuristic(), current);
            ns.add(left);
        }

        //Handle top edge case
        if(i != 0){
            char[][] data = makeCopy(current.getData());
            PuzzleNode up = new PuzzleNode(moveUp(data), current.getDepth() + 1, current.getHeuristic(), current);
            up.setParent(current);
            ns.add(up);
        }
        return ns;
    }
}
