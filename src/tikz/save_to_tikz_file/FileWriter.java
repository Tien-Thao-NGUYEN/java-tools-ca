package tikz.save_to_tikz_file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import tikz.tikz_factory.TikzBaseElementFactory;

public class FileWriter {
	public static void tikzFile(String fileContent, String file) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(file);
		pw.println(fileContent);
		pw.close();
	}
	
	public static void texFile(List<String> tikzFileList, String file)
			throws FileNotFoundException {
		String fileContent = TikzBaseElementFactory.mainTikzFile(tikzFileList); 
		tikzFile(fileContent, file);
	}
}
