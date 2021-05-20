package simulator.implement;

import java.util.List;

import simulator.interfaces.SolutionInfo_Interface;

public class SolutionInfo<T> implements SolutionInfo_Interface<T> {
	private List<String> stringStateList;
	private T a;
	private T q;
	private T s;
	private T o;
	private int nCellLeft;
	private int nCellRight;
	private int[] pg;
	
	public SolutionInfo(List<String> stringStateList, T a, T q, T s, T o, int nCellLeft, int nCellRight, int[] pg) {
		this.stringStateList = stringStateList;
		this.a = a;
		this.q = q;
		this.s = s;
		this.o = o;
		this.nCellLeft = nCellLeft;
		this.nCellRight = nCellRight;
		this.pg = pg;
	}

	@Override
	public int intState(String stringState) {
		return stringStateList.indexOf(stringState);
	}

	@Override
	public String stringState(int intState) {
		return stringStateList.get(intState);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(stringStateList.get(0).toString());
		for (int i = 1; i < stringStateList.size(); i++) {
			stringBuilder.append(" ");
			stringBuilder.append(stringStateList.get(i));
		}
		stringBuilder.append("\n");
		stringBuilder.append(a.toString());
		stringBuilder.append(" ");
		stringBuilder.append(q.toString());
		stringBuilder.append(" ");
		stringBuilder.append(s.toString());
		stringBuilder.append(" ");
		stringBuilder.append(o.toString());
		stringBuilder.append(" ");
		stringBuilder.append(nCellLeft);
		stringBuilder.append(" ");
		stringBuilder.append(nCellRight);
		stringBuilder.append(" ");

		if (pg == null)
			stringBuilder.append("...");
		else {
			stringBuilder.append(String.valueOf(pg[0]));
			for (int i = 1; i < pg.length; i++) {
				stringBuilder.append(",");
				stringBuilder.append(String.valueOf(pg[i]));
			}
		}

		return stringBuilder.toString();
	}

	@Override
	public T activeState() {
		return a;
	}

	@Override
	public T quiescenceState() {
		return q;
	}

	@Override
	public T specialState() {
		return s;
	}

	@Override
	public T spaceOutState() {
		return o;
	}

	@Override
	public int nCellLeft() {
		return nCellLeft;
	}

	@Override
	public int nCellRight() {
		return nCellRight;
	}
	
	@Override
	public int[] activePosition() {
		return pg;
	}
	
	@Override
	public List<String> stringStateList() {
		return stringStateList;
	}

}
