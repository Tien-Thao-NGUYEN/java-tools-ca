package tikz.main_tikz;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.parser.ParseException;

import tikz.save_to_tikz_file.SaveTransitionTable5State;

public class MainTransitionTable {
	public static void main(String[] args) throws IOException, ParseException {
//		String path = "/home/nguyen/Bureau/umeo_gen/5state/";
//		String doc = "/home/nguyen/Bureau/img_these/rtsg/transition_table_local_mapping_5state/";
//		
//		RebuildRuleFromCandidate.initial(path + "info.json", path + "reorderVList.txt", 5);
//		Candidate candidate = RebuildRuleFromCandidate.getCandidate(path + "candidates/15977.txt", 3564, 4);
//		Rule_Interface<Integer> rule = RebuildRuleFromCandidate.buildRule(candidate);
//	
////		SaveTransitionTable5State.saveTransitionTable5StateRTSG(rule, doc);
//		SaveTransitionTable5State.saveTransitionTableLocalMapping5StateRTSG(rule, doc);
		
		buildRuleSimulationOf58();
	}
	
	public static void buildRuleSimulationOf58() throws IOException, ParseException {
		String path = "/home/nguyen/Bureau/rtsg_5state/5state58rule/";
		RebuildRuleFromCandidate.initial(path + "info.json", path + "reorderVList.txt", 5);
		
		for (int f = 13584; f <= 13584; f++) {
			List<String> lines = RebuildRuleFromCandidate.getLines(path + "candidates/" + f + ".txt");
			for (int lineNbr = 0; lineNbr < lines.size(); lineNbr++) {
				Candidate candidate = RebuildRuleFromCandidate.getCandidate(lines, lineNbr, 4);
				Rule_Interface<Integer> rule = RebuildRuleFromCandidate.buildRTSGRule(candidate);
				System.out.print(rule.getMap().size() + " - ");
				System.out.println(rule.getMap().values().stream().collect(Collectors.toSet()).size());
//				System.out.println(rule.);
			}
		}
	}
}
