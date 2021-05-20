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

public class Operations {

	// créer un local mapping entre deux diagramme espace-temps
	public static List<List<List<String>>> localMapping(Diagram_Interface<String> dgmSrc,
			Diagram_Interface<String> dgmDst) {
		int nCellLeft = dgmSrc.getSolutionInfo().nCellLeft();
		int nCellRight = dgmSrc.getSolutionInfo().nCellRight();

		List<List<List<String>>> localMapping = new ArrayList<>();
		List<List<String>> dt0List = new ArrayList<>();

		GConfig_Interface<String> gcSrc0 = dgmSrc.getGConfig(0);
		GConfig_Interface<String> gcDst0 = dgmDst.getGConfig(0);
		Set<String> existState = new HashSet<>();
		for (int p = 0; p < gcSrc0.size(); p++) {
			String stateSrc = gcSrc0.state(p, dgmSrc.spaceOutState());
			if (existState.add(stateSrc)) {
				List<String> element = new ArrayList<>();
				element.add(stateSrc);
				element.add("=>");
				element.add(gcDst0.state(p, dgmDst.spaceOutState()));
				dt0List.add(element);
			}
		}
		localMapping.add(dt0List);

		List<List<String>> dt1List = new ArrayList<>();
		Set<List<String>> existeLConfig = new HashSet<>();
		for (int t = 0; t < dgmSrc.timeFin(); t++) {
			GConfig_Interface<String> gcSrc_t = dgmSrc.getGConfig(t);
			GConfig_Interface<String> gcDst_t1 = dgmDst.getGConfig(t + 1);
			for (int p = 0; p < gcSrc_t.size(); p++) {
				List<String> lc = gcSrc_t.lConfig(LConfig.getSupport(p, nCellLeft, nCellRight), dgmDst.spaceOutState())
						.getListState();
				if (existeLConfig.add(lc)) {
					List<String> element = new ArrayList<>();
					element.addAll(lc);
					element.add("=>");
					element.add(gcDst_t1.state(p, dgmDst.spaceOutState()));
					dt1List.add(element);
				}
			}
		}
		localMapping.add(dt1List);

		return localMapping;
	}

	// créer un super local transition table à partir d'un diagramme espace-temps
	public static List<List<List<String>>> sltTable(Diagram_Interface<String> dgm) {
		int nCellLeft = dgm.getSolutionInfo().nCellLeft();
		int nCellRight = dgm.getSolutionInfo().nCellRight();
		String outState = dgm.spaceOutState();

		List<List<List<String>>> localMapping = new ArrayList<>();
		List<List<String>> dt0List = new ArrayList<>();

		GConfig_Interface<String> gc0 = dgm.getGConfig(0);
		Set<List<String>> existState = new HashSet<>();
		for (int p = 0; p < gc0.size(); p++) {
			List<String> tuple = gc0.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, 0), outState).tuple();
			if (existState.add(tuple)) {
				dt0List.add(tuple);
			}
		}
		localMapping.add(dt0List);

		List<List<String>> dt1List = new ArrayList<>();
		Set<List<String>> existeLConfig = new HashSet<>();
		for (int t = 0; t < dgm.timeFin(); t++) {
			GConfig_Interface<String> gc_t = dgm.getGConfig(t);
			GConfig_Interface<String> gc_t1 = dgm.getGConfig(t + 1);
			for (int p = 0; p < gc_t.size(); p++) {
				List<String> lc = gc_t.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, 2), outState).tuple();
				if (existeLConfig.add(lc)) {
					List<String> element = new ArrayList<>();
					element.addAll(lc);
					element.add("=>");
					element.addAll(gc_t1.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, 1), outState).tuple());
					dt1List.add(element);
				}
			}
		}
		localMapping.add(dt1List);

		return localMapping;
	}

	// extraire les transitions locales à partir d'un diagramme espace-temps
	public static List<List<String>> localTransitionTable(Diagram_Interface<String> dgm) {
		int nCellLeft = dgm.getSolutionInfo().nCellLeft();
		int nCellRight = dgm.getSolutionInfo().nCellRight();
		
		List<List<String>> ltList = new ArrayList<>();
		Set<List<String>> existed = new HashSet<>();

		for (int t = 0; t < dgm.timeFin(); t++) {
			GConfig_Interface<String> gc_t = dgm.getGConfig(t);
			GConfig_Interface<String> gc_t1 = dgm.getGConfig(t + 1);
			for (int p = 0; p < gc_t.size(); p++) {
				List<String> lc = gc_t.lConfig(LConfig.getSupport(p, nCellLeft, nCellRight), dgm.spaceOutState()).getListState();
				if (existed.add(lc)) {
					List<String> lt = new ArrayList<>();
					lt.addAll(lc);
					lt.add("=>");
					lt.add(gc_t1.state(p, dgm.spaceOutState()));
					ltList.add(lt);
				}
			}
		}

		return ltList;
	}

	// récupérer une liste de locations d'un motif donné dans un diagram
	public static List<Location> getMotifLocationList(Diagram_Interface<String> dgm, List<String> motif, int beginTime,
			int endTime, int nCellLeft, int nCellRight) {
		List<Location> locList = new ArrayList<>();
		for (int t = beginTime; t <= endTime; t++) {
			GConfig_Interface<String> gc = dgm.getGConfig(t);
			for (int p = 0; p < gc.size(); p++) {
				List<String> tuple = gc.tuple(Tuple.getSupport(p, nCellLeft, nCellRight, 0), dgm.spaceOutState()).tuple();
				if (tuple.equals(motif))
					locList.add(new Location(gc.size(), t, p));
			}
		}

		return locList;
	}

	// remplacer le résultat 
	// d'une configuration locale 
	// dans un diagramme espace-temps
	public static Diagram_Interface<String> replaceLCResultInDgm(Diagram_Interface<String> dgm, List<String> motif,
			String newState) {
		Diagram_Interface<String> clone = new Diagram<>(dgm);
		List<Location> locList = getMotifLocationList(dgm, motif, 0, dgm.timeFin(), 1, 1);
		for (Location loc : locList)
			clone.getGConfig(loc.getTime() + 1).replace(loc.getPosition(), newState);

		return clone;
	}

	//
	public static List<Integer> indexList(List<List<String>> subDt) {
		List<Integer> indexList = new ArrayList<>();
		boolean take = true;
		for (int currentIndex = 0; currentIndex < subDt.size(); currentIndex++) {
			if (!subDt.get(currentIndex).get(0).equals("empty") && take) {
				indexList.add(currentIndex);
				take = false;
			}

			if (subDt.get(currentIndex).get(0).equals("empty") && !take) {
				indexList.add(currentIndex - 1);
				take = true;
			}
		}

		if (!subDt.get(subDt.size() - 1).get(0).equals("empty") && !take)
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
