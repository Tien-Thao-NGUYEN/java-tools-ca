package simulator.interfaces;

import java.util.List;
import java.util.stream.Stream;

public interface LConfig_Interface<T> {
	public void add(T state);
	public T get(int position);
	public int size();
	public boolean hasState(T state);
	public LConfig_Interface<T> newLConfigByReplace(int position, T state);
	public LConfig_Interface<T> newLConfigByReplaceAll(T originalState, T permuteState);
	public List<T> getListState();
	public LConfig_Interface<T> newLConfigByReverse();
	public Stream<T> stream();
	public LConfig_Interface<T> copy();
}
