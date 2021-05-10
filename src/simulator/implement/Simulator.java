package simulator.implement;

import simulator.interfaces.Diagram_Interface;
import simulator.interfaces.GConfig_Interface;
import simulator.interfaces.Rule_Interface;
import simulator.interfaces.Simulator_Interface;

public class Simulator<T> implements Simulator_Interface<T>{
	private Rule_Interface<T> rule;

	public Simulator(Rule_Interface<T> rule) {
		this.rule = rule;
	}

	private GConfig_Interface<T> oneStep(GConfig_Interface<T> currentGConfig) {
		int nCellLeft = rule.getSolutionInfo().nCellLeft();
		int nCellRight= rule.getSolutionInfo().nCellRight();
		T o = rule.getSolutionInfo().spaceOutState();
		GConfig_Interface<T> nextGConfig = new GConfig<>(currentGConfig.size());
		for (int pos = 0; pos < currentGConfig.size(); pos++) {
			nextGConfig.add(rule.transition(currentGConfig.lConfig(LConfig.getSupport(pos, nCellLeft, nCellRight), o)));
		}

		return nextGConfig;
	}

	@Override
	public Diagram_Interface<T> getDiagram(GConfig_Interface<T> gc0) {
		Diagram_Interface<T> dgm = new Diagram<>(gc0, rule.getSolutionInfo());
		GConfig_Interface<T> currentGConfig = gc0;
		while (!rule.stop(currentGConfig)) {
			currentGConfig = oneStep(currentGConfig);
			dgm.add(currentGConfig);
		}

		return dgm;
	}
	
	@Override
	public Diagram_Interface<T> getDiagramWithDebug(GConfig_Interface<T> initialGConfig) {
		int t = 0;
		Diagram_Interface<T> dgm = new Diagram<T>(initialGConfig, rule.getSolutionInfo());
		
		GConfig_Interface<T> newGConfig = initialGConfig;
		while (!rule.stop(newGConfig)) {
			System.out.println(t + "\t" + newGConfig);
			t++;
			newGConfig = oneStep(newGConfig);
			dgm.add(newGConfig);
		}
		return dgm;
	}
}
