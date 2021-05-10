package psearch.json_handle;


import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import psearch.objects.IndexLinker;

//called in PSearchMain
public class ReadJSON {
	
	public static List<Integer> getChangeableIndexList(JSONObject jsonObject) {
		JSONArray jarr = (JSONArray) jsonObject.get(JSONKey.CHANGEABLE_INDEX_LIST);
		List<Integer> changeableIndex = new ArrayList<>();
		for (int i = 0; i < jarr.size(); i++)
			changeableIndex.add(Integer.valueOf((String) jarr.get(i)));
		
		return changeableIndex;
	}
	
	public static List<Integer> getInitialVector(JSONObject jsonObject) {
		JSONArray jarr = (JSONArray) jsonObject.get(JSONKey.INITIAL_CANDIDATE);
		List<Integer> vector = new ArrayList<>();
		for (int i = 0; i < jarr.size(); i++)
			vector.add(Integer.valueOf((String) jarr.get(i)));
		
		return vector;
	}
	
	public static List<IndexLinker> getIndexLinkerList(JSONObject jsonObject) {
		JSONArray jarr = (JSONArray) jsonObject.get(JSONKey.INDEX_lINKER_LIST);
		List<IndexLinker> list = new ArrayList<>();
		for(int i = 0; i < jarr.size(); i++) {
			String[] data = ((String) jarr.get(i)).split(" ");
			int[] slc = new int[] {Integer.valueOf(data[0]), Integer.valueOf(data[1]), Integer.valueOf(data[2])};
			int sr = Integer.valueOf(data[3]);
			list.add(new IndexLinker(slc, sr));
		}
		
		return list;
	}
	
	public static List<Integer> getSpecialStateList(JSONObject jsonObject) {
		Integer a = ((Long) jsonObject.get(JSONKey.A)).intValue();
		Integer q = ((Long) jsonObject.get(JSONKey.Q)).intValue();
		Integer s = ((Long) jsonObject.get(JSONKey.S)).intValue();
		Integer o = ((Long) jsonObject.get(JSONKey.O)).intValue();
		return List.of(a, q, s, o);
	}
}
