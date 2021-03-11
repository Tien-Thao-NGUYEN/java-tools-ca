package config_psearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import objects_psearch.TransformElement;
import objects_simulator.Tuple;
import simulator_interface.Diagram_Interface;
import simulator_interface.GConfig_Interface;

public class LMappingControler {
	private static <T> void takeNewLTransformElementDt(Diagram_Interface<T> dgm, int t, int nCellLeft, int nCellRight, int dt, T o,
			List<TransformElement<T>> sltList, Set<Tuple<T>> existeTuple) {
		GConfig_Interface<T> gc = dgm.getGConfig(t);
		for (int p = 0; p < gc.size(); p++) {
			Tuple<T> slcTuple = gc.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, dt), o);
			if (!existeTuple.contains(slcTuple)) {
				if (t + dt <= dgm.timeFin()) {
					T res = dgm.getGConfig(t + dt).state(p, o);
					TransformElement<T> slt = new TransformElement<>(slcTuple, res);
					sltList.add(slt);
					existeTuple.add(slcTuple);
//					System.out.println(gc.size());
				}
			}
		}
	}

	public static <T> void extractHMapFromDgm(Diagram_Interface<T> dgm, int nCellLeft, int nCellRight, int delta_t, T o,
			Map<Integer, List<TransformElement<T>>> hMap, Set<Tuple<T>> existeTuple) {
		for (int dt = 0; dt < delta_t; dt++)
			LMappingControler.takeNewLTransformElementDt(dgm, 0, nCellLeft, nCellRight, dt, o, hMap.get(dt),
					existeTuple);

		int t_end = dgm.timeFin() - delta_t;
		for (int t = 0; t <= t_end; t++)
			LMappingControler.takeNewLTransformElementDt(dgm, t, nCellLeft, nCellRight, delta_t, o, hMap.get(delta_t),
					existeTuple);
	}
	
	public static <T> List<TransformElement<T>> mergeLocalMapping(Map<Integer, List<TransformElement<T>>> hMap) {
		List<TransformElement<T>> mergeList = new ArrayList<>();
		hMap.forEach((k, v) -> {
			mergeList.addAll(v);
		});
		
		return mergeList;
	}
	
	private static <T> boolean isFixed(TransformElement<T> te, T q, T s, T o) {
		if (te.res().equals(s))
			return true;
		
		return te.tuple().stream().allMatch(e -> e.equals(q) || e.equals(o));
	}
	
	public static <T> List<Integer> indexListForChange(List<TransformElement<T>> mergeList, T q, T s, T o) {
		List<Integer> indexList = new ArrayList<>();
		for(int i = 1; i < mergeList.size(); i++) {
			if(!isFixed(mergeList.get(i), q, s, o))
				indexList.add(i);
		}
		
		return indexList;
	}
	
	public static <T> List<T> getInitialCandidate(List<TransformElement<T>> mergeList) {
		return mergeList.stream().map(e -> e.res()).collect(Collectors.toList());
	}
}
