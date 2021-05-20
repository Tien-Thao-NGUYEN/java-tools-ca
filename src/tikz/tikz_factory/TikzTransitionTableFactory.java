package tikz.tikz_factory;

import java.util.List;

import simulator.implement.LConfig;
import simulator.interfaces.LConfig_Interface;
import simulator.interfaces.Rule_Interface;
import tikz.tikz_itf_implement.TikzInterface;

public class TikzTransitionTableFactory {
	public static String oneState(Integer state, Rule_Interface<Integer> rule, int[] line, int[] column,
			TikzInterface dstTikz) {
		return diffState(state, rule, rule, line, column, dstTikz);
	}

	public static String diffState(Integer state, Rule_Interface<Integer> srcRule, Rule_Interface<Integer> dstRule,
			int[] line, int[] column, TikzInterface dstTikz) {

		String sameStyle = "fill=white";
		String diffStyle = "fill=lightgray";

		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append(TikzBaseElementFactory.drawNode((line.length - 1) / 2., 2,
				"fill=white,minimum width=1cm,minimum height=1cm,scale=1.5", "\\textbf{Right State}"));
		strBuilder.append(TikzBaseElementFactory.drawNode(-2, -(column.length - 1) / 2.,
				"fill=white,minimum width=1cm,minimum height=1cm,scale=1.5,rotate=-90", "\\textbf{Left State}"));

		for (int x = 0; x < line.length; x++)
			strBuilder.append(TikzBaseElementFactory.drawNode(x, 1, sameStyle, dstTikz.display(line[x])));

		for (int y = 0; y < column.length; y++)
			strBuilder.append(TikzBaseElementFactory.drawNode(-1, -y, sameStyle, dstTikz.display(column[y])));

		for (int y = 0; y < column.length; y++) {
			int dstLeftState = column[y];
			int srcLeftState = dstLeftState == dstRule.spaceOutState() ? srcRule.spaceOutState() : dstLeftState;
			for (int x = 0; x < line.length; x++) {
				int dstRightState = line[x];
				int srcRightState = dstRightState == dstRule.spaceOutState() ? srcRule.spaceOutState() : dstRightState;

				LConfig_Interface<Integer> srcLc = new LConfig<>(List.of(srcLeftState, state, srcRightState));
				Integer srcRes = srcRule.transition(srcLc);
				LConfig_Interface<Integer> dstLc = new LConfig<>(List.of(dstLeftState, state, dstRightState));
				Integer dstRes = dstRule.transition(dstLc);

				if (dstRes == null && srcRes != null)
					strBuilder.append(TikzBaseElementFactory.drawNode(x, -y, diffStyle, " "));

				if (dstRes != null) {
					if (dstRes.equals(srcRes))
						strBuilder.append(TikzBaseElementFactory.drawNode(x, -y, sameStyle, dstTikz.display(dstRes)));
					else
						strBuilder.append(TikzBaseElementFactory.drawNode(x, -y, diffStyle, dstTikz.display(dstRes)));
				}
			}
		}

		strBuilder.append(TikzBaseElementFactory.drawGrid("black", 1, -0.5, 0.5, -1, 1, line.length, -column.length));
		strBuilder.append(
				TikzBaseElementFactory.drawRect("line width=2", "black", -0.5, 0.5, 0, 0, line.length, -column.length));
		strBuilder.append(TikzBaseElementFactory.drawNode(-1, 1,
				"draw,line width=2,fill=white,minimum width=1cm,minimum height=1cm,xshift=-0.5cm,yshift=0.5cm,scale=2",
				dstTikz.display(state)));
		strBuilder.append(TikzBaseElementFactory.drawRect("line width=2", "black", -0.5, 0.5, -2, 2, line.length,
				-column.length));

		return strBuilder.toString();
	}
}
