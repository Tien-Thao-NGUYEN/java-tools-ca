package for_rtsg_4_55;

import java.io.IOException;

import rule.RTSGRule;
import rule.file_handle.RuleReader;
import simulator.implement.Simulator;
import simulator.interfaces.Simulator_Interface;

public class Main {

	public static void main(String[] args) throws IOException {
		RTSGRule rule = RuleReader.rtsgRuleFromTextFile("D:\\rule_4_55.txt", " ");
		rule.setMaxTermsRTSG(10);
		System.out.println(rule.size());
		System.out.println(rule.allState().size());
		Simulator_Interface<Integer> sim = new Simulator<>(rule);
		sim.getDiagramWithDebug(rule.getGC0(15));
		
		RTSGRule checkRule = new RTSGRule(rule.getSolutionInfo());
		rule.getMap().forEach((lc, r) -> {
			if (!checkRule.addAndCheck(lc, r))
				System.out.println("not det");
		});
	}

}
