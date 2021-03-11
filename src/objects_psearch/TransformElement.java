package objects_psearch;

import simulator_interface.Tuple_Interface;

@SuppressWarnings("preview")
public record TransformElement<T>(Tuple_Interface<T> tuple, T res) {
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder(tuple.toString());
		strBuilder.append(" ");
		strBuilder.append(res.toString());
		
		return strBuilder.toString();
	}
}
