package tikz.main_tikz;

import java.io.IOException;
import java.util.List;

import rule.file_handle.RuleReader;
import simulator.implement.Diagram;
import simulator.implement.Location;
import simulator.implement.Simulator;
import simulator.interfaces.Diagram_Interface;
import simulator.interfaces.GConfig_Interface;
import simulator.interfaces.Rule_Interface;
import simulator.interfaces.Simulator_Interface;
import tikz.save_to_tikz_file.FileWriter;
import tikz.save_to_tikz_file.SaveDiagram;
import tikz.tikz_itf_implement.Tikz6State;
import tikz.tikz_itf_implement.TikzInterface;


public class MainDiagram {

	public static void main(String[] args) throws IOException {
		int maxTerms = 10;
		Rule_Interface<String> rule = RuleReader.rtsgRuleFromTextFile("/home/nguyen/Bureau/umeo_gen/5state/redrule.txt", " ");
		rule.setMaxTermsRTSG(maxTerms);
		Simulator_Interface<String> sim = new Simulator<>(rule);
		GConfig_Interface<String> initGC = rule.getGC0(maxTerms + 5);
		Diagram_Interface<String> dgm = sim.getDiagram(initGC);

		TikzInterface tikz6State = new Tikz6State("\\Huge");
		SaveDiagram.saveSubDgmTikz(dgm, 100, 150, 0, 10, 2, 0, true, true, 0, 0, tikz6State,
				"/home/nguyen/Bureau/test_java_tikz/testsub.tikz");
		
		SaveDiagram.saveSurroundSubDgmTikz(dgm, 100, 150, 0, 12, 1, 1, true, true, 20, 0,
				List.of(new Location(maxTerms + 3, 110, 4), new Location(maxTerms + 3, 120, 3)), 1, 1, "black", tikz6State,
				"/home/nguyen/Bureau/test_java_tikz/testssub.tikz");
		
		
		Diagram_Interface<String> dgm1 = new Diagram<>(dgm);
		dgm1.getGConfig(3).replace(0, "4");
		dgm1.getGConfig(24).replace(8, "4");
		SaveDiagram.saveDiffDgmTikz(dgm, dgm1, 0, 30, 0, 12, 1, 1, true, true, 40, 0, tikz6State,
				"/home/nguyen/Bureau/test_java_tikz/testdif.tikz");
		
		FileWriter.texFile(List.of("testsub.tikz"
				, "testssub.tikz"
				, "testdif.tikz"
				), "/home/nguyen/Bureau/test_java_tikz/main_test.tex");
	}

}
