import java.util.*;

class Node{
    
    int[] grid ;
    int g;
    int h;
    int f;
    Node parent;

    public Node(int[] grid, Node parent){
        this.grid = grid;
        this.parent = parent;
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }

    @Override
    public boolean equals(Object o) { 
  
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Node)) { 
            return false; 
        } 
          
        Node c = (Node) o; 
        
        return Arrays.equals(this.grid,c.grid); 
    } 

    @Override
    public int hashCode() 
    {    
        int prime = 7;
        for(int i=0;i<9;i++){
            prime+=prime*this.grid[i];
        }
        return prime;
    } 
}

class FComparator implements Comparator<Node>{ 
              
    public int compare(Node s1, Node s2) { 
        if (s1.f < s2.f) 
            return -1; 
        else if (s1.f > s2.f) 
            return 1; 
        else
            return 0; 
        } 
    } 

class EightPuzzle{

    PriorityQueue<Node> openList ;
    List<Node> closedList;
    Node startState;
    Node goalState;

    public EightPuzzle(Node startState, Node goalState){
        openList = new PriorityQueue<>(new FComparator());
        closedList = new LinkedList<>();
        this.goalState = goalState;
        startState.f = startState.g + startState.h;        
        startState.h = getHeuristic(startState);
        this.startState = startState;
        openList.add(startState);

    }

   

    private void printNode(Node node){
        int[] grid = new int[9];

        for(int i=0; i<9; i++){
            grid[node.grid[i]] = i;
        }

        for(int i=0; i<9; i++){
            if(i%3==0){
                System.out.println();
            }

            System.out.print(grid[i] + " ");
        }
        System.out.println();
        System.out.println("f: "+node.f+" g: "+node.g+" h: "+node.h);

        
    }

    private List<Node> findPath(Node node){
        List<Node> path = new LinkedList<>();

        while(node != null){
            path.add(0,node);
            node = node.parent;
        }
        System.out.println("\nFound Path is with size "+(path.size()-1));
        for(Node n : path){
            printNode(n);
            
        }
        return path;
    }

    private int getHeuristic(Node node){
        
        int h = 0;

        for(int i = 0; i < 9 ;i++){
            h+=Math.abs( (node.grid[i]/3) - (this.goalState.grid[i]/3)  ) + Math.abs( (node.grid[i]%3) - (this.goalState.grid[i]%3) ); 
        }

        return h;

    }

    private int getPositionAtIndex(Node node,int index){
        for(int i = 1;i < 9; i++){
            if(node.grid[i] == index){
                return i;
            }
        }
        return -1;
    }

    private Node getNewNode(Node node,int[] offset){

        Node newNode = null;

        int blank = node.grid[0];

        int blankX = blank/3;
        int blankY = blank%3;

        int newBlankX = blankX + offset[0];
        int newBlankY = blankY + offset[1];
        int newBlank = newBlankX*3 + newBlankY;

        if(newBlankX < 0 || newBlankX > 2 || newBlankY < 0 || newBlankY > 2){
            return newNode;
        }

        newNode = new Node(node.grid.clone(),node);
        newNode.grid[0] = newBlank;
        newNode.grid[getPositionAtIndex(node,newBlank)] = blank; 
        newNode.g = node.g + 1;
        newNode.h = getHeuristic(newNode);
        newNode.f = newNode.g + newNode.h;

        return newNode;

    }

    private void printOpenList(){
        System.out.println("The Open queue elements:"); 
        Iterator itr = this.openList.iterator(); 
        while (itr.hasNext()) {
            printNode((Node)itr.next()); 

        }
    }

    private void printColsedList(){
        System.out.println("The Closed queue elements:"); 
        Iterator itr = this.closedList.iterator(); 
        while (itr.hasNext()) {
            
            printNode((Node)itr.next()); 


        }
    }

    private List<Node> generateNeighbours(Node node){

        List<Node> list = new ArrayList<>();
        int[][] directions = new int[][]{{0,1},{1,0},{-1,0},{0,-1}};

        for(int i=0; i<4;i++){
            Node newNode = getNewNode(node,directions[i]);
            if(newNode != null){
                list.add(newNode);
            }

        }

        return list;

    }

    public List<Node> AStar(){
            Node node = null;
	    int skippedNodes = 0;

        while(this.openList.size() > 0){
            printOpenList();
            printColsedList();
            node = this.openList.poll();
            System.out.println("\nSelected Node is ");
            this.printNode(node);


            if (node.h == 0){
                break;
            }
            this.closedList.add(node);

            for (Node neighbour : this.generateNeighbours(node)){
                if(this.openList.contains(neighbour) ){
                    System.out.println("\nSkipping node\n");
                    printNode(neighbour);
                    skippedNodes++;
                    continue;
                }

                this.openList.add(neighbour);
                System.out.println("\nNew node added");
                printNode(neighbour);

                
            }
            


        }
        System.out.println("\nTotal no of unique states explored "+ (this.openList.size()+this.closedList.size()));
        System.out.println("\nTotal no of redundent states explored "+ skippedNodes);
        return findPath(node);
    }






}
