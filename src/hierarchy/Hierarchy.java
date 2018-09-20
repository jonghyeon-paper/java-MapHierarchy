package hierarchy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hierarchy {
	
	public static enum Sort {
		ASCENDING(1),
		DESCENDING(2);
		
		private int sort;
		
		private Sort(int sort) {
			this.sort = sort;
		}
		
		public int getValue() {
			return this.sort;
		}
	}
	
	private String keyId;
	private String masterKeyId;
	private String sortKeyId;
	private Sort sortDirection;
	
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	
	public void setMasterKeyId(String masterKeyId) {
		this.masterKeyId = masterKeyId;
	}
	
	public void setSort(String sortKeyId, Sort sortDirection) {
		this.sortKeyId = sortKeyId;
		this.sortDirection = sortDirection;
	}
	
	@SuppressWarnings("unchecked")
	public List<? extends Map<String, Object>> convert(List<? extends Map<String, Object>> itemList) {
		Map<Object, Map<String, Object>> itemMap = new HashMap<>();
		for (Map<String, Object> item :  itemList) {
			itemMap.put(item.get(keyId), item);
		}
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (Map<String, Object> item :  itemList) {
			if (item.get(masterKeyId) == null) {
				resultList.add(item);
				continue;
			}
			Object itemMasterKeyId = item.get(masterKeyId);
			Map<String, Object> masterMap = itemMap.get(itemMasterKeyId);
			if (masterMap == null) {
				throw new RuntimeException("Master Object is null. Master keyId : " + itemMasterKeyId);
			}
			List<Map<String, Object>> slaveList = null;
			if (masterMap.containsKey("slave")) {
				slaveList = (List<Map<String, Object>>) masterMap.get("slave");
			} else {
				slaveList = new ArrayList<>();
				masterMap.put("slave", slaveList);
			}
			slaveList.add(item);
		}
		if (sortKeyId != null) {
			this.sort(resultList);
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	private void sort(List<Map<String, Object>> itemList) {
		for (int i = 0; i < itemList.size() - 1; i++) {
			for (int j = 0; j < itemList.size() - 1 - i; j++) {
				Map<String, Object> foreItem = itemList.get(j);
				Map<String, Object> rearItem = itemList.get(j + 1);
				if (sortDirection == Sort.ASCENDING) {
					if (Number.class.isAssignableFrom(foreItem.get(sortKeyId).getClass())) {
						Double foreValue = Double.valueOf(String.valueOf(foreItem.get(sortKeyId)));
						Double rearValue = Double.valueOf(String.valueOf(rearItem.get(sortKeyId)));
						if (foreValue > rearValue) {
							itemList.set(j, rearItem);
							itemList.set(j + 1, foreItem);
						}
					} else if (String.class.isAssignableFrom(foreItem.get(sortKeyId).getClass())) {
						String foreValue = String.valueOf(foreItem.get(sortKeyId));
						String rearValue = String.valueOf(rearItem.get(sortKeyId));
						if (foreValue.compareTo(rearValue) == 1) {
							itemList.set(j, rearItem);
							itemList.set(j + 1, foreItem);
						}
					}
				} else if (sortDirection == Sort.DESCENDING) {
					if (Number.class.isAssignableFrom(foreItem.get(sortKeyId).getClass())) {
						Double foreValue = Double.valueOf(String.valueOf(foreItem.get(sortKeyId)));
						Double rearValue = Double.valueOf(String.valueOf(rearItem.get(sortKeyId)));
						if (rearValue > foreValue) {
							itemList.set(j, rearItem);
							itemList.set(j + 1, foreItem);
						}
					} else if (String.class.isAssignableFrom(foreItem.get(sortKeyId).getClass())) {
						String foreValue = String.valueOf(foreItem.get(sortKeyId));
						String rearValue = String.valueOf(rearItem.get(sortKeyId));
						if (rearValue.compareTo(foreValue) == 1) {
							itemList.set(j, rearItem);
							itemList.set(j + 1, foreItem);
						}
					}
				}
			}
			if (itemList.get(itemList.size() - 1 - i).containsKey("slave")) {
				this.sort((List<Map<String, Object>>) itemList.get(itemList.size() - 1 - i).get("slave"));
			}
		}
	}
}