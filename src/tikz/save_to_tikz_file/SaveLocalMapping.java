package tikz.save_to_tikz_file;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import tikz.operation_for_tikz.Operations;
import tikz.tikz_factory.TikzLocalMapping;
import tikz.tikz_itf_implement.Tikz6State;
import tikz.tikz_itf_implement.TikzInterface;

public class SaveLocalMapping {
	public static void local_mapping_6state_to_5state_rtsg(int nPart) throws IOException {
		String path = "/home/nguyen/Bureau/img_these/rtsg/local_mapping_6state_to_5state/";
		TikzInterface tikz6State = new Tikz6State("\\Huge");

		// LMap
		List<List<String>> dt0 = List.of(List.of("1", "=>", "1"), List.of("0", "=>", "0"), List.of("6", "=>", "6"));
		List<List<String>> dt1 = new ArrayList<>();
		dt1.add(List.of("empty", "empty", "empty", "empty", "empty"));
		dt1.add(List.of("empty", "vdots", "empty", "empty", "vdots"));
		dt1.add(List.of("empty", "empty", "empty", "empty", "empty"));
		dt1.add(List.of("6", "1", "0", "=>", "4"));
		dt1.add(List.of("6", "1", "1", "=>", "4"));
		dt1.add(List.of("empty", "empty", "empty", "empty", "empty"));
		dt1.add(List.of("empty", "vdots", "empty", "empty", "vdots"));
		dt1.add(List.of("empty", "empty", "empty", "empty", "empty"));
		dt1.add(List.of("3", "2", "1", "=>", "3"));
		dt1.add(List.of("3", "2", "2", "=>", "3"));
		dt1.add(List.of("3", "2", "4", "=>", "3"));
		dt1.add(List.of("3", "2", "3", "=>", "3"));
		dt1.add(List.of("5", "2", "1", "=>", "3"));
		dt1.add(List.of("5", "2", "2", "=>", "3"));
		dt1.add(List.of("5", "2", "4", "=>", "3"));
		dt1.add(List.of("5", "2", "3", "=>", "3"));
		dt1.add(List.of("empty", "empty", "empty", "empty", "empty"));
		dt1.add(List.of("empty", "vdots", "empty", "empty", "vdots"));
		dt1.add(List.of("empty", "empty", "empty", "empty", "empty"));
		dt1.add(List.of("5", "2", "0", "=>", "2"));
		dt1.add(List.of("0", "0", "2", "=>", "4"));
		dt1.add(List.of("empty", "empty", "empty", "empty", "empty"));
		dt1.add(List.of("empty", "vdots", "empty", "empty", "vdots"));
		dt1.add(List.of("empty", "empty", "empty", "empty", "empty"));

		List<List<List<String>>> lmapID = List.of(dt0, dt1);
		List<List<Integer>> divise = Operations.getDivLMapping(lmapID, nPart);
		String lmapIDTikz = TikzLocalMapping.ofMapping(lmapID, divise, tikz6State, tikz6State,
				List.of("\\Huge{$h_{\\mathtt{z}}$}", "\\Huge{$h_{\\mathtt{s}}$}"));

		String defs = "\\def \\x {0}\n";
		defs += "\\def \\y {0}\n";

		PrintWriter pw = new PrintWriter(path + "localMapping_6state_to_5state.tex");
		pw.println(defs + lmapIDTikz);
		pw.close();

		FileWriter.texFile(List.of("localMapping_6state_to_5state.tex"), path + "main.tex");
	}
}
