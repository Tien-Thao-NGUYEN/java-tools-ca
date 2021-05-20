package simulator.interfaces;

import java.util.Map;
import java.util.Set;


public interface Rule_Interface<T> {

	public void setSolutionInfo(SolutionInfo_Interface<T> solutionInfo);

	public SolutionInfo_Interface<T> getSolutionInfo();

	public T spaceOutState();

	public Map<LConfig_Interface<T>, T> getMap();

	public void add(LConfig_Interface<T> lConfig, T state);

	public void addAll(Rule_Interface<T> otherRule);

	public boolean addAndCheck(LConfig_Interface<T> lConfig, T state);

	public T remove(LConfig_Interface<T> lConfig);

	public Set<T> allState();

	public boolean hasLConfig(LConfig_Interface<T> lc);

	public T transition(LConfig_Interface<T> lConfig);

	public boolean stop(GConfig_Interface<T> gConfig);

	public Set<LConfig_Interface<T>> allLConfig();

	public int size();

	public void setMaxTermsRTSG(int numberMax);

	public GConfig_Interface<T> getGC0(int size);

}
