package ListAllDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class GraphData {
	HashMap<String,ArrayList<ArrayList<String>>> graph = new HashMap<String,ArrayList<ArrayList<String>>>();

	public HashMap<String, ArrayList<ArrayList<String>>> getGraph() {
		return graph;
	}

	public void setGraph(HashMap<String, ArrayList<ArrayList<String>>> graph) {
		this.graph = graph;
	}
	
	public GraphData(HashMap<String,ArrayList<ArrayList<String>>> hm) {
		for (Entry<String, ArrayList<ArrayList<String>>> ee : hm.entrySet()) {
			   String key = ee.getKey();
			   ArrayList<ArrayList<String>> value = ee.getValue();
			   for(int i = 0; i < value.size(); i++) {
				   ArrayList<String> tempSisi = new ArrayList<>();
				   tempSisi = value.get(i);
				   String [] arraySisi = (String[]) tempSisi.toArray();
				   Arrays.sort(arraySisi);
				   ArrayList<String> isiNilai = new ArrayList<String>(Arrays.asList(arraySisi));
				   value.set(i, isiNilai);
			   }
			   ArrayList<String> tempList = new ArrayList<String>(Arrays.asList(tempEdges));
			   hm.put(key,tempList);
			}
		this.graph = hm;
	}
	
	public void setNewData(HashMap<String,ArrayList<String>> hm) {
		String [] tempHm = new String[hm.size()];
		int i = 0;
		for (Entry<String, ArrayList<String>> ee : hm.entrySet()) {
		   String key = ee.getKey();
		   tempHm[i] = key;
		   ArrayList<String> value = ee.getValue();
		   String [] tempEdges = new String[value.size()];
		   tempEdges = (String[]) value.toArray();
		   Arrays.sort(tempEdges);
		   ArrayList<String> tempList = new ArrayList<String>(Arrays.asList(tempEdges));
		   hm.put(key,tempList);
		   i++;
		}
		Arrays.sort(tempHm);
		this.graph = hm;
	}
}
