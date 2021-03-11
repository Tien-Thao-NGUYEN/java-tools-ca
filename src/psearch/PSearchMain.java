package psearch;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import json_psearch_handle.ReadJSON;
import objects_psearch.IndexLinker;
import rule_file_handle.Path;

public class PSearchMain {

	//c'est avec dt varié
	public static void main(String[] args) throws IOException, ParseException {
		int sol = 3;
		int pertNumber = 0;
		int delta_t = 1;
		Reader reader = new FileReader(Path.getOneOf718SolutionsJSON(sol, delta_t));
		JSONParser jparser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jparser.parse(reader);
		List<Integer> changeableIndexList =  ReadJSON.getChangeableIndexList(jsonObject);
		List<Integer> initialVector = ReadJSON.getInitialVector(jsonObject);
		List<Integer> specialStateList = ReadJSON.getSpecialStateList(jsonObject);
		List<IndexLinker> indexLinkerList = ReadJSON.getIndexLinkerList(jsonObject);
		
		Candidate.changeableIndexList = changeableIndexList;
		Candidate initialCandidate = new Candidate(initialVector, specialStateList);
		Set<Candidate> res = PSearch.explore(indexLinkerList, initialCandidate, specialStateList, pertNumber);
		System.out.println(res.size());
//		res.forEach(System.out::println);
		
//		changeableIndexList.forEach(System.out::println);
//		System.out.println("---------");
//		initialVector.forEach(System.out::println);
//		System.out.println("---------");
//		specialStateList.forEach(System.out::println);
//		System.out.println("---------");
//		indexLinkerList.forEach(System.out::println);
		
//		List<Integer> spec = List.of(1, 0, 5, 6);
//		List<Integer> vec = List.of(4, 2, 3, 4, 1, 0, 5, 4, 5, 3, 2, 6);
//		List<Integer> app = vec.stream().filter(e -> !spec.contains(e)).distinct().collect(Collectors.toList()); 
//		System.out.println(app);
//		System.out.println(app.stream().sorted().collect(Collectors.toList()));
	}

}
