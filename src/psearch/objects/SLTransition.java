package psearch.objects;

import simulator.interfaces.Tuple_Interface;

@SuppressWarnings("preview")
public record SLTransition<T>(Tuple_Interface<T> slc, Tuple_Interface<T> sr) {
	
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder(slc.toString());
		strBuilder.append(" ");
		strBuilder.append(sr.toString());
		
		return strBuilder.toString();
	}
}
