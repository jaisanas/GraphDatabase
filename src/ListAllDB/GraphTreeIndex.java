package ListAllDB;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphTreeIndex {
	private HashMap<String,ArrayList<String>> indexTree;
	
	public GraphTreeIndex() {
		this.indexTree = new HashMap<>();
	}
	
	public void setNewValue(HashMap<String,ArrayList<String>> hm, String namaGraph) {
		if(this.indexTree == null) {
			init(hm,namaGraph);
		}else {
			
		}
	}
	
	public HashMap<String, ArrayList<String>> getIndexTree() {
		return indexTree;
	}

	public void setIndexTree(HashMap<String, ArrayList<String>> indexTree) {
		this.indexTree = indexTree;
	}

	public void init(HashMap<String,ArrayList<String>> hm, String namaGraph) {
		this.indexTree = new HashMap<String,ArrayList<String>>();
		ArrayList<String> temp = new ArrayList<>();
		ArrayList<String> temp1 = new ArrayList<>();
		ArrayList<String> temp2 = new ArrayList<>();
		temp.add("root_0");
		temp1.add("root_1");
		temp2.add(namaGraph);
		this.indexTree.put("null",temp);
		this.indexTree.put("root_0", temp1);
		this.indexTree.put("root_1", temp2);
	}
	
	public void init(String namaGraph) {
		this.indexTree = new HashMap<String,ArrayList<String>>();
		ArrayList<String> temp = new ArrayList<>();
		ArrayList<String> temp1 = new ArrayList<>();
		ArrayList<String> temp2 = new ArrayList<>();
		temp.add("root_0");
		temp1.add("root_1");
		temp2.add(namaGraph);
		this.indexTree.put("null",temp);
		this.indexTree.put("root_0", temp1);
		this.indexTree.put("root_1", temp2);
		System.out.println("ukuran this.indexTree "+this.indexTree.size());
	}
}
