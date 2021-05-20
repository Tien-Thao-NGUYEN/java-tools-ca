package tikz.tikz_itf_implement;

public abstract class TikzAbstract implements TikzInterface{
	public static final Integer FAT_ARROW = 999999;
	public static final Integer DOTS = 999998;
	public static final Integer VDOTS = 999997;
	public static final Integer EMPTY = 999996;
	public static final Integer SIM = 999995;
	
	protected String symbolStyle;
	
	public TikzAbstract(String style) {
		symbolStyle = style;
	}
}
