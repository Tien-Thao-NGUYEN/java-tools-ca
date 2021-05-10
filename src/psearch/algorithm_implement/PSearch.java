package psearch.algorithm_implement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import psearch.objects.IndexLinker;

public class PSearch {
	public static boolean isSimul(Candidate cand, List<IndexLinker> indexLinkerList) {
		Map<List<Integer>, Integer> rule = new HashMap<>();
		for (IndexLinker link : indexLinkerList) {
			List<Integer> lc = Arrays.stream(link.indexSLC()).mapToObj(index -> cand.getValue(index))
					.collect(Collectors.toList());
			Integer newVal = cand.getValue(link.indexSR());
			Integer oldVal = rule.get(lc);
			if (oldVal == null)
				rule.put(lc, newVal);
			else if (oldVal.intValue() != newVal.intValue())
				return false;
		}
		
		return true;
	}

	public static void handleCandidate(Candidate neighbour, List<IndexLinker> indexLinkerList, List<Candidate> next, Set<Candidate> res) {
		neighbour.setSimul(isSimul(neighbour, indexLinkerList));
		if (neighbour.isSimul())
			if (res.add(neighbour)) {
				next.add(neighbour);
//				System.out.println(res.size());
			}
	}

	public static Set<Candidate> explore(List<IndexLinker> indexLinkerList, Candidate initialCandidate,
			List<Integer> specialStateList, int pertNumber) {
		Set<Candidate> res = new HashSet<>();
		res.add(initialCandidate);

		List<Candidate> current = new ArrayList<>();
		current.add(initialCandidate);

		while (current.size() > 0) {
			List<Candidate> next = new ArrayList<>();
			for (Candidate cand : current) {
				for (Candidate neighbour : cand.getNeighbourList(specialStateList))
					handleCandidate(neighbour, indexLinkerList, next, res);

				if (cand.isSimul()) {
					Candidate pCand = cand.perturbation(pertNumber, specialStateList);
					handleCandidate(pCand, indexLinkerList, next, res);
					if (!pCand.isSimul())
						next.add(pCand);
				}
			}

			current = next;
		}

		return res;
	}
}
