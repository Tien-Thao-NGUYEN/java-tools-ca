package config_psearch;

import java.util.List;
import java.util.Map;
import java.util.Set;

import objects_psearch.SLTransition;
import objects_simulator.Tuple;
import simulator_interface.Diagram_Interface;
import simulator_interface.GConfig_Interface;

public class SLTTableControler {
	private static <T> void takeNewLTransitionDt(GConfig_Interface<T> gc_0, int nCellLeft, int nCellRight, int dt, T o,
			List<SLTransition<T>> sltList, Set<Tuple<T>> existeTuple) {
		for (int p = 0; p < gc_0.size(); p++) {
			Tuple<T> slcTuple = gc_0.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, dt), o);
			if (!existeTuple.contains(slcTuple)) {
				SLTransition<T> slt = new SLTransition<>(slcTuple, slcTuple);
				sltList.add(slt);
				existeTuple.add(slcTuple);
//				System.out.println(gc_0.size());
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
}
