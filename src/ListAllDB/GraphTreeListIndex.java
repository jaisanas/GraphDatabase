package ListAllDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class GraphTreeListIndex {
	private HashMap<String,GraphData> gTreeIndex;
	
	public GraphTreeListIndex(HashMap<String, ArrayList<String>> hm, String namaGraph) {
		this.gTreeIndex = new HashMap<String,GraphData>();
		GraphData gd = new GraphData(hm);
		this.gTreeIndex.put(namaGraph,gd);
	}
	
	public GraphTreeListIndex() {
		
	}

	public HashMap<String, GraphData> getgTreeIndex() {
		return gTreeIndex;
	}

	public void setgTreeIndex(HashMap<String, GraphData> gTreeIndex) {
		this.gTreeIndex = gTreeIndex;
	}
	
	public void putData(HashMap<String, ArrayList<String>> hm, String namaGraph) {
		this.gTreeIndex = new HashMap<String,GraphData>();
		GraphData gd = new GraphData(hm);
		this.gTreeIndex.put(namaGraph,gd);
	}
	
	public void union(HashMap<String, ArrayList<String>> hm, String namaNode) {
		GraphData x = this.gTreeIndex.get(namaNode);
		HashMap<String, ArrayList<String>> y = x.getGraph();
		for (Entry<String, ArrayList<String>> ee : hm.entrySet()) {
			String key = ee.getKey();
			
		}
	}
}
