package simulator.interfaces;

import java.util.List;
import java.util.stream.Stream;


public interface Tuple_Interface<T> {
	public Stream<T> stream();
	public T middleState(int nCellLeft, int dt);
	public List<Tuple_Interface<T>> decompose(int nCellLeft, int nCellRight, int decompose_dt);
}
