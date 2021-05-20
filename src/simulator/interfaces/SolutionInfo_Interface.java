package simulator.interfaces;

import java.util.List;

import simulator.implement.SolutionInfo;

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

	public List<String> stringStateList();

	public static <T> SolutionInfo_Interface<String> getSolutionInfoString(SolutionInfo_Interface<T> solInfo) {
		return new SolutionInfo<>(solInfo.stringStateList(), solInfo.activeState().toString(),
				solInfo.quiescenceState().toString(), solInfo.specialState().toString(),
				solInfo.spaceOutState().toString(), solInfo.nCellLeft(), solInfo.nCellRight(),
				solInfo.activePosition());
	}
}
