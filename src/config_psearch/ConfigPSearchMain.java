package config_psearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import org.json.simple.JSONObject;

import json_psearch_handle.WriteJSON;
import objects_psearch.IndexLinker;
import objects_psearch.SLTransition;
import objects_psearch.TransformElement;
import objects_simulator.Simulator;
import objects_simulator.Tuple;
import rule.FSSPRule;
import rule_file_handle.Path;
import rule_file_handle.RuleReader;
import simulator_interface.Diagram_Interface;
import simulator_interface.Simulator_Interface;
import simulator_interface.SolutionInfo_Interface;

public class ConfigPSearchMain {

	public static void main(String[] args) throws IOException {
		int sol = 3;
		int beginSize = 2;
		int endSize = 500;
		int delta_t = 1;
		FSSPRule rule = RuleReader.fsspRuleFromTextFile(Path.getOneOf718SolutionsFilter(sol));
		SolutionInfo_Interface<Integer> info = rule.getSolutionInfo();

		// TODO sltTable
		System.out.println("****** build slt table ******");
		Map<Integer, List<SLTransition<Integer>>> sltTable = getSLTTable(rule, beginSize, endSize, delta_t);
//		sltTable.forEach((k, v) -> {
//			System.out.println(k + " ---");
//			v.forEach(e -> {
//				System.out.print(e + " ");
//				System.out.println(e.slc().decompose(info.nCellLeft(), info.nCellRight(), k - 1));
//			});
//		});

		// TODO localMapping
		System.out.println("****** build local mapping ******");
		Map<Integer, List<TransformElement<Integer>>> localMapping = getLocalMapping(rule, beginSize, endSize, delta_t);
//		localMapping.forEach((k, v) -> {
//			System.out.println("------ " + k);
//			v.forEach(e -> {
//				System.out.println(e + " (" + e.tuple().middleState(info.nCellLeft(), k) + ")");
//			});
//		});

		System.out.println("****** build merge list ******");
		// TODO mergeList
		List<TransformElement<Integer>> mergeList = LMappingControler.mergeLocalMapping(localMapping);
//		mergeList.forEach(e -> {
//			System.out.println(e.toString());
//		});

		System.out.println("****** build index list for change ******");
		// TODO indexList
		List<Integer> changeableIndexList = LMappingControler.indexListForChange(mergeList, info.quiescenceState(), info.specialState(),
				info.spaceOutState());
//		changeableIndexList.forEach(System.out::println);

		System.out.println("****** build initial candidate ******");
		// TODO initialCandidate
		List<Integer> initialCandidate = LMappingControler.getInitialCandidate(mergeList);
//		initialCandidate.forEach(System.out::println);

		System.out.println("****** build index linker list ******");
		// TODO indexLinkerList
		List<IndexLinker> indexLinkerList = IndexLinkerControler.linking(mergeList, sltTable, info.nCellLeft(),
				info.nCellRight(), delta_t, info.spaceOutState());
//		indexLinkerList.forEach(System.out::println);

		JSONObject jsonObject = WriteJSON.getJSONObject(info, sltTable, localMapping, indexLinkerList, initialCandidate,
				changeableIndexList);
		WriteJSON.write(jsonObject, Path.getOneOf718SolutionsJSON(sol, delta_t));
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

	public static Map<Integer, List<TransformElement<Integer>>> getLocalMapping(FSSPRule rule, int beginSize,
			int endSize, int delta_t) {
		int nCellLeft = rule.getSolutionInfo().nCellLeft();
		int nCellRight = rule.getSolutionInfo().nCellRight();
		Integer o = rule.getSolutionInfo().spaceOutState();

		Map<Integer, List<TransformElement<Integer>>> localMapping = new HashMap<>();
		IntStream.rangeClosed(0, delta_t).forEach(h -> {
			localMapping.put(h, new ArrayList<>());
		});

		Set<Tuple<Integer>> existeTuple = new HashSet<>();

		Simulator_Interface<Integer> sim = new Simulator<>(rule);
		for (int size = beginSize; size <= endSize; size++) {
			Diagram_Interface<Integer> dgm = sim.getDiagram(rule.getGC0(size));
			LMappingControler.extractHMapFromDgm(dgm, nCellLeft, nCellRight, delta_t, o, localMapping, existeTuple);
		}

		localMapping.forEach((k, v) -> {
			TransformElement<Integer> te = new TransformElement<>(Tuple.getOTuple(nCellLeft, nCellRight, k, o), o);
			v.add(te);
		});

		return localMapping;
	}
}
