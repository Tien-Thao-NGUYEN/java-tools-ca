package objects_simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import simulator_interface.LConfig_Interface;

public class LConfig<T> implements LConfig_Interface<T> {
	public static String separator = " ";
	
	private List<T> motif;
	
	public LConfig() {
		this.motif = new ArrayList<>();
	}
	
	public LConfig(List<T> motif) {
		this.motif = new ArrayList<>(motif);
	}
	
	@Override
	public void add(T state) {
		motif.add(state);
	}

	@Override
	public T get(int position) {
		return motif.get(position);
	}
	
	@Override
	public int size() {
		return motif.size();
	}
	
	@Override
	public boolean hasState(T element) {
		return motif.contains(element);
	}
	
	@Override
	public LConfig_Interface<T> newLConfigByReplace(int index, T element) {
		LConfig_Interface<T> clone = copy();
		clone.getListState().set(index, element);
		return clone;
	}
	
	@Override
	public LConfig_Interface<T> newLConfigByReplaceAll(T oldElement, T newElement) {
		return new LConfig<>(
				stream().map(s -> s.equals(oldElement) ? newElement : s).collect(Collectors.toList()));
	}
	
	@Override
	public Stream<T> stream() {
		return motif.stream();
	}
	
	@Override
	public List<T> getListState() {
		return motif;
	}

	@Override
	public LConfig_Interface<T> newLConfigByReverse() {
		LConfig_Interface<T> clone = copy();
		Collections.reverse(clone.getListState());
		return clone;
	}

	@Override
	public LConfig_Interface<T> copy() {
		return new LConfig<>(stream().collect(Collectors.toList()));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((motif == null) ? 0 : motif.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LConfig<?> other = (LConfig<?>) obj;
		if (motif == null) {
			if (other.motif != null)
				return false;
		} else if (!motif.equals(other.motif))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return stream().map(Object::toString).collect(Collectors.joining(separator));
	}
	
	public static IntStream getSupport(int pos, int nCellLeft, int nCellRight) {
		return IntStream.rangeClosed(pos - nCellLeft, pos + nCellRight);
	}
}
