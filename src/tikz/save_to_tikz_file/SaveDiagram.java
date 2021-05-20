package tikz.save_to_tikz_file;

import java.io.FileNotFoundException;
import java.util.List;

import simulator.implement.Location;
import simulator.interfaces.Diagram_Interface;
import tikz.tikz_factory.TikzBaseElementFactory;
import tikz.tikz_factory.TikzDiagramFactory;
import tikz.tikz_itf_implement.TikzInterface;

public class SaveDiagram {
	
	public static void saveDiffDgmTikz(Diagram_Interface<String> sDgm, Diagram_Interface<String> difDgm, int beginTime, int endTime, int beginCell,
			int endCell, int nOLeft, int nORight, boolean showCell, boolean showTime, double valX, double valY,
			TikzInterface tikz, String file) throws FileNotFoundException {

		String defs = TikzBaseElementFactory.def(valX, valY);
		String tikzDiff = defs + TikzDiagramFactory.getDiffTwoDiagram(sDgm, difDgm, beginTime, endTime, beginCell,
				endCell, nOLeft, nORight, showTime, showCell, tikz);

		FileWriter.tikzFile(tikzDiff, file);
	}

	public static void saveSubDgmTikz(Diagram_Interface<String> dgm, int beginTime, int endTime, int beginCell, int endCell,
			int nbrLeftSymbol, int nbrRightSymbol, boolean showCell, boolean showTime, double valX, double valY,
			TikzInterface tikzItf, String file) throws FileNotFoundException {

		String defs = TikzBaseElementFactory.def(valX, valY);
		String subDgm = TikzDiagramFactory.getSubDgm(dgm, beginTime, endTime, beginCell, endCell, nbrLeftSymbol,
				nbrRightSymbol, showCell, showTime, tikzItf);

		String tikzDgm = defs + subDgm;
		FileWriter.tikzFile(tikzDgm, file);
	}

	public static void saveSurroundSubDgmTikz(Diagram_Interface<String> dgm, int beginTime, int endTime, int beginCell, int endCell,
			int nOLeft, int nORight, boolean showCell, boolean showTime, double valX, double valY,
			List<Location> locationList, int nLeftUnit, int nRightUnit, String colorSurrond, TikzInterface tikzItf,
			String file) throws FileNotFoundException {

		String defs = TikzBaseElementFactory.def(valX, valY);
		String tikzDgm = TikzDiagramFactory.getSubDgm(dgm, beginTime, endTime, beginCell, endCell, nOLeft, nORight,
				showCell, showTime, tikzItf);
		String surround = TikzBaseElementFactory.surround(locationList, nLeftUnit, nRightUnit, beginTime, colorSurrond, 2);

		String fileContent = defs + tikzDgm + surround;
		FileWriter.tikzFile(fileContent, file);
	}
}
