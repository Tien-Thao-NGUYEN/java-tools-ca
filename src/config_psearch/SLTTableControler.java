package config_psearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import objects_psearch.SLTransition;
import objects_simulator.Location;
import objects_simulator.Simulator;
import objects_simulator.Tuple;
import rule.FSSPRule;
import simulator_interface.Diagram_Interface;
import simulator_interface.GConfig_Interface;
import simulator_interface.Simulator_Interface;

public class SLTTableControler {
	//localtion pour un état dans diagram résulta
	public static List<Location> resultLocationList = new ArrayList<>();
	
	private static <T> void takeNewLTransitionDt(GConfig_Interface<T> gc_0, int nCellLeft, int nCellRight, int dt, T o,
			List<SLTransition<T>> sltList, Set<Tuple<T>> existeTuple) {
		for (int p = 0; p < gc_0.size(); p++) {
			Tuple<T> slcTuple = gc_0.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, dt), o);
			if (!existeTuple.contains(slcTuple)) {
				SLTransition<T> slt = new SLTransition<>(slcTuple, slcTuple);
				sltList.add(slt);
				existeTuple.add(slcTuple);
//				System.out.println(gc_0.size());
				//localtion: gc_0.size, dt, p;
				resultLocationList.add(new Location(gc_0.size(), dt, p));
			}
		}
	}

	private static <T> void takeNewLTransitionDelta_t(Diagram_Interface<T> dgm, int nCellLeft, int nCellRight, int delta_t, T o,
			List<SLTransition<T>> sltList, Set<Tuple<T>> existeTuple) {
		int t_end = dgm.timeFin() - delta_t - 1;
		for (int t = 0; t <= t_end; t++) {
			GConfig_Interface<T> gc_t = dgm.getGConfig(t);
			GConfig_Interface<T> gc_t1 = dgm.getGConfig(t + 1);
			for (int p = 0; p < gc_t.size(); p++) {
				Tuple<T> slcTuple = gc_t.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, delta_t + 1), o);
				if (!existeTuple.contains(slcTuple)) {
					Tuple<T> srTuple = gc_t1.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, delta_t), o);
					SLTransition<T> slt = new SLTransition<>(slcTuple, srTuple);
					sltList.add(slt);
					existeTuple.add(slcTuple);
//					System.out.println(gc_t.size());
					//localtion: gc_t.size, (t + 1) + dt, p;//(t + 1) parce que je veux la location de destination cellule résultat
					resultLocationList.add(new Location(gc_t1.size(), t + 1 + delta_t, p));
				}
			}
		}
	}

	public static <T> void extractDtSLTFromDgm(Diagram_Interface<T> dgm, int nCellLeft, int nCellRight, int delta_t, T o,
			Map<Integer, List<SLTransition<T>>> dtMap, Set<Tuple<T>> existeTuple) {
		int end_dt = dgm.timeFin() >= delta_t ? delta_t : dgm.timeFin();
		for (int dt = 1; dt <= end_dt; dt++)
			SLTTableControler.takeNewLTransitionDt(dgm.getGConfig(0), nCellLeft, nCellRight, dt, o, dtMap.get(dt), existeTuple);

		SLTTableControler.takeNewLTransitionDelta_t(dgm, nCellLeft, nCellRight, delta_t, o, dtMap.get(delta_t + 1),
				existeTuple);

	}
	
	public static Map<Integer, List<SLTransition<Integer>>> getSLTTable(FSSPRule rule, int beginSize, int endSize,
			int delta_t) {
		int nCellLeft = rule.getSolutionInfo().nCellLeft();
		int nCellRight = rule.getSolutionInfo().nCellRight();
		Integer o = rule.getSolutionInfo().spaceOutState();

		Map<Integer, List<SLTransition<Integer>>> sltTable = new HashMap<>();
		for (int dt = 1; dt <= delta_t + 1; dt++)
			sltTable.put(dt, new ArrayList<>());

		Set<Tuple<Integer>> existeTuple = new HashSet<>();

		Simulator_Interface<Integer> sim = new Simulator<>(rule);
		for (int size = beginSize; size <= endSize; size++) {
			Diagram_Interface<Integer> dgm = sim.getDiagram(rule.getGC0(size));
			SLTTableControler.extractDtSLTFromDgm(dgm, nCellLeft, nCellRight, delta_t, o, sltTable, existeTuple);
		}

		return sltTable;
	}
}
