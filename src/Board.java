
import java.util.*;

/**
 * Created by Mike on 7/6/16.
 */
public class Board {
    private final char BLANK = ' ';
    private final ArrayList<String> WIN_STATES =
            new ArrayList<>(Arrays.asList("123456789ABCDEF ", "123456789ABCDFE "));
    private char[][] myBoard;
    private PuzzleTree myTree;
    private PuzzleNode myRoot;
    private Map<Character, Integer> blankPosition;
    private int myHeuristic;

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
        PuzzleNode root = new PuzzleNode(myBoard, 0);
        myTree = new PuzzleTree(root);
        System.out.println(myTree.getRoot().checkState());
        blankPosition = getBlank(myBoard);


    }

    /**
     * Board constructor for searches with a Heuristic
     *
     * @param startState 2D Array start state for puzzle
     * @param theHeuristic the Heuristic to be uses: 1 for misplaced tiles, 2 for total moves until win
     */
    public Board(String startState, int theHeuristic){
        myBoard = new char[4][4];
        int k = 0;
        for(int i=0; i< 4; i++){
            for(int j = 0; j <4; j++){
                myBoard[i][j] = startState.charAt(k);
                k++;
            }
        }
        myHeuristic = theHeuristic;
        PuzzleNode root = new PuzzleNode(myBoard, 0, theHeuristic);
        myTree = new PuzzleTree(root);
        System.out.println(myTree.getRoot().checkState());
        blankPosition = getBlank(myBoard);
    }

    /**
     * Get the state of the board.
     *
     * @return 2D Array of the board state
     */
    public char[][] getMyBoard() {
        return myBoard;
    }


    public String BFS(){
        Queue<PuzzleNode> fringe = new LinkedList<>();
        LinkedList<PuzzleNode> created = new LinkedList<>(); //store the string
        ArrayList<PuzzleNode> visited = new ArrayList<>();

        PuzzleNode current = new PuzzleNode(myBoard, 0);
        PuzzleTree tree = new PuzzleTree(current);
        tree.incrementCreated();
        created.add(current);
        fringe.offer(current);
        int fringeSize = fringe.size();
        boolean win = false;

        while(!win){//until a win state is found
            int row;
            int column;
            if(!fringe.isEmpty()){
                current = fringe.poll();
                row = getRow(current.getData(), BLANK);
                column = getColumn(current.getData(), BLANK);
                visited.add(current);
                tree.incrementExpanded();

                if(current.getDepth() > tree.getDepth()){
                    tree.setDepth(current.getDepth());
                }
            } else {
                row=-1;
                column=-1;
            }

            if(current.winState()){
                win = true;
            }

            if(row >=0 && column >=0){ // we have a valid row and column
                ArrayList<PuzzleNode> neighbors = movesNoH(current, row, column);
                for(int i = 0; i< neighbors.size(); i++){
                    fringe.offer(neighbors.get(i)); //add each node to the fringe
                    tree.incrementCreated();
                }

                if(fringe.size() > fringeSize){
                    fringeSize = fringe.size();
                }
            }
        }

        return tree.getDepth() + " "+ tree.getCreated() + " " + tree.getExpanded() + " "+ fringeSize;
    }

    /**
     * Runs a Breadth First Search of the Tree.
     *
     * @return String with results
     */
    public String BFS1(){
        Queue<PuzzleNode> fringe = new LinkedList<>();
        LinkedList<PuzzleNode> created = new LinkedList<>(); //store the string
        ArrayList<PuzzleNode> visited = new ArrayList<>();

        PuzzleNode current = new PuzzleNode(myBoard, 0);
        PuzzleTree tree = new PuzzleTree(current);


        int i = blankPosition.get('i');
        int j = blankPosition.get('j');
        boolean win = false;
        fringe.offer(current);
        int fringeSize = fringe.size();
        while(!win){
            tree.incrementExpanded();

            if(current.winState()){
                win = true;
            }
            if(current.getDepth() > tree.getDepth()){
                tree.setDepth(current.getDepth());
            }
            created.add(current);
            tree.incrementCreated();
            visited.add(current);

            if(j != 3){//MOVE RIGHT
                char[][] state = makeCopy(current.getData());
                PuzzleNode right = new PuzzleNode(moveRight(state), current.getDepth() + 1);
                if(created.contains(right)){
//                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(right.toString());
                    created.addLast(right);
                    tree.incrementCreated();
//                    created.add(right);
                    if(visited.contains(right)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(right);
                        fringe.offer(right);
                    }
                }
            }

            if(i != 3){//MOVE DOWN
                char[][] state = makeCopy(current.getData());
                PuzzleNode down = new PuzzleNode(moveDown(state), current.getDepth() + 1);
                if(created.contains(down)){
//                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(down.toString());
                    created.addLast(down);
                    tree.incrementCreated();
//                    created.add(down);
                    if(visited.contains(down)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(down);
                        fringe.offer(down);
                    }
                }
            }

            if(j != 0){//MOVE LEFT
                char[][] state = makeCopy(current.getData());
                PuzzleNode left = new PuzzleNode(moveLeft(state), current.getDepth() + 1);
                if(created.contains(left)){
//                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(left.toString());
                    created.addLast(left);
                    tree.incrementCreated();
//                    created.add(left);
                    if(visited.contains(left)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(left);
                        fringe.offer(left);
                    }
                }
            }

            if(i != 0){//MOVE UP
                char[][] state = makeCopy(current.getData());
                PuzzleNode up = new PuzzleNode(moveUp(state), current.getDepth() + 1);
                if(created.contains(up)){
//                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(up.toString());
                    created.addLast(up);
                    tree.incrementCreated();
//                    created.add(up);
                    if(visited.contains(up)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(up);
                        fringe.offer(up);
                    }
                }
            }

            if(fringe.size()>fringeSize){
                fringeSize = fringe.size();
            }
            if(!fringe.isEmpty()){
                current = fringe.poll();
                Map<Character, Integer> coord = getBlank(current.getData());
                i = coord.get('i');
                j = coord.get('j');
            }


        }



        return tree.getDepth() + " "+ tree.getCreated() + " " + tree.getExpanded() + " "+ fringeSize;
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

        PuzzleTree tree = myTree;
        PuzzleNode current = tree.getRoot();
        created.add(current);
        tree.incrementCreated();
        int row = getRow(current.getData(), ' ');
        int column = getColumn(current.getData(), ' ');
        int i = blankPosition.get('i');
        int j = blankPosition.get('j');
        boolean win = false;
        int fringeSize = fringe.size();
        while(!win){
            tree.incrementExpanded();

            if(current.winState()){
                win = true;
            }
            if(current.getDepth() > tree.getDepth()){
                tree.setDepth(current.getDepth());
            }


            visited.add(current);

            if(j != 3){//MOVE RIGHT
                char[][] state = makeCopy(current.getData());
                PuzzleNode right = new PuzzleNode(moveRight(state), current.getDepth() + 1);
                if(created.contains(right)){
                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(right.toString());
                    created.addLast(right);
                    tree.incrementCreated();
//                    created.add(right);
                    if(visited.contains(right)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(right);
                        fringe.push(right);
                    }
                }
            }

            if(i != 3){//MOVE DOWN
                char[][] state = makeCopy(current.getData());
                PuzzleNode down = new PuzzleNode(moveDown(state), current.getDepth() + 1);
                if(created.contains(down)){
                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(down.toString());
                    created.addLast(down);
                    tree.incrementCreated();
//                    created.add(down);
                    if(visited.contains(down)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(down);
                        fringe.push(down);
                    }
                }
            }

            if(j != 0){//MOVE LEFT
                char[][] state = makeCopy(current.getData());
                PuzzleNode left = new PuzzleNode(moveLeft(state), current.getDepth() + 1);
                if(created.contains(left)){
                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(left.toString());
                    created.addLast(left);
                    tree.incrementCreated();
//                    created.add(left);
                    if(visited.contains(left)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(left);
                        fringe.push(left);
                    }
                }
            }

            if(i != 0){//MOVE UP
                char[][] state = makeCopy(current.getData());
                PuzzleNode up = new PuzzleNode(moveUp(state), current.getDepth() + 1);
                if(created.contains(up)){
                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(up.toString());
                    created.addLast(up);
                    tree.incrementCreated();
//                    created.add(up);
                    if(visited.contains(up)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(up);
                        fringe.push(up);
                    }
                }
            }

            if(fringe.size()>fringeSize){
                fringeSize = fringe.size();
            }
            if(!fringe.isEmpty()){
                current = fringe.pop();
                Map<Character, Integer> coord = getBlank(current.getData());
                i = coord.get('i');
                j = coord.get('j');
            }
        }



        return tree.getDepth() + " "+ tree.getCreated() + " " + tree.getExpanded() + " "+ fringeSize;
    }

    //TODO
    /**
     *
     * @return
     */
    public String GBFS(){
        ArrayList<PuzzleNode> created = new ArrayList<>();
        ArrayList<PuzzleNode> visited = new ArrayList<>();

        //which heuristic are we using?
        int heuristic = myHeuristic;
        myRoot = new PuzzleNode(myBoard, 0, heuristic);


        PuzzleTree tree = new PuzzleTree(myRoot);
        PuzzleNode current = tree.getRoot();
        created.add(current);
        tree.incrementCreated();

        //the position of the blank tile
        int i = blankPosition.get('i');
        int j = blankPosition.get('j');

        //runs the while loop like a boss
        boolean win = false;
        int fringeSize = 0;
        while(!win){
            PriorityQueue<PuzzleNode> fringe = new PriorityQueue<>();
            tree.incrementExpanded();

            if(current.winState()){
                win = true;
            }
            if(current.getDepth() > tree.getDepth()){
                tree.setDepth(current.getDepth());
            }

            if(j != 3){//MOVE RIGHT
                char[][] state = makeCopy(current.getData());
                PuzzleNode right = new PuzzleNode(moveRight(state), current.getDepth() + 1);
                if(created.contains(right)){
                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(right.toString());
                    created.add(right);
                    tree.incrementCreated();
//                    created.add(up);
                    if(visited.contains(right)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(right);
                        fringe.offer(right);
                    }
                }
            }

            if(i != 3){//MOVE DOWN
                char[][] state = makeCopy(current.getData());
                PuzzleNode down = new PuzzleNode(moveDown(state), current.getDepth() + 1);
                if(created.contains(down)){
                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(down.toString());
                    created.add(down);
                    tree.incrementCreated();
//                    created.add(up);
                    if(visited.contains(down)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(down);
                        fringe.offer(down);
                    }
                }
            }

            if(j != 0){//MOVE LEFT
                char[][] state = makeCopy(current.getData());
                PuzzleNode left = new PuzzleNode(moveLeft(state), current.getDepth() + 1);
                if(created.contains(left)){
                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(left.toString());
                    created.add(left);
                    tree.incrementCreated();
//                    created.add(up);
                    if(visited.contains(left)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(left);
                        fringe.offer(left);
                    }
                }
            }

            if(i != 0){//MOVE UP
                char[][] state = makeCopy(current.getData());
                PuzzleNode up = new PuzzleNode(moveUp(state), current.getDepth() + 1);
                if(created.contains(up)){
                    System.out.println("NO THANK YOU!");
                }else{
                    System.out.println(up.toString());
                    created.add(up);
                    tree.incrementCreated();
//                    created.add(up);
                    if(visited.contains(up)){
                        System.out.println("Visited indeed");
                    } else {
                        current.addChild(up);
                        fringe.offer(up);
                    }
                }
            }

            visited.add(current);

            if(fringe.size()>fringeSize){
                fringeSize = fringe.size();
            }

            if(!fringe.isEmpty()){
                current = fringe.poll();
                Map<Character, Integer> coord = getBlank(current.getData());
                i = coord.get('i');
                j = coord.get('j');
            }
            System.out.println("Let's do that again?");
        }


        return tree.getDepth() + " "+ tree.getCreated() + " " + tree.getExpanded() + " "+ fringeSize;

    }

    /**
     * Runs the AStar algorithm.
     * @return A string with the results of the search algorithm
     */
    public String AStar(){
        String toReturn  = "";

        return toReturn;
    }



    private Boolean winState(String stateToCheck){
        return WIN_STATES.contains(stateToCheck);
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
    Helper method that returns a collection of possible moves
     */
    private ArrayList<PuzzleNode> movesNoH(PuzzleNode current, int i, int j){

        ArrayList<PuzzleNode> ns = new ArrayList<>();
        //Handle right edge case
        if(j != myBoard.length - 1){
            char[][] data = makeCopy(current.getData());
            PuzzleNode right = new PuzzleNode(moveRight(data), current.getDepth() + 1);
            ns.add(right);
        }

        //Handle bottom edge case
        if(i != myBoard.length - 1){
            char[][] data = makeCopy(current.getData());
            PuzzleNode down = new PuzzleNode(moveDown(data), current.getDepth() + 1);
            ns.add(down);
        }

        //Handle left edge case
        if(j != 0){
            char[][] data = makeCopy(current.getData());
            PuzzleNode left = new PuzzleNode(moveLeft(data), current.getDepth()+ 1);
            ns.add(left);
        }

        //Handle top edge case
        if(i != 0){
            char[][] data = makeCopy(current.getData());
            PuzzleNode up = new PuzzleNode(moveUp(data), current.getDepth() + 1);
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

    private PriorityQueue<PuzzleNode> movesWithH(PuzzleNode current, int i, int j, int heuristic){

        PriorityQueue<PuzzleNode> ns = new PriorityQueue<>();
        //Handle right edge case
        if(j != myBoard.length){
            char[][] data = makeCopy(myBoard);
            PuzzleNode right = new PuzzleNode(moveRight(data), current.getDepth() + 1, heuristic);
            ns.offer(right);
        }

        //Handle bottom edge case
        if(i != myBoard.length){
            char[][] data = makeCopy(myBoard);
            PuzzleNode down = new PuzzleNode(moveDown(data), current.getDepth() + 1, heuristic);
            ns.offer(down);
        }

        //Handle left edge case
        if(j != 0){
            char[][] data = makeCopy(myBoard);
            PuzzleNode left = new PuzzleNode(moveLeft(data), current.getDepth()+ 1, heuristic);
            ns.offer(left);
        }

        //Handle top edge case
        if(i != 0){
            char[][] data = makeCopy(myBoard);
            PuzzleNode up = new PuzzleNode(moveUp(data), current.getDepth() + 1, heuristic);
            ns.offer(up);
        }

        return ns;

    }
}
