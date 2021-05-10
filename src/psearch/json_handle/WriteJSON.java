package psearch.json_handle;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import psearch.objects.IndexLinker;
import psearch.objects.SLTransition;
import psearch.objects.TransformElement;
import simulator.implement.Location;
import simulator.interfaces.SolutionInfo_Interface;

//called in ConfigPSearchMain
public class WriteJSON {

	@SuppressWarnings("unchecked")
	public static <T> JSONObject getJSONObject(SolutionInfo_Interface<T> info,
			Map<Integer, List<SLTransition<Integer>>> sltTable,
			Map<Integer, List<TransformElement<Integer>>> localMapping, List<IndexLinker> indexLinkerList,
			List<T> initialCandidate, List<Integer> changeableIndexList, List<Location> resultLocationList) {
		JSONObject jsonObject = new JSONObject();

		/** info */
		jsonObject.put(JSONKey.A, info.activeState());
		jsonObject.put(JSONKey.Q, info.quiescenceState());
		jsonObject.put(JSONKey.S, info.specialState());
		jsonObject.put(JSONKey.O, info.spaceOutState());

		/** sltTable **/
		JSONObject sltTableJO = new JSONObject();
		sltTable.forEach((k, v) -> {
			JSONArray vArr = new JSONArray();
			v.forEach(e -> vArr.add(e.toString()));
			sltTableJO.put(k.toString(), vArr);
		});
		jsonObject.put(JSONKey.SLT_TABLE, sltTableJO);

		/** local mapping **/
		JSONObject lMappingJO = new JSONObject();
		localMapping.forEach((k, v) -> {
			JSONArray vArr = new JSONArray();
			v.forEach(e -> vArr.add(e.toString()));
			lMappingJO.put(k.toString(), vArr);
		});
		jsonObject.put(JSONKey.LOCAL_MAPPING, lMappingJO);

		/** index linker **/
		JSONArray indexLinkerJA = new JSONArray();
		indexLinkerList.forEach(e -> {
			indexLinkerJA.add(e.toString());
		});
		jsonObject.put(JSONKey.INDEX_lINKER_LIST, indexLinkerJA);

		/** initial candidate **/
		JSONArray initCandidateJA = new JSONArray();
		initialCandidate.forEach(e -> {
			initCandidateJA.add(e.toString());
		});
		jsonObject.put(JSONKey.INITIAL_CANDIDATE, initCandidateJA);

		/** changeable index list **/
		JSONArray changeableIndexJA = new JSONArray();
		changeableIndexList.forEach(e -> {
			changeableIndexJA.add(e.toString());
		});
		jsonObject.put(JSONKey.CHANGEABLE_INDEX_LIST, changeableIndexJA);
		
		/** result localtion list **/
		JSONArray resultLocationJA = new JSONArray();
		resultLocationList.forEach(e -> {
			resultLocationJA.add(e.toString());
		});
		jsonObject.put(JSONKey.RESULT_LOCATION_LIST, resultLocationJA);

		return jsonObject;
	}

	public static void write(JSONObject jsonObject, String file) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(file);
		pw.print(jsonObject.toJSONString());
		pw.close();
	}
}
