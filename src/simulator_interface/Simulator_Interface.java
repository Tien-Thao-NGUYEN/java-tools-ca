package simulator_interface;

public interface Simulator_Interface<T> {
	public Diagram_Interface<T> getDiagram(GConfig_Interface<T> initialGConfig);
	public Diagram_Interface<T> getDiagramWithDebug(GConfig_Interface<T> initialGConfig);
}
