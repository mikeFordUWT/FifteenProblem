import java.util.*;
import java.util.stream.Collector;

/**
 * Created by Mike on 7/6/16.
 */
public class Board {

    private final ArrayList<String> WIN_STATES =
            new ArrayList<>(Arrays.asList("123456789ABCDEF ", "123456789ABCDFE "));
    private char[][] myBoard;
    private PuzzleTree myTree;
    private Map<Character, Integer> blankPosition;

    public Board(String startState){
        myBoard = new char[4][4];
        int k = 0;
        for(int i=0; i< 4; i++){
            for(int j = 0; j <4; j++){
                myBoard[i][j] = startState.charAt(k);
                k++;
            }
        }

        myTree = new PuzzleTree(myBoard);
        System.out.println(myTree.getRoot().checkState());
        blankPosition = getBlank(myBoard);
//        System.out.println("Current Blank Location: ROW: " + blankPosition.get('i')
//                + " COLUMN: " + blankPosition.get('j'));
//
//        char[][] newBoard = moveLeft(myBoard);
//        PuzzleNode newNode = new PuzzleNode(newBoard);
//        System.out.println(newNode.checkState());

    }


    public char[][] getMyBoard() {
        return myBoard;
    }

    //TODO
    /**
     *
     * @return
     */
    public String BFS(){
        Queue<PuzzleNode> fringe = new LinkedList<>();
        ArrayList<PuzzleNode> visited = new ArrayList<>();
        ArrayList<PuzzleNode> created = new ArrayList<>();
        StringBuilder toReturn = new StringBuilder();
        PuzzleTree temp = myTree;
        PuzzleNode current = temp.getRoot();
        created.add(current);
        int i = blankPosition.get('i');
        int j = blankPosition.get('j');
        boolean win = false;

        while(!win){
            if(current.getDepth() > myTree.getDepth()){
                myTree.setDepth(current.getDepth());
            }
            if(!current.winState()){
//                System.out.println(current.checkState());
                char[][] tempData = makeCopy(current.getData());
                myTree.incrementExpanded();
                int lastIndex = current.getData().length -1;
                int depth = current.getDepth() + 1;



                if(j != lastIndex){
                    PuzzleNode right = new PuzzleNode(moveRight(tempData), depth);
//                    System.out.println("RIGHT:\n"+right.checkState());
                    addToFringeBFS(fringe,visited,current,right);
                }

                if(i != lastIndex){
                    tempData = makeCopy(current.getData());
                    PuzzleNode down = new PuzzleNode(moveDown(tempData), depth);
//                    System.out.println("DOWN:\n"+down.checkState());
                    addToFringeBFS(fringe, visited, current, down);
                }

                if(j != 0){
                    tempData = makeCopy(current.getData());
                    PuzzleNode left = new PuzzleNode(moveLeft(tempData), depth);
//                    System.out.println("LEFT:\n" + left.checkState());
                    addToFringeBFS(fringe, visited, current, left);
                }

                if(i != 0){
                    tempData = makeCopy(current.getData());
                    PuzzleNode up = new PuzzleNode(moveUp(tempData), depth);
//                    System.out.println("UP:\n"+up.checkState());
                    addToFringeBFS(fringe, visited, current, up);
                }

//                myTree.incrementDepth();

                if(!fringe.isEmpty()){
                    current = fringe.poll();
                    Map<Collector.Characteristics, Integer> iJ = getBlank(current.getData());
                    i = iJ.get('i');
                    j = iJ.get('j');
                }else{
                    System.out.println("SHIT!!!!!");
                    break;
                }
                if(myTree.getDepth()>10000){
                    break;
                }


//                if(i>0 && j>0 && i<lastIndex && j< lastIndex){
//
////                    char[][] tempData = makeCopy(current.getData());
////                    PuzzleNode right = new PuzzleNode(moveRight(tempData));
////                    System.out.println("RIGHT:\n"+right.checkState());
////                    addToFringeBFS(fringe,visited,current,right);
//
////                    if(!created.contains(right)){
////                        current.addChild(right);
////                        created.add(right);
////                        if(!visited.contains(right)){
////                            fringe.add(right);
////                        }
////
////                    }
//
//
////                    tempData = makeCopy(current.getData());
////                    PuzzleNode down = new PuzzleNode(moveDown(tempData));
////                    System.out.println("DOWN:\n"+down.checkState());
////                    addToFringeBFS(fringe, visited, current, down);
//
////                    if(!created.contains(down)){
////                        current.addChild(down);
////                        created.add(down);
////                        if(!visited.contains(down)){
////                            fringe.add(down);
////                        }
////
////                    }
//
//
////                    tempData = makeCopy(current.getData());
////                    PuzzleNode left = new PuzzleNode(moveLeft(tempData));
////                    System.out.println("LEFT:\n" + left.checkState());
////                    addToFringeBFS(fringe, visited, current, left);
////                    if(!created.contains(left)){
////                        current.addChild(left);
////                        created.add(left);
////                        if(!visited.contains(left)){
////                            fringe.add(left);
////                        }
////
////                    }
//
//
////                    tempData = makeCopy(current.getData());
////                    PuzzleNode up = new PuzzleNode(moveUp(tempData));
////                    System.out.println("UP:\n"+up.checkState());
////
////                    addToFringeBFS(fringe, visited, current, up);
////                    if(!created.contains(up)){
////                        current.addChild(up);
////                        created.add(up);
////                        if(!visited.contains(up)){
////                            fringe.add(up);
////                            visited.add(up);
////                        }
////
////                    }
//
//
////                    if(!fringe.isEmpty()){
////                        current = fringe.poll();
////                        Map<Collector.Characteristics, Integer> iJ = getBlank(current.getData());
////                        i = iJ.get('i');
////                        j = iJ.get('j');
////                    }else{
////                        System.out.println("SHIT!!!!!");
////                    }
//
//                }
//               if(i == lastIndex && j == lastIndex){
////                    TODO bottom right corner
//                    tempData = makeCopy(current.getData());
//                    PuzzleNode left = new PuzzleNode(moveLeft(tempData));
//                    System.out.println("LEFT:\n" + left.checkState());
//                    addToFringeBFS(fringe, visited, current, left);
////                    if(!created.contains(left)){
////                        current.addChild(left);
////                        created.add(left);
////                        if(!visited.contains(left)){
////                            fringe.add(left);
////                            visited.add(left);
////                        }
////                    }
//
//
//                    tempData = makeCopy(current.getData());
//                    PuzzleNode up = new PuzzleNode(moveUp(tempData));
//                    System.out.println("UP:\n"+up.checkState());
//                    addToFringeBFS(fringe, visited, current, up);
////                    current.addChild(up);
//
//                } else if(i == lastIndex && j == 0){//bottom left corner
//                    tempData = makeCopy(current.getData());
//                    PuzzleNode right = new PuzzleNode(moveRight(tempData));
//                    System.out.println("RIGHT:\n"+right.checkState());
//                    addToFringeBFS(fringe, visited, current, right);
////                    current.addChild(right);
//
//                    tempData = makeCopy(current.getData());
//                    PuzzleNode up = new PuzzleNode(moveUp(tempData));
//                    System.out.println("UP:\n"+up.checkState());
//                    addToFringeBFS(fringe, visited, current, up);
////                    current.addChild(up);
//
//                } else if(i == 0 && j == lastIndex){
////                    TODO top right corner
//                    tempData = makeCopy(current.getData());
//                    PuzzleNode down = new PuzzleNode(moveDown(tempData));
//                    System.out.println("DOWN:\n"+down.checkState());
//                    addToFringeBFS(fringe, visited, current, down);
////                    current.addChild(down);
//
//                    tempData = makeCopy(current.getData());
//                    PuzzleNode left = new PuzzleNode(moveLeft(tempData));
//                    System.out.println("LEFT:\n" + left.checkState());
//                    addToFringeBFS(fringe, visited, current, left);
////                    current.addChild(left);
//                } else if(i == 0 && j == 0){//TOP LEFT
////                    todo top left
//                    tempData = makeCopy(current.getData());
//                    PuzzleNode right = new PuzzleNode(moveRight(tempData));
//                    System.out.println("RIGHT:\n"+right.checkState());
////                    current.addChild(right);
//                    addToFringeBFS(fringe, visited, current, right);
//
//                    tempData = makeCopy(current.getData());
//                    PuzzleNode down = new PuzzleNode(moveDown(tempData));
//                    System.out.println("DOWN:\n"+down.checkState());
//                    addToFringeBFS(fringe, visited, current, down);
////                    current.addChild(down);
//                }
//                else if(i == lastIndex){
////                    TODO
//                    if(j == lastIndex){
//                        //should never hit this block
//                        System.out.println("HOW? i:"+ i + " j: "+ j);
//                    } else if (j ==0) {
//                        //should never hit
//                        System.out.println("WHY? i:"+ i + " j: "+ j);
//                    } else{
//                        System.out.println("IIII:" + i);
//
//                        tempData = makeCopy(current.getData());
//                        PuzzleNode right = new PuzzleNode(moveRight(tempData));
//                        System.out.println("RIGHT:\n"+right.checkState());
//                        addToFringeBFS(fringe, visited, current, right);
////                        current.addChild(right);
//
//                        tempData = makeCopy(current.getData());
//                        PuzzleNode left = new PuzzleNode(moveLeft(tempData));
//                        System.out.println("LEFT:\n" + left.checkState());
//                        addToFringeBFS(fringe, visited, current, left);
////                        current.addChild(left);
//
//                        tempData = makeCopy(current.getData());
//                        PuzzleNode up = new PuzzleNode(moveUp(tempData));
//                        System.out.println("UP:\n"+up.checkState());
//                        addToFringeBFS(fringe, visited, current, up);
////                        current.addChild(up);
//                        break;
//
//                    }
//                } else if (j == 3){
//                    tempData = makeCopy(current.getData());
//                    PuzzleNode down = new PuzzleNode(moveDown(tempData));
//                    addToFringeBFS(fringe, visited, current, down);
//
//                    tempData = makeCopy(current.getData());
//                    PuzzleNode left = new PuzzleNode(moveLeft(tempData));
//                    addToFringeBFS(fringe, visited, current, left);
//
//                    tempData = makeCopy(current.getData());
//                    PuzzleNode up = new PuzzleNode(moveUp(tempData));
//                    addToFringeBFS(fringe, visited, current, up);
//
//                } else if(i == 0){//BORDER CASE UPPER
//                    tempData = makeCopy(current.getData());
//
//                } else if(j == 0){
//
//                }
            }else{
                win = true;
            }
        }

        System.out.println("\"" + current.toString() + "\"");
        toReturn.append(myTree.getDepth() + " "+ myTree.getCreated() + " " + myTree.getExpanded() + " "+ myTree.getMaxFringe());
        return myTree.getDepth() + " "+ myTree.getCreated() + " " + myTree.getExpanded() + " "+ myTree.getMaxFringe();
    }

    //TODO
    /**
     *
     * @return
     */
    public String DFS(){
        Stack<PuzzleNode> fringe = new Stack<>();
        ArrayList<PuzzleNode> visited = new ArrayList<>();
        ArrayList<PuzzleNode> created = new ArrayList<>();
        PuzzleTree temp = myTree;
        PuzzleNode current = temp.getRoot();
        created.add(current);
        StringBuilder toReturn = new StringBuilder();

        return myTree.getDepth() + " "+ myTree.getCreated() + " " + myTree.getExpanded() + " "+ myTree.getMaxFringe();
    }

    //TODO
    /**
     *
     * @return
     */
    public String GBFS(){
        StringBuilder toReturn = new StringBuilder();

        return toReturn.toString();
    }


    public String toString(){
        return myTree.getDepth() + ", " + myTree.getCreated() + ", " + myTree.getExpanded() + ", " + myTree.getMaxFringe();

    }

    private Boolean winState(String stateToCheck){
        return WIN_STATES.contains(stateToCheck);
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


    /** HELPER METHODS FOR BFS*/
    private void addToFringeBFS(Queue<PuzzleNode> theFringe, ArrayList<PuzzleNode> theVisited, PuzzleNode current,
                                PuzzleNode toAdd){
        current.addChild(toAdd);
        myTree.incrementCreated();
        if(!theVisited.contains(toAdd)){
            theFringe.add(toAdd);
            myTree.incrementMaxFringe();
            theVisited.add(toAdd);
        }
    }

    private void addToFringeDFS(Stack<PuzzleNode> theFringe, ArrayList<PuzzleNode> theVisited, PuzzleNode current,
                                PuzzleNode toAdd){
        current.addChild(toAdd);
        myTree.incrementCreated();
        if(!theVisited.contains(toAdd)){
            theFringe.add(toAdd);
            myTree.incrementMaxFringe();
            theVisited.add(toAdd);
        }
    }

}
