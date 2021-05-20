package tikz.save_to_tikz_file;

import java.io.IOException;
import java.util.List;

import simulator.implement.LConfig;
import simulator.interfaces.Rule_Interface;
import tikz.tikz_itf_implement.Tikz6State;
import tikz.tikz_itf_implement.TikzInterface;

public class SaveTransitionTable5State {
	public static void saveTransitionTableLocalMapping5StateRTSG(Rule_Interface<Integer> rule, String doc)
			throws IOException {
		Rule_Interface<String> ruleSrc = RuleHelper.buildRTSGRuleFromFile("/home/nguyen/Bureau/umeo_gen/5state/redrule.txt",
				" ");
		ruleSrc.remove(new LConfig<>(List.of("0", "0", "5")));

		rule.setMaxTermsRTSG(10);
		Rule_Interface<String> ruleDst = RuleHelper.toRTSGRuleString(rule);
		ruleDst.remove(new LConfig<>(List.of("0", "0", "5")));

		TikzInterface tikz6State = new Tikz6State("\\Huge");

		String[] line = new String[] { "0", "1", "2", "4", "3" };
		String[] column = new String[] { "0", "1", "2", "4", "3", "5" };
		SaveTransitionTable.diffState("0", ruleSrc, ruleDst, line, column, 0, 0, tikz6State, doc + "state_diff_Q.tex");
		SaveTransitionTable.diffState("1", ruleSrc, ruleDst, line, column, 8, 0, tikz6State, doc + "state_diff_B.tex");
		SaveTransitionTable.diffState("2", ruleSrc, ruleDst, line, column, 16, 0, tikz6State, doc + "state_diff_C.tex");
		SaveTransitionTable.diffState("4", ruleSrc, ruleDst, line, column, 24, 0, tikz6State, doc + "state_diff_D.tex");
		SaveTransitionTable.diffState("3", ruleSrc, ruleDst, line, column, 32, 0, tikz6State, doc + "state_diff_E.tex");

		FileWriter.texFile(List.of("state_diff_Q.tex", "state_diff_B.tex", "state_diff_C.tex", "state_diff_D.tex",
				"state_diff_E.tex"), doc + "rtsg_transTable_lm5State_diff.tex");
	}

	public static void saveTransitionTable5StateRTSG(Rule_Interface<Integer> rule, String doc) throws IOException {
		rule.setMaxTermsRTSG(10);
		Rule_Interface<String> ruleDst = RuleHelper.toRTSGRuleString(rule);
		ruleDst.remove(new LConfig<>(List.of("0", "0", "5")));

		TikzInterface tikz6State = new Tikz6State("\\Huge");

		String[] line = new String[] { "0", "1", "2", "4", "3" };
		String[] column = new String[] { "0", "1", "2", "4", "3", "5" };
		SaveTransitionTable.diffState("0", ruleDst, ruleDst, line, column, 0, 0, tikz6State, doc + "state_diff_Q.tex");
		SaveTransitionTable.diffState("1", ruleDst, ruleDst, line, column, 8, 0, tikz6State, doc + "state_diff_B.tex");
		SaveTransitionTable.diffState("2", ruleDst, ruleDst, line, column, 16, 0, tikz6State, doc + "state_diff_C.tex");
		SaveTransitionTable.diffState("4", ruleDst, ruleDst, line, column, 24, 0, tikz6State, doc + "state_diff_D.tex");
		SaveTransitionTable.diffState("3", ruleDst, ruleDst, line, column, 32, 0, tikz6State, doc + "state_diff_E.tex");

		FileWriter.texFile(List.of("state_diff_Q.tex", "state_diff_B.tex", "state_diff_C.tex", "state_diff_D.tex",
				"state_diff_E.tex"), doc + "rtsg_transTable_lm5State_diff.tex");
	}
}
