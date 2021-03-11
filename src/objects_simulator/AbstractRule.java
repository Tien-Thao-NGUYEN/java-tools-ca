package objects_simulator;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import simulator_interface.GConfig_Interface;
import simulator_interface.LConfig_Interface;
import simulator_interface.Rule_Interface;
import simulator_interface.SolutionInfo_Interface;

public abstract class AbstractRule<T> implements Rule_Interface<T>{
	protected SolutionInfo_Interface<T> info;
	protected Map<LConfig_Interface<T>, T> map;
	
	public AbstractRule(SolutionInfo_Interface<T> info, Map<LConfig_Interface<T>, T> map) {
		this.info = info;
		this.map = map;
	}
	
	@Override
	public SolutionInfo_Interface<T> getSolutionInfo() {
		return info;
	}
	
	@Override
	public void setSolutionInfo(SolutionInfo_Interface<T> solutionInfo) {
		this.info = solutionInfo;
	}
	
	@Override
	public T spaceOutState() {
		return info.spaceOutState();
	}
	
	@Override
	public Map<LConfig_Interface<T>, T> getMap() {
		return map;
	}
	
	@Override
	public void add(LConfig_Interface<T> lConfig, T state) {
		map.put(lConfig, state);
	}
	
	@Override
	public void addAll(Rule_Interface<T> otherRule) {
		map.putAll(otherRule.getMap());
	}
	
	@Override
	public boolean addAndCheck(LConfig_Interface<T> lConfig, T state) {
		if (map.containsKey(lConfig) && !map.get(lConfig).equals(state)) {
			System.err.println("Err when put : " + lConfig.toString() + " " + map.get(lConfig).toString() + " "
					+ state.toString());
			return false;
		}

		map.put(lConfig,state);
		return true;
	}
	
	@Override
	public T remove(LConfig_Interface<T> lConfig) {
		return map.remove(lConfig);
	}
	
	@Override
	public Set<T> allState() {
		return map.values().stream().collect(Collectors.toSet());
	}
	
	@Override
	public boolean hasLConfig(LConfig_Interface<T> lConfig) {
		return map.containsKey(lConfig);
	}
	
	@Override
	public T transition(LConfig_Interface<T> lConfig) {
		T existedState = map.get(lConfig);
		if (existedState == null)
			System.err.println(lConfig.toString());
		
		return existedState;
	}
	
	@Override
	public Set<LConfig_Interface<T>> allLConfig() {
		return map.keySet();
	}
	
	@Override
	public int size() {
		return map.size();
	}
	
	@Override
	abstract public boolean stop(GConfig_Interface<T> gConfig);
	
	@Override
	abstract public void setMaxTermsRTSG(int numberMax);
	
	public GConfig<T> getGC0(int size) {
		GConfig<T> gc0 = new GConfig<>(size);
		for(int pos = 0; pos < size; pos++)
			gc0.add(info.quiescenceState());
		
		for(int i : info.activePosition())
			gc0.replace(i, info.activeState());
		
		return gc0;
	}
}
