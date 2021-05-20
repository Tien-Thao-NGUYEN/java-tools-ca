package tikz.tikz_itf_implement;

public class Tikz2State extends TikzAbstract{
	
	public Tikz2State(String style) {
		super(style);
	}
	
	@Override
	public String display(String state) {
		return " ";
	}

	@Override
	public String nodeStyle(String state) {
		switch(state) {
			case "0" : return "fill=white";
			case "1" : return "fill=darkgray";
		}
		
		return null;
	}

}
