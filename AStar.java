import java.util.*;

public class AStar {
    private int totalTime;
    private ArrayList<State> frontier;
    private HashSet<State> closedSet;

    //constructor
    AStar(int t){
        this.totalTime=t;
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }
    
    public State aStarAlg(State initial){
          
        if(initial.isFinal()) return initial;
        this.frontier.add(initial);
        while(!this.frontier.isEmpty()){
            State currentState = this.frontier.remove(0);//gets the first state(state with min f)
            if(currentState.isFinal()) return currentState;// returns the final state
            //returns the current state if there is not enough time
            if(currentState.getG()> totalTime){
                System.out.println("Error: Out of time!"+ "T: " +currentState.getG() );
                return null;
            }
            if(!this.closedSet.contains(currentState)){   
                this.closedSet.add(currentState);
                //adds all children to frontier
                ArrayList<State> newPath = new ArrayList<>();
                newPath =currentState.getChildren();
                for(int i=0;i<newPath.size();i++){
                    frontier.add(newPath.get(i)); 
                }
            }
            Collections.sort(this.frontier); //Sort the frontier by f
        }
        return null;
    }

}

   