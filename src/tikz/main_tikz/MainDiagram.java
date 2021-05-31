package tikz.main_tikz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import rule.file_handle.Path;
import rule.file_handle.RuleReader;
import simulator.implement.Simulator;
import simulator.interfaces.Diagram_Interface;
import simulator.interfaces.Rule_Interface;
import simulator.interfaces.Simulator_Interface;
import tikz.save_to_tikz_file.FileWriter;
import tikz.save_to_tikz_file.SaveDiagram;
import tikz.tikz_itf_implement.Tikz6State;
import tikz.tikz_itf_implement.TikzInterface;


public class MainDiagram {
	public static String PATH_UMEO = Path.DATA_RTSG_N3_UMEO_RULE;
	public static String PATH_5_STATE_HAND_CRAFT = Path.DATA_RTSG_N3_5_72_RULE;
	public static String PATH_5_STATE_58_RULE = Path.DATA_RTSG_N3_5_58_RULE;
	public static String PATH_4_STATE_55_RULE = Path.DATA_RTSG_N3_4_55_RULE;

	public static void main(String[] args) throws IOException {
//		String pathSrc = PATH_UMEO;
//		String pathDiff = PATH_4_STATE_55_RULE;
//		String folder = "dgm_umeo_vs_4_55";
		
//		String pathSrc = PATH_5_STATE_58_RULE;
//		String pathDiff = PATH_4_STATE_55_RULE;
//		String folder = "dgm_5_58_vs_4_55";
		
//		String pathSrc = PATH_5_STATE_HAND_CRAFT;
//		String pathDiff = PATH_4_STATE_55_RULE;
//		String folder = "dgm_5_72_vs_4_55";
		
//		String pathSrc = PATH_5_STATE_HAND_CRAFT;
//		String pathDiff = PATH_5_STATE_58_RULE;
//		String folder = "dgm_5_72_vs_5_58";
		
//		String pathSrc = PATH_UMEO;
//		String pathDiff = PATH_5_STATE_58_RULE;
//		String folder = "test";
		
//		String pathSrc = PATH_UMEO;
//		String pathDiff = PATH_5_STATE_HAND_CRAFT;
//		String folder = "test";
		
		String pathSrc = Path.getOneOf718SolutionsFilter(0);
		String pathDiff = Path.getOneOf718SolutionsFilter(1);
		String folder = "test";
		
//		int maxTerms = 10;
		Rule_Interface<Integer> ruleSrc = RuleReader.fsspRuleFromTextFile(pathSrc, " ");
//		ruleSrc.setMaxTermsRTSG(maxTerms);
		Simulator_Interface<Integer> simSrc = new Simulator<>(ruleSrc);
		Diagram_Interface<Integer> dgmSrc = simSrc.getDiagram(ruleSrc.getGC0(20));
		
		Rule_Interface<Integer> ruleDiff = RuleReader.fsspRuleFromTextFile(pathDiff, " ");
//		ruleDiff.setMaxTermsRTSG(maxTerms);
		Simulator_Interface<Integer> simDiff = new Simulator<>(ruleDiff);
		Diagram_Interface<Integer> dgmDiff = simDiff.getDiagram(ruleDiff.getGC0(20));
		
		saveRTSGDGM(dgmSrc, dgmDiff, folder);
	}

	public static void saveRTSGDGM(Diagram_Interface<Integer> dgmSrc, Diagram_Interface<Integer> dgmDiff, String folder) throws FileNotFoundException {
		String path = "./img-tikz/" + folder;
		File file = new File(path);
		if (!file.exists())
			file.mkdir();

		TikzInterface tikz6State = new Tikz6State("\\Huge");

		SaveDiagram.saveSubDgmTikz(dgmSrc, 0, 38, 0, 19, 1, 1, true, true, 0, 0, tikz6State,
				path + "/dgm_src_0_19.tikz");
//		SaveDiagram.saveSubDgmTikz(dgmSrc, 37, 73, 0, 6, 1, 0, true, true, 10, 0, tikz6State,
//				path + "/dgm_src_37_73.tikz");

		SaveDiagram.saveDiffDgmTikz(dgmSrc, dgmDiff, 0, 38, 0, 19, 1, 1, true, true, 30, 0, tikz6State,
				path + "/dgm_diff_0_19.tikz");
//		SaveDiagram.saveDiffDgmTikz(dgmSrc, dgmDiff, 37, 73, 0, 6, 1, 0, true, true, 40, 0, tikz6State,
//				path + "/dgm_diff_37_73.tikz");

		FileWriter.texFile(List.of("dgm_src_0_19.tikz"
//				, "dgm_src_37_73.tikz"
				, "dgm_diff_0_19.tikz"
//				, "dgm_diff_37_73.tikz"
				),
				path + "/main.tex");
	}

}
