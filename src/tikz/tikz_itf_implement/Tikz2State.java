package tikz.tikz_itf_implement;

public class Tikz2State extends TikzAbstract {

	public Tikz2State(String style) {
		super(style);
	}

	@Override
	public String display(Integer state) {
		return " ";
	}

	@Override
	public String nodeStyle(Integer state) {
		switch (state) {
		case 0:
			return "fill=white";
		case 1:
			return "fill=darkgray";
		default:
			System.out.println("Error : State" + state + " not found !!!");
			return null;
		}
	}

}
