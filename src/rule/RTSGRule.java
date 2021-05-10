package rule;

import java.util.Map;

import simulator.implement.AbstractRule;
import simulator.interfaces.GConfig_Interface;
import simulator.interfaces.LConfig_Interface;
import simulator.interfaces.SolutionInfo_Interface;

public class RTSGRule extends AbstractRule<Integer>{
	private int currentGeneratedNumber;
	private int maxGeneratedNumber;
	
	public RTSGRule(SolutionInfo_Interface<Integer> info, Map<LConfig_Interface<Integer>, Integer> map) {
		super(info, map);
		this.currentGeneratedNumber = 0;
	}
	
	public void setGeneratedNumberMax(int numberMax) {
		this.maxGeneratedNumber = numberMax;
	}

	@Override
	public boolean stop(GConfig_Interface<Integer> gConfig) {
		if(gConfig.state(0, info.spaceOutState()).equals(info.specialState()))
			currentGeneratedNumber++;
		
		return currentGeneratedNumber == maxGeneratedNumber;
	}

	@Override
	public void setMaxTermsRTSG(int numberMax) {
		maxGeneratedNumber = numberMax;
		
	}

}
