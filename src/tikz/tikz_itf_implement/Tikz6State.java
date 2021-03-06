package tikz.tikz_itf_implement;

public class Tikz6State extends TikzAbstract {

	public Tikz6State(String style) {
		super(style);
	}

	@Override
	public String display(Integer state) {
		if (state == null)
			return "";

		if (state.equals(FAT_ARROW))
			return "\\Large $\\to$";

		if (state.equals(DOTS))
			return symbolStyle + " $\\dots$";

		if (state.equals(VDOTS))
			return symbolStyle + " $\\vdots$";

		if (state.equals(EMPTY))
			return " ";

		if (state.equals(SIM))
			return symbolStyle + " $\\tt \\sim$";

		switch (state) {
		case 0:
			return symbolStyle + " $\\tt Q$";
		case 1:
			return symbolStyle + " $\\tt B$";
		case 2:
			return symbolStyle + " $\\tt C$";
		case 3:
			return symbolStyle + " $\\tt E$";
		case 4:
			return symbolStyle + " $\\tt D$";
		case 5:
			return symbolStyle + " $\\tt A$";
		case 6:
			return symbolStyle + " $\\tt \\star$";
		default:
			System.out.println("Error (display): State " + state + " not found !!!");
			return null;
		}
	}

	@Override
	public String nodeStyle(Integer state) {
		if (state == null)
			return "fill=gray";

		if (state.equals(FAT_ARROW) || state.equals(DOTS) || state.equals(VDOTS) || state.equals(EMPTY)
				|| state.equals(SIM))
			return "fill=white";

		switch (state) {
		case 0:
			return "fill=brown";
		case 1:
			return "fill=cyan";
		case 2:
			return "fill=YellowGreen";
		case 3:
			return "fill=yellow";
		case 4:
			return "fill=red";
		case 5:
			return "fill=magenta";
		case 6:
			return "fill=white";
		default:
			System.out.println("Error (nodeStyle): State " + state + " not found !!!");
			return null;
		}
	}
}
