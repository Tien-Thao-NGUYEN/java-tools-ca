package simulator_interface;

import java.util.stream.Stream;

public interface Diagram_Interface<T> {
	public SolutionInfo_Interface<T> getSolutionInfo();
	public T spaceOutState();
	public void add(GConfig_Interface<T> gConfig);
	public GConfig_Interface<T> getGConfig(int time);
	public int timeFin();
	public Stream<GConfig_Interface<T>> stream();
}
