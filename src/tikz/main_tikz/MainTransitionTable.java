package tikz.main_tikz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import rule.file_handle.Path;
import rule.file_handle.RuleReader;
import simulator.interfaces.Rule_Interface;
import tikz.save_to_tikz_file.FileWriter;
import tikz.save_to_tikz_file.SaveTransitionTable;
import tikz.tikz_itf_implement.Tikz6State;
import tikz.tikz_itf_implement.TikzInterface;

public class MainTransitionTable {
	public static String PATH_UMEO = Path.DATA_RTSG_N3_UMEO_RULE;
	public static String PATH_5_STATE_HAND_CRAFT = Path.DATA_RTSG_N3_5_72_RULE;
	public static String PATH_5_STATE_58_RULE = Path.DATA_RTSG_N3_5_58_RULE;
	public static String PATH_4_STATE_55_RULE = Path.DATA_RTSG_N3_4_55_RULE;

	public static void main(String[] args) throws IOException, ParseException {
//		String pathSrc = PATH_UMEO;
//		String pathDiff = PATH_UMEO;
//		String[] lineDiff = new String[] { "0", "5", "1", "2", "3", "4" };
//		String[] columnDiff = new String[] { "0", "5", "1", "2", "3", "4", "6" };
//		String folder = "rule_umeo";

		String pathSrc = PATH_UMEO;
		String pathDiff = PATH_5_STATE_HAND_CRAFT;
		int[] lineDiff = new int[] { 0, 1, 2, 3, 4 };
		int[] columnDiff = new int[] { 0, 1, 2, 3, 4, 6 };
		String folder = "test";

//		String pathSrc = PATH_5_STATE_HAND_CRAFT;
//		String pathDiff = PATH_5_STATE_58_RULE;
//		String[] lineDiff = new String[] { "0", "1", "2", "3", "4" };
//		String[] columnDiff = new String[] { "0", "1", "2", "3", "4", "6" };
//		String folder = "rule_5_58_compare_to_5_72";

//		String pathSrc = PATH_5_STATE_58_RULE;
//		String pathDiff = PATH_4_STATE_55_RULE;
//		String[] lineDiff = new String[] { "0", "1", "2", "3" };
//		String[] columnDiff = new String[] { "0", "1", "2", "3", "6" };
//		String folder = "rule_4_55_compare_to_5_58";

		Rule_Interface<Integer> ruleSrc = RuleReader.rtsgRuleFromTextFile(pathSrc, " ");
		ruleSrc.setMaxTermsRTSG(10);
		Rule_Interface<Integer> ruleDiff = RuleReader.rtsgRuleFromTextFile(pathDiff, " ");
		ruleDiff.setMaxTermsRTSG(10);
		buildRule(ruleSrc, ruleDiff, lineDiff, columnDiff, folder);

	}

	public static void buildRule(Rule_Interface<Integer> ruleSrc, Rule_Interface<Integer> ruleDiff, int[] lineDiff,
			int[] columnDiff, String folder) throws FileNotFoundException {
		String path = "/home/nguyen/Bureau/rtsg_test/img-tikz/" + folder;
		File file = new File(path);
		if (!file.exists())
			file.mkdir();

		TikzInterface tikz6State = new Tikz6State("\\Huge");
		List<String> fileList = new ArrayList<>();
		int x = 0;
		for (int state : lineDiff) {
			String texFileState = "state_diff_" + state + ".tex";
			SaveTransitionTable.diffState(state, ruleSrc, ruleDiff, lineDiff, columnDiff, x, 0, tikz6State,
					path + "/" + texFileState);
			fileList.add(texFileState);
			x += lineDiff.length + 3;
		}

		FileWriter.texFile(fileList, path + "/main.tex");
	}

}
