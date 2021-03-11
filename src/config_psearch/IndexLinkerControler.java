package config_psearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import objects_psearch.IndexLinker;
import objects_psearch.SLTransition;
import objects_psearch.TransformElement;
import objects_simulator.Tuple;
import simulator_interface.Tuple_Interface;

public class IndexLinkerControler {
	public static <T> List<IndexLinker> linking(List<TransformElement<T>> localMapping,
			Map<Integer, List<SLTransition<T>>> sltTable, int nCellLeft, int nCellRight, int delta_t, T o) {
		List<Tuple_Interface<T>> lmDomain = localMapping.stream().map(e -> e.tuple()).collect(Collectors.toList());
//		System.out.println(lmDomain);//TODO
		List<IndexLinker> indexLinkerList = new ArrayList<>();
		for (int dt = 1; dt <= delta_t + 1; dt++) {
//			System.out.println(dt);//TODO
			final int fdt = dt;
			final int decompose_dt = fdt - 1;
			List<SLTransition<T>> sltDtList = sltTable.get(fdt);
//			System.out.println(sltDtList);//TODO
			sltDtList.forEach(e -> {
				List<Tuple_Interface<T>> decomposeSLC = e.slc().decompose(nCellLeft, nCellRight, decompose_dt);
				int[] indexSLC = decomposeSLC.stream().mapToInt(tuple -> {
					if (o.equals(tuple.middleState(nCellLeft, decompose_dt))) {
						Tuple_Interface<T> oTuple = Tuple.getOTuple(nCellLeft, nCellRight, decompose_dt, o);
						return lmDomain.indexOf(oTuple);
					}

					return lmDomain.indexOf(tuple);
				}).toArray();
				int indexSR = lmDomain.indexOf(e.sr());
				indexLinkerList.add(new IndexLinker(indexSLC, indexSR));
			});
		}

		return indexLinkerList;
	}
}
