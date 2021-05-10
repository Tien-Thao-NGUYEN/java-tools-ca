package cellular_automata.eca;

public class ECAState {
	private boolean state;
	
	public ECAState(){
		state = false;
	}
	
	public ECAState(ECAState eac){
		state = eac.state;
	}
	
	public ECAState(boolean state){
		this.state = state;
	}
	
	
	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return state ? "1" : "0";
	}
}
