import java.util.*;

public class Main{
    public static void main(String[] args){
        
        int T = 30;//total time 
        ArrayList<Integer> left = new ArrayList<Integer>();//who is left
        ArrayList<Integer> right = new ArrayList<Integer>();//who is right
        Scanner input = new Scanner(System.in);  
        System.out.println("Enter number of family members:");
        int N = input.nextInt(); 
        //Initialize left(0: no members) and right(1: member on this side) side
        for (int i=0; i<N; i++){ 
            left.add(0);
            right.add(1);
        }

        ArrayList<Integer> membersTime = new ArrayList<Integer>();//time the member needs to pass the bridge
        
        System.out.println("The family members have " + T + " minutes to pass the bridge");
        for(int i =0; i<N; i++) {
            System.out.println("Enter minutes of traversal of family member No" + (i+1)+ ":");
            membersTime.add(input.nextInt());
        }
        
        
        State initialState = new State(N, left, right,membersTime);//creates initial state
        System.out.println("The game started!");
        AStar game =new AStar(T);//creates AStar object
        State result =game.aStarAlg(initialState);//returns the final state
        
        if (result==null){//something went wrong
            System.out.println("The game has finished without a solution:(");
        }else{
            State ans = result; 
            ArrayList<State> path = new ArrayList<>();
			path.add(result);
            //finds the path by getting the father of the state 
            while(ans.getFather() != null) {
                path.add(ans.getFather());
                ans = ans.getFather();
            }
            Collections.reverse(path);//reverse the path
            //prints the path 
            for(State state: path){
                state.print();
            }
            
            System.out.println("All the members have passed the bridge!");
            System.out.println("They needed " + result.getG()+ " minutes!");
        }
    
        input.close();
    
    }
}
