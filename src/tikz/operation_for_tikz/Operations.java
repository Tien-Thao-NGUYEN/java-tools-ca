package tikz.operation_for_tikz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import simulator.implement.Diagram;
import simulator.implement.LConfig;
import simulator.implement.Location;
import simulator.implement.Tuple;
import simulator.interfaces.Diagram_Interface;
import simulator.interfaces.GConfig_Interface;
import tikz.tikz_itf_implement.TikzAbstract;

public class Operations {

	// créer un local mapping entre deux diagramme espace-temps
	public static List<List<List<Integer>>> localMapping(Diagram_Interface<Integer> dgmSrc,
			Diagram_Interface<Integer> dgmDst) {
		int nCellLeft = dgmSrc.getSolutionInfo().nCellLeft();
		int nCellRight = dgmSrc.getSolutionInfo().nCellRight();

		List<List<List<Integer>>> localMapping = new ArrayList<>();
		List<List<Integer>> dt0List = new ArrayList<>();

		GConfig_Interface<Integer> gcSrc0 = dgmSrc.getGConfig(0);
		GConfig_Interface<Integer> gcDst0 = dgmDst.getGConfig(0);
		Set<Integer> existState = new HashSet<>();
		for (int p = 0; p < gcSrc0.size(); p++) {
			Integer stateSrc = gcSrc0.state(p, dgmSrc.spaceOutState());
			if (existState.add(stateSrc)) {
				List<Integer> element = new ArrayList<>();
				element.add(stateSrc);
				element.add(TikzAbstract.FAT_ARROW);
				element.add(gcDst0.state(p, dgmDst.spaceOutState()));
				dt0List.add(element);
			}
		}
		localMapping.add(dt0List);

		List<List<Integer>> dt1List = new ArrayList<>();
		Set<List<Integer>> existeLConfig = new HashSet<>();
		for (int t = 0; t < dgmSrc.timeFin(); t++) {
			GConfig_Interface<Integer> gcSrc_t = dgmSrc.getGConfig(t);
			GConfig_Interface<Integer> gcDst_t1 = dgmDst.getGConfig(t + 1);
			for (int p = 0; p < gcSrc_t.size(); p++) {
				List<Integer> lc = gcSrc_t.lConfig(LConfig.getSupport(p, nCellLeft, nCellRight), dgmDst.spaceOutState())
						.getListState();
				if (existeLConfig.add(lc)) {
					List<Integer> element = new ArrayList<>();
					element.addAll(lc);
					element.add(TikzAbstract.FAT_ARROW);
					element.add(gcDst_t1.state(p, dgmDst.spaceOutState()));
					dt1List.add(element);
				}
			}
		}
		localMapping.add(dt1List);

		return localMapping;
	}

	// créer un super local transition table à partir d'un diagramme espace-temps
	public static List<List<List<Integer>>> sltTable(Diagram_Interface<Integer> dgm) {
		int nCellLeft = dgm.getSolutionInfo().nCellLeft();
		int nCellRight = dgm.getSolutionInfo().nCellRight();
		Integer outState = dgm.spaceOutState();

		List<List<List<Integer>>> localMapping = new ArrayList<>();
		List<List<Integer>> dt0List = new ArrayList<>();

		GConfig_Interface<Integer> gc0 = dgm.getGConfig(0);
		Set<List<Integer>> existState = new HashSet<>();
		for (int p = 0; p < gc0.size(); p++) {
			List<Integer> tuple = gc0.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, 0), outState).tuple();
			if (existState.add(tuple)) {
				dt0List.add(tuple);
			}
		}
		localMapping.add(dt0List);

		List<List<Integer>> dt1List = new ArrayList<>();
		Set<List<Integer>> existeLConfig = new HashSet<>();
		for (int t = 0; t < dgm.timeFin(); t++) {
			GConfig_Interface<Integer> gc_t = dgm.getGConfig(t);
			GConfig_Interface<Integer> gc_t1 = dgm.getGConfig(t + 1);
			for (int p = 0; p < gc_t.size(); p++) {
				List<Integer> lc = gc_t.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, 2), outState).tuple();
				if (existeLConfig.add(lc)) {
					List<Integer> element = new ArrayList<>();
					element.addAll(lc);
					element.add(TikzAbstract.FAT_ARROW);
					element.addAll(gc_t1.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, 1), outState).tuple());
					dt1List.add(element);
				}
			}
		}
		localMapping.add(dt1List);

		return localMapping;
	}

	// extraire les transitions locales à partir d'un diagramme espace-temps
	public static List<List<Integer>> localTransitionTable(Diagram_Interface<Integer> dgm) {
		int nCellLeft = dgm.getSolutionInfo().nCellLeft();
		int nCellRight = dgm.getSolutionInfo().nCellRight();
		
		List<List<Integer>> ltList = new ArrayList<>();
		Set<List<Integer>> existed = new HashSet<>();

		for (int t = 0; t < dgm.timeFin(); t++) {
			GConfig_Interface<Integer> gc_t = dgm.getGConfig(t);
			GConfig_Interface<Integer> gc_t1 = dgm.getGConfig(t + 1);
			for (int p = 0; p < gc_t.size(); p++) {
				List<Integer> lc = gc_t.lConfig(LConfig.getSupport(p, nCellLeft, nCellRight), dgm.spaceOutState()).getListState();
				if (existed.add(lc)) {
					List<Integer> lt = new ArrayList<>();
					lt.addAll(lc);
					lt.add(TikzAbstract.FAT_ARROW);
					lt.add(gc_t1.state(p, dgm.spaceOutState()));
					ltList.add(lt);
				}
			}
		}

		return ltList;
	}

	// récupérer une liste de locations d'un motif donné dans un diagram
	public static List<Location> getMotifLocationList(Diagram_Interface<Integer> dgm, List<Integer> motif, int beginTime,
			int endTime, int nCellLeft, int nCellRight) {
		List<Location> locList = new ArrayList<>();
		for (int t = beginTime; t <= endTime; t++) {
			GConfig_Interface<Integer> gc = dgm.getGConfig(t);
			for (int p = 0; p < gc.size(); p++) {
				List<Integer> tuple = gc.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, 0), dgm.spaceOutState()).tuple();
				if (tuple.equals(motif))
					locList.add(new Location(gc.size(), t, p));
			}
		}

		return locList;
	}

	// remplacer le résultat 
	// d'une configuration locale 
	// dans un diagramme espace-temps
	public static Diagram_Interface<Integer> replaceLCResultInDgm(Diagram_Interface<Integer> dgm, List<Integer> motif,
			Integer newState) {
		Diagram_Interface<Integer> clone = new Diagram<>(dgm);
		List<Location> locList = getMotifLocationList(dgm, motif, 0, dgm.timeFin(), 1, 1);
		for (Location loc : locList)
			clone.getGConfig(loc.getTime() + 1).replace(loc.getPosition(), newState);

		return clone;
	}

	//
	public static List<Integer> indexList(List<List<Integer>> subDt) {
		List<Integer> indexList = new ArrayList<>();
		boolean take = true;
		for (int currentIndex = 0; currentIndex < subDt.size(); currentIndex++) {
			if (!subDt.get(currentIndex).get(0).equals(TikzAbstract.EMPTY) && take) {
				indexList.add(currentIndex);
				take = false;
			}

			if (subDt.get(currentIndex).get(0).equals(TikzAbstract.EMPTY) && !take) {
				indexList.add(currentIndex - 1);
				take = true;
			}
		}

		if (!subDt.get(subDt.size() - 1).get(0).equals(TikzAbstract.EMPTY) && !take)
			indexList.add(subDt.size() - 1);

		return indexList;
	}

	// diviser un local mapping en plusieurs parties
	public static <T> List<List<Integer>> getDivLMapping(List<List<T>> lMapping, int nbrPart) {
		List<List<Integer>> divise = new ArrayList<>();
		for (List<T> dt : lMapping)
			divise.add(getDivDt(dt.size(), nbrPart));

		return divise;
	}

	// calculer la taille de chaque partie d'un local mapping
	public static <T> List<Integer> getDivDt(int size, int nbrPart) {
		List<Integer> div = new ArrayList<>();
		int res = size % nbrPart;
		int partSize = size / nbrPart;

		int max = res == 0 ? partSize : partSize + 1;
		int next = 0;
		do {
			div.add(next);
			next += max;
		} while (next < size);

		div.add(size);

		return div;
	}

}
