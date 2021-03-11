package rule_file_handle;

import java.util.Map;

import simulator_interface.LConfig_Interface;
import simulator_interface.SolutionInfo_Interface;

@SuppressWarnings("preview")
public record RuleData<T1, T2>(SolutionInfo_Interface<T1> info, Map<LConfig_Interface<T2>, T2> map) {
	
}
