package tikz.tikz_itf_implement;

public abstract class TikzAbstract implements TikzInterface{
	protected String symbolStyle;
	
	public TikzAbstract(String style) {
		symbolStyle = style;
	}
}
