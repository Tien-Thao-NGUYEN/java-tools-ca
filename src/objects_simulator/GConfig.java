package objects_simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import simulator_interface.GConfig_Interface;
import simulator_interface.LConfig_Interface;

public class GConfig<T> implements GConfig_Interface<T>{
	private int size;
	private List<T> config;
	
	public GConfig(int size) {
		this.config = new ArrayList<>();
		this.size = size;
	}
	
	public GConfig(List<T> gConfigContent) {
		this.config = new ArrayList<>(gConfigContent);
		this.size = gConfigContent.size();
	}
	
	@Override
	public boolean outOfSpace(int position) {
		return position < 0 || position > size - 1;
	}
	
	@Override
	public T state(int position, T o) {
		if(outOfSpace(position))
			return o;
		
		return config.get(position);
	}
	
	@Override
	public void replace(int position, T element) {
		config.set(position, element);
	}
	
	@Override
	public void add(T element) {
		config.add(element);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public Tuple<T> tuple(IntStream positions, T o) {
		List<T> tuple = positions.mapToObj(e -> state(e, o)).collect(Collectors.toList());
		return new Tuple<>(tuple);
	}
	
	@Override
	public LConfig_Interface<T> lConfig(IntStream positions, T o) {
		List<T> list = positions.mapToObj(e -> state(e, o)).collect(Collectors.toList());
		return new LConfig<>(list);
	}
	
	@Override
	public Stream<T> stream() {
		return config.stream();
	}
	
	@Override
	public GConfig_Interface<T> copy() {
		return new GConfig<>(stream().collect(Collectors.toList()));
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(config.get(0).toString());
		for(int i = 1; i < config.size(); i++) {
			stringBuilder.append(" ");
			stringBuilder.append(config.get(i).toString());
		}
		
		return stringBuilder.toString();
	}
}
