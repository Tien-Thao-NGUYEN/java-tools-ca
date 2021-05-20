package tikz.save_to_tikz_file;

import java.io.FileNotFoundException;

import simulator.interfaces.Rule_Interface;
import tikz.tikz_factory.TikzBaseElementFactory;
import tikz.tikz_factory.TikzTransitionTableFactory;
import tikz.tikz_itf_implement.TikzInterface;

public class SaveTransitionTable {
	public static void state(String state, Rule_Interface<String> rule, String[] line, String[] column, double valX,
			double valY, TikzInterface tikz, String file)
			throws FileNotFoundException {

		String defs = TikzBaseElementFactory.def(valX, valY);
		String tikzState = TikzTransitionTableFactory.oneState(state, rule, line, column, tikz);
		String fileContent = defs + tikzState;

		FileWriter.tikzFile(fileContent, file);
	}

	public static void diffState(String state, Rule_Interface<String> ruleSrc, Rule_Interface<String> ruleDst, String[] line,
			String[] column, double valX, double valY, TikzInterface tikz, String file) throws FileNotFoundException {

		String defs = TikzBaseElementFactory.def(valX, valY);
		String tikzDiffState = TikzTransitionTableFactory.diffState(state, ruleSrc, ruleDst, line, column, tikz); 
		String fileContent = defs + tikzDiffState; 

		FileWriter.tikzFile(fileContent, file);
	}
}
