package rule;

import java.util.Map;

import objects_simulator.AbstractRule;
import simulator_interface.GConfig_Interface;
import simulator_interface.LConfig_Interface;
import simulator_interface.SolutionInfo_Interface;

public class FSSPRule extends AbstractRule<Integer> {
	
	public FSSPRule(SolutionInfo_Interface<Integer> info, Map<LConfig_Interface<Integer>, Integer> map) {
		super(info, map);
	}

	@Override
	public boolean stop(GConfig_Interface<Integer> gConfig) {
		return gConfig.stream().allMatch(e -> info.specialState().equals(e));
	}

	@Override
	public void setMaxTermsRTSG(int numberMax) {		
	}
}
