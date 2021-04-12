package objects_simulator;

public class Location {
	private int size;
	private int time;
	private int position;
	
	public Location(int size, int time, int position) {
		this.size = size;
		this.time = time;
		this.position = position;
	}

	public int getSize() {
		return size;
	}

	public int getTime() {
		return time;
	}

	public int getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return size + " " + time + " " + position;
	}
	
	
}
