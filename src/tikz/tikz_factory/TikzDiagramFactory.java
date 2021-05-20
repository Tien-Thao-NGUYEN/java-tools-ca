package tikz.tikz_factory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import simulator.implement.Location;
import simulator.interfaces.Diagram_Interface;
import simulator.interfaces.GConfig_Interface;
import tikz.tikz_itf_implement.TikzInterface;

public class TikzDiagramFactory {
	public static String getDgm(Diagram_Interface<Integer> dgm, int nbrOLeft, int nbrORight, boolean showTime,
			boolean showNumberCell, TikzInterface tikzItf) {
		return getSubDgm(dgm, 0, dgm.timeFin(), 0, dgm.getGConfig(0).size() - 1, nbrOLeft, nbrORight, showTime,
				showNumberCell, tikzItf);
	}

	public static String getSubDgm(Diagram_Interface<Integer> dgm, int beginTime, int endTime, int beginCell,
			int endCell, int nOLeft, int nORight, boolean showCell, boolean showTime, TikzInterface tikzItf) {
		StringBuilder strBuilder = new StringBuilder();

		if (showCell) {
			int x = 0;
			for (int p = beginCell; p <= endCell; p++) {
				strBuilder.append(TikzBaseElementFactory.drawNode(x, 1, "fill=white", String.valueOf(p)));
				x++;
			}
		}

		int y = 0;
		for (int t = beginTime; t <= endTime; t++) {
			GConfig_Interface<Integer> gc = dgm.getGConfig(t);

			if (showTime)
				strBuilder.append(TikzBaseElementFactory.drawNode(-(nOLeft + 1), -y, "fill=white", String.valueOf(t)));

			for (int i = 1; i <= nOLeft; i++) {
				String display = tikzItf.display(dgm.spaceOutState());
				String nodeStyle = tikzItf.nodeStyle(dgm.spaceOutState());
				strBuilder.append(TikzBaseElementFactory.drawNode(-i, -y, nodeStyle, display));
			}

			for (int i = 1; i <= nORight; i++) {
				String display = tikzItf.display(dgm.spaceOutState());
				String nodeStyle = tikzItf.nodeStyle(dgm.spaceOutState());
				strBuilder.append(TikzBaseElementFactory.drawNode(endCell + i, -y, nodeStyle, display));
			}

			for (int px = beginCell; px <= endCell; px++) {
				int state = gc.state(px, dgm.spaceOutState());
				String display = tikzItf.display(state);
				String nodeStyle = tikzItf.nodeStyle(state);
				strBuilder.append(TikzBaseElementFactory.drawNode(px, -y, nodeStyle, display));
			}

			y++;
		}

		strBuilder.append(TikzBaseElementFactory.drawGrid("black", 1, -0.5, 0.5, 0, 0, endCell - beginCell + 1,
				-(endTime - beginTime + 1)));

		return strBuilder.toString();
	}

	public static String getDiffTwoDiagram(Diagram_Interface<Integer> sDgm, Diagram_Interface<Integer> difDgm,
			int beginTime, int endTime, int beginCell, int endCell, int nOLeft, int nORight, boolean showCell,
			boolean showTime, TikzInterface difDgmTikz) throws FileNotFoundException {

		StringBuilder strBuilder = new StringBuilder();

		if (showCell) {
			int x = 0;
			for (int p = beginCell; p <= endCell; p++) {
				strBuilder.append(TikzBaseElementFactory.drawNode(x, 1, "", String.valueOf(p)));
				x++;
			}
		}

		List<Location> locList = new ArrayList<>();
		int y = 0;
		for (int t = beginTime; t <= endTime; t++) {
			if (showTime)
				strBuilder.append(TikzBaseElementFactory.drawNode(-(nOLeft + 1), -y, "", String.valueOf(t)));

			for (int i = 1; i <= nOLeft; i++)
				strBuilder.append(TikzBaseElementFactory.drawNode(-i, -y, "", difDgmTikz.display(difDgm.spaceOutState())));

			GConfig_Interface<Integer> gcSrc = sDgm.getGConfig(t);
			GConfig_Interface<Integer> gcDif = difDgm.getGConfig(t);
			int x = 0;
			for (int p = beginCell; p <= endCell; p++) {
				int state = gcDif.state(p, difDgm.spaceOutState());
				String display = difDgmTikz.display(state);
				String nodeStyle = difDgmTikz.nodeStyle(state);
				if (gcSrc.state(p, sDgm.spaceOutState()).equals(state))
					nodeStyle += "!20";
				else
					locList.add(new Location(gcDif.size(), t, p));

				strBuilder.append(TikzBaseElementFactory.drawNode(x, -y, nodeStyle, display));
				x++;
			}

			for (int i = 1; i <= nORight; i++)
				strBuilder.append(
						TikzBaseElementFactory.drawNode(gcDif.size() + i - 1, -y, "", difDgmTikz.display(difDgm.spaceOutState())));

			y++;
		}

		strBuilder.append(TikzBaseElementFactory.drawGrid("black", 1, -0.5, 0.5, 0, 0, endCell - beginCell + 1,
				-(endTime - beginTime + 1)));
		strBuilder.append(TikzBaseElementFactory.surround(locList, 0, 0, beginTime, "black", 2));

		return strBuilder.toString();
	}
}
