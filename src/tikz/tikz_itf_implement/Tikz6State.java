package tikz.tikz_itf_implement;

public class Tikz6State extends TikzAbstract{
	
	public Tikz6State(String style) {
		super(style);
	}
	
	@Override
	public String display(String state) {
		if(state == null)
			return "";
		
		switch(state) {
		case "0" : return symbolStyle + " $\\tt Q$";
		case "1" : return symbolStyle + " $\\tt B$";
		case "2" : return symbolStyle + " $\\tt C$";
		case "3" : return symbolStyle + " $\\tt E$";
		case "4" : return symbolStyle + " $\\tt D$";
		case "5" : return symbolStyle + " $\\tt A$";
		case "6" : return symbolStyle + " $\\tt \\star$";
		case "=>": return "\\Large $\\to$";
		case "..." : return symbolStyle + " $\\dots$";
		case "vdots": return symbolStyle + " $\\vdots$";
		case "empty" : return " ";
		case "~": return symbolStyle + " $\\tt \\sim$";
		default : System.out.println("Error : State" + state + " not found !!!"); return null;
		}
	}

	@Override
	public String nodeStyle(String state) {
		if(state == null)
			return "fill=gray";
		
		switch(state) {
		case "0" : return "fill=brown";
		case "1" : return "fill=cyan";
		case "2" : return "fill=YellowGreen";
		case "3" : return "fill=yellow";
		case "4" : return "fill=red";
		case "5" : return "fill=magenta";
		case "6" : return "fill=white";
		case "=>": return "fill=white";
		case "...": return "fill=white";
		case "vdots": return "fill=white";
		case "empty" : return "fill=white";
		case "~": return "fill=white";
		default : System.out.println("Error : State" + state + " not found !!!"); return null;
		}
	}
}
