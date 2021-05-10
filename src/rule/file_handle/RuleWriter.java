package rule.file_handle;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import simulator.interfaces.LConfig_Interface;

public class RuleWriter {
	public static void dataToTextFile(RuleData<String, String> data, String file) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(file);
		pw.println(data.info().toString());
		Map<LConfig_Interface<String>, String> map = data.map();
		List<LConfig_Interface<String>> lConfigList = map.keySet().stream().collect(Collectors.toList());
		lConfigList.sort(Comparator.comparing((LConfig_Interface<String> lc) -> lc.get(1))
				.thenComparing(lc -> lc.get(0))
				.thenComparing(lc -> lc.get(2)));
		
		LConfig_Interface<String> curlc = lConfigList.get(0);
		pw.print(curlc.toString() + " " + map.get(curlc).toString());
		for(int i = 1; i < lConfigList.size(); i++) {
			curlc = lConfigList.get(i);
			pw.println();
			pw.print(curlc.toString() + " " + map.get(curlc).toString());
		}
		
		pw.close();
	}
}
