package cellular_automata.eca;

import java.util.List;

import simulator.implement.AbstractRule;
import simulator.implement.SolutionInfo;
import simulator.interfaces.GConfig_Interface;
import simulator.interfaces.LConfig_Interface;


public class ECARule extends AbstractRule<ECAState> {
	private int[] bits;
	private int t;
	private int timeMax;

	public ECARule(int rule, int timeMax, int[] activePostion) {
			super(new SolutionInfo<ECAState>(List.of("0", "1", "2"), 
					new ECAState(true), 
					new ECAState(false), 
					new ECAState(false), 
					new ECAState(false), 
					1, 
					1, 
					activePostion));
			
			bits = new int[8];
			remplirBits(rule);
			this.timeMax = timeMax;
			t = 0;
		}

	private void remplirBits(int rule) {
		int index = 0;
		while (rule != 0) {
			bits[index] = rule % 2;
			rule = rule / 2;
			index++;
		}
	}

	private int bit2int(LConfig_Interface<ECAState> local, int index, int value) {
		return local.get(index) == null ? 0 : local.get(index).isState() ? value : 0;
	}

	private int getIndex(LConfig_Interface<ECAState> local) {
		return bit2int(local, 0, 4) + bit2int(local, 1, 2) + bit2int(local, 2, 1);
	}

	@Override
	public ECAState transition(LConfig_Interface<ECAState> local) {
		return new ECAState(bits[getIndex(local)] == 1);
	}

	@Override
	public boolean stop(GConfig_Interface<ECAState> config) {
		t++;
		return t == timeMax;
	}

	@Override
	public void setMaxTermsRTSG(int numberMax) {
	}
}
