package hierarchy.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hierarchy.Hierarchy;

public class Test5 {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String keyId = "id";
		String masterKeyId = "masterId";
		
		Map<String, Object> sample1 = new HashMap<>();
		sample1.put(keyId, 1);
		sample1.put(masterKeyId, null);
		sample1.put("value", "apple");
		
		Map<String, Object> sample2 = new HashMap<>();
		sample2.put(keyId, 2);
		sample2.put(masterKeyId, 3);
		sample2.put("value", "banana");
		
		Map<String, Object> sample3 = new HashMap<>();
		sample3.put(keyId, 3);
		sample3.put(masterKeyId, null);
		sample3.put("value", "peach");
		
		Map<String, Object> sample4 = new HashMap<>();
		sample4.put(keyId, 4);
		sample4.put(masterKeyId, 2);
		sample4.put("value", "strawberry");
		
		Map<String, Object> sample5 = new HashMap<>();
		sample5.put(keyId, 5);
		sample5.put(masterKeyId, null);
		sample5.put("value", "mellon");
		
		Map<String, Object> sample6 = new HashMap<>();
		sample6.put(keyId, 6);
		sample6.put(masterKeyId, 3);
		sample6.put("value", "mellon");
		
		List<Map<String, Object>> sampleList = new ArrayList<>();
		sampleList.add(sample6);
		sampleList.add(sample5);
		sampleList.add(sample4);
		sampleList.add(sample3);
		sampleList.add(sample2);
		sampleList.add(sample1);
		
		Hierarchy test1 = new Hierarchy();
		test1.setKeyId(keyId);
		test1.setMasterKeyId(masterKeyId);
		test1.setSort(keyId, Hierarchy.Sort.DESCENDING);
		Print.print((List<Map<String, Object>>) test1.convert(sampleList), 0);
	}
}
