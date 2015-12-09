package ListAllDB;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphTreeIndex {
	private HashMap<String,ArrayList<String>> indexTree;
	private GraphTreeListIndex gTreeIndex;
	public GraphTreeIndex() {
		
	}
	
	public void setNewValue(HashMap<String,ArrayList<String>> hm, String namaGraph) {
		if(this.indexTree == null) {
			init(hm,namaGraph);
		}else {
			union(hm,namaGraph);
		}
	}
	
	public void init(HashMap<String,ArrayList<String>> hm, String namaGraph) {
		this.indexTree = new HashMap<String,ArrayList<String>>();
		this.gTreeIndex = new GraphTreeListIndex();
		ArrayList<String> temp = new ArrayList<>();
		ArrayList<String> temp1 = new ArrayList<>();
		ArrayList<String> temp2 = new ArrayList<>();
		temp.add(namaGraph);
		temp1.add("root");
		temp2.add("root0");
		this.indexTree.put("null",temp1);
		this.gTreeIndex.putData(hm, temp1.get(0));
		this.indexTree.put("root", temp2);
		this.gTreeIndex.putData(hm, temp2.get(0));
		this.indexTree.put("root0", temp);
		this.gTreeIndex.putData(hm, temp.get(0));
	}
	
	public void union(HashMap<String,ArrayList<String>> hm, String namaGraph) {
		ArrayList<String> root = new ArrayList<>();
		root = hm.get("null");
		String rootVal = root.get(0);
		ArrayList<String> level1 = new ArrayList<>();
		level1 = hm.get(rootVal);
		for(int i = 0; i < level1.size(); i++) {
			
		}
	}
}
