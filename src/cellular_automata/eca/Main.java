package cellular_automata.eca;

import simulator.implement.Simulator;
import simulator.interfaces.Rule_Interface;
import simulator.interfaces.Simulator_Interface;

public class Main {
	public static void main(String[] args) {
		Rule_Interface<ECAState> rule = new ECARule(45, 20, new int[] {10});
		Simulator_Interface<ECAState> sim = new Simulator<>(rule);
		System.out.println(sim.getDiagram(rule.getGC0(50)).toString());
	}
	
}
