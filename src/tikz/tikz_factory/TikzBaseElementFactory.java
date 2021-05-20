package tikz.tikz_factory;

import java.util.List;

import simulator.implement.Location;

public class TikzBaseElementFactory {
	public static String mainTikzFile(List<String> tikzFileList) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("\\documentclass[dvipsnames]{standalone}\n");
		strBuilder.append("\\usepackage[T1]{fontenc}\n");
		strBuilder.append("\\usepackage[utf8]{inputenc}\n");
		strBuilder.append("\\usepackage[french]{babel}\n");
		strBuilder.append("\\usepackage{lmodern}\n");
		strBuilder.append("\\usepackage{tikz}\n");
		strBuilder.append("\\usepackage{amsmath,tikz-cd}\n");
		strBuilder.append("\\usepackage{amssymb}\n\n");

		strBuilder.append("\\begin{document}\n\n");

		strBuilder.append("\n");
		strBuilder.append("\\def \\sc {1}\n");
		strBuilder.append("\\begin{tikzpicture}[scale=\\sc, every node/.style={scale=\\sc, minimum size=1cm}]\n");
		strBuilder.append("\n");

		for (String file : tikzFileList) {
			strBuilder.append("\\input{");
			strBuilder.append(file);
			strBuilder.append("}\n");
		}

		strBuilder.append("\n");
		strBuilder.append("\\end{tikzpicture}\n\n");
		strBuilder.append("\\end{document}\n");

		return strBuilder.toString();
	}

	public static String drawNode(double x, double y, String tikzStyle, String tikzDisplay) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("\\node [");
		strBuilder.append(tikzStyle);
		strBuilder.append("] at (");
		strBuilder.append("\\x");
		strBuilder.append("+");
		strBuilder.append(x);
		strBuilder.append(",");
		strBuilder.append("\\y");
		strBuilder.append("+");
		strBuilder.append(y);
		strBuilder.append(") {");
		strBuilder.append(tikzDisplay);
		strBuilder.append("};\n");

		return strBuilder.toString();
	}

	public static String drawGrid(String color, double step, double xshift, double yshift, double beginX, double beginY,
			double endX, double endY) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("\\draw[color=");
		strBuilder.append(color);
		strBuilder.append(",step=");
		strBuilder.append(step);
		strBuilder.append("cm,xshift=");
		strBuilder.append(xshift);
		strBuilder.append("cm,yshift=");
		strBuilder.append(yshift);
		strBuilder.append("cm] (");
		strBuilder.append("\\x");
		strBuilder.append("+");
		strBuilder.append(beginX);
		strBuilder.append(",");
		strBuilder.append("\\y");
		strBuilder.append("+");
		strBuilder.append(beginY);
		strBuilder.append(") grid (");
		strBuilder.append("\\x");
		strBuilder.append("+");
		strBuilder.append(endX);
		strBuilder.append(",");
		strBuilder.append("\\y");
		strBuilder.append("+");
		strBuilder.append(endY);
		strBuilder.append(");\n");

		return strBuilder.toString();
	}

	public static String drawRect(String thickness, String color, double xshift, double yshift, double beginX,
			double beginY, double endX, double endY) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("\\draw[color=");
		strBuilder.append(color);
		strBuilder.append(",");
		strBuilder.append(thickness);
		strBuilder.append(",xshift=");
		strBuilder.append(xshift);
		strBuilder.append("cm,yshift=");
		strBuilder.append(yshift);
		strBuilder.append("cm] (");
		strBuilder.append("\\x");
		strBuilder.append("+");
		strBuilder.append(beginX);
		strBuilder.append(",");
		strBuilder.append("\\y");
		strBuilder.append("+");
		strBuilder.append(beginY);
		strBuilder.append(") rectangle (");
		strBuilder.append("\\x");
		strBuilder.append("+");
		strBuilder.append(endX);
		strBuilder.append(",");
		strBuilder.append("\\y");
		strBuilder.append("+");
		strBuilder.append(endY);
		strBuilder.append(");\n");

		return strBuilder.toString();
	}

	public static String surround(List<Location> locationList, int nLeftUnit, int nRightUnit, int beginTime,
			String surroundColor, double lineWidth) {
		StringBuilder strBuilder = new StringBuilder();
		for (Location loc : locationList) {
			double x = loc.getPosition();
			double y = -(loc.getTime() - beginTime);
			strBuilder.append(drawRect("line width=" + lineWidth + "mm", surroundColor, -0.5, 0.5, x - nLeftUnit, y,
					x + nRightUnit + 1, y - 1));
		}

		return strBuilder.toString();
	}

	public static String def(double valX, double valY) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("\\def \\x {");
		strBuilder.append(valX);
		strBuilder.append("}\n");
		strBuilder.append("\\def \\y {");
		strBuilder.append(valY);
		strBuilder.append("}\n");

		return strBuilder.toString();
	}
}
