package psearch.objects;

@SuppressWarnings("preview")
public record IndexLinker(int[] indexSLC, int indexSR) {
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		for(int i = 0; i < indexSLC.length; i++) {
			strBuilder.append(indexSLC[i]);
			strBuilder.append(" ");
		}
		strBuilder.append(indexSR);
		
		return strBuilder.toString();
	}
}
