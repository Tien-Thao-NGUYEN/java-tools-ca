package objects_simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import simulator_interface.Tuple_Interface;

@SuppressWarnings("preview")
public record Tuple<T> (List<T> tuple) implements Tuple_Interface<T>{

	@Override
	public Stream<T> stream() {
		return tuple.stream();
	}
	
	@Override
	public T middleState(int nCellLeft, int dt) {
		return tuple.get(nCellLeft * dt);
	}

	// decompose_dt = dt - 1;
	@Override
	public List<Tuple_Interface<T>> decompose(int nCellLeft, int nCellRight, int decompose_dt) {
		int windowSize = nCellLeft * decompose_dt + 1 + nCellRight * decompose_dt;
		List<Tuple_Interface<T>> decomposeList = new ArrayList<>();
		for (int i = 0; i <= tuple.size() - windowSize; i++) {
			List<T> subTuple = new ArrayList<>();
			for (int j = 0; j < windowSize; j++) {
				subTuple.add(tuple.get(i + j));
			}
			decomposeList.add(new Tuple<>(subTuple));
		}

		return decomposeList;
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		tuple.forEach(e -> strBuilder.append(e.toString()));
		return strBuilder.toString();
	}

	public static IntStream getSupport(int pos, int nCellLeft, int nCellRight, int delta_t) {
		return IntStream.rangeClosed(pos - (nCellLeft * delta_t), pos + (nCellRight * delta_t));
	}

	public static <T> Tuple_Interface<T> getOTuple(int nCellLeft, int nCellRight, int dt, T o) {
		List<T> tuple = IntStream.rangeClosed(1, (nCellLeft * dt) + 1 + (nCellRight * dt)).mapToObj(i -> {
			return o;
		}).collect(Collectors.toList());
		
		return new Tuple<>(tuple);
	}
}
