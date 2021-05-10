package simulator.interfaces;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import simulator.implement.Tuple;

public interface GConfig_Interface<T> {
	public boolean outOfSpace(int position);
	public T state(int position, T o);
	public void replace(int position, T state);
	public void add(T state);
	public int size();
	public LConfig_Interface<T> lConfig(IntStream positions, T o);
	public Tuple<T> tuple(IntStream positions, T o);
	public Stream<T> stream();
	public GConfig_Interface<T> copy();
}
