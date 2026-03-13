import java.util.*;

public class State implements Comparable<State>{
	private int f, h, g;
	private State father = null;
	private int totalTime;
	private int num;
	public boolean isLeft=false; //true if we're left 
	private int[] left;
	private int[] right;
	public int[] membersTime;

	public State() {}
	
	//constructor for initial state 
	public State(int N, ArrayList<Integer> l, ArrayList<Integer> r,ArrayList<Integer> mT ){	
		this.left = new int[N];
		this.right = new int[N];
		this.membersTime = new int[N];

		this.num = N;
		this.isLeft = false;
		for(int i=0; i<N; i++){
			this.left[i] = l.get(i);
			this.right[i] = r.get(i);
			this.membersTime[i] = mT.get(i);
		}
		this.h = heuristic(this);
		evaluate();
		this.g = 0;
		this.father = null;
		this.totalTime = 0;
	}
	
	//constructor for states 
	public State(int [] left, int [] right, boolean isLeft, int g, int [] membersTime ){
		this.num = right.length;
		this.isLeft = isLeft;
		this.g += g;
		this.right= new int [num];
		this.left = new int[num];
		this.membersTime = new int[num];

		for(int i=0; i<num; i++){
			this.left[i] = left[i];
			this.right[i] = right[i];
			this.membersTime[i] = membersTime[i];
		}

	}

	//calculates the number of people on the right side
	public int peopleSide(int []table){
		int sum=0;
		for (int i= 0; i<table.length; i++){
			if(table[i] == 1){
                sum+= 1;
            }
		}
		return sum;
	}
	
	public boolean getisLeft(){
		return this.isLeft;
	}

	public int getF(){
		return this.f;
	}
	
	public int getG(){
		return this.g;
	}
	
	public int getH(){
		return this.h;
	}
	
	public State getFather(){
		return this.father;
	}
	
	public void setF(int f){
		this.f = f;
	}
	
	public void setG(int g){
		this.g = g;
	}
	
	public void setH(int h){
		this.h = h;
	}
	
	public void setFather(State father){
		this.father = father;
	}
	
	public int getTotalTime() {
		return this.totalTime;
	}
	
	public void setTotalTime(int time){
		this.totalTime = time;
	}
	
	//calculates and sets f
	public void evaluate() {
		this.f = this.g + this.h;
	}
	
	//Heuristic method returns h = cost to reach the final state 
	public int heuristic(State child){
		return child.peopleSide(child.getRight());
	}

	public int[] getRight(){ 
		return right;
	}

 	//moves 2 persons from right to left
	public void goLeft(int first , int second) {
		//chooses max time and adds it to g
        g+= Math.max( membersTime[first],  membersTime[second]);
		//changes the condition of the members moving
        left[first] = 1;
        left[second] = 1;
        right[first] = 0;
        right[second] = 0;
		//changes the condition of the lamp 
        isLeft = true;
    }
	
    //moves a person from left to right
    public void goRight(int mem) {
		//adds the time the member needs to return
        g +=  membersTime[mem];
		//changes the condition of the member moving
        left[mem] = 0;
        right[mem] = 1;
		//changes the condition of the lamp
        isLeft = false;
    }

	//prints the state
	public void print() {
		if(this.isFinal()){
			System.out.println("FINAL STATE");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("LEFT SIDE");
		for(int i=0;i<left.length;i++){
			if(left[i]==1){
			System.out.println("Member"+ (i+1) );
			}
		}
		System.out.println("\nRIGHT SIDE");
		for(int i=0;i<left.length;i++){
			if(right[i]==1){
			System.out.println("Member"+ (i+1) );
			}
		}
		if(!this.isFinal()){
			if(isLeft){
				System.out.println("\nThe lamp is left!");
			}
			else{
				System.out.println("\nThe lamp is right!");
			}
			System.out.println("Time elapsed: " + this.getG() + " minutes");
		}

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	
	}

	//creates children of the given state 
	public ArrayList<State> getChildren() {
		ArrayList<State> children = new ArrayList<State>();
		ArrayList<Integer> onright = new ArrayList<Integer>(); //who is on the right side 
		
		if(isLeft){
			//creates every possible combination and calculates h and f for each one
			for (int i =0; i<num; i++){
				if(left[i]==1){
					State child = new State(left, right, isLeft, this.getG(), membersTime);
					child.goRight(i);
					child.setH(heuristic(child));
					child.evaluate();
					child.setFather(this);
					children.add(child);
				}
			}

		}else{
			onright.clear();

			for (int i =0; i<num; i++){
				if(right[i]==1){
					onright.add(i);
				}
			}

			//creates every possible combination and calculates h and f for each one
			while(onright.size()!= 0){ 
				int person = onright.remove(0);
				for(int i=0; i< onright.size();i++){
					State child = new State (left, right, isLeft, this.getG(), membersTime);
					child.goLeft(person, onright.get(i));
					child.setH(heuristic(child));
					child.evaluate();
					child.setFather(this);
					children.add(child);	
				}
			}
		}
		return children;
	}

	//true if all the family members are on the left side
	public boolean isFinal() {

		for (int i = 0; i < num; i++) {
			if (right[i] == 1) return false;
		}
		return true;
	}
	
	@Override
    public int compareTo(State s)
    {
        return Double.compare(this.f, s.getF()); // compares based on the f
    }

	
	
}