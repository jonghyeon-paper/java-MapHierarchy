package hierarchy.test;

import java.util.List;
import java.util.Map;

public class Print {
	private static final String SPACE = "    ";
	@SuppressWarnings("unchecked")
	public static void print(List<Map<String, Object>> itemList, int level) {
		String indentation = "";
		for (int i = 0; i < level; i++) {
			indentation += SPACE;
		}
		for (Map<String, Object> item : itemList) {
			System.out.println(indentation + item.toString());
			if (item.containsKey("slave")) {
				Print.print((List<Map<String, Object>>) item.get("slave"), level + 1);
			}
		}
	}
}
