import java.util.Scanner;

class Game{
    static Scanner scanner = new Scanner(System.in);
    
    static int[] getInput(){
        int[] in = new int[9];
        in[0] = scanner.nextInt();
        System.out.println(in[0]);
        for(int i=1; i < 9; i++){
            int c = scanner.nextInt();
            in[c] = i;
            System.out.println(i+" "+in[c]);
        }
        return in;
    }

    public static void main(String args[]){

        int[] start = getInput();
        int[] goal = getInput();

        for(int i : start){
            System.out.print(i+" ");
        }
for(int i : goal){
            System.out.print(i+" ");
        }
        

        EightPuzzle game = new EightPuzzle(new Node(start,null),new Node(goal,null));

        game.AStar();
        

        
    }
}