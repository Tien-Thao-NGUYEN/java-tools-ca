package rule_file_handle;

public class Path {
	public static final String DATA = "D:\\data\\";
	
	
	public static final String DATA_FSSP = DATA + "FSSP\\";
	
	public static final String DATA_FSSP_ORIGIN = DATA_FSSP + "solutions_origin\\";
	public static final String DATA_FSSP_ORIGIN_2N = DATA_FSSP_ORIGIN + "2n\\";
	public static String getOneOf718SolutionsOrigin(int solutionNumber) {
		return DATA_FSSP_ORIGIN_2N + "718\\" + solutionNumber + ".txt";
	}
	
	public static final String DATA_FSSP_FILTER = DATA_FSSP + "solutions_filter\\";
	public static final String DATA_FSSP_FILTER_2N = DATA_FSSP_FILTER + "2n\\";
	public static final String DATA_FSSP_FILTER_2N_BALZER_8_165 = DATA_FSSP_FILTER_2N + "balzer_8_165.txt";
	public static final String DATA_FSSP_FILTER_2N_NOGUCHI_8_119 = DATA_FSSP_FILTER_2N + "noguchi_8_119.txt";
	public static final String DATA_FSSP_FILTER_2N_NOGUCHI_8_134 = DATA_FSSP_FILTER_2N + "noguchi_8_134.txt";
	public static final String DATA_FSSP_FILTER_2N_NOGUCHI_9_141 = DATA_FSSP_FILTER_2N + "noguchi_9_141.txt";
	public static final String DATA_FSSP_FILTER_2N_MY_LVLMOD2_15_329 = DATA_FSSP_FILTER_2N + "my_lvlmod2_15_329.txt";
	public static final String DATA_FSSP_FILTER_2N_MY_ORIGIN_21_486= DATA_FSSP_FILTER_2N + "my_origin_21_486.txt";
	public static String getOneOf718SolutionsFilter(int solutionNumber) {
		return DATA_FSSP_FILTER_2N + "718\\" + solutionNumber + ".txt";
	}
	
	public static final String DATA_FSSP_JSON = DATA_FSSP + "json\\";
	public static final String DATA_FSSP_JSON_2N = DATA_FSSP_JSON + "2n\\";
	public static String getOneOf718SolutionsJSON(int solutionNumber, int delta_t) {
		return DATA_FSSP_JSON_2N + "718\\" + solutionNumber + "_" + delta_t + ".json";
	}
	
	public static final String DATA_GSNC = DATA + "GSNC\\";
}
