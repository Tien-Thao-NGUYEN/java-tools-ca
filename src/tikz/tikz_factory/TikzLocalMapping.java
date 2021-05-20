package tikz.tikz_factory;

import java.util.ArrayList;
import java.util.List;

import tikz.operation_for_tikz.Operations;
import tikz.tikz_itf_implement.TikzAbstract;
import tikz.tikz_itf_implement.TikzInterface;

public class TikzLocalMapping {
	private static String drawGridSubDt(List<List<Integer>> subDt, double currentBeginX, double currentBeginY) {
		StringBuilder strBuilder = new StringBuilder();
		List<Integer> indexList = Operations.indexList(subDt);
		for (int i = 0; i < indexList.size(); i += 2) {
			int arrowIndex = subDt.get(indexList.get(i)).indexOf(TikzAbstract.FAT_ARROW);
			arrowIndex = (arrowIndex == -1) ? subDt.get(indexList.get(i)).size() : arrowIndex;

			double beginY = currentBeginY - indexList.get(i);
			double endY = currentBeginY - indexList.get(i + 1) - 1;

			double firstBeginX = currentBeginX;
			double firstEndX = currentBeginX + arrowIndex;
			strBuilder.append(
					TikzBaseElementFactory.drawGrid("black", 1, -0.5, 0.5, firstBeginX, beginY, firstEndX, endY));

			if (arrowIndex != subDt.get(indexList.get(i)).size()) {
				double lastBeginX = currentBeginX + arrowIndex + 1;
				double lastEndX = currentBeginX + subDt.get(indexList.get(0)).size();
				strBuilder.append(
						TikzBaseElementFactory.drawGrid("black", 1, -0.5, 0.5, lastBeginX, beginY, lastEndX, endY));
			}
		}

		return strBuilder.toString();
	}

	private static String ofSubDt(List<List<Integer>> subDt, double currentBeginX, double currentBeginY,
			TikzInterface tikzItfSrc, TikzInterface tikzItfDst) {
		StringBuilder strBuilder = new StringBuilder();

		double currentY = currentBeginY;

		for (int indElem = 0; indElem < subDt.size(); indElem++) {
			List<Integer> elem = subDt.get(indElem);

			TikzInterface tikz = tikzItfSrc;
			for (int pos = 0; pos < elem.size(); pos++) {
				double currentX = currentBeginX + pos;
				int state = elem.get(pos);
				if (state == TikzAbstract.FAT_ARROW)
					tikz = tikzItfDst;
				String symbol = tikz.display(state);
				String nodeStyle = tikz.nodeStyle(state);
				strBuilder.append(TikzBaseElementFactory.drawNode(currentX, currentY, nodeStyle, symbol));
			}

			currentY--;
		}
		strBuilder.append(drawGridSubDt(subDt, currentBeginX, currentBeginY));

		return strBuilder.toString();
	}

	public static String ofMapping(List<List<List<Integer>>> localMapping, List<List<Integer>> divise,
			TikzInterface tikzItfSrc, TikzInterface tikzItfDst, List<String> labelList) {
		StringBuilder strBuilder = new StringBuilder();

		int sommeMaxDiv = 0;
		double currentBeginY = 0;
		for (int dt = 0; dt < localMapping.size(); dt++) {
			List<List<Integer>> localMappingDt = localMapping.get(dt);
			List<Integer> divDt = divise.get(dt);
			sommeMaxDiv += divDt.get(1);
			List<List<List<Integer>>> subDtList = new ArrayList<>();
			for (int i = 0; i < divDt.size() - 1; i++)
				subDtList.add(localMappingDt.subList(divDt.get(i), divDt.get(i + 1)));

			String label = labelList == null ? "" : labelList.get(dt);
			strBuilder.append(TikzBaseElementFactory.drawNode(-1.5, currentBeginY + 0.5, "", label));
			for (int index = 0; index < subDtList.size(); index++) {
				List<List<Integer>> subDt = subDtList.get(index);
				double currentX = index * (subDt.get(0).size() + 2);
				double currentY = currentBeginY;
				strBuilder.append(ofSubDt(subDt, currentX, currentY, tikzItfSrc, tikzItfDst));
			}

			double currentEndY = currentBeginY - divDt.get(1);
			strBuilder.append(TikzBaseElementFactory.drawRect("very thick", "black", -1, 1, 0, currentBeginY,
					(divDt.size() - 1) * (localMappingDt.get(0).size() + 2) - 2 + 1, currentEndY - 1));
			currentBeginY = currentEndY - 2;
		}

		if (localMapping.size() > 1)
			strBuilder.append(TikzBaseElementFactory.drawRect("ultra thick", "black", -2, 2, 0, 0,
					(divise.get(divise.size() - 1).size() - 1)
							* (localMapping.get(localMapping.size() - 1).get(0).size() + 2) - 2 + 3,
					-(sommeMaxDiv + (localMapping.size() - 1) * 2 + 3)));

		return strBuilder.toString();
	}
}
