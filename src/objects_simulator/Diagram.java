package objects_simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import simulator_interface.Diagram_Interface;
import simulator_interface.GConfig_Interface;
import simulator_interface.SolutionInfo_Interface;

public class Diagram<T> implements Diagram_Interface<T> {
	private SolutionInfo_Interface<T> solInfo;
	private List<GConfig_Interface<T>> listGConfig;

	// from file.txt
	public Diagram(SolutionInfo_Interface<T> solInfo) {
			listGConfig = new ArrayList<>();
			this.solInfo = solInfo;
		}

	// from rule
	public Diagram(GConfig_Interface<T> gc0, SolutionInfo_Interface<T> solInfo) {
			listGConfig = new ArrayList<>();
			listGConfig.add(gc0);
			this.solInfo = solInfo;
		}

	public Diagram(List<GConfig_Interface<T>> listGConfig, SolutionInfo_Interface<T> solInfo) {
			this.solInfo = solInfo;
			this.listGConfig = new ArrayList<>(listGConfig);
		}

	public Diagram(Diagram_Interface<T> other) {
			this(other.stream().map(e -> e.copy()).collect(Collectors.toList()), other.getSolutionInfo());
		}

	@Override
	public GConfig_Interface<T> getGConfig(int time) {
		return listGConfig.get(time);
	}

	@Override
	public Stream<GConfig_Interface<T>> stream() {
		return listGConfig.stream();
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(listGConfig.get(0).toString());
		for (int t = 1; t < listGConfig.size(); t++) {
			stringBuilder.append("\n");
			stringBuilder.append(listGConfig.get(t).toString());
		}

		return stringBuilder.toString();
	}

	@Override
	public SolutionInfo_Interface<T> getSolutionInfo() {
		return solInfo;
	}

	@Override
	public T spaceOutState() {
		return solInfo.spaceOutState();
	}

	@Override
	public void add(GConfig_Interface<T> gConfig) {
		listGConfig.add(gConfig);
	}

	@Override
	public int timeFin() {
		return listGConfig.size() - 1;
	}
}
