package simulator.interfaces;

public interface SolutionInfo_Interface<T> {
	public int intState(String stringState);
	public String stringState(int intState);
	public T activeState();
	public T quiescenceState();
	public T specialState();
	public T spaceOutState();
	public int nCellLeft();
	public int nCellRight();
	public int[] activePosition();
	
}
