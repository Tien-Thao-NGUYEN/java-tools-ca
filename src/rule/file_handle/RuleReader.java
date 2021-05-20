package rule.file_handle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.stream.Collectors;

import rule.FSSPRule;
import rule.RTSGRule;
import simulator.implement.LConfig;
import simulator.implement.SolutionInfo;
import simulator.interfaces.LConfig_Interface;
import simulator.interfaces.SolutionInfo_Interface;

public class RuleReader {
	//TODO sau khi chuyen het dc sang kieu moi thi bo di
//	public static RuleData<Integer, String> dataFromOldTextFile(String file) throws IOException {
//		FileInputStream fis = new FileInputStream(file);
//		LineNumberReader lnr = new LineNumberReader(new BufferedReader(new InputStreamReader(fis)));
//
//		String line = lnr.readLine();
//		String[] dataInfo = line.split(" ");
//
//		Map<LConfig_Interface<String>, String> map = new HashMap<>();
//		while ((line = lnr.readLine()) != null) {
//			String[] ts = line.split(" ");
//			LConfig<String> lc = new LConfig<>(Arrays.asList(ts[0], ts[1], ts[2]));
//			map.put(lc, ts[3]);
//		}
//
//		List<String> states = map.values().stream().sorted().distinct().collect(Collectors.toList());
//		states.add(dataInfo[3]);
//
//		String pgs = dataInfo[4];
//		int[] pg = null;
//
//		if (!"...".equals(pgs))
//			pg = Arrays.stream(pgs.split(",")).mapToInt(Integer::valueOf).toArray();
//
//		SolutionInfo<Integer> info = new SolutionInfo<>(states, states.indexOf(dataInfo[0]), states.indexOf(dataInfo[1]),
//				states.indexOf(dataInfo[2]), states.indexOf(dataInfo[3]), 1, 1, pg);
//
//		lnr.close();
//		fis.close();
//
//		return new RuleData<>(info, map);
//	}

	private static SolutionInfo<Integer> parseStringInfo(String stringState, String stringInfo, String separator) {
		List<String> stateList = List.of(stringState.split(separator));
		String[] dataInfo = stringInfo.split(separator);
		String pgs = dataInfo[6];
		int[] pg = null;

		if (!"...".equals(pgs))
			pg = Arrays.stream(pgs.split(",")).mapToInt(Integer::valueOf).toArray();

		return new SolutionInfo<>(stateList, Integer.valueOf(dataInfo[0]), Integer.valueOf(dataInfo[1]),
				Integer.valueOf(dataInfo[2]), Integer.valueOf(dataInfo[3]), Integer.valueOf(dataInfo[4]),
				Integer.valueOf(dataInfo[5]), pg);
	}

	public static RuleData<Integer, Integer> dataFromTextFile(String file, String separator) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		LineNumberReader lnr = new LineNumberReader(new BufferedReader(new InputStreamReader(fis)));

		String stringState = lnr.readLine();
		String stringInfo = lnr.readLine();
		SolutionInfo_Interface<Integer> info = parseStringInfo(stringState, stringInfo, separator);

		String line;
		Map<LConfig_Interface<Integer>, Integer> map = new HashMap<>();
		while ((line = lnr.readLine()) != null) {
			String[] ts = line.split(separator);
			LConfig<Integer> lc = new LConfig<>(
					Arrays.asList(info.intState(ts[0]), info.intState(ts[1]), info.intState(ts[2])));
			map.put(lc, info.intState(ts[3]));
		}

		lnr.close();
		fis.close();

		return new RuleData<>(info, map);
	}

	public static FSSPRule fsspRuleFromTextFile(String file, String separator) throws IOException {
		RuleData<Integer, Integer> data = dataFromTextFile(file, separator);
		return new FSSPRule(data.info(), data.map());
	}
	
	public static RTSGRule rtsgRuleFromTextFile(String file, String separator) throws IOException {
		RuleData<Integer, Integer> data = dataFromTextFile(file, separator);
		return new RTSGRule(data.info(), data.map());
	}
	
	//TODO dung cho test �galit�
//	public static RuleData<Integer, String> dataTestEquals(String file) throws IOException {
//		FileInputStream fis = new FileInputStream(file);
//		LineNumberReader lnr = new LineNumberReader(new BufferedReader(new InputStreamReader(fis)));
//
//		String stringState = lnr.readLine();
//		String stringInfo = lnr.readLine();
//		SolutionInfo_Interface<Integer> info = parseStringInfo(stringState, stringInfo);
//
//		String line;
//		Map<LConfig_Interface<String>, String> map = new HashMap<>();
//		while ((line = lnr.readLine()) != null) {
//			String[] ts = line.split(" ");
//			LConfig<String> lc = new LConfig<>(Arrays.asList(ts[0], ts[1], ts[2]));
//			map.put(lc, ts[3]);
//		}
//
//		lnr.close();
//		fis.close();
//
//		return new RuleData<>(info, map);
//	}
}
