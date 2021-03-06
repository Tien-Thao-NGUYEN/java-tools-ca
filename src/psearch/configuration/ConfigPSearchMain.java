package psearch.configuration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import psearch.json_handle.WriteJSON;
import psearch.objects.IndexLinker;
import psearch.objects.SLTransition;
import psearch.objects.TransformElement;
import rule.FSSPRule;
import rule.file_handle.Path;
import rule.file_handle.RuleReader;
import simulator.interfaces.SolutionInfo_Interface;

public class ConfigPSearchMain {

	public static void analyzingConfigDT(int sol, int dt_max) throws IOException {
		FSSPRule rule = RuleReader.fsspRuleFromTextFile(Path.getOneOf718SolutionsFilter(sol), " ");
		//SolutionInfo_Interface<Integer> info = rule.getSolutionInfo();
		
		int endSize = 300;
		for (int dt = 1; dt <= dt_max; dt++) {
			int beginSize = (dt + 3) / 2;
			beginSize = beginSize < 2 ? 2 : beginSize;
			Map<Integer, List<SLTransition<Integer>>> sltTable = SLTTableControler.getSLTTable(rule, beginSize, endSize, dt);
			System.out.println("dt = " + dt + " up to size = " + endSize + " has " + sltTable.get(dt + 1).size() + " elements.");
		}
	}
	
	public static void main(String[] args) throws IOException {
		//analyzingConfigDT(634, 10);
		analyzingConfigDT(587, 10);
		/*
		int sol = 718;
		FSSPRule rule = RuleReader.fsspRuleFromTextFile(Path.getOneOf718SolutionsFilter(sol), " ");
		int delta_t = 1;
		
		int beginSize = 2;
		int endSize = 300;
		SolutionInfo_Interface<Integer> info = rule.getSolutionInfo();

		System.out.println("****** build slt table and result location list******");
		Map<Integer, List<SLTransition<Integer>>> sltTable = SLTTableControler.getSLTTable(rule, beginSize, endSize, delta_t);
//		sltTable.forEach((k, v) -> {
//			System.out.println(k + " ---");
//			v.forEach(e -> {
//				System.out.print(e + " ");
//				System.out.println(e.slc().decompose(info.nCellLeft(), info.nCellRight(), k - 1));
//			});
//		});

		System.out.println("****** build local mapping ******");
		Map<Integer, List<TransformElement<Integer>>> localMapping = LMappingControler.getLocalMapping(rule, beginSize, endSize, delta_t);
//		localMapping.forEach((k, v) -> {
//			System.out.println("------ " + k);
//			v.forEach(e -> {
//				System.out.println(e + " (" + e.tuple().middleState(info.nCellLeft(), k) + ")");
//			});
//		});

		System.out.println("****** build merge list ******");
		List<TransformElement<Integer>> mergeList = LMappingControler.mergeLocalMapping(localMapping);
//		mergeList.forEach(e -> {
//			System.out.println(e.toString());
//		});

		System.out.println("****** build index list for change ******");
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
				changeableIndexList, SLTTableControler.resultLocationList);
		WriteJSON.write(jsonObject, Path.getOneOf718SolutionsJSON(sol, delta_t));
		*/
	}

}
